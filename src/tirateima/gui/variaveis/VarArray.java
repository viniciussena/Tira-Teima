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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Variável que contém uma coleção homogênea de variáveis indexadas por um
 * índice inteiro (vetor).
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public class VarArray extends VarGrade {
	/**
	 * Constrói um novo array com as variáveis definidas. Cada variável deve
	 * ter um nome que corresponda à posição dela no array, e todas devem 
	 * ser do mesmo tipo, porém essas propriedades não são verificadas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis.
	 */
	public VarArray(String nome, List<Variavel> variaveis, Color cor, Dimension dimensao, Point posicao, Boolean mostraNome) {
		super(nome, 1, variaveis.size(), variaveis);
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	/**
	 * Constrói um novo array com as variáveis definidas. Cada variável deve
	 * ter um nome que corresponda à posição dela no array, e todas devem 
	 * ser do mesmo tipo, porém essas propriedades não são verificadas.
	 * @param nome       nome da variável.
	 * @param variaveis  variáveis.
	 */
	public VarArray(String nome, List<Variavel> variaveis, Color cor,  Color corExterna, Dimension dimensao, Point posicao, Boolean mostraNome) {
		super(nome, 1, variaveis.size(), variaveis);
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	/**
	 * Constrói um novo array vazio a partir de uma classe.
	 * @param nome       nome da variável.
	 * @param tamanho    tamanho do array.
	 * @param tipo       tipo das variáveis (deve derivar de Variavel).
	 * @throws Exception lança uma exceção caso haja algum erro ao criar
	 *                   as variáveis. 
	 */
	public VarArray(String nome, int tamanho, Class<Variavel> tipo, Boolean mostraNome) throws Exception {
		super(nome, 1, tamanho, criarVariaveis(tamanho, tipo));
		super.mostraNome = mostraNome;
	}
	
	/**
	 * Mesmo método anterior, mas recebe a variável pronta em
	 * vez de seu tipo. Útil para instanciar arrays de records.
	 * 
	 * @param nome Nome da variável.
	 * @param tamanho Tamanho do array.
	 * @param tipo Instância de variável usada como base para os elementos do array.
	 * 
	 * @throws Exception Lança exceção caso haja erro ao criar as variáveis.
	 * 
	 * @author Luciano Santos
	 */
	public VarArray(String nome, int tamanho, Variavel tipo) throws Exception{
		super(nome, 1, tamanho, criarVariaveis(tamanho, tipo));
	}
	
	/**
	 * Mesmo método anterior, mas recebe a variável pronta em
	 * vez de seu tipo. Útil para instanciar arrays de records com cor e tamanhos customizados
	 * 
	 * @param nome Nome da variável.
	 * @param tamanho Tamanho do array.
	 * @param tipo Instância de variável usada como base para os elementos do array.
	 * 
	 * @throws Exception Lança exceção caso haja erro ao criar as variáveis.
	 * 
	 * @author Luciano Santos
	 */
	public VarArray(String nome, int tamanho, Variavel tipo, Color cor, Dimension dimensao, Point posicao) throws Exception{
		super(nome, 1, tamanho, criarVariaveis(tamanho, tipo));
		super.cor = cor;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Mesmo método anterior, mas recebe a variável pronta em
	 * vez de seu tipo. Útil para instanciar arrays de records com cor e tamanhos customizados
	 * 
	 * @param nome Nome da variável.
	 * @param tamanho Tamanho do array.
	 * @param tipo Instância de variável usada como base para os elementos do array.
	 * 
	 * @throws Exception Lança exceção caso haja erro ao criar as variáveis.
	 * 
	 * @author Luciano Santos
	 */
	public VarArray(String nome, int tamanho, Variavel tipo, Color cor, Color corExterna, Dimension dimensao, Point posicao) throws Exception{
		super(nome, 1, tamanho, criarVariaveis(tamanho, tipo));
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
	}
	
	/**
	 * Mesmo método anterior, mas com possibilidade de não mostrar nome
	 * 
	 * @param nome Nome da variável.
	 * @param tamanho Tamanho do array.
	 * @param tipo Instância de variável usada como base para os elementos do array.
	 * 
	 * @throws Exception Lança exceção caso haja erro ao criar as variáveis.
	 * 
	 * @author Luciano Santos
	 */
	public VarArray(String nome, int tamanho, Variavel tipo, Color cor, Color corExterna, Dimension dimensao, Point posicao, boolean mostraNome) throws Exception{
		super(nome, 1, tamanho, criarVariaveis(tamanho, tipo));
		super.cor = cor;
		super.corExterna = corExterna;
		super.dimensao = dimensao;
		super.posicao = posicao;
		super.mostraNome = mostraNome;
	}
	
	private static List<Variavel> criarVariaveis(int tamanho, Class<Variavel> tipo) throws Exception {
		Variavel[] ret = new Variavel[tamanho];
		Constructor<Variavel> constr = tipo.getConstructor(String.class);
		for (int i = 0; i < tamanho; i++)
			ret[i] = constr.newInstance(String.valueOf(i+1));
		return Arrays.asList(ret);
	}	

	@Override
	public String dimensions() {
		StringBuilder builder = new StringBuilder();
		
		Variavel v = variaveis.get(0);
		if (v instanceof VarGrade) {
			builder.append(((VarGrade) v).dimensions());
		}
		
		builder.append("[");
		builder.append(Integer.toString(variaveis.size()));
		builder.append("]");
		
		return builder.toString();
	}
	
	@Override
	public VarArray criarCopia() {
		int tamanho = variaveis.size();
		Variavel[] novo = new Variavel[tamanho];
		for (int i = 0; i < tamanho; i++)
			novo[i] = variaveis.get(i).criarCopia();
		try {
			VarArray ret = new VarArray(nome, Arrays.asList(novo), cor, corExterna, dimensao, posicao, mostraNome);
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
	public Color getCorTitulo() {
		if(corExterna != null){
			return corExterna;
		}
		if(cor == null){
			return new Color(0.7f, 0.7f, 0.7f, 1.0f);
		}
		return cor;
	}
	
	@Override
	public Dimension getTamanhoMaximo() {
		return new Dimension(7000, 1500);
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
	 * Define um ou mais valores das variáveis deste array. Se valor for null,
	 * todas as variáveis são limpas como se não tivessem qualquer valor. Caso
	 * contrário, valor deve implementar Map<Integer, Object> onde cada chave
	 * corresponde a um índice a partir de zero (variáveis que não sejam
	 * referenciadas não serão modificadas).
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValor(Object valor) {
		if (valor == null) {
			for (Variavel v : variaveis)
				v.setValor(null);
		} else {
			Map<Integer, Object> map = (Map<Integer, Object>) valor;
			for (Entry<Integer, Object> entry : map.entrySet())
				variaveis.get(entry.getKey()).setValor(entry.getValue());
		}
	}
}
