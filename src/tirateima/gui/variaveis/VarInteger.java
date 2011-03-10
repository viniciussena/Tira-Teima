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
 * Representa uma variável do tipo integer.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarInteger extends VarLinha {
	private int valor = 0xdeadbeef;
	private static String base = String.valueOf(Integer.MAX_VALUE);
	
	/**
	 * Cria uma nova variável do tipo integer. 
	 * @param nome  nome da variável.
	 */
	public VarInteger(String nome) {
		super(nome, base);
	}
	
	
	/**
	 * Cria uma nova variável do tipo integer com cor e tamanhos customizados.  
	 * @param nome  nome da variável.
	 */
	public VarInteger(String nome, Color cor, Dimension dimensao, Point posicao, Boolean mostraNome) {
		super(nome, base);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	
	/**
	 * Cria uma nova variável do tipo integer com cor e tamanhos customizados.  
	 * @param nome  nome da variável.
	 */
	public VarInteger(String nome, int valor, Color cor, Dimension dimensao, Point posicao, Boolean mostraNome) {
		this(nome);
		setValor(valor);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	/**
	 * Cria uma nova variável do tipo integer. 
	 * @param nome   nome da variável.
	 * @param valor  valor inicial.
	 */
	public VarInteger(String nome, int valor) {
		this(nome);
		setValor(valor);
	}
	
	@Override
	public VarInteger criarCopia() {
		VarInteger ret;
		if (lixo)
			ret = new VarInteger(nome, cor, dimensao, posicao, mostraNome);
		else
			ret = new VarInteger(nome, valor, cor, dimensao, posicao, mostraNome);
		ret.modificado = modificado;
		modificado = false;
		return ret;
	}
	
	@Override
	public String typeName() {
		return "int";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return new Color(1.0f, 0.5f, 0.5f, 1.0f);
		}
		return cor;
	}

	@Override
	public Integer getValor() {
		return valor;
	}
	
	@Override
	public void setValor(Object valor) {
		if (valor == null)
			lixo = true;
		else {
			lixo = false;
			this.valor = Integer.parseInt(valor.toString());
			this.setTexto(String.valueOf(this.valor));
		}
	}
	
	public int readData(BufferedReader buffer) throws IOException{
		String linha = buffer.readLine();
		
		if (linha == null) return -1;
		
		try{
			setValor(Integer.valueOf(linha));
		}catch(NumberFormatException e){
			throw new IOException("O valor não representa " +
					"um inteiro válido!");
		}
		
		return 0;
	}
	
	public void writeData(Writer buffer) throws IOException{
		String valor = lixo ? ((Integer) getValor()).toString() : "";
		valor += "\n";
		
		buffer.write(valor);
	}
}
