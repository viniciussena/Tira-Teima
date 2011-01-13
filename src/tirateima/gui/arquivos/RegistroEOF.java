package tirateima.gui.arquivos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import tirateima.gui.Constantes;
import tirateima.gui.variaveis.VarLinha;
import tirateima.gui.variaveis.Variavel;


public class RegistroEOF extends VarLinha {
	private static final long serialVersionUID = 1L;
	
	private Variavel tipo = null;
	private static final String texto = "EOF";
	private Dimension p_size = null;
	
	public RegistroEOF(Variavel tipo){
		super("", texto);
		assert tipo != null;
		
		this.tipo = tipo;
		this.lixo = false;
		this.setTexto("");
	}
	
	@Override
	public Variavel criarCopia() {
		return new RegistroEOF(tipo);
	}

	@Override
	public String typeName() {
		return null;
	}
	
	@Override
	public Color getCorTitulo() {
		return new Color(100, 100, 100);
	}

	@Override
	public Object getValor() {
		return null;
	}

	@Override
	public void setValor(Object valor) {

	}
	
	public Dimension getMinimumSize(){
		return tipo.getMinimumSize();
	}
	
	public Dimension getMaximumSize(){
		return tipo.getMaximumSize();
	}
	
	public Dimension getPreferredSize(){
		if(p_size != null){
			return p_size;
		}
		return tipo.getPreferredSize();
	}
	
	public void setPreferredSize(Dimension size){
		p_size = size;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Pega valores da fonte base
		Font fonte = Constantes.FONTE_VARIAVEL;
		FontMetrics metrics = g.getFontMetrics(fonte);
		Rectangle2D bounds = metrics.getStringBounds(texto, g);
		
		//Redimensiona a fonte conforme tamanho da variável
		Dimension my_size = getRealSize();
		
		double prop = my_size.width / bounds.getWidth();
		int nova_altura = (int) (bounds.getHeight() * prop);
		if(nova_altura > my_size.height){
			prop = my_size.height / bounds.getHeight();
		}
		
		Font nova_fonte = fonte.deriveFont((float) (prop * fonte.getSize()));
		metrics = g.getFontMetrics(nova_fonte);
		bounds = metrics.getStringBounds(texto, g);
		
		//Encontra ponto central
		int center_x = my_size.width / 2;
		int center_y = my_size.height / 2;
		
		int pos_x = center_x - (int) bounds.getWidth() / 2 + 1;
		int pos_y = center_y + (int) bounds.getHeight() / 2 - 1;
		
		g2d.setFont(fonte);
		g2d.setColor(Constantes.COR_FONTE);
		g2d.drawString(texto, pos_x, pos_y);
	}
	
	public int readData(BufferedReader r) throws IOException {
		return 0;
	}

	public void writeData(Writer w) throws IOException {
		
	}
}
