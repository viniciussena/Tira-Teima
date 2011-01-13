/* Copyright (C) 2007  Felipe A. Lessa
 * Copyright (C) 2007  Alex Rodrigo Oliveira
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
package tirateima.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * Algumas constantes sendo usadas em todo o programa.
 * 
 * @author felipe.lessa e alex.rodrigo.oliveira
 *
 */
public final class Constantes {
	/**
	 * Cor normal de fundo.
	 */
	public static final Color COR_FUNDO_NORMAL = Color.WHITE;
	
	
	/**
	 * Cor da fonte.
	 */
	public static final Color COR_FONTE = Color.BLACK;
	
	
	/**
	 * Cor do fundo quando modificada.
	 */
	public static final Color COR_FUNDO_MODIFICADO = new Color(0.8f, 1.0f, 0.8f);
	
	
	/**
	 * Cor da varável quando destacada.
	 */
	public static final Color COR_DESTAQUE = new Color(1.0f, 0.0f, 0.1f);
	
	
	/**
	 * Largura da borda das janelas.
	 */
	public static final int LARGURA_BORDA = 2;
	
	
	/**
	 * Fonte do título das janelas.
	 */
	public static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 12);
	
	
	/**
	 * Fonte usada no texto de variáveis simples.
	 */
	public static final Font FONTE_VARIAVEL = new Font("Arial", Font.BOLD, 8);
	
	
	/**
	 * Fonte usada no texto de variáveis que sofreram mudança no tamanho (tamanho(x,y)
	 */
	public static final Font FONTE_VARIAVEL_PERSONALIZADA = new Font("Arial", Font.BOLD, 1);
	
	/**
	 * Largura máxima de um Mostrador.
	 */
	public static final int LARGURA_MAXIMA = 700;
}
