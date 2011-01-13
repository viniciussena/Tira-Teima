package tirateima.gui.editortexto;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import tirateima.gui.highlighting.CParser;
import tirateima.gui.highlighting.CParserConstants;
import tirateima.gui.highlighting.Token;

class FullLineHighlightPainter implements Highlighter.HighlightPainter {
	Color color;

	public FullLineHighlightPainter(Color c){
		color = c;
	}

	public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
		Rectangle r0 = null, r1 = null;

		try {  // convert positions to pixel coordinates
			r0 = c.modelToView(p0);
			r1 = c.modelToView(p1);
		} catch (BadLocationException ex) { return; }

		if ((r0 == null) || (r1 == null)) return;

		g.setColor(color);

		// special case if p0 and p1 are on the same line
		if (r0.y == r1.y) {
			g.fillRect(0, r0.y, c.getWidth(), r0.height);
		}else{
			g.fillRect(0, r0.y, c.getWidth(), r1.y - r0.y + r1.height);
		}
		return;
	}
}

/**
 * Modela uma caixa de texto com código-fonte em Pascal.
 * 
 * @author Renan e Luciano Santos
 */
@SuppressWarnings("serial")
public class CaixaTexto extends JTextPane implements DocumentListener{
	private boolean analisando = false;
	private boolean mudou = false; 
	private CParser parser = null;
	private InputStream is = null;
	private int fimColuna, fimLinha, iniColuna, iniLinha;

	/* Posição inicial de cada linha. */
	List<Integer> linhas;

	/*Define se o texto conterá higlighting ou não...*/
	private boolean highlighted = true;

	/*Define qual linha deve ser destacada. Se for menor que zero, nenhuma.*/
	private int linha_destacada = -1;

	/*Índices de acesso aos nomes dos estilos...*/
	public static final String[] estilos = {"regular", "palavra_chave",
		"literal", "comentario", "pontuacao", "num", "tipo", "diretivas"};

	/*Cores do highlighting e do destaque.*/
	private Color cores[] = {Color.BLACK, new Color(0, 0, 177)/*Dark Blue*/, new Color(64, 64, 255)/*Light Blue*/,
			new Color(180,180,180),Color.RED, /*new Color(180, 50, 180)*/Color.MAGENTA,
			new Color(0, 0, 177),  new Color(0, 135, 0)/*Verde Escuro*/, new Color(255, 255, 0)};

	/*Fonte básica...*/
	Font fonte_basica = new Font("Courier New", Font.PLAIN, 14);


	/*Constantes para identificar as cores a serem alteradas...*/
	public static final int REGULAR 	  	= 0;
	public static final int PALAVRA_CHAVE 	= 1;
	public static final int LITERAL 		= 2;
	public static final int COMENTARIO 		= 3;
	public static final int PONTUACAO 		= 4;
	public static final int NUMEROS 		= 5;
	public static final int TIPO 			= 6;
	public static final int DIRETIVAS		= 7;
	public static final int DESTAQUE 		= 8;


	/**
	 * Constrói nova CaixaTexto usando o método construtor básico.
	 */
	public CaixaTexto() {
		this("");
	}

	/**
	 * Constrói nova CaixaTexto.
	 * 
	 * @param texto texto inicial da caixa de texto.
	 */
	public CaixaTexto(String texto){
		super();
		addStyles();
		setTabs(this, 4);
		setText(texto);
		getStyledDocument().addDocumentListener(this);
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){}

			public void keyReleased(KeyEvent e){}

			public void keyTyped(KeyEvent e){
				if(mudou){
					parseText();
					mudou = false;
				}
			}
		});
	}

	public void setText(String texto){    	
		try{
			linhas = getLinhas(texto);
		}catch(Exception e){
			linhas = new ArrayList<Integer>();
		}

		parseText(texto);
	}

	public void setText(Reader reader) throws IOException{
		StringBuffer s = new StringBuffer("");
		BufferedReader r = new BufferedReader(reader);
		String linha = null;
		try{
			while((linha = r.readLine()) != null)
				s.append(linha + "\n");
		}catch(IOException e){
			throw e;
		}

		this.setText(s.toString());
	}

	public void setHighlighted(boolean high){
		if(highlighted != high){
			highlighted = high;
			parseText();
		}
	}

	public void setMarcada(int linha){
		if(linha != linha_destacada){
			if(linha > linhas.size()){
				linha_destacada = linhas.size();
			}else{
				linha_destacada = linha;
			}

			try{
				DefaultHighlighter dh = (DefaultHighlighter) this.getHighlighter();
				dh.removeAllHighlights();

				if(linha_destacada > 0){
					int i = linhas.get(linha_destacada - 1);
					dh.addHighlight(i, i, new FullLineHighlightPainter(cores[DESTAQUE]));

				}

				this.update(this.getGraphics());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void setBaseFont(Font f){
		this.fonte_basica = f;
		addStyles();
		parseText();
	}

	/**
	 * 	Muda uma das cores de highlighting/destaque.
	 * 
	 *  Espera uma constante definindo qual cor deve ser mudada.
	 *  
	 * @param const_cor Espera uma das constantes informando qual cor deve ser alterada.
	 * @param c Nova cor.
	 */
	public void setColor(int const_cor, Color c){
		if((const_cor >= REGULAR) && (const_cor <= TIPO)){
			cores[const_cor] = c;
		}else{
			cores[REGULAR] = c;
		}

		addStyles();

		parseText();
	}

	public int getMarcada(){
		return linha_destacada;
	}

	public String adicionaNumeracao(String texto){
		StringBuffer sb = new StringBuffer();
		int carret = 0;
		for(int i=0; i<texto.length(); i++){
			sb.append(i+1 + " ");
			int index = texto.substring(carret).indexOf("\n");
			if(index > -1){
				sb.append(texto.substring(carret, carret + index) + "\n");
				carret += ++index;
			}else{
				break;
			}
		}

		return sb.toString();
	}

	public int getTotalLinhas(){
		StringTokenizer st = new StringTokenizer(getText(), "\n");
		return st.countTokens();
	}

	private void parseText(){
		parseText(null);
	}

	private void parseText(String text){
		String texto = text == null ? getText() : text;
		super.setText("");
		StyledDocument doc = getStyledDocument();
		analisando = true;
		if(highlighted){
			//Anlex anlex = new Anlex(texto);
			//Token t = anlex.getToken();

			if ((!texto.equals("")) && (texto != null)) {
				try {
					is = new ByteArrayInputStream(texto.getBytes("UTF-8"));

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}



				if (parser == null)
					parser = new CParser(is);




				Token t = CParser.getNextToken();

				boolean coment_chave = false;
				boolean coment_par = false;
				String estilo;
				int line = 1;
				boolean insert_line = true;
				while(t.kind != CParserConstants.EOF){
					if (t.kind == CParserConstants.LETTER)
						System.out.println(t.kind + "   "+ t.image);
					estilo = estilos[REGULAR];
					switch(t.kind){

					case CParserConstants.IDENTIFIER: 
						estilo = estilos[REGULAR];
						break;	
						
					case CParserConstants.DIRETIVAS:
						estilo = estilos[DIRETIVAS];
						break;
						
					case CParserConstants.COMENTARIO_BLOCO:
						estilo = estilos[COMENTARIO];
						break;
						
					case CParserConstants.COMENTARIO_LINHA:
						estilo = estilos[COMENTARIO];
						break;
						
					case CParserConstants.INTEGER_LITERAL:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.DECIMAL_LITERAL:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.HEX_LITERAL:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.OCTAL_LITERAL:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.FLOATING_POINT_LITERAL:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.EXPONENT:
						estilo = estilos[NUMEROS];
						break;

					case CParserConstants.CHARACTER_LITERAL:
						estilo = estilos[LITERAL];
						break;

					case CParserConstants.STRING_LITERAL:
						estilo = estilos[LITERAL];
						break;

					case CParserConstants.CONTINUE:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.VOLATILE:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.REGISTER:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.UNSIGNED:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.TYPEDEF:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.DFLT:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.DOUBLE:
						estilo = estilos[TIPO];
						break;

					case CParserConstants.SIZEOF:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.SWITCH:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.RETURN:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.EXTERN:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.STRUCT:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.STATIC:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.SIGNED:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.WHILE:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.BREAK:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.UNION:
						estilo = estilos[PALAVRA_CHAVE];
						break;

					case CParserConstants.CONST:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.FLOAT:
						estilo = estilos[TIPO];
						break;
					case CParserConstants.SHORT:
						estilo = estilos[TIPO];
						break;
					case CParserConstants.ELSE:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.CASE:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.LONG:
						estilo = estilos[TIPO];
						break;
					case CParserConstants.ENUM:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.AUTO:
						estilo = estilos[LITERAL];
						break;
					case CParserConstants.VOID:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.CHAR:
						estilo = estilos[TIPO];
						break;
					case CParserConstants.GOTO:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.FOR:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.INT:
						estilo = estilos[TIPO];
						break;
					case CParserConstants.IF:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.DO:
						estilo = estilos[PALAVRA_CHAVE];
						break;
					case CParserConstants.LETTER:
						estilo = estilos[LITERAL];
						break;
					case CParserConstants.DIGIT:
						estilo = estilos[NUMEROS];
						break;
						
					
					default:
						if ((t.kind >= 53) && (t.kind <= 98)) {
							estilo = estilos[PONTUACAO];
						}
					}


	    		try{
	    			if(insert_line){
	    				insert_line = false;
	    				line++;
	    			}
	    			if("\n".equals(t.image)){
	    				insert_line = true;
	    			}

	    			doc.insertString(doc.getLength(), t.image, doc.getStyle(estilo));
	    		}catch(Exception e){}

	    		
	    			
					t = CParser.getNextToken();


				}
			}
		}else{
			try{
				doc.insertString(0, texto, doc.getStyle(estilos[REGULAR]));
			}catch(Exception e){}
		}
		analisando = false;
	}

	private boolean isNome(int kind) {

		return false;
	}
	
	void setTabs( JTextPane textPane, int charactersPerTab)
	{
		FontMetrics fm = textPane.getFontMetrics( textPane.getFont() );
		int charWidth = fm.charWidth( 'w' );
		int tabWidth = charWidth * charactersPerTab;

		TabStop[] tabs = new TabStop[10];

		for (int j = 0; j < tabs.length; j++)
		{
			int tab = j + 1;
			tabs[j] = new TabStop( tab * tabWidth );
		}

		TabSet tabSet = new TabSet(tabs);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setTabSet(attributes, tabSet);
		int length = textPane.getDocument().getLength();
		textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, false);
	}

	/**
	 * Divide o texto em linhas e retorna a posição de cada uma.
	 * 
	 * @param origem Origem do texto.
	 * @return Posição inicial de cada linha no texto.
	 * 
	 * @throws IOException
	 */
	private List<Integer> getLinhas(String origem) throws IOException{
		List<Integer> lista = new ArrayList<Integer>();

		BufferedReader br = new BufferedReader(new StringReader(origem));
		String linha = null;
		int i = 0;
		while((linha = br.readLine()) != null){
			lista.add(i);
			/* Tamanho da linha + \n */
			i += linha.length() + 1;
		}
		return lista;
	}

	private void addStyles(){
		StyledDocument doc = getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().
		getStyle(StyleContext.DEFAULT_STYLE);

		/*Remove estilos antes de adicionar...*/
		for(int i = 0; i <= DIRETIVAS; i++){
			doc.removeStyle(estilos[i]);
			doc.addStyle(estilos[i], def);
		}

		/*Estilo para texto qualquer...*/
		Style s = doc.getStyle("regular");
		StyleConstants.setFontFamily(def, fonte_basica.getFamily());
		StyleConstants.setFontSize(def, fonte_basica.getSize());

		/*Estilo para palavras chave...*/
		s = doc.getStyle("palavra_chave");
		StyleConstants.setBold(s, true);
		StyleConstants.setForeground(s, cores[PALAVRA_CHAVE]);

		/*Estilo para literais...*/
		s = doc.getStyle("literal");
		StyleConstants.setBold(s, false);
		StyleConstants.setForeground(s, cores[LITERAL]);

		/*Estilo para comentários...*/
		s = doc.getStyle("comentario");
		StyleConstants.setBold(s, false);
		StyleConstants.setItalic(s, true);
		StyleConstants.setForeground(s, cores[COMENTARIO]);

		/*Estilo para pontuação...*/
		s = doc.getStyle("pontuacao");
		StyleConstants.setBold(s, true);
		StyleConstants.setItalic(s, false);
		StyleConstants.setForeground(s, cores[PONTUACAO]);

		/*Estilo para numeração...*/
		s = doc.getStyle("num");
		StyleConstants.setBold(s, false);
		StyleConstants.setItalic(s, false);
		StyleConstants.setForeground(s, cores[NUMEROS]);

		/*Estilo para tipos...*/
		s = doc.getStyle("tipo");
		StyleConstants.setBold(s, true);
		StyleConstants.setItalic(s, false);
		StyleConstants.setForeground(s, cores[TIPO]);
		
		/*Estilo para diretivas...*/
		s = doc.getStyle("diretivas");
		StyleConstants.setBold(s, true);
		StyleConstants.setItalic(s, false);
		StyleConstants.setForeground(s, cores[DIRETIVAS]);
	}

	public void insertUpdate(DocumentEvent e){
		mudou();
	}

	public void removeUpdate(DocumentEvent e){
		mudou();
	}

	public void changedUpdate(DocumentEvent e){}

	private void mudou(){
		if(!analisando){
			mudou = true;
		}
	}
}


