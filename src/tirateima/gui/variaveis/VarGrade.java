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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Variável abstrata que coloca outras variáveis dispostas numa grade.
 * 
 * @author felipe.lessa
 *
 */
@SuppressWarnings("serial")
public abstract class VarGrade extends Variavel {
	private Dimension tamPadrao;
	private double multiplicador;
	private GridLayout layout;
	private Insets insets = new Insets(2, 2, 2, 2);
	
	protected List<Variavel> variaveis;
	protected Janela[] janelas;
	
	/**
	 * Constrói um nova grade.
	 * @param nome       nome da variável.
	 * @param rows       número de linhas.
	 * @param cols       número de colunas.
	 * @param variaveis  variáveis contidas no record (nesta ordem).
	 */
	public VarGrade(String nome, int rows, int cols,
			List<Variavel> variaveis) {
		super(nome);
		
		assert rows * cols == variaveis.size();
		
		this.variaveis = variaveis;
		
		janelas = new Janela[variaveis.size()];
		layout = new GridLayout(rows, cols);
		setLayout(layout);
		int i = 0;
		for (Variavel v : variaveis) {
			Janela j = new Janela(v);
			janelas[i++] = j;
			add(j);
		}
	}
	
	/**
	 * Deve retornar o tamanho máximo desta grade.
	 */
	protected abstract Dimension getTamanhoMaximo();
	
	/**
	 * Retorna uma cópia da lista das variáveis.
	 * 
	 * @return Cópia da lista de variáveis dessa VarGrade.
	 */
	public List<Variavel> getVariaveis(){
		List<Variavel> lista = new ArrayList<Variavel>();
		for(Variavel v: variaveis)
			lista.add(v.criarCopia());
		return lista;			
	}
	
	@Override
	public String signature() {
		return typeName() + " " + getName() + dimensions();
	}
	
	@Override
	public String typeName() {
		Variavel v = variaveis.get(0);
		return v.typeName();
	}
	
	/**
	 * Retorna as dimensões dessa VarGrade, na notação de colchetes.
	 * 
	 * Ex.: Array de array de matrizes:
	 * 
	 * [10][15][2, 3]
	 * 
	 * @return
	 */
	public abstract String dimensions();
	
	@Override
	public Dimension getTamanhoPadrao() {
		if (tamPadrao == null) {
			// Define as proporções
			for (Janela j : janelas)
				j.setProporcao(1.0);
			
			// Acha nossas restrições
			Dimension minimo = layout.minimumLayoutSize(this);
			Dimension maximo = getTamanhoMaximo();
			
			// Acha o maior tamanho menor ou igual ao máximo e ao mínimo.
			int height = Math.min(minimo.height, maximo.height);
			int width = (minimo.width * height) / minimo.height;
			if (width > maximo.width) {
				width = maximo.width;
				height = (minimo.height * width) / minimo.width;
			}
			
			// Salva tudo
			tamPadrao = new Dimension(width, height);
			multiplicador = width / (double) minimo.width;
		}
		return tamPadrao;
	}
	
	@Override
	public Dimension getSize() {
		getTamanhoPadrao();
		return layout.minimumLayoutSize(this);
	}

	
	@Override
	public void setModificado(boolean modificado) {
		super.setModificado(modificado);
		for (Variavel v : variaveis)
			v.setModificado(modificado);
	}
	
	@Override
	public void setProporcao(double prop) {
		super.setProporcao(prop);
		
		int b = (int) Math.round(proporcao * 2.0);
		insets = new Insets(b, b, b, b);
		layout.setHgap(b);
		layout.setVgap(b);
		
		double nprop = prop * multiplicador;		
		for (Janela j : janelas)
			j.setProporcao(nprop);
		revalidate();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		realPaint(g);
	}
	
	@Override
	public Insets getInsets() {
		return insets;
	}
	
	public Variavel getCopiaTipo() {
		return variaveis.get(0).criarCopia();
	}
	
	public int readData(BufferedReader r) throws IOException{
		boolean inicio = true;
		for(Variavel v: variaveis)
			if(v.readData(r) == -1){
				if(inicio) return -1;
				else throw new IOException("Arquivo terminou inesperadamente!");
			}
			else
				inicio = false;
		return 0;
	}
	
	public void writeData(Writer w) throws IOException{
		for(Variavel v:variaveis)
			v.writeData(w);
	}
	
	/**
	 * Cria lista de variáveis de tamanho fixo a partir de uma
	 * variável modelo.
	 * 
	 * @param tamanho Tamanho da lista.
	 * @param tipo Variavel modelo.
	 * 
	 * @return A lista gerada.
	 * 
	 * @throws Exception
	 * 
	 * @author Luciano Santos
	 */
	protected static List<Variavel> criarVariaveis(int tamanho, Variavel tipo) throws Exception {
		Variavel temp = tipo.criarCopia();
		temp.setValor(null);
		temp.nome = "";
		Variavel lista[] = new Variavel[tamanho];
		for(int i = 0; i < lista.length; i++)
			lista[i] = temp.criarCopia();
		
		return Arrays.asList(lista);
	}
}
