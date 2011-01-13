package tirateima.gui.variaveis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Seta extends JComponent {
	
	private static final long serialVersionUID = 101L;
	String nome;
	Direcao direcao;
	Integer tamanho;
	protected double proporcao = 1.0;
	Point posicaoOriginal;

	public Seta(){
		super();
	}
	
	public Seta(String nome, Direcao direcao, Integer tamanho) {
		this.nome = nome;
		this.direcao = direcao;
		this.tamanho = tamanho;
	}

	public void setProporcao(double prop) {
		if (prop <= 0) 
			throw new AssertionError("Proporção negativa ou nula.");
		proporcao = prop;
		this.setSize(getTamanhoParaProporcao());
		this.validate();
	}

	private Dimension getTamanhoParaProporcao() {
		Double larguraReal = this.tamanho.doubleValue() * proporcao;
		Integer largura = (int)Math.round(larguraReal);
		Double alturaReal = this.tamanho.doubleValue() * proporcao;
		Integer altura = (int)Math.round(alturaReal);
		return new Dimension(largura,
				             altura);
	}
	
	@Override
	public void paint(Graphics g){
		//prepara o desenho e os tamanhos da seta
		Graphics2D g2d = (Graphics2D) g;
		Double tamanhoReal = tamanho.doubleValue()*proporcao;
		Integer tamanhoSeta = (int)Math.round(tamanhoReal);
		Integer descontoSeta = tamanhoSeta / 5;
		Integer alturaBase = tamanhoSeta - descontoSeta;
		Polygon triangulo = new Polygon();
		//referencias para o desenho da seta
		Integer  valMaior = (int)Math.round(10*proporcao);
		Integer  valMedio = (int)Math.round(5*proporcao);
		Integer  valMenor = 0;
		if(this.direcao == Direcao.BAIXO){
			//desenha a base da seta
			g2d.drawLine(valMedio, valMenor, valMedio, alturaBase);
			//desenha o triangulo da seta			
			triangulo.addPoint(valMedio, tamanhoSeta - 1);
			triangulo.addPoint(valMaior, alturaBase);
			triangulo.addPoint(valMenor, alturaBase);		
		}
		else if(this.direcao == Direcao.CIMA){
			//desenha a base da seta
			g2d.drawLine(valMedio, descontoSeta,valMedio, tamanhoSeta);
			//desenha o triangulo da seta
			triangulo.addPoint(valMedio,valMenor);
			triangulo.addPoint(valMenor,descontoSeta);
			triangulo.addPoint(valMaior,descontoSeta);
		}else if(this.direcao == Direcao.ESQUERDA){
			//desenha a base da seta
			g2d.drawLine(descontoSeta,valMedio,tamanhoSeta,valMedio);
			//desenha o triangulo da seta
			triangulo.addPoint(valMenor,valMedio);
			triangulo.addPoint(descontoSeta,valMenor);
			triangulo.addPoint(descontoSeta,valMaior);
		}else if(this.direcao == Direcao.DIREITA){
			//desenha a base da seta
			g2d.drawLine(valMenor,valMedio,alturaBase,valMedio);
			//desenha o triangulo da seta
			triangulo.addPoint(tamanhoSeta,valMedio);
			triangulo.addPoint(alturaBase,valMenor);
			triangulo.addPoint(alturaBase,valMaior);
		}
		g2d.fillPolygon(triangulo);
		g2d.setColor(Color.BLACK);
		g2d.dispose();
	}

	public Seta criarCopia() {
		Seta setaCopia = new Seta(this.nome,this.direcao,this.tamanho);
		return setaCopia;
	}

	/**
	 * Método que recebe uma variável e retorna um ponto a partir do qual ela será desenhada.
	 * De acordo com o sentido (cima, baixo, esquerda ou direita) ela terá de ter seu posicio-
	 * namento deslocado.
	 * @param v
	 * @return
	 */
	public Point calculaPosicao(Variavel v) {
		Integer x;
		Integer y;
		//pega a largura e altura reais da variavel
		Integer larguraReal = v.getRealSize().width;
		Integer alturaReal = v.getRealSize().height;
		//inicializa o que for necessario (larguraReal, alturaReal e proporcao)
		if (larguraReal == 0){
			larguraReal = v.dimensao.width;
		}
		if (alturaReal == 0){
			alturaReal = v.dimensao.height;
		}
		if (v.proporcao == 0.0){
			v.proporcao = 1.0;
		}
		//se a seta for para baixo ou par direita, posiciona sua origem no centro da variavel.
		if(this.direcao == Direcao.BAIXO){
			x = v.posicao.x + larguraReal/2 - (int)Math.nextUp(4.0 * v.proporcao);
			y = v.posicao.y + alturaReal/2 + (int)Math.nextUp(14 * v.proporcao);
		}
		else if(this.direcao == Direcao.DIREITA){
			x = v.posicao.x + larguraReal/2;
			y = v.posicao.y + alturaReal/2 + (int)Math.nextUp(8 * v.proporcao);			
		}
		//se a seta for para cima, prosiciona sua origem sobre a variavel
		else if(this.direcao == Direcao.CIMA){
			x = v.posicao.x + larguraReal/2 - (int)Math.nextUp(4 * v.proporcao);
			y = v.posicao.y + alturaReal/2 + (int)Math.nextUp(14 * v.proporcao) - this.tamanho;
		}
		//se a seta for para esquerda, posiciona sua origem `a esquerda da variavel
		else if(this.direcao == Direcao.ESQUERDA){
			x = v.posicao.x + larguraReal/2  - this.tamanho;
			y = v.posicao.y + alturaReal/2 + (int)Math.nextUp(8 * v.proporcao);
		}else{
			return null;
		}
		return new Point(x,y);
	}
}
