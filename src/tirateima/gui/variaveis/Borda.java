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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.Scrollable;

import tirateima.gui.Constantes;


/**
 * Um componente que implementa uma borda arredondada com um título.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public abstract class Borda extends JComponent implements Scrollable {
	protected String titulo = "";
	protected Font fonte = Constantes.FONTE_TITULO;
	protected Color corFundo = Color.BLACK;
	protected Color corFonte = Constantes.COR_FONTE;
	protected double proporcao = -1.0;
	
	private Dimension tamPadrao;
	private int alturaTexto;
	private Insets insets;
	private JComponent interno;
	private Font fonte_atual;
	

	/**
	 * Cria uma nova borda com o componente especificado.
	 * @param c   o componente a ser exibido.
	 */
	protected Borda(JComponent c) { 
		this.interno = c;
		setLayout(new BorderLayout());
		this.add(c, BorderLayout.CENTER);
	}
	
	/**
	 * Faz a analise da dimensao da janela e retorna o tamanho da mesma
	 * 
	 * @return tamPadrao (variavel do tipo Dimension)
	 */
	protected Dimension getTamanhoPadrao() {
		if (tamPadrao == null) {
			proporcao = 1.0;
			tamPadrao = getTamanhoParaProporcao();
		}
		return tamPadrao;
	}
	
	/**
	 * A proporção que será utilizada quando o desenho da janela for feito.
	 * @return a proporção (onde 1.0 é equivalente a 100%).
	 * @see #setProporcao(double)
	 */
	public double getProporcao() {
		return proporcao;
	}
	
	/**
	 * Retorna o tamanho desta janela para a proporção atual. Além disso,
	 * altera as variáveis utilizadas na hora da pintura.
	 * @return      o tamanho da janela.
	 */
	protected Dimension getTamanhoParaProporcao() {
		Dimension tamInterno = interno.getSize();
		
		float fonte_tam = (float) (fonte.getSize2D());
		fonte_atual = fonte.deriveFont(fonte_tam);
		
		Graphics g = getGraphics();
		FontMetrics metrics = g != null ?
				g.getFontMetrics(Constantes.FONTE_VARIAVEL) :
				getFontMetrics(Constantes.FONTE_VARIAVEL);
		
		int larguraTexto = metrics.stringWidth(titulo);
		int larguraMaior = Math.max(larguraTexto, tamInterno.width);
		alturaTexto = metrics.getHeight() - metrics.getDescent();

		double borda, altura, largura;
		borda   = Constantes.LARGURA_BORDA * proporcao;
		altura  = borda + metrics.getHeight() + borda + tamInterno.height + borda;
		largura = borda + larguraMaior + borda;
		
		int itopo  = (int) Math.round(borda + metrics.getHeight() + borda);
		int iborda = (int) Math.round(borda);
		insets = new Insets(itopo, iborda, iborda, iborda);
		return new Dimension((int) Math.round(largura),
				             (int) Math.round(altura));
	}
	
	
	/**
	 * Define a proporção que será utilizada. Deve ser um número positivo. 
	 * @param prop a nova proporção (onde 1.0 é equivalente a 100%).
	 * @see #getProporcao()
	 */
	public void setProporcao(double prop) {
		if (prop <= 0) 
			throw new AssertionError("Proporção negativa ou nula.");
		proporcao = prop;
		this.setSize(getTamanhoParaProporcao());
		this.validate();
	}
	
	/**
	 * Desenha a nossa borda, com o título e tudo mais.
	 * @see #paint(Graphics)
	 * @see #paintChildren(Graphics)
	 */
	@Override 
	public void paintBorder(Graphics g) {
		// Não precisamos nos preocupar com o desenho do componente
		// porque ele será feito por nós em paintChildren.
		Graphics2D g2d = (Graphics2D) g;

		int arc = (int) (5 * proporcao);
		Dimension tamanho = getSize();
		
		g2d.setColor(corFundo);
		g2d.fillRoundRect(0, 0, tamanho.width, tamanho.height, arc, arc);
		
		g2d.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
		Stroke s = g2d.getStroke();
		g2d.setStroke(new BasicStroke(Math.min((float) proporcao, 1.5f)));
		g2d.drawRoundRect(0, 0, tamanho.width, tamanho.height, arc, arc);
		g2d.setStroke(s);

		g2d.setColor(corFonte);
		g2d.setFont(fonte_atual);
		float offset = (float) (Constantes.LARGURA_BORDA * proporcao);
		g2d.drawString(titulo, offset, offset + alturaTexto);
	}
	
	@Override
	public void paint(Graphics g) {
		// Liga o antialiasing antes de pintar tudo 
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					         RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2d);
	}
	
	@Override
	public Insets getInsets() {
		if (insets == null) {
			/**
			 *  Força os cálculos que dependem de um Graphics a
			 *  serem feitos.
			 */
			getTamanhoPadrao();
			setProporcao(1.0);
		}
		return insets;
	}
	
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		super.setMinimumSize(d);
		super.setPreferredSize(d);
		super.setMaximumSize(d);
	}
	
	
	public void setCorFundo(Color c){
		if(c != null){
			this.corFundo = c;
			repaint();
		}
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo != null ? titulo : "";
		repaint();
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	// Interface Scrollable
	public Dimension getPreferredScrollableViewportSize() {
		getInsets(); // Força o cálculo do tamanho certo
		return getSize();
	}

	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return (int) Math.ceil(proporcao);
	}

	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return (int) Math.ceil(proporcao);
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
}
