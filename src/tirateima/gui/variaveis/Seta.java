package tirateima.gui.variaveis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform;

public class Seta extends JComponent {
	
	private static final long serialVersionUID = 101L;
	
	String nome;
	Point posicaoPartida;/**< Posição de origem da seta*/
	Point posicaoApontada;/**< Posição de destino da seta*/
	Point posicaoOriginal;    /**< Localização da seta no conteiner pai (tudo)*/
	
	protected double proporcao = 1.0;

	public Seta(){
		super();
	}
	
	public Seta(String nome, Point posicaoApontada) {
		this.nome = nome;
		this.posicaoApontada = posicaoApontada;
	}

	public void setProporcao(double prop) {
		if (prop <= 0) 
			throw new AssertionError("Proporção negativa ou nula.");
		proporcao = prop;
		this.setSize(getTamanhoParaProporcao());
		this.validate();
	}

	/**
	 * Cria um quadrado com os lados tendo o dobro do tamanho máximo da seta.
	 * @return
	 */
	private Dimension getTamanhoParaProporcao() {
		Integer largura = (int)((double)Math.abs(posicaoApontada.x - posicaoPartida.x) * proporcao);
		Integer altura  = (int)((double)Math.abs(posicaoApontada.y - posicaoPartida.y) * proporcao);
		return new Dimension(largura,
				             altura);
	}

	/**
	 * Método que desenha a seta.
	 * 
	 * Para se entender como ela é desenhada, veja:
	 * 
	 * Divida a variável pelo centro em quatro na horizontal e na vertical.
	 * Imaginem-se quatro quadrantes, de modo que o primeiro esteja na parte inferior direita,
	 * o segundo na inferior esquerda, o terceiro, na superior esquerda e o quarto na 
	 * superior direita. Para calcular o quadrante em que ela estará, basta olhar para qual é maior, o x ou o
	 * y de origem. Lembre-se que no swing o x aponta para baixo. Fica assim:
	 * 
	 * x destino > x origem e y destino > y origem - primeiro quadrante
	 * x destino < x origem e y destino > y origem - segundo quadrante 
	 * x destino < x origem e y destino < y origem - terceiro quadrante
	 * x destino > x origem e y destino < y origem - quarto quadrante
	 * 
	 * Dessa forma, suas fronteiras (que serão um retângulo) deverá ser posicionado de modo que 
	 * duas de suas laterais encostem em nos eixos que cortam a variável no meio. Isso se dá para
	 * que a seta possa ser desenhada.
	 * 
	 * Além disso, de acordo com cada quadrante, a posição de origem da seta será diferente,
	 * pois a seta é desenhada relativamente ao conteiner dela, não do conteiner no qual ela
	 * está inserido. Será dessa forma.
	 * 
	 * Se estiver no primeiro quadrante, ela terá seu início no canto superior esquerdo.
	 * Se estiver no segundo quadrante, ela terá seu início no canto superior direito.
	 * Se estiver no terceiro quadrante, ela terá seu início no canto inferior direito.
	 * Se estiver no quarto quadrante, ela terá seu início no canto inferior esquerdo. 
	 * @param v
	 * @return
	 */
	@Override
	public void paint(Graphics g){
		//prepara o desenho
		Graphics2D g2d = (Graphics2D) g;		
		
		//TODO: Desenhar o triângulo da seta.
		//Desenha a seta de acordo com o quadrante em que ela se encontra.
		Point posicaoPartidaRelativa;
		Point posicaoApontadaRelativa;
		Integer tamanhoSeta = 0;
		Double anguloRotacaoSeta;
		//Primeiro quadrante
		if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			//calcula a posição de partida da seta
			posicaoPartidaRelativa = 
				new Point(0,
						  0);
			//calcula a posição apontada pela seta
			posicaoApontadaRelativa = 
				new Point(posicaoApontada.x - posicaoPartida.x,
						  posicaoApontada.y - posicaoPartida.y);
			//calcula o tamanho da seta
			tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
			//calcula o angulo de rotação da seta
			anguloRotacaoSeta = calculaAnguloRotacaoSeta(
					posicaoPartidaRelativa, posicaoApontadaRelativa);
			//translada a seta dado o quadrante
			g2d.translate(0,0);
			//gira a seta no ângulo de rotação adequado
			g2d.rotate(anguloRotacaoSeta);
		}
		//Segundo quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			//parte do canto superior direito
			posicaoPartidaRelativa =
				new Point(posicaoPartida.x - posicaoApontada.x,
						  0);
			//chega até o canto inferior esquerdo
			posicaoApontadaRelativa = 
				new Point(0,
						  posicaoApontada.y - posicaoPartida.y);
			//calcula o tamanho da seta
			tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
			//calcula o angulo de rotação da seta
			anguloRotacaoSeta = calculaAnguloRotacaoSeta(
					posicaoPartidaRelativa, posicaoApontadaRelativa);
			//coloca a seta na posição correta
			g2d.translate(posicaoPartidaRelativa.x, 0);
			g2d.rotate(Math.PI+anguloRotacaoSeta);			
		}
		//Terceiro quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			//parte do canto inferior direito
			posicaoPartidaRelativa =
				new Point(posicaoPartida.x-posicaoApontada.x,
						  posicaoPartida.y-posicaoApontada.y);
			//chega até a origem
			posicaoApontadaRelativa = 
				new Point(0,
						  0);
			//calcula o tamanho da seta
			tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
			//calcula o angulo de rotação da seta
			anguloRotacaoSeta = calculaAnguloRotacaoSeta(
					posicaoPartidaRelativa, posicaoApontadaRelativa);
			//coloca a seta na posição correta
			g2d.translate(posicaoPartidaRelativa.x, posicaoPartidaRelativa.y);
			g2d.rotate(Math.PI+anguloRotacaoSeta);
		}
		//Quarto quadrante
		else if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			//parte do canto inferior esquerdo
			posicaoPartidaRelativa =
				new Point(0,
						  posicaoPartida.y-posicaoApontada.y);
			//chega até o canto superior direito
			posicaoApontadaRelativa =
				new Point(posicaoApontada.x-posicaoPartida.x,
						  0);
			//calcula o tamanho da seta
			tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
			//calcula o angulo de rotação da seta
			anguloRotacaoSeta = calculaAnguloRotacaoSeta(
					posicaoPartidaRelativa, posicaoApontadaRelativa);
			//coloca a seta na posição correta
			g2d.translate(0, posicaoPartidaRelativa.y);
			g2d.rotate(anguloRotacaoSeta);
		}

		//desenha a linha
		g2d.drawLine(0,
				     0,
				     tamanhoSeta,
				     0);
		//desenha o triângulo
		Polygon trianguloSeta = new Polygon();
		trianguloSeta.addPoint(tamanhoSeta,0);
		trianguloSeta.addPoint(tamanhoSeta-15,-5);
		trianguloSeta.addPoint(tamanhoSeta-15,5);
		g2d.fillPolygon(trianguloSeta);			

		g2d.setColor(Color.BLACK);
		g2d.dispose();
	}

	private Double calculaAnguloRotacaoSeta(Point posicaoPartidaRelativa,
			Point posicaoApontadaRelativa) {
		Double anguloRotacaoSeta;
		anguloRotacaoSeta = 
			Math.atan(
					((double)(posicaoApontadaRelativa.y-posicaoPartidaRelativa.y)/
					 (double)(posicaoApontadaRelativa.x-posicaoPartidaRelativa.x))
			);
		return anguloRotacaoSeta;
	}

	/**
	 * Método que calcula o tamanho da seta. Esse tamanho é dado pela fórmula da distância entre dois pontos.
	 * 
	 * @param posicaoPartidaRelativa
	 * @param posicaoApontadaRelativa
	 * @return Integer tamanho da seta
	 */
	private Integer calculaTamanhoSeta(Point posicaoPartidaRelativa, Point posicaoApontadaRelativa) {
		Double distanciaSeta = 
			Math.sqrt(Math.pow(posicaoApontadaRelativa.x-posicaoPartidaRelativa.x, 2)+
					  Math.pow(posicaoApontadaRelativa.y-posicaoPartidaRelativa.y,2));
		return (int)Math.round(distanciaSeta.doubleValue());
	}

	public Seta criarCopia() {
		Seta setaCopia = new Seta(this.nome,this.posicaoApontada);
		return setaCopia;
	}

	/**
	 * Método que recebe uma variável e retorna um ponto a partir do qual ela será desenhada.
	 * Este ponto será sempre o centro da variável e deverá ser depois setado na 
	 * posicaoOriginal para que ele funcione corretamente.
	 * De acordo com o quadrante em que a variável se encontra, a posição da origem irá variar.
	 * Veja:
	 * 
	 * Divida a variável pelo centro em quatro na horizontal e na vertical.
	 * Imaginem-se quatro quadrantes, de modo que o primeiro esteja na parte inferior direita,
	 * o segundo na inferior esquerda, o terceiro, na superior esquerda e o quarto na 
	 * superior direita. Para calcular o quadrante em que ela estará, basta olhar para qual é maior, o x ou o
	 * y de origem. Lembre-se que no swing o x aponta para baixo. Fica assim:
	 * 
	 * x destino > x origem e y destino > y origem - primeiro quadrante
	 * x destino < x origem e y destino > y origem - segundo quadrante 
	 * x destino < x origem e y destino < y origem - terceiro quadrante
	 * x destino > x origem e y destino < y origem - quarto quadrante
	 * 
	 * Dessa forma, suas fronteiras (que serão um retângulo) deverá ser posicionado de modo que 
	 * duas de suas laterais encostem em nos eixos que cortam a variável no meio. Isso se dá para
	 * que a seta possa ser desenhada.
	 * 
	 * Além disso, de acordo com cada quadrante, a posição de origem da seta será diferente,
	 * pois a seta é desenhada relativamente ao conteiner dela, não do conteiner no qual ela
	 * está inserido. Será dessa forma.
	 * 
	 * Se estiver no primeiro quadrante, ela terá seu início no canto superior esquerdo.
	 * Se estiver no segundo quadrante, ela terá seu início no canto superior direito.
	 * Se estiver no terceiro quadrante, ela terá seu início no canto inferior direito.
	 * Se estiver no quarto quadrante, ela terá seu início no canto inferior esquerdo. 
	 * @param Variavel v
	 * @return Point posicaoOriginal
	 */
	public Point calculaPosicaoOriginal(Variavel v) {
		//Pega a largura e altura reais da variavel
		Integer larguraReal = v.getRealSize().width;
		Integer alturaReal = v.getRealSize().height;
		//Inicializa o que for necessario (larguraReal, alturaReal e proporcao)
		if (larguraReal == 0){
			larguraReal = v.dimensao.width;
		}
		if (alturaReal == 0){
			alturaReal = v.dimensao.height;
		}
		if (v.proporcao == 0.0){
			v.proporcao = 1.0;
		}
		//Calcula o centro da variável
		Point centroVariavel = 
			new Point(v.posicao.x + larguraReal/2,
					  v.posicao.y + alturaReal/2 + (int)Math.nextUp(14 * v.proporcao));
		//Assume de início que a posicao original da seta é o seu centro
		posicaoPartida = centroVariavel;
		//Calcula a dimensão da seta
		Dimension dimensaoSeta 
			= new Dimension(Math.abs(posicaoApontada.x - centroVariavel.x),
					        Math.abs(posicaoApontada.y - centroVariavel.y));
		//Calcula a posicao original de acordo com cada quadrante
		//Primeiro quadrante.
		if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			posicaoOriginal = 
				new Point(centroVariavel.x,
						  centroVariavel.y);
		}
		//Segundo quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			posicaoOriginal = 
				new Point(centroVariavel.x - dimensaoSeta.width,
						  centroVariavel.y);
		}
		//Terceiro quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			posicaoOriginal = 
				new Point(centroVariavel.x - dimensaoSeta.width,
						  centroVariavel.y - dimensaoSeta.height);
		}
		//Quarto quadrante
		else if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			posicaoOriginal = 
				new Point(centroVariavel.x,
						  centroVariavel.y - dimensaoSeta.height);
		}
		
		return new Point(posicaoOriginal);
	}
}
