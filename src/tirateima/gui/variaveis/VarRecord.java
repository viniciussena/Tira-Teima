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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Variável que contém uma coleção heterogênea de variáveis nomeadas (estrutura
 * composta).
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarRecord extends VarGrade {
	private String typeName;
	private Map<String, Variavel> porNome;

	/**
	 * Constrói um novo record com as variáveis definidas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis contidas no record (nesta ordem).
	 */
	public VarRecord(String typeName, String nome, List<Variavel> variaveis) {
		super(nome, variaveis.size(), 1, variaveis);
		
		this.typeName = typeName;
		
		this.porNome = new HashMap<String, Variavel>();
		
		int count = 0;
		for (Variavel v : variaveis) {
			this.porNome.put(v.getName(), v);
			count++;
		}
	}
	
	/**
	 * Constrói um novo record com cor e tamanhos customizados e com as variáveis definidas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis contidas no record (nesta ordem).
	 */
	public VarRecord(String typeName, String nome, List<Variavel> variaveis, Color cor, Dimension dimensao, Point posicao) {
		super(nome, variaveis.size(), 1, variaveis);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		
		this.typeName = typeName;
		
		this.porNome = new HashMap<String, Variavel>();
		
		int count = 0;
		for (Variavel v : variaveis) {
			this.porNome.put(v.getName(), v);
			count++;
		}
	}
	
	/**
	 * Constrói um novo record com cor e tamanhos customizados e com as variáveis definidas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis contidas no record (nesta ordem).
	 */
	public VarRecord(String typeName, String nome, List<Variavel> variaveis, Color cor,  Color corExterna, Dimension dimensao, Point posicao) {
		super(nome, variaveis.size(), 1, variaveis);
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
		
		this.typeName = typeName;
		
		this.porNome = new HashMap<String, Variavel>();
		
		int count = 0;
		for (Variavel v : variaveis) {
			this.porNome.put(v.getName(), v);
			count++;
		}
	}
	
	@Override
	public VarRecord criarCopia() {
		int tamanho = variaveis.size();
		Variavel[] novo = new Variavel[tamanho];
		for (int i = 0; i < tamanho; i++)
			novo[i] = variaveis.get(i).criarCopia();
		try {
			VarRecord ret = new VarRecord(typeName, nome, Arrays.asList(novo), cor, corExterna, dimensao, posicao);
			ret.modificado = modificado;
			modificado = false;
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	@Override
	public String typeName() {
		return typeName;
	}
	
	@Override
	public String dimensions() {
		return "";
	}
	
	@Override
	public Color getCorTitulo() {
		if(cor == null){
			return new Color(0.5f, 0.5f, 1.0f, 1.0f);
		}
		return cor;
	}
	
	@Override
	public Dimension getTamanhoMaximo() {
		return new Dimension(10000, 15000);
	}
	
	@Override
	public Map<String, Object> getValor() {
		Map<String, Object> valores = new HashMap<String, Object>(porNome.size());
		for (Entry<String, Variavel> entry : porNome.entrySet())
			valores.put(entry.getKey(), entry.getValue().getValor());
		return valores;
	}

	/**
	 * Define um ou mais valores das variáveis deste record. Se valor for null,
	 * todas as variáveis são limpas como se não tivessem qualquer valor. Caso
	 * contrário, valor deve implementar Map<String, Object> onde cada chave
	 * corresponde a uma variável deste record (variáveis que não sejam
	 * referenciadas não serão modificadas).
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValor(Object valor) {
		if (valor == null) {
			for (Variavel v : porNome.values())
				v.setValor(null);
		} else {
			Map<String, Object> map = (Map<String, Object>) valor;
			for (Entry<String, Object> entry : map.entrySet())
				porNome.get(entry.getKey()).setValor(entry.getValue());
		}
	}
	
	public Variavel getCopiaCampo(String nome) {
		return porNome.get(nome).criarCopia();
	}
}
