/* Copyright (C) 2007  Felipe A. Lessa
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package tirateima.gui.variaveis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.JComponent;

import tirateima.gui.Constantes;
import tirateima.gui.arquivos.IDataReader;


/**
 * 
 * Representa um tipo do Pascal. É responsável por desenhar a variável e manter
 * seu conteúdo em ordem.
 * 
 * @author felipe.lessa
 * @since 2007/04/03
 *
 */
@SuppressWarnings("serial")
public abstract class Variavel extends JComponent implements IDataReader {
	protected String nome;
	protected boolean modificado = true;
	protected boolean destacado;
	protected double proporcao;
	protected Color cor = null;
	protected Color corExterna = null; //Usada para arrays e matrizes
	protected Dimension dimensao = null;
	protected Point posicao = null;
	
	/**
	 * Cria uma nova variável com este nome.
	 * @param nome  nome da variável.
	 */
	protected Variavel(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Cria uma cópia exata porém desconexa desta variável.
	 * @return A cópia.
	 */
	public abstract Variavel criarCopia();
	
	/**
	 * Retorna uma "assinatura" para essa variável,
	 * na forma <tipo> <nome> <dimensão (talvez)>.
	 * 
	 * @return
	 */
	public abstract String signature();
	
	/**
	 * Retorna o nome do tipo dessa variável.
	 * 
	 * @return
	 */
	public abstract String typeName();
	
	/**
	 * Usado para que outro componente defina a proporção necessária
	 * para que este tipo caiba bem. 
	 * 
	 * @return O tamanho padrão deste tipo (em pixels).
	 */
	public abstract Dimension getTamanhoPadrao();
	
	
	/**
	 * Desenha o conteúdo atual desta variável a uma determinada proporção.
	 * @param g o Graphics2D onde será desenhado.
	 * @see #setProporcao(double)
	 */
	@Override
	public void paint(Graphics g) {
//		Dimension tamAtual = dimensao == null? super.getSize() : dimensao; // Configura o tamanho por variavel. Causa bug em array. Ver figuras: tamanho_interno_auto e tamanho_interno_manual 
		Dimension tamAtual = super.getSize();
		int arc = (int) (5 * proporcao);
		g.setColor(Color.GRAY);
		g.fillRoundRect(0, 0, tamAtual.width, tamAtual.height, arc, arc);
		g.setColor(modificado ? Constantes.COR_FUNDO_MODIFICADO
				: Constantes.COR_FUNDO_NORMAL);
		
		g.fillRoundRect(1, 1, tamAtual.width-2, tamAtual.height-2, arc, arc);
		Float rect = new RoundRectangle2D.Float(0, 0, tamAtual.width, 
				                                tamAtual.height, arc, arc);
		((Graphics2D)g).clip(rect);
	}
	
	
	/**
	 * O estado atual da variável, modificada ou não. Quando modificada,
	 * seu desenho passa ser destacado. 
	 * @return true se o estado for "modificado".
	 * @see #setModificado(boolean)
	 * 
	 * @author Luciano Santos
	 */
	public boolean getModificado() {
		return modificado;
	}
	
	/**
	 * Define se a variável deve ser desenhada como modificada ou não.
	 * @param modificado 
	 * @see #getModificado()
	 */
	public void setModificado(boolean modificado) {
		this.modificado = modificado;
		repaint();
	}
	
	/**
	 * Retorna o valor atual da variável. Use com cuidado pois a variável
	 * pode ser de qualquer tipo.
	 * @return o valor atual.
	 */
	public abstract Object getValor();
	/**
	 * Define o valor atual da variável. Uma chamada a este método *não*
	 * modifica o valor de "modificado". Caso você deseje destacar a variável,
	 * chame o método setModificado.
	 * @param valor o novo valor da variável.
	 */
	public abstract void setValor(Object valor);
	
	/**
	 * A proporção que será utilizada quando o desenho da variável for feito.
	 * @return a proporção (onde 1.0 é equivalente a 100%).
	 * @see #setProporcao(double)
	 */
	public double getProporcao() {
		return proporcao;
	}
	/**
	 * Define a proporção que será utilizada. Deve ser um número positivo. 
	 * @param prop a nova proporção (onde 1.0 é equivalente a 100%).
	 * @see #setProporcao(Dimension)
	 * @see #getProporcao()
	 */
	public void setProporcao(double prop) {
		if (prop <= 0)
			throw new AssertionError("Proporção deve ser positiva.");
		proporcao = prop;
		setSize(getSize());
		validate();		
	}
	
	/**
	 * O nome desta variável. Pode não ser um identificador válido Pascal 
	 * (e.g. quando a variável for elemento de um array) ou pode ser nulo. 
	 * @return o nome da variável.
	 */
	@Override
	public String getName() {
		return nome;
	}
	
	/**
	 * A cor do título da janela dependendo do tipo de variável.
	 * @return a cor do título.
	 */
	public abstract Color getCorTitulo();
	
	/** 
	 * Define a proporção que será utilizada quando o desenho da variável for
	 * feito a partir de um tamanho máximo. Caso o tamanho não seja um múltiplo
	 * do tamanho padrão, parte da área não será utilizada.
	 * @param tamanho tamanho máximo a ser usado no desenho.
	 * @see #setProporcao(double)
	 */
	public void setProporcao(Dimension tamanho) {
		if (tamanho.width <= 0 || tamanho.height <= 0)
			throw new AssertionError("Tamanho negativo ou nulo");
		Dimension padrao = getTamanhoPadrao();
		double propWidth = tamanho.width / (double) padrao.width;
		double propHeight = tamanho.height / (double) padrao.height;
		setProporcao(Math.min(propWidth, propHeight));
	}
	
	
	/**
	 * Calcula o tamanho final desta variável para uma certa proporação. 
	 * Basicamente multiplica o tamanho padrão pela proporção.
	 * @return o tamanho final.
	 * @see #getTamanhoPadrao()
	 * @see #setProporcao(double)
	 */
	@Override
	public Dimension getSize() {
		double prop = getProporcao();
		Dimension original = getTamanhoPadrao();
		double newWidth = original.width * prop;
		double newHeight = original.height * prop;
		return new Dimension((int) Math.ceil(newWidth), 
							 (int) Math.ceil(newHeight));
	}
	
	
	// Métodos protegidos auxiliares
	/**
	 * Retorna uma fonte igual à original, porém com a proporção atual aplicada
	 * ao seu tamanho.
	 * @param original  fonte original a ser modificada.
	 * @return  a fonte original com o tamnho modificado.
	 */
	protected Font proporcional(Font original) {
		double tamanho = original.getSize2D() * proporcao;
		return original.deriveFont((float) tamanho);
	}
	
	/**
	 * Retorna uma fonte igual à original, porém com a proporção atual aplicada
	 * ao seu tamanho.
	 * @param original  fonte original a ser modificada.
	 * @param tamanhoCaixa usado para calcular a fonte dependendo do tamanho da variável (tamanho(x,y))
	 * @param texto a ser escrito
	 * @param g Graphics
	 * @return  a fonte original com o tamnho modificado.
	 */
	protected Font proporcional(Font original, Dimension tamanhoCaixa, String texto, Graphics g) {
		Font nova;
		double tamanho;
		float razao = 0.6f;
		Rectangle2D rect;
		int loop = 100;
		do{
			tamanho = original.getSize2D() * proporcao + tamanhoCaixa.height * razao;
			nova = original.deriveFont((float) tamanho);
			FontMetrics metrics = g.getFontMetrics(nova);
			rect = metrics.getStringBounds(texto, g);
			razao = razao - 0.1f;
			loop--;
			if(loop < 0) break;
		}while(rect.getWidth() > tamanhoCaixa.width);
		
		return nova;
	}
	
	/**
	 * Retorna o tamanho real desta variável. 
	 * @return  o tamanho dado pelo container.
	 */
	protected Dimension getRealSize() {
		return super.getSize();
	}
	
	/**
	 * Chama o método paint da classe pai (JComponent).
	 * @param g
	 */
	protected void realPaint(Graphics g) {
		super.paint(g);
	}
	
	// Não vamos usar estes métodos, mesmo porque dependendo da proporção
	// uma determinada variável pode ficar "invisível" de tão pequena.
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	@Override
	public void setName(String name) {
		//throw new AssertionError("O nome não deve ser modificado.");
		this.nome = name == null ? "" : name;
	}
}
