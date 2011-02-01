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
 * Representa uma variável do tipo real.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarReal extends VarLinha {
	private double valor = -0.1;
	
	/**
	 * Cria uma nova variável do tipo real. 
	 * @param nome  nome da variável.
	 */
	public VarReal(String nome) {
		super(nome, "000.000000");
	}
	
	/**
	 * Cria uma nova variável do tipo real com cor e tamanhos customizados. 
	 * @param nome  nome da variável.
	 */
	public VarReal(String nome, Color cor, Dimension dimensao, Point posicao) {
		super(nome, "000.000000");
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Cria uma nova variável do tipo real com cor e tamanhos customizados. 
	 * @param nome  nome da variável.
	 */
	public VarReal(String nome, double valor, Color cor, Dimension dimensao, Point posicao) {
		this(nome);
		setValor(valor);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}

	/**
	 * Cria uma nova variável do tipo real. 
	 * @param nome   nome da variável.
	 * @param valor  valor inicial.
	 */
	public VarReal(String nome, double valor) {
		this(nome);
		setValor(valor);
	}
	
	@Override
	public VarReal criarCopia() {
		VarReal ret;
		if (lixo)
			ret = new VarReal(nome, cor, dimensao, posicao);
		else
			ret = new VarReal(nome, valor, cor, dimensao, posicao);
		ret.modificado = modificado;
		modificado = false;
		return ret;
	}
	
	@Override
	public String typeName() {
		return "real";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return new Color(1.0f, 0.5f, 1.0f, 1.0f);
		}
		return cor;
	}

	@Override
	public Double getValor() {
		return new Double(valor);
	}
	
	@Override
	public void setValor(Object valor) {
		if (valor == null)
			lixo = true;
		else {
			lixo = false;
			try{
				this.valor = (Double) valor;
			} catch (Exception e) {
				this.valor = Double.parseDouble(valor.toString());
			}
			this.setTexto(String.valueOf(this.valor));
		}
	}
	
	public int readData(BufferedReader buffer) throws IOException{
		String linha = buffer.readLine();
		
		if(linha == null) return -1;
		
		try{
			setValor(Double.valueOf(linha));
		}catch(NumberFormatException e){
			throw new IOException("O valor lido não representa " +
					"um real válido!");
		}
		
		return 0;
	}
	
	public void writeData(Writer buffer) throws IOException{
		String valor = lixo ? ((Double) getValor()).toString() : "";
		valor += "\n";
		
		buffer.write(valor);
	}
}
