package tirateima.gui.arquivos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import tirateima.IEstado;
import tirateima.gui.variaveis.VarChar;
import tirateima.gui.variaveis.VarInteger;
import tirateima.gui.variaveis.VarReal;


/**
 * O HidhlightPainter para arquivos texto.
 * 
 * @author Luciano Santos
 */
class VarTextHighlightPainter implements Highlighter.HighlightPainter {
	Color color;
	
	public VarTextHighlightPainter(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}
	
    /**
     * Pinta o highlight.
     */
    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
    	if(offs0 != offs1){
	    	try {
	    		offs1 = drawHighlight(g, offs0, offs1, c);
		    	drawMarks(g, offs0, offs1, c);
		    } catch (BadLocationException e) {
		    }
    	}
    }
    
    private int drawHighlight(
    		Graphics g,
    		int offs0, int offs1,
    		JTextComponent c) throws BadLocationException {
    	
    	g.setColor(getColor() == null ? c.getSelectionColor() : getColor());
    	
    	TextUI mapper = c.getUI();
    	int i;
    	Rectangle p0 = null, p1 = null;
    	int newoffs1 = offs1;
    	while (offs0 < offs1) {
    		//vai andando nos offsets até mudar de linha ou acabar
    		p0 = mapper.modelToView(c, offs0);
    		for (i = offs0; i < offs1; i++) {
    			p1 = mapper.modelToView(c, i + 1);
    			
    			if (p0.y != p1.y) //mudou de linha
    				break;
    		}
    		
    		if (i < offs1) {
    			//corrige posição, ignorando marca de final de linha
    			newoffs1 = i - 1;
    			p1 = mapper.modelToView(c, newoffs1);
    		}
    		
    		Rectangle r = p0.union(p1);
		    g.fillRect(r.x, r.y, r.width, r.height);
		    
		    //prepara para a próxima linha
		    offs0 = i + 1;
    	}
    	
    	return newoffs1;
    }
    
    private void drawMarks(
    		Graphics g,
    		int offs0, int offs1,
    		JTextComponent c) throws BadLocationException {
    	
    	//determina  as posições
		TextUI mapper = c.getUI();
		Rectangle p0 = mapper.modelToView(c, offs0);
		Rectangle p1 = mapper.modelToView(c, offs1);
		
    	/* Desenha os marcadores */
		int altura = p0.height/4;
		int largura = (int) (altura * 0.85);
		int xpoints[] = new int[3];
		int ypoints[] = new int[3];
		
		/* Primeiro */
		
		/* Esquerdo. */
		xpoints[0] = p0.x;
		ypoints[0] = p0.y + p0.height;
		
		/* Superior. */
		xpoints[1] = p0.x;
		ypoints[1] = p0.y + p0.height - altura;
		
		/* Direito. */
		xpoints[2] = p0.x + largura;
		ypoints[2] = p0.y + p0.height;
		
		g.setColor(new Color(255, 30, 150));
		g.drawPolygon(new Polygon(xpoints, ypoints, 3));
		g.fillPolygon(new Polygon(xpoints, ypoints, 3));
		
		/* Segundo */
		
		/* Canto esquerdo. */
		xpoints[0] = p1.x + (p1.x == 0 ? 0 : -1) * largura;
		ypoints[0] = p1.y + p1.height;
		
		/* Ponto superior. */
		xpoints[1] = p1.x;
		ypoints[1] = p1.y + p1.height - altura;
		
		/* Canto direito. */
		xpoints[2] = p1.x;
		ypoints[2] = p1.y + p1.height;
		
		g.setColor(new Color(150, 30, 255));
		g.drawPolygon(new Polygon(xpoints, ypoints, 3));
		g.fillPolygon(new Polygon(xpoints, ypoints, 3));
    }
}

public class VarText extends AbstractArquivo implements IEstado{
	private class Linha{
		/* Posições de início e fim de linha */
		public int off1, off2;
		
		public String valor = "";
		
		public Linha(String valor, int off1, int off2){
			this.off1 = off1;
			this.off2 = off2;
			this.valor = valor;
		}
	}
	
	private class EstadoVarText {
		public int lin_atual;
		
		public int pos_ant_lin;
		
		public int pos_atual_lin;
		
		public int off1, off2;
		
		public List<Linha> linhas; 
		
		public boolean is_opened, read_only;
		
		public File file;
	}
	
	private JTextPane pPrincipal;
	
	/** Número da linha atual. */
	private int lin_atual = 0; //linha atual
	
	/** Último caractere lido da linha atual. */
	private int pos_ant_lin = 0;
	
	/** Caractere a ser lido no momento. */
	private int pos_atual_lin = 1;
	
	/** Offsets para highlighting. */
	private int off1 = 0, off2 = 0;
	
	/** Lista das linhas nesse vartext. */
	private List<Linha> linhas = new ArrayList<Linha>();
	
	private StringBuffer texto = null;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cria uma nova variável do tipo arquivo texto de Pascal.
	 * 
	 * @param nome Nome desta variável
	 */
	public VarText(String nome){
		super(nome);
		inicializar();
	}

	public void close() throws IOException {
		linhas = null;
		lin_atual = pos_atual_lin = pos_ant_lin = 0;
		pPrincipal.setText("");
	}

	public boolean eof() {
		if(linhas == null){
			return true;
		}else if(lin_atual == linhas.size()){
			return true;
		}else if(lin_atual == linhas.size() - 1){
			return pos_atual_lin == linhas.get(lin_atual).valor.length();
		}else
			return false;
	}

	public Color getCorTitulo() {
		return Color.green;
	}

	public Object read() throws IOException {
		if(eof()){
			throw new IOException("Fim de arquivo atingido!");
		}
		
		/*Lê o próximo caractere. */ 
		Linha linha = linhas.get(lin_atual);
		char c;
		
		off1 = linha.off1 + pos_ant_lin;
		
		/* Chegou ao final da linha ? */
		if(pos_atual_lin > linha.valor.length()){
			off2 = linha.off2;
			pos_ant_lin = 0;
			pos_atual_lin = 1;
			lin_atual++;
			c = '\n';
		}else{
			off2 = linha.off1 + pos_atual_lin;
			c = linha.valor.charAt(pos_ant_lin);
			pos_ant_lin = pos_atual_lin++;
		}
		
		atualizarHighlights();
		return new Character(c);
	}
	
	public String readString(int tam) throws IOException {
		if ((tam <= 0) || (eof()))
			throw new IllegalArgumentException("Tamanho para leitura inválido!");
		
		off1 = linhas.get(lin_atual).off1 + pos_ant_lin;
		
		StringBuffer sb = new StringBuffer();
		int tam_restante_a_ler = tam;
		do {
			if (lin_atual >= linhas.size()) {
				throw new IllegalArgumentException("Fim de arquivo atingido!");
			}
			
			Linha linha = linhas.get(lin_atual);
			
			//o que resta da linha atual
			String resto_linha = linha.valor.substring(pos_ant_lin);
			int tam_resto_linha = resto_linha.length();
			
			if (tam_resto_linha >= tam_restante_a_ler) {
				sb.append(resto_linha.substring(0, tam_restante_a_ler));
				
				if (tam_resto_linha > tam_restante_a_ler)
					pos_atual_lin = (pos_ant_lin += tam_restante_a_ler) + 1;
				else {
					pos_atual_lin = (pos_ant_lin = 0) + 1;
					lin_atual++;
				}
				
				break;
			} else {
				sb.append(resto_linha.substring(0, tam_resto_linha));
				tam_restante_a_ler -= tam_resto_linha;
				
				pos_atual_lin = (pos_ant_lin = 0) + 1;
				lin_atual++;
			}
		} while (true);
		
		off2 = linhas.get(lin_atual).off1 + pos_ant_lin;
		
		return sb.toString();
	}
	
	public Object readInt() throws IOException{
		String bloco = procurarBlocoIsolado();
		
		int result;
		try {
			result = Integer.parseInt(bloco);
		} catch (NumberFormatException e) {
			return null;
		}
		
		return result;
	}
	
	public Object readReal() throws IOException{
		String bloco = procurarBlocoIsolado();
		
		double result;
		try {
			result = Double.parseDouble(bloco);
		} catch (NumberFormatException e) {
			return null;
		}
		
		return result;
	}
	
	public String readLine() throws IOException {
		if(eof()){
			throw new IOException("Fim de arquivo atingido!");
		}
		
		Linha linha = linhas.get(lin_atual);
		String retorno = "";
		
		off1 = linha.off1 + pos_ant_lin;
		off2 = linha.off2 - 1;
		
		if(pos_atual_lin <= linha.valor.length()){
			retorno = linha.valor.substring(pos_ant_lin);
		}
		
		lin_atual++;
		pos_ant_lin = 0;
		pos_atual_lin = 1;
		
		atualizarHighlights();
		
		return retorno;
	}
	
	public void readln() throws IOException {
		if(eof()){
			throw new IOException("Fim de arquivo atingido!");
		}
		
		lin_atual++;
		pos_ant_lin = 0;
		pos_atual_lin = 1;
	}
	
	public void reset() throws Exception {
		open();
		read_only = true;
	}
	
	public void rewrite() throws Exception {
		is_opened = true;
		read_only = false;
		texto = new StringBuffer();
		linhas = new ArrayList<Linha>();
		
		off1 = off2 = 0;
		lin_atual = 0;
		pos_atual_lin = 1;
		pos_ant_lin = 0;
	}
	
	public void writeln(String s) {
		write(s + "\n");
	}
	
	public void writeln(VarChar var) {
		writeln(((Character) var.getValor()).toString());
	}
	
	public void writeln(VarInteger var) {
		writeln(((Integer) var.getValor()).toString());
	}
	
	public void writeln(VarReal var) {
		writeln(((Double) var.getValor()).toString());
	}
	
	public void write(String s) {
		if (!is_opened) {
			throw new RuntimeException("Arquivo fechado!");
		}
		
		if(read_only){
			throw new RuntimeException("Arquivo aberto em modo de leitura!");
		}
		
		try{
			texto.append(s);
			linhas = gerarLinhas(new StringReader(texto.toString()));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void write(VarChar var) {
		write(((Character) var.getValor()).toString());
	}
	
	public void write(VarInteger var) {
		write(((Integer) var.getValor()).toString());
	}
	
	public void write(VarReal var) {
		write(((Double) var.getValor()).toString());
	}
	
	private void inicializar(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1.0;
		
		add(new JScrollPane(getPPrincipal()), gbc);
	}
	
	private JTextPane getPPrincipal(){
		if(pPrincipal == null){
			pPrincipal = new JTextPane();
			pPrincipal.setFont(pPrincipal.getFont().deriveFont(30.0f));
			//pPrincipal.setEditable(false);
			criarEstilos();
		}
		return pPrincipal;
	}
	
	private void atualizarHighlights() {
		DefaultHighlighter dh = (DefaultHighlighter) pPrincipal.getHighlighter();
		dh.removeAllHighlights();
		try{
			dh.addHighlight(off1, off2, new VarTextHighlightPainter(Color.orange));
			pPrincipal.repaint();
			pPrincipal.setCaretPosition(off2 + 1);
		}catch(Exception e){}
	}
	
	private void open() throws Exception{
		if(file == null){
			throw new IOException("Nenhum nome associado ao arquivo!");
		}
		
		if (is_opened) {
			close();
		}
		
		//lê o texto
		BufferedReader br = new BufferedReader(getReader());
		texto = new StringBuffer();
		String linha = br.readLine();
		if (linha != null) {
			do {
				texto.append(linha);
				
				if ((linha = br.readLine()) != null)
					texto.append("\n");
				else
					break;
			}while (true);
		}
		
		linhas = gerarLinhas(new StringReader(texto.toString()));
		
		off1 = off2 = 0;
		lin_atual = 0;
		pos_atual_lin = 1;
		pos_ant_lin = 0;
		
		atualizarHighlights();
		is_opened = true;
	}
	
	private void arrumarTexto(){
		StyledDocument doc = pPrincipal.getStyledDocument();
		int len = doc.getLength();
		
		try{
			doc.remove(0, len == 0 ? 0 : len);
			
			if (linhas != null) {
				for(Linha l:linhas){
					doc.insertString(doc.getLength(), l.valor, doc.getStyle("normal"));
					doc.insertString(doc.getLength(), "\u2605\n", doc.getStyle("eol"));
				}
				
				//marca de final de arquivo só no modo de leitura
				if (read_only)
					doc.insertString(doc.getLength(), "\u2588\n", doc.getStyle("eof"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void criarEstilos() {
		StyledDocument doc = pPrincipal.getStyledDocument();
		
		StyleContext sc = StyleContext.getDefaultStyleContext();
		Font f = pPrincipal.getFont();
		
		/* Estilo para texto qualquer...*/
		Style normal = sc.new NamedStyle();
		normal.addAttributes(StyleContext.getDefaultStyleContext().
	        	getStyle(StyleContext.DEFAULT_STYLE).copyAttributes());
		//StyleConstants.setFontSize(normal, 20);
		StyleConstants.setFontFamily(normal, f.getFamily());
		StyleConstants.setBold(normal, true);
		doc.addStyle("normal", normal);
		
		/* Estilo para caractere de fim de linha...*/
		Style s = sc.new NamedStyle();
		s.addAttributes(normal.copyAttributes());
		StyleConstants.setForeground(s, Color.BLUE);
		StyleConstants.setBold(s, false);
		doc.addStyle("eol", s);
		
		/* Estilo para caractere de fim de arquivo...*/
		s = sc.new NamedStyle();
		s.addAttributes(normal.copyAttributes());
		StyleConstants.setForeground(s, Color.RED);
		StyleConstants.setBold(s, false);
		doc.addStyle("eof", s);
	}
	
	private List<Linha> gerarLinhas(Reader origem) throws IOException{
		List<Linha> lista = new ArrayList<Linha>();
		
		BufferedReader br = new BufferedReader(origem);
		String linha = null;
		int i = 0;
		while ((linha = br.readLine()) != null) {
			int off1 = i;
			
			/* Tamanho da linha + tamanho do sinal de eoln */
			i += linha.length() + 1;
			
			lista.add(new Linha(linha, off1, i));
			i++;
		}
		
		return lista;
	}
	
	//IEstado
	public Object getEstado() {
		EstadoVarText e = new EstadoVarText();
		e.is_opened = is_opened;
		e.file = file;
		e.read_only = read_only;
		
		if (linhas != null) {
			e.linhas = new ArrayList<Linha>();
			e.linhas.addAll(linhas);
		} else {
			e.linhas = null;
		}
		
		e.off1 = off1;
		e.off2 = off2;
		e.lin_atual = lin_atual;
		e.pos_ant_lin = pos_ant_lin;
		e.pos_atual_lin = pos_atual_lin;
		
		return e;
	}
	
	public void setEstado(Object estado) {
		if(estado == null) {
			linhas = new ArrayList<Linha>();
			
			assign(null);
			off1 = off2 = 0;
			lin_atual = 0;
			pos_atual_lin = 1;
			pos_ant_lin = 0;
		}
		else {
			EstadoVarText e = (EstadoVarText) estado;
			is_opened = e.is_opened;
			assign(e.file == null ? null : e.file.getName());
			read_only = e.read_only;
			linhas = e.linhas;
			off1 = e.off1;
			off2 = e.off2;
			lin_atual = e.lin_atual;
			pos_ant_lin = e.pos_ant_lin;
			pos_atual_lin = e.pos_atual_lin;
			
			arrumarTexto();
			atualizarHighlights();
		}
		
		validate();
		repaint();
	}
	
	/**
	 * Procura um bloco isolado por espaços em branco.
	 * 
	 * Consome todos os espaços em branco até achar um caractere que
	 * não seja espaço em branco. Em seguida, pega todos os caracteres
	 * até o próximo espaço em branco, sem incluí-lo.
	 * 
	 * Para fins desse método, c é um espaço em branco se
	 *    java.lang.Character.isWhiteSpace(c)
	 * for true, ou seja, ' ', '\t', '\n', dentre outros, são espaços
	 * em branco.
	 */
	private String procurarBlocoIsolado() throws IOException {
		if (eof()) {
			throw new IOException("Fim de arquivo atingido!");
		}
		
		// Pega todos os espaço brancos até achar um não espaço em branco
		Linha linha = linhas.get(lin_atual);
		char buffer[];
		int i;
		do {
			//o que resta da linha atual
			buffer = linha.valor.substring(pos_ant_lin).toCharArray();
			for (i = 0; i < buffer.length && Character.isWhitespace(buffer[i]); i++);
			
			//terminou a linha?
			if (i == buffer.length) {
				lin_atual++;
				
				if (lin_atual >= linhas.size()) {
					throw new IOException("Fim de arquivo atingido!");
				}
				
				linha = linhas.get(lin_atual);
				pos_ant_lin = 0;
				pos_atual_lin = 1;
			} else {
				break; //i marca o primeiro caractere não espaço branco
			}
		} while (true);
		
		/* Pega caracteres a partir da posição atual até achar um espaço em branco. */
		pos_ant_lin += i;
		
		off1 = linha.off1 + pos_ant_lin;
		StringBuffer sb = new StringBuffer();
		for(; (i < buffer.length) && (!Character.isWhitespace(buffer[i])); i++)
			sb.append(buffer[i]);
		off2 = off1 + sb.length();
		
		pos_ant_lin += sb.length();
		pos_atual_lin = pos_ant_lin + 1;
		
		return sb.toString();
	}
}
