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
import java.awt.Point;

/**
 * @author felipe.lessa e alex.rodrigo.oliveira
 *
 */
@SuppressWarnings("serial")
public class Janela extends Borda {
	private Variavel variavel;
	protected Point posicaoOriginal;

	/**
	 * Cria uma nova janela representando uma determinada variável.
	 * @param var   a variável a ser representada.
	 */
	public Janela(Variavel var) {
		super(var);
		assert(var != null);
		
		variavel = var;
		
		/** Verifica se será mostrado o nome da variável */
		if (variavel.getMostraNome()){
			titulo = variavel.getName();
		}
		else{
			titulo = "";
		}
		corFundo = variavel.getCorTitulo();
	}
	
	public void setCorFundo(Color c){
		this.corFundo = c != null ? c : variavel.getCorTitulo();
	}
	
	@Override
	protected Dimension getTamanhoParaProporcao() {		
		variavel.setProporcao(proporcao);
		return super.getTamanhoParaProporcao();
	}

	public Variavel getVariavel() {
		return variavel;
	}
	
}
