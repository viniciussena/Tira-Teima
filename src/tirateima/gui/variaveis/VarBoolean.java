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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * Representa uma variável do tipo boolean.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarBoolean extends VarLinha {
	private boolean valor = false;
	
	/**
	 * Cria uma nova variável do tipo boolean. 
	 * @param nome  nome da variável.
	 */
	public VarBoolean(String nome) {
		super(nome, "false");
	}
	
	/**
	 * Cria uma nova variável do tipo boolean com cor e tamanhos customizados. 
	 * @param nome   nome da variável.
	 * @param valor  valor inicial.
	 */
	public VarBoolean(String nome, Color cor, Dimension dimensao, Point posicao) {
		super(nome, "false");
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Cria uma nova variável do tipo boolean com cor e tamanhos customizados. 
	 * @param nome   nome da variável.
	 * @param valor  valor inicial.
	 */
	public VarBoolean(String nome, boolean valor, Color cor, Dimension dimensao, Point posicao) {
		this(nome);
		setValor(valor);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	@Override
	public VarBoolean criarCopia() {
		VarBoolean ret;
		if (lixo)
			ret = new VarBoolean(nome, cor, dimensao, posicao);
		else
			ret = new VarBoolean(nome, valor, cor, dimensao, posicao);
		ret.modificado = modificado;
		modificado = false;
		return ret;
	}
	
	@Override
	public String typeName() {
		return "boolean";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return new Color(0.5f, 1.0f, 0.5f, 1.0f);
		}
		return cor;
	}

	@Override
	public Boolean getValor() {
		return new Boolean(valor);
	}
	
	@Override
	public void setValor(Object valor) {
		if (valor == null)
			lixo = true;
		else {
			lixo = false;
			this.valor = (Boolean) valor;
			this.setTexto(String.valueOf(this.valor));
		}
	}
	
	public int readData(BufferedReader buffer) throws IOException{
		String linha = buffer.readLine();

		if(linha == null) return -1;
		
		if(linha.compareToIgnoreCase("true") == 0){
			setValor(new Boolean(true));
		}else if(linha.compareToIgnoreCase("false") == 0){
			setValor(new Boolean(false));
		}else{
			throw new IOException(
					"O arquivo não contém um campo booleano válido!");
		}
		
		return 0;
	}
	
	public void writeData(Writer buffer) throws IOException{
		String valor = lixo ? ((Boolean) getValor()).toString() : "";
		valor += "\n";
		
		buffer.write(valor);
	}
}
