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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import tirateima.gui.Constantes;


/**
 * Classe abstrata que escreve uma linha de texto única. Usada por diversas
 * outras classes.
 * 
 * @author felipe.lessa
 * @author Andrew Biller
 *
 */
@SuppressWarnings("serial")
public abstract class VarLinha extends Variavel {
	// Começamos com lixo na memória
	protected boolean lixo = true;
	
	private String base;
	private String texto = "";
	
	// TODO: Fazer um cache por classe, e não por variável
	protected Dimension tamPadrao = null;
	
	/**
	 * Cria uma nova variável. O tamanho padrão será determinado a partir do
	 * texto base (para que este caiba).
	 * @param nome  nome da variável.
	 * @param base  string com o texto base.
	 */
	protected VarLinha(String nome, String base) {
		super(nome);
		this.base = base;
	}
	
	/**
	 * Define o texto que será exibido quando esta variável for pintada.
	 * Se o texto for null, a variável será exibida como se não houvesse
	 * sido inicializada.
	 * @param texto   string com o texto a ser exibido.
	 */
	protected void setTexto(String texto) {
		this.modificado = true;
		this.texto = texto;
		this.setToolTipText("Valor da variável: " + texto);
		repaint();
	}
	
	@Override
	public String signature() {
		return typeName() + " " + getName();
	}
	
	@Override
	public Dimension getTamanhoPadrao() {
		if (tamPadrao == null) {
			Graphics g = getGraphics();
			
			FontMetrics metrics = g != null ?
					g.getFontMetrics(Constantes.FONTE_VARIAVEL) :
					getFontMetrics(Constantes.FONTE_VARIAVEL);
			
			Rectangle2D bounds = metrics.getStringBounds(base, g);
			int borda = Constantes.LARGURA_BORDA * 2;
			if(dimensao != null){
				tamPadrao = dimensao;
			}else{
				int largura = (int) Math.ceil(borda + bounds.getWidth());
				int altura = (int) Math.ceil(borda + bounds.getHeight());
				tamPadrao = new Dimension(largura, altura);
			}
		}
		return tamPadrao;
	}

	@Override
	/**
	 * Desenha as linhas diagonais quando a variável tem lixo, o valor quando este for definido e uma bolinha quando a variável for anulada.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		//se for lixo
		if (lixo) {
			Stroke s = g2d.getStroke();
			g2d.setStroke(new BasicStroke((float) proporcao / 2.0f));
			g2d.setColor(Color.GRAY);
			Dimension size = getRealSize(); //original
			int max = size.width + size.height;
			int incr = (int) Math.ceil(proporcao*6);
			for (int x = 0 - size.height - 4; x < max; x += incr) {
				g2d.drawLine(x, 0, x + size.height, size.height);
			}
			g2d.setStroke(s);
		//se houver um valor (não nulo)
		} else if(this.texto != null){			
			Font fonte = proporcional(Constantes.FONTE_VARIAVEL_PERSONALIZADA, getRealSize(), texto, g);
			//Redimensiona a fonte conforme tamanho da variável
			FontMetrics metrics = g.getFontMetrics(fonte);
			Rectangle2D bounds = metrics.getStringBounds(texto, g);
			Dimension my_size = getRealSize();
			//Encontra ponto central
			int center_x = my_size.width / 2;
			int center_y = my_size.height / 2;
			
			int pos_x = center_x - (int) bounds.getWidth() / 2;
			int pos_y = center_y + (int) bounds.getHeight() / 2;
			
			g2d.setFont(fonte);
			g2d.setColor(Constantes.COR_FONTE);
			g2d.drawString(texto, pos_x, pos_y);
			
			Dimension size = getRealSize();
			if (bounds.getWidth() > size.getWidth()) {
				int fonte_height = (int) Math.ceil(bounds.getHeight());
				float offset = (float) (Constantes.LARGURA_BORDA * proporcao);
				int y = (int) Math.ceil(fonte_height + offset*0.7);
				int s = (int) Math.ceil(offset);
				double x = size.getWidth();
				g2d.fillOval((int) Math.ceil(x - 2.0*s), y, s, s);
				g2d.fillOval((int) Math.ceil(x - 3.5*s), y, s, s);
				g2d.fillOval((int) Math.ceil(x - 5.0*s), y, s, s);
			}
		//se o valor for nulo (variável anulada) escreve o caracter '•' centralizado
		} else {			 
			this.texto = "•";
			Font fonte = proporcional(Constantes.FONTE_VARIAVEL_PERSONALIZADA, getRealSize(), texto, g);
			g2d.setFont(fonte);
			g2d.setColor(Constantes.COR_FONTE);

			FontMetrics metrics = g.getFontMetrics(fonte);
			Rectangle2D bounds = metrics.getStringBounds(texto, g);
			int fonte_height = (int) Math.ceil(bounds.getHeight());
			float offset = (float) ((getRealSize().getWidth()/2 - 3) * proporcao);
			g2d.drawString(texto, offset, fonte_height);
			
			Dimension size = getRealSize();
			if (bounds.getWidth() > size.getWidth()) {
				/*
				bounds = metrics.getStringBounds("...", g2d);
				g2d.drawString("...", 
				               (float) (size.getWidth() - bounds.getWidth()), 
				               fonte_height + offset*2);*/
				int y = (int) Math.ceil(fonte_height + offset*0.7);
				int s = (int) Math.ceil(offset);
				double x = size.getWidth();
				g2d.fillOval((int) Math.ceil(x - 2.0*s), y, s, s);
				g2d.fillOval((int) Math.ceil(x - 3.5*s), y, s, s);
				g2d.fillOval((int) Math.ceil(x - 5.0*s), y, s, s);
			}
		}
		
	}
	
	/**
	 * Retorna uma representação textual do valor desta variável.
	 * 
	 * @return O valor na forma de uma string.
	 */
	public String getTexto(){
		return texto;
	}
}
