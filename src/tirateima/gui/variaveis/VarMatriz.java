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
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tirateima.Par;


/**
 * Variável que contém uma coleção homogênea de variáveis indexadas por dois
 * índices inteiros (matriz).
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarMatriz extends VarGrade {
	private static List<Variavel> concatenar(List<List<Variavel>> vars) {
		ArrayList<Variavel> ret = new ArrayList<Variavel>();
		for (List<Variavel> l : vars)
			ret.addAll(l);
		return ret;
	}

	private int cols;
	private int rows;
	
	/**
	 * Constrói uma nova matriz com as variáveis definidas. Cada variável deve
	 * ter um nome que corresponda à posição dela na matriz, todas devem 
	 * ser do mesmo tipo, e a matriz deve ser quadrada, porém essas propriedades
	 * não são verificadas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis.
	 */
	public VarMatriz(String nome, List<List<Variavel>> variaveis) throws Exception {
		super(nome, variaveis.size(), variaveis.get(0).size(), 
		      concatenar(variaveis));
		this.cols = variaveis.get(0).size();
		this.rows = variaveis.size();
	}
	
	/**
	 * Constrói uma nova matriz com as variáveis definidas. Cada variável deve
	 * ter um nome que corresponda à posição dela na matriz, todas devem 
	 * ser do mesmo tipo, e a matriz deve ser quadrada, porém essas propriedades
	 * não são verificadas.
	 * @param nome       nome da variável.
	 * @param cols       número de colunas.
	 * @param rows       número de linhas.
	 * @param variaveis  variáveis.
	 */
	public VarMatriz(String nome, int rows, int cols, List<Variavel> variaveis, Color cor, Color corExterna, Dimension dimensao, Point posicao) {
		super(nome, rows, cols, variaveis);
		this.rows = rows;
		this.cols = cols;
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Constrói um novo array vazio a partir de uma classe.
	 * @param nome       nome da variável.
	 * @param rows       número de linhas.
	 * @param cols       número de colunas.
	 * @param tipo       tipo das variáveis (deve derivar de Variavel).
	 * @throws Exception lança uma exceção caso haja algum erro ao criar
	 *                   as variáveis. 
	 */
	@SuppressWarnings("unchecked")
	public VarMatriz(String nome, int rows, int cols, Class tipo) throws Exception {
		super(nome, rows, cols, criarVariaveis(rows, cols, tipo));
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * Constrói uma nova matriz vazia a partir de uma variável modelo.
	 * Utilizado para criar matriz de record.
	 * @param nome       nome da variável.
	 * @param rows       número de linhas.
	 * @param cols       número de colunas.
	 * @param tipo       tipo das variáveis (deve derivar de Variavel).
	 * @throws Exception lança uma exceção caso haja algum erro ao criar
	 *                   as variáveis. 
	 */
	public VarMatriz(String nome, int rows, int cols, Variavel tipo) throws Exception {
		super(nome, rows, cols, criarVariaveis(rows*cols, tipo));
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Constrói uma nova matriz vazia a partir de uma variável modelo com cor e tamanhos customizados
	 * Utilizado para criar matriz de record.
	 * @param nome       nome da variável.
	 * @param rows       número de linhas.
	 * @param cols       número de colunas.
	 * @param tipo       tipo das variáveis (deve derivar de Variavel).
	 * @throws Exception lança uma exceção caso haja algum erro ao criar
	 *                   as variáveis. 
	 */
	public VarMatriz(String nome, int rows, int cols, Variavel tipo, Color cor, Dimension dimensao, Point posicao) throws Exception {
		super(nome, rows, cols, criarVariaveis(rows*cols, tipo));
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Constrói uma nova matriz vazia a partir de uma variável modelo com cor e tamanhos customizados
	 * Utilizado para criar matriz de record.
	 * @param nome       nome da variável.
	 * @param rows       número de linhas.
	 * @param cols       número de colunas.
	 * @param tipo       tipo das variáveis (deve derivar de Variavel).
	 * @throws Exception lança uma exceção caso haja algum erro ao criar
	 *                   as variáveis. 
	 */
	public VarMatriz(String nome, int rows, int cols, Variavel tipo, Color cor, Color corExterna, Dimension dimensao, Point posicao) throws Exception {
		super(nome, rows, cols, criarVariaveis(rows*cols, tipo));
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
		this.rows = rows;
		this.cols = cols;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Variavel> criarVariaveis(int rows, int cols, Class tipo) 
			throws Exception 
	{
		int tamanho = rows * cols;
		Variavel[] ret = new Variavel[tamanho];
		Constructor<Variavel> constr = tipo.getConstructor(String.class);
		for (int i = 0; i < tamanho; i++) {
			int coluna = i % cols;
			int linha  = i / cols;
			ret[i] = constr.newInstance((linha+1) + "," + (coluna+1));
		}
		return Arrays.asList(ret);
	}
	
	@Override
	public VarMatriz criarCopia() {
		int tamanho = variaveis.size();
		Variavel[] novo = new Variavel[tamanho];
		for (int i = 0; i < tamanho; i++)
			novo[i] = variaveis.get(i).criarCopia();
		try {
			VarMatriz ret = new VarMatriz(nome, rows, cols, Arrays.asList(novo), cor, corExterna, dimensao, posicao);
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
	public String dimensions() {
		StringBuilder builder = new StringBuilder();
		
		Variavel v = variaveis.get(0);
		if (v instanceof VarGrade) {
			builder.append(((VarGrade) v).dimensions());
		}
		
		builder.append("[");
		builder.append(Integer.toString(rows));
		builder.append(",");
		builder.append(Integer.toString(cols));
		builder.append("]");
		
		return builder.toString();
	}
	
	@Override
	public Color getCorTitulo() {
		if(corExterna != null){
			return corExterna;
		}
		if(cor == null){
			return Color.PINK;
		}
		return cor;
	}
	
	@Override
	public Dimension getTamanhoMaximo() {
		return new Dimension(700, 700);
	}
	
	@Override
	public Object[] getValor() {
		Object[] ret = new Object[variaveis.size()];
		int i = 0;
		for (Variavel v : variaveis)
			ret[i++] = v.getValor();
		return ret;
	}

	/**
	 * Define um ou mais valores das variáveis desta matriz. Se valor for null,
	 * todas as variáveis são limpas como se não tivessem qualquer valor. Caso
	 * contrário, valor deve implementar Map<Par<Integer, Integer>, Object> 
	 * onde cada chave corresponde a um índice da matriz (variáveis que não 
	 * sejam referenciadas não serão modificadas).
	 * 
	 * Por exemplo, depois de criar uma matriz 5x7 (5 linhas por 7 colunas) de
	 * inteiros, você pode modificar o último elemento para conter o número 42
	 * fazendo algo como:
	 * 
	 * Integer valor;
	 * Par<Integer,Integer> par;
	 * Map<Par<Integer,Integer>, Object> map;
	 * 
	 * par   = new Par<Integer,Integer>(4,6);
	 * valor = new Integer(42);
	 * map   = new HashMap<Par<Integer,Integer>, Object>();
	 * map.put(par, valor);
	 * matriz.setValor(map);
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValor(Object valor) {
		if (valor == null) {
			for (Variavel v : variaveis)
				v.setValor(null);
		} else {
			Map<Par<Integer,Integer>, Object> map;
			map = (Map<Par<Integer,Integer>, Object>) valor;
			for (Entry<Par<Integer,Integer>, Object> entry : map.entrySet()) {
				Par<Integer,Integer> par = entry.getKey();
				variaveis.get(par.fst * cols + par.snd).setValor(entry.getValue());
			}
		}
	}
}
