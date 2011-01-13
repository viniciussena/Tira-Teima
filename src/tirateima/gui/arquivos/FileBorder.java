package tirateima.gui.arquivos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class FileBorder extends AbstractBorder{
	private String titulo;
	private Font fonte;
	private Color color;
	private Color cor_titulo;
	
	/** Espaço vertical entre o a linha do componente e o texto. */ 
	public static final int ESP_VERT = 2;
	
	/** Largura da linha exterior desenhada. */
	public static final int LARG_LINHA = 2;
	
	/** Fonte padrão a ser usada no título. */
	public static final Font FONTE_PADRAO = tirateima.gui.Constantes.FONTE_TITULO;
	
	/** Cor padrão a ser usada na borda. */
	public static final Color COR_PADRAO = new Color(50, 100, 255);
	
	/** Cor padrão do título. */
	public static final Color COR_TITULO = Color.black;
	
	/**
	 * Cria uma nova FileBorder.
	 * 
	 * @param titulo Título a ser mostrado na borda.
	 * @param fonte Fonte a ser usada no título.
	 * @param color Cor da borda.
	 * @param cor_titulo Cor do título.
	 */
	public FileBorder(String titulo, Font fonte, Color color, Color cor_titulo){
		super();
		setTitulo(titulo);
		setFonte(fonte);
		setCorBorda(color);
		setCorTitulo(cor_titulo);
	}
	
	/**
	 * Cria uma nova FileBorder com cor de título padrão.
	 * 
	 * @param titulo Título a ser mostrado na borda.
	 * @param fonte Fonte a ser usada no título.
	 * @param color Cor da borda.
	 */
	public FileBorder(String titulo, Font fonte, Color color){
		this(titulo, fonte, color, null);
	}
	
	/**
	 * Cria uma nova FileBorder com cores de fundo e de título padrão.
	 * 
	 * @param titulo Título a ser mostrado na borda.
	 * @param fonte Fonte a ser usada no título.
	 */
	public FileBorder(String titulo, Font fonte){
		this(titulo, fonte, null, null);
	}
	
	/**
	 * Cria uma nova FileBorder com valores padrão.
	 * 
	 * @param titulo Título a ser mostrado na borda.
	 */
	public FileBorder(String titulo){
		this(titulo, null, null, null);
	}
	
	/**
	 * Cria uma nova FileBorder com valores padrão.
	 * 
	 * @param titulo Título a ser mostrado na borda.
	 * @param c Cor desta borda.
	 */
	public FileBorder(String titulo, Color c){
		this(titulo, null, c, null);
	}
	
	/**
	 * Cria uma nova FileBorder com valores padrão e título vazio.
	 */
	public FileBorder(){
		this(null, null, null, null);
	}
	
	/**
	 * Define o tamanho e a largura da fonte
	 * 
	 * @param c: variável da classe Component
	 * @return Insets object (altura e largura da linha)
	 */
	public Insets getBorderInsets(Component c){
		int altura_fonte = c.getFontMetrics(fonte).getHeight();
		altura_fonte /= getTitulo().equals("") ? 2 : 1;  
		int altura = 2 * LARG_LINHA + 2 * ESP_VERT + altura_fonte;
		return new Insets(altura, LARG_LINHA, LARG_LINHA, LARG_LINHA);
	}
	
	public Insets getBorderInsets(Component c, Insets insets){
		Insets ins = getBorderInsets(c);
		insets.bottom = ins.bottom;
		insets.left = ins.left;
		insets.right = ins.right;
		insets.top = ins.top;
		return ins;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
		Insets insets = getBorderInsets(c);
		Graphics2D g2d = (Graphics2D) g;
		
        /* Desenha o fundo. */
		int largura = c.getWidth();
		int altura = c.getHeight();
		g2d.setColor(getCorBorda());
		g2d.fillRoundRect(0, 0, largura, altura, 7, 7);
		
		largura = c.getWidth() - insets.left - insets.right;
		altura = c.getHeight() - insets.bottom - insets.top;
		g2d.setColor(c.getBackground());
		g2d.fillRoundRect(insets.left, insets.top, largura, altura, 7, 7);
		
		/* Se necessário, desenha o título. */
		if(!getTitulo().equals("")){
			FontMetrics fm = c.getFontMetrics(getFont());
			int base = fm.getAscent() + fm.getLeading() + fm.getDescent();
			g2d.setColor(getCorTitulo());
			g2d.setFont(getFont());
			g2d.drawString(getTitulo(), 3, base);
		}
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public Font getFont(){
		return fonte;
	}
	
	public Color getCorBorda(){
		return color;
	}
	
	public Color getCorTitulo(){
		return cor_titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = new String(titulo == null ? "" : titulo);
	}
	
	public void setFonte(Font fonte){
		this.fonte = fonte == null ? FONTE_PADRAO : fonte;
	}
	
	public void setCorBorda(Color color){
		this.color = color == null ? COR_PADRAO : color;
	}
	
	public void setCorTitulo(Color cor_titulo){
		this.cor_titulo = cor_titulo == null ? COR_TITULO : cor_titulo;
	}
}
