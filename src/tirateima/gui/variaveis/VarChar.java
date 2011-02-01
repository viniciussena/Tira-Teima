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
public class VarChar extends VarLinha {
	private Character valor = ' ';
	
	/**
	 * Cria uma nova variável do tipo string. 
	 * @param nome  nome da variável.
	 */
	public VarChar(String nome, Color cor, Dimension dimensao, Point posicao) {
		super(nome, "\"\\n\"");
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Cria uma nova variável do tipo string. 
	 * @param nome  nome da variável.
	 */
	public VarChar(String nome) {
		super(nome, "\"\\n\"");
	}
	
	/**
	 * Cria uma nova variável do tipo string. 
	 * @param nome  nome da variável.
	 */
	public VarChar(String nome, Character valor, Color cor, Dimension dimensao, Point posicao) {
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
	public VarChar(String nome, Character valor) {
		this(nome);
		setValor(valor);
	}
	
	@Override
	public VarChar criarCopia() {
		VarChar ret;
		if (lixo)
			ret = new VarChar(nome, cor, dimensao, posicao);
		else
			ret = new VarChar(nome, valor, cor, dimensao, posicao);
		ret.modificado = modificado;
		modificado = false;
		return ret;
	}
	
	@Override
	public String typeName() {
		return "char";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return Color.orange;
		}
		return cor;
	}

	@Override
	public Character getValor() {
		return valor;
	}
	
	@Override
	public void setValor(Object valor) {
		if (valor == null)
			lixo = true;
		else {
			lixo = false;
			if(valor.toString().length() > 1){
				throw new RuntimeException("Só é aceito um caracter");
			}
			Character v = valor.toString().charAt(0);
			this.valor = v;
			String t;
			switch (v.charValue()) {
				case '\\':
					t = "\\\\";
					break;
				case '"':
					t = "\\\"";
					break;
				case '\n':
					t = "\\n";
					break;
				case '\r':
					t = "\\r";
					break;
				case '\b':
					t = "\\b";
					break;
				case '\t':
					t = "\\t";
					break;
				default:
					t = v.toString();
					break;
			}
			this.setTexto("'" + t + "'");
		}
	}
	
	public int readData(BufferedReader buffer) throws IOException{
		String linha = buffer.readLine();
		
		if(linha == null) return -1;
		
		if(linha.length() == 1){
			setValor(new Character(linha.toCharArray()[0]));
		}else{
			throw new IOException(
					"Arquivo no formato incorreto!");
		}
		
		return 0;
	}
	
	public void writeData(Writer buffer) throws IOException{
		String valor = lixo ? ((Character) getValor()).toString() : "";
		valor += "\n";
		
		buffer.write(valor);
	}
}
