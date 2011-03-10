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
 * Representa uma variável do tipo string.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarString extends VarLinha {
	private String valor = "";
	
	/**
	 * Cria uma nova variável do tipo string. 
	 * @param nome  nome da variável.
	 */
	public VarString(String nome) {
		super(nome, "TAMANHO NORMAL DE STRING!!! =)");
	}
	
	/**
	 * Cria uma nova variável do tipo string com cor e tamanhos customizados.
	 * @param nome  nome da variável.
	 */
	public VarString(String nome, Color cor, Dimension dimensao, Point posicao, Boolean mostraNome) {
		super(nome, "TAMANHO NORMAL DE STRING!!! =)");
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	/**
	 * Cria uma nova variável do tipo string com cor e tamanhos customizados.
	 * @param nome  nome da variável.
	 */
	public VarString(String nome, String valor, Color cor, Dimension dimensao, Point posicao) {
		this(nome);
		setValor(valor);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	
	/**
	 * Cria uma nova variável do tipo string. 
	 * @param nome   nome da variável.
	 * @param valor  valor inicial.
	 */
	public VarString(String nome, String valor) {
		this(nome);
		setValor(valor);
	}
	
	@Override
	public VarString criarCopia() {
		VarString ret = new VarString(nome, cor, dimensao, posicao, mostraNome);
		ret.valor = valor;
		ret.setTexto("'" + valor + "'");
		ret.lixo = lixo;
		ret.modificado = modificado;
		modificado = false;
		return ret;
	}
	
	@Override
	public String typeName() {
		return "string";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return new Color(1.0f, 1.0f, 0.8f, 1.0f);
		}
		return cor;
	}

	@Override
	public String getValor() {
		return valor;
	}
	
	@Override
	public void setValor(Object valor) {
		if (valor == null)
			lixo = true;
		else {
			lixo = false;
			this.valor = (String) valor;
			String v = new String(this.valor);
			v = v.replace("\\", "\\\\").replace("'", "\\'");
			v = v.replace("\n", "\\n").replace("\r", "\\r");
			v = v.replace("\t", "\\t");
			v = "'" + v + "'";
			this.setTexto(v);
		}
	}
	
	public int readData(BufferedReader buffer) throws IOException{
		try{
			String valor = null;
			String linha = buffer.readLine();
			
			if(linha == null) return -1;
			
			int tam = Integer.valueOf(linha);
			
			if(tam >= 0){
				char temp[] = new char[tam];
				
				if(buffer.read(temp) < temp.length)
					throw new IOException(
					"Não foi possível ler a string no arquivo!");
				
				if(buffer.readLine() == null)
					throw new IllegalArgumentException(
					"Arquivo no formato incorreto!");
				
				valor = new String(temp);
			}
			
			setValor(valor);
			
			return 0;
		}catch(NumberFormatException e){
			throw new IllegalArgumentException(
			"Não foi possível ler valor no arquivo!");
		}
	}
	
	public void writeData(Writer buffer) throws IOException{
		String valor = lixo ? getValor() + "\n": "";
		int tam = lixo ? getValor().length() : -1;
		
		buffer.write(Integer.toString(tam) + "\n");
		buffer.write(valor);
	}
}
