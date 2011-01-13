package tirateima.gui.variaveis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Texto extends JTextArea{
	
	public Integer tamanho;
	public Point posicaoOriginal;
	public Double proporcao = 1.0;
	
	/**
	 * Construtor que recebe o conteudo (texto), tamanho e posicao e insere esses parametros
	 * @param conteudo
	 * @param tamanho
	 * @param posicao
	 */
	public Texto(String conteudo,Integer tamanho,Point posicao){
		this.tamanho = tamanho;
		this.posicaoOriginal = posicao;		
		this.setText(conteudo);
	}

	/**
	 * Copia a lista de textos criando uma nova lista, para nao copiar por 
	 * referencia.
	 * @param textos - lista de Texto
	 * @return nova lista de Texto copiada 
	 */
	public static List<Texto> copiarTextos(List<Texto> textos){
		List<Texto> copiaTextos = new ArrayList<Texto>();
		for(Texto t : textos){
			copiaTextos.add(new Texto(t.getText(), t.getFont().getSize(),t.posicaoOriginal));
		}
		return copiaTextos;
	}

	public void setProporcao(double prop) {
		if (prop <= 0) 
			throw new AssertionError("Proporção negativa ou nula.");
		proporcao = prop;
		this.setSize(getTamanhoParaProporcao());
		this.validate();
	}
	
	private Dimension getTamanhoParaProporcao() {
		Integer largura = (int)Math.round(this.getMinimumSize().getWidth() * proporcao);
		Integer altura = (int)Math.round(this.getMinimumSize().getHeight() * proporcao);
		return new Dimension(largura,
				             altura);
	}
	
	@Override
	public void paint(Graphics g) {
		Integer tamanhoAtual = (int)Math.round(tamanho.doubleValue() * proporcao);
		this.setFont(new Font("Arial",Font.BOLD,tamanhoAtual));
		this.setOpaque(false);
		super.paint(g);
	}
}
