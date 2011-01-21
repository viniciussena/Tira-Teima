package tirateima.gui.variaveis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;

/**
 * 
 * Desenha a seta que sai do ponteiro.
 * 
 * @author Vinícius
 *
 */
public class Seta extends JComponent {
	
	private static final long serialVersionUID = 101L;
	
	String nome;/**< Nome do ponteiro ao qual pertence a seta*/
	Point posicaoPartida; /**< Posição de origem da seta*/
	Point posicaoApontada;/**< Posição de destino da seta*/
	Point posicaoOriginal;/**< Localização da seta no conteiner pai (tudo)*/
	
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
		//soma a largura (tamanho no eixo x) mais uma margem para o triângulo da seta
		Integer largura = 
			(int)((double)Math.abs(posicaoApontada.x - posicaoPartida.x) * proporcao) +
			(int)(10*proporcao);
		//soma a altura (tamanho no eixo y) mais uma pargem para o triângulo da seta
		Integer altura  = 
			(int)((double)Math.abs(posicaoApontada.y - posicaoPartida.y) * proporcao) +
			(int)(10*proporcao);
		return new Dimension(largura,altura);
	}

	/**
	 * Método que desenha a seta.
	 * 
	 * Para se entender como ela é desenhada, veja:
	 * 
	 * Divida a variável pelo centro em quatro na horizontal e na vertical.
	 * Imaginem-se quatro quadrantes, de modo que o primeiro esteja na parte inferior direita,
	 * o segundo na inferior esquerda, o terceiro, na superior esquerda e o quarto na 
	 * superior direita. Para calcular o quadrante em que ela estará, basta olhar para qual é 
	 * maior, o x ou o y de origem. Lembre-se que no swing o y aponta para baixo. Fica assim:
	 * 
	 * x apontado > x partida e y apontado >= y partida - primeiro quadrante
	 * x apontado <= x partida e y apontado > y partida - segundo quadrante 
	 * x apontado < x partida e y apontado <= y partida - terceiro quadrante
	 * x apontado >= x partida e y apontado < y partida - quarto quadrante
	 * 
	 * Nos casos especiais (x apontado = x partida ou y apontado = y partida) é necessário 
	 * também haver um tratamento especial, por conta da distorção causada no triângulo da 
	 * seta. Não só nesses casos, mas também quando está muito próximo desses casos limites
	 * deve-se arredondar para as posições coincidentes com os eixos, para evitar disorções.
	 * 
	 * Dessa forma, suas fronteiras (que serão um retângulo) deverá ser posicionado de modo que 
	 * duas de suas laterais encostem em nos eixos que cortam a variável no meio. Isso se dá 
	 * para que a seta possa ser desenhada.
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
	//TODO: tratar os casos de seta coincidindo com os eixos.
	@Override
	public void paint(Graphics g){
		//prepara o desenho
		Graphics2D g2d = (Graphics2D) g;		
		//desenha a seta de acordo com o quadrante em que ela se encontra.
		Point posicaoPartidaRelativa;
		Point posicaoApontadaRelativa;
		Integer tamanhoSeta = 0;
		Double anguloRotacaoSeta;
		//primeiro quadrante
		if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y >= posicaoPartida.y){
			//caso geral
			if(Math.abs(posicaoApontada.y - posicaoPartida.y) > 5){
				//calcula a posição de partida da seta, considerando-se a proporção
				posicaoPartidaRelativa = 
					new Point(0,
							  0);
				//calcula a posição apontada pela seta, considerando-se a proporção
				posicaoApontadaRelativa = 
					new Point((int)((double)(posicaoApontada.x - posicaoPartida.x)*proporcao),
							  (int)((double)(posicaoApontada.y - posicaoPartida.y)*proporcao));
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
			//caso especial em que a seta coincide com ou se aproxima do eixo x
			else{
				//calcula a posição de partida da seta, considerando-se a proporção
				posicaoPartidaRelativa = 
					new Point(0,
							  0);
				//calcula a posição apontada pela seta, considerando-se a proporção
				posicaoApontadaRelativa = 
					new Point((int)((double)(posicaoApontada.x - posicaoPartida.x)*proporcao),
							  (int)((double)(posicaoApontada.y - posicaoPartida.y)*proporcao));
				//calcula o tamanho da seta
				tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
				//calcula o angulo de rotação da seta
				anguloRotacaoSeta = calculaAnguloRotacaoSeta(
						posicaoPartidaRelativa, posicaoApontadaRelativa);
				//translada a seta dado o quadrante
				g2d.translate(0,(int)(5*proporcao));
				//gira a seta no ângulo de rotação adequado
				g2d.rotate(0);
				
			}
		}
		//segundo quadrante
		else if (posicaoApontada.x <= posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			//caso geral 
			if(Math.abs(posicaoApontada.x - posicaoPartida.x) > 5){
				//parte do canto superior direito
				posicaoPartidaRelativa =
					new Point((int)((double)(posicaoPartida.x - posicaoApontada.x)*proporcao),
							  0);
				//chega até o canto inferior esquerdo
				posicaoApontadaRelativa = 
					new Point(0,
							  (int)((double)(posicaoApontada.y - posicaoPartida.y)*proporcao));
				//calcula o tamanho da seta
				tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
				//calcula o angulo de rotação da seta
				anguloRotacaoSeta = calculaAnguloRotacaoSeta(
						posicaoPartidaRelativa, posicaoApontadaRelativa);
				//coloca a seta na posição correta
				g2d.translate(posicaoPartidaRelativa.x, 0);
				g2d.rotate(Math.PI+anguloRotacaoSeta);
			}
			//caso especial em que a seta coincide com o eixo y
			else{
				//parte do centro superior
				posicaoPartidaRelativa =
					new Point(5,
							  0);
				//chega até o centro inferior
				posicaoApontadaRelativa = 
					new Point(5,
							  (int)((double)(posicaoApontada.y - posicaoPartida.y)*proporcao));
				//calcula o tamanho da seta
				tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
				//calcula o angulo de rotação da seta
				anguloRotacaoSeta = Math.PI/2;
				//coloca a seta na posição correta
				g2d.translate(posicaoPartidaRelativa.x, 0);
				g2d.rotate(anguloRotacaoSeta);
			}
						
		}
		//terceiro quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y <= posicaoPartida.y){
			if(Math.abs(posicaoApontada.y - posicaoPartida.y) > 5){
				//parte do canto inferior direito
				posicaoPartidaRelativa =
					new Point((int)((double)(posicaoPartida.x-posicaoApontada.x)*proporcao),
							  (int)((double)(posicaoPartida.y-posicaoApontada.y)*proporcao));
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
			else{
				//parte do canto inferior direito
				posicaoPartidaRelativa =
					new Point((int)((double)(posicaoPartida.x-posicaoApontada.x)*proporcao),
							  0);
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
				g2d.translate(posicaoPartidaRelativa.x, (int)(5*proporcao));
				g2d.rotate(Math.PI);
			}
		}
		//quarto quadrante
		else if (posicaoApontada.x >= posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			//caso geral
			if(Math.abs(posicaoApontada.x - posicaoPartida.x) > 5){
				//parte do canto inferior esquerdo
				posicaoPartidaRelativa =
					new Point(0,
							  (int)((double)(posicaoPartida.y-posicaoApontada.y)*proporcao));
				//chega até o canto superior direito
				posicaoApontadaRelativa =
					new Point((int)((double)(posicaoApontada.x-posicaoPartida.x)*proporcao),
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
			//caso particular em que a seta coincide com o eixo x
			else{
				//parte do canto inferior esquerdo
				posicaoPartidaRelativa =
					new Point(0,
							  (int)((double)(posicaoPartida.y-posicaoApontada.y)*proporcao));
				//chega até o canto superior direito
				posicaoApontadaRelativa =
					new Point((int)((double)(posicaoApontada.x-posicaoPartida.x)*proporcao),
							  0);
				//calcula o tamanho da seta
				tamanhoSeta = calculaTamanhoSeta(posicaoPartidaRelativa, posicaoApontadaRelativa);
				//calcula o angulo de rotação da seta
				anguloRotacaoSeta = calculaAnguloRotacaoSeta(
						posicaoPartidaRelativa, posicaoApontadaRelativa);
				//coloca a seta na posição correta
				g2d.translate(5, posicaoPartidaRelativa.y);
				g2d.rotate(anguloRotacaoSeta);
			}
		}

		//desenha a linha
		g2d.drawLine(0,
				     0,
				     tamanhoSeta,
				     0);
		//desenha o triângulo
		Polygon trianguloSeta = new Polygon();
		trianguloSeta.addPoint(tamanhoSeta,0);
		trianguloSeta.addPoint(tamanhoSeta-(int)(15*proporcao),-(int)(5*proporcao));
		trianguloSeta.addPoint(tamanhoSeta-(int)(15*proporcao),(int)(5*proporcao));
		g2d.fillPolygon(trianguloSeta);			

		g2d.setColor(Color.BLACK);
		g2d.dispose();
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
	 * superior direita. Para calcular o quadrante em que ela estará, basta olhar para qual é 
	 * maior, o x ou o y de origem. Lembre-se que no swing o y aponta para baixo. Fica assim:
	 * 
	 * x apontado > x partida e y apontado >= y partida - primeiro quadrante
	 * x apontado <= x partida e y apontado > y partida - segundo quadrante 
	 * x apontado < x partida e y apontado <= y partida - terceiro quadrante
	 * x apontado >= x partida e y apontado < y partida - quarto quadrante
	 * 
	 * Nos casos especiais (x apontado = x partida ou y apontado = y partida) é necessário também
	 * haver um tratamento especial, por conta da distorção causada no triângulo da seta.
	 * 
	 * Dessa forma, suas fronteiras (que serão um retângulo) deverá ser posicionado de modo que 
	 * duas de suas laterais encostem em nos eixos que cortam a variável no meio. Isso se dá
	 * para que a seta possa ser desenhada.
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
					  v.posicao.y + alturaReal/2 + (int)Math.round(15 * v.proporcao));
		//Assume de início que a posicao original da seta é o seu centro
		posicaoPartida = centroVariavel;
		//Calcula a dimensão da seta
		Dimension dimensaoSeta 
			= new Dimension(Math.abs((int)((double)(posicaoApontada.x - centroVariavel.x)*proporcao)),
					        Math.abs((int)((double)(posicaoApontada.y - centroVariavel.y)*proporcao)));
		//Calcula a posicao original de acordo com cada quadrante
		//Primeiro quadrante.
		if (posicaoApontada.x > posicaoPartida.x && posicaoApontada.y >= posicaoPartida.y){
			//caso geral
			if(Math.abs(posicaoApontada.y - posicaoPartida.y) > 5){
				posicaoOriginal = 
					new Point(centroVariavel.x,
							  centroVariavel.y);
			}
			//caso especial em que a seta coincide com o eixo x
			else{
				posicaoOriginal = 
					new Point(centroVariavel.x,
							  centroVariavel.y-(int)(5*proporcao));
			}
		}
		//Segundo quadrante
		else if (posicaoApontada.x <= posicaoPartida.x && posicaoApontada.y > posicaoPartida.y){
			//caso especial em que a seta coincide com o eixo y
			if(Math.abs(posicaoApontada.x - posicaoPartida.x) > 5){
				posicaoOriginal = 
					new Point(centroVariavel.x - dimensaoSeta.width,
							  centroVariavel.y);
			}
			//caso geral
			else{
				posicaoOriginal =
					new Point(centroVariavel.x - (int)(5*proporcao),
							  centroVariavel.y);
			}
		}
		//Terceiro quadrante
		else if (posicaoApontada.x < posicaoPartida.x && posicaoApontada.y <= posicaoPartida.y){
			//caso geral
			if(Math.abs(posicaoApontada.y - posicaoPartida.y) > 5){
				posicaoOriginal = 
					new Point(centroVariavel.x - dimensaoSeta.width,
							  centroVariavel.y - dimensaoSeta.height);
			}
			//caso particular em que a seta coincide com o eixo y
			else{
				posicaoOriginal = 
					new Point(centroVariavel.x - dimensaoSeta.width,
							  centroVariavel.y - (int)(5*proporcao));
			}
		}
		//Quarto quadrante
		else if (posicaoApontada.x >= posicaoPartida.x && posicaoApontada.y < posicaoPartida.y){
			//caso geral
			if(Math.abs(posicaoApontada.x - posicaoPartida.x) > 5){
				posicaoOriginal = 
					new Point(centroVariavel.x,
							  centroVariavel.y - dimensaoSeta.height);
			}
			//caso particular em que a seta coincide com o eixo x
			else{
				posicaoOriginal = 
					new Point(centroVariavel.x-(int)(5*proporcao),
							  centroVariavel.y - dimensaoSeta.height);
			}
		}
		
		return new Point(posicaoOriginal);
	}
	
	/**
	 * Método que calcula o tamanho da seta. Esse tamanho é dado pela fórmula da distância 
	 * entre dois pontos sqrt((x-x0)^2+(y-yo)^2).
	 * 
	 * @param posicaoPartidaRelativa
	 * @param posicaoApontadaRelativa
	 * @return Integer tamanho da seta
	 */
	private Integer calculaTamanhoSeta(Point posicaoPartidaRelativa, 
			Point posicaoApontadaRelativa) {
		Double distanciaSeta = 
			Math.sqrt(Math.pow(posicaoApontadaRelativa.x-posicaoPartidaRelativa.x, 2)+
					  Math.pow(posicaoApontadaRelativa.y-posicaoPartidaRelativa.y,2));
		return (int)Math.round(distanciaSeta.doubleValue());
	}
	
	/**
	 * Calcula o ângulo, em radianos, de rotação da seta, de acordo com a fórmula geométrica
	 * arctg((y-yo)/(x-x0)).
	 * 
	 * @param posicaoPartidaRelativa
	 * @param posicaoApontadaRelativa
	 * @return
	 */
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

	public Seta criarCopia() {
		Seta setaCopia = new Seta(this.nome,this.posicaoApontada);
		return setaCopia;
	}
}
