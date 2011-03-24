/* Copyright (C) 2007  Felipe A. Lessa e Luciano H. O. Santos
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tirateima.IEstado;
import tirateima.controlador.TiraTeimaLanguageException;


/**
 * 
 * Mostra um conjunto de variáveis.
 * 
 * @author Felipe Lessa
 * @author Luciano Santos
 * @author Andrew Biller
 * @author Vinícius
 */
@SuppressWarnings("serial")
public class Mostrador extends JScrollPane implements IEstado {
	private static final Comparator<Color> comparadorColor = 
		new Comparator<Color>() {
			public int compare(Color c1, Color c2) {
				return new Integer(c1.getRGB()).compareTo(c2.getRGB());
			}
		};
	
	private static final Comparator<Variavel> comparadorVariavel = 
		new Comparator<Variavel>() {
			public int compare(Variavel v1, Variavel v2) {
				return v1.getName().compareToIgnoreCase(v2.getName());
			}
		};
	
	/** Componentes representando elementos do programa em execução no Tira-Teima */
	private Variaveis vars = new Variaveis();
	private Setas setas = new Setas();
	private List<Texto> textos = new ArrayList<Texto>();
	
	/** Componentes removidos guardados para fins de restauração (reversão de comando) */
	private Stack<Variavel> variaveisRemovidas = new Stack<Variavel>();
	private Stack<Seta> setasRemovidas = new Stack<Seta>();

	/** Conjunto de painéis que compõem o mostrador */
	private JPanel painelPrincipal;
	private Painel painelVars = null;
	private Function function = null;
	private JPanel tudo = new JPanel();
	
	/** Parâmetros de detalhes de visualização do mostrador*/
	private double prop = -100.0;
	public static enum zoom {AUMENTA, REINICIA, DIMINUI}
	public Enum<zoom> acaoZoom = null;
	private Point ultimoPonto;
	
	/**
	 * Cria um novo mostrador vazio.
	 */
	public Mostrador() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
		      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new GridBagLayout());
		this.viewport.setView(painelPrincipal);
	}
	
	/**
	 * A proporção atual de todas as janelas.
	 * @return  a proporção (1.0 = 100%).
	 */
	public double getProporcao() {
		return prop;
	}
	
	/**
	 * Define a proporção a ser usada em todas as janelas.
	 * @param nova_prop
	 */
	public void setProporcao(double nova_prop) {
		if (prop == nova_prop)
			return;
			
		//esconde durante o zoom: evita flicker na tela
		viewport.setVisible(false);
		
		if (painelVars != null)
			painelVars.setProporcao(nova_prop);
		prop = nova_prop;
		this.validate();
		
		if (function != null) {
			function.setProporcao(nova_prop);
		}
		
		//mostra depois de desenhar o estado
		viewport.setVisible(true);
	}
	
	@Override
	public Insets getInsets() {
		// Força a proporção inicial
		if (prop <= 0)
			setProporcao(1.0);
		return super.getInsets();
	}
	
	/**
	 * Adiciona uma variável ao mostrador.
	 * @param v  variável a ser adicionada.
	 */
	public void adicionarVariavel(Variavel v) {
		if (function != null) {
			function.adicionarVariavel(v);
		} else {
			vars.adicionarVariavel(v);
		}
	}
	
	/**
	 * Armazena uma variável removida para fins de restauração em caso de reversão de 
	 * comando
	 * @param variavelRemovida
	 */
	public void armazenarVariavelRemovida(Variavel variavelRemovida) {
		if (function != null) {
			function.armazenarVariavelRemovida(variavelRemovida);
		} else {
			armazenarVariavel(variavelRemovida);
		}		
	}
	
	/**
	 * Restaura uma variável removida. 
	 * @param variavelRemovida
	 */
	public void restaurarVariavelRemovida() {
		if (function != null) {
			function.restaurarVariavelRemovida();
		} else {
			//adiciona novamente uma variável excluída
			adicionarVariavel(restaurarVariavel());
		}		
	}
	
	/**
	 * Restaura uma seta removida para fins de reversão de comando
	 */
	public void restaurarSetaRemovida() {
		Seta setaRemovida = restauraSeta();
		if(setaRemovida != null){
			if(!hasVariavel(setaRemovida.nome)){
				throw new RuntimeException("Variavel " + setaRemovida.nome + " nao localizada.");
			}		
			if (function != null) {
				function.adicionarSeta(setaRemovida.nome,setaRemovida);
			} else {
				setas.adicionarSeta(setaRemovida.nome, setaRemovida);
			}
		}
	}
	
	/**
	 * Restaura uma seta removida.
	 * @return
	 */
	private Seta restauraSeta(){
		return setasRemovidas.pop();
	}
	
	/**
	 * Remove uma variável do mostrador.
	 * @param nome  nome da variável a ser removida.
	 */
	public Variavel removerVariavel(String nome) {
		return vars.removerVariavel(nome);
	}
	
	/**
	 * Armazena uma variável em uma pilha de variáveis removidas
	 * @param variavelRemovida
	 */
	private void armazenarVariavel(Variavel variavelRemovida) {
		this.variaveisRemovidas.push(variavelRemovida);		
	}
	
	/**
	 * Restaura uma variável removida de uma pilha
	 * @return variavel removida
	 */
	private Variavel restaurarVariavel(){
		return this.variaveisRemovidas.pop();
	}
	
	/**
	 * Modifica o valor de uma variável.
	 * @param nome   nome da variável a ser modificada.
	 * @param valor  seu novo valor.
	 */
	public boolean modificarVariavel(String nome, Object valor) {
		if (function != null) {
			if (function.modificarVariavel(nome, valor))
				return true;
		}
		
		if (hasVariavel(nome)) {
			vars.modificarVariavel(nome, valor);
			return true;
		}
		
		return false;
	}
	

	/**
	 * Cria uma seta em uma variável
	 * @param nome
	 * @param direcao
	 * @param tamanho
	 */
	public void adicionarSeta(String nome, Seta s) {
		if(!hasVariavel(nome)){
			throw new RuntimeException("Variavel " + nome + " nao localizada.");
		}		
		if (function != null) {
			function.adicionarSeta(nome,s);
		} else {
			setas.adicionarSeta(nome, s);
		}
	}
	
	public void armazenarSetaRemovida(Seta setaRemovida){
		this.setasRemovidas.push(setaRemovida);
	}

	/**
	 * Retorna uma cópia de uma variável.
	 * 
	 * @param nome
	 * 
	 * @return
	 */
	public Variavel getCopiaVariavel(String nome) {
		return vars.getCopiaVariavel(nome);
	}
	
	public Object getEstado() {
		return new EstadoMostrador(
				new Painel(vars,setas,textos),
				function);
	}
	
	public void setEstado(Object estado) {
		//esconde durante o desenho: evita flicker na tela
		painelPrincipal.setVisible(false);
		
		painelPrincipal.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints(
				0, 0,
				1, 1,
				1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.FIRST_LINE_START,
				new Insets(0, 0, 0, 0),
				0, 0);
		
		if (estado != null) {
			EstadoMostrador e = (EstadoMostrador) estado;

			painelVars = e.painel;
			function = e.function;
			
			painelPrincipal.add(painelVars, gbc);
			painelVars.criar();
			
			painelVars.setProporcao(prop < 0 ? 1.0 : prop);
			
			if (function != null) {
				function.setEstado(e.estadoFunction);
				gbc.gridy++;
				gbc.ipadx = 100;
				gbc.ipady = 100;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				painelPrincipal.add(function, gbc);
			}
			
			Rectangle r1 = this.getBounds();
			Rectangle r2 = viewport.getViewRect();
			double x = r1.getCenterX() - r2.width/2,
			       y = r1.getCenterY() - r2.height/2;
			Point p = new Point((int) Math.round(x), (int) Math.round(y));
			viewport.setViewPosition(p);
		}

		
		validate();
		repaint();

		
		//mostra depois de ter desenhado o estado
		painelPrincipal.setVisible(Boolean.TRUE);
	}

	
	@Override
	public void processMouseEvent(MouseEvent e) {
		try {
			if (e.getID() != MouseEvent.MOUSE_PRESSED)
			{
				return;
			}
			ultimoPonto = e.getPoint();
		} finally {
			super.processMouseEvent(e);
		}
	}
      
	@Override
	public void processMouseMotionEvent(MouseEvent e) {
		try {
			if (painelVars == null || e.getID() != MouseEvent.MOUSE_DRAGGED)
			{
				return;
			}
			
			int diffX = e.getX() - ultimoPonto.x;
			int diffY = e.getY() - ultimoPonto.y;
			Dimension s = viewport.getExtentSize();
			Dimension m = painelVars.getSize();
			Point p = viewport.getViewPosition();
			double mult = -1.0; //Math.sqrt(prop);
			p.x += diffX * mult;
			p.y += diffY * mult;
			if (p.x < 0) p.x = 0;
			if (p.y < 0) p.y = 0;
			if (p.x + s.width > m.width) p.x = m.width - s.width;
			if (p.y + s.height > m.height) p.y = m.height - s.height;
			
			viewport.setViewPosition(p);
			
			ultimoPonto = e.getPoint();
		} finally {
			super.processMouseMotionEvent(e);
		}
	}
	
	public void startFunction(Function f) {
		if (function != null) {
			function.startFunction(f);
		} else {
			function = f;
		}
	}
	
	public boolean endFunction() {
		if (function != null) {
			if (!function.endFunction()) {
				function = null;
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean hasVariavel(String nome) {
		return vars.contains(nome) ||
			(function != null ? function.hasVariavel(nome) : false);
	}	
	
	public boolean hasSeta(String nome) {
		return setas.contains(nome) ||
			(function != null ? function.hasSeta(nome) : false);
	}
	
	/**
	 * Contém as variáveis *sem* mostrá-las.
	 */
	protected class Variaveis {
		private HashMap<String, Variavel> variaveis;

		/**
		 * Constrói um contâiner vazio.
		 */
		public Variaveis() {
			this.variaveis = new HashMap<String, Variavel>();
		}

		/**
		 * Adiciona uma variável ao contêiner.
		 * @param v  variável a ser adicionada.
		 */		
		public void adicionarVariavel(Variavel v) {
			variaveis.put(v.getName(), v);
		}
		
		/**
		 * Remove uma variável do contêiner.
		 * @param nome  nome da variável a ser removida.
		 */
		public Variavel removerVariavel(String nome) {
			return variaveis.remove(nome);
		}
		
		/**
		 * Modifica o valor de uma variável no contêiner.
		 * @param nome   nome da variável a ser modificada.
		 * @param valor  seu novo valor.
		 */
		public void modificarVariavel(String nome, Object valor) {
			Variavel v = variaveis.get(nome);
			if (v == null)
				throw new RuntimeException("Variável " + nome + " não existe.");
			v.setValor(valor);
		}
		
		/**
		 * Retorna uma cópia de uma variável.
		 * 
		 * @param nome
		 * @return
		 */
		public Variavel getCopiaVariavel(String nome) {
			Variavel v = variaveis.get(nome);
			if (v == null)
				throw new RuntimeException("Variável " + nome + " não existe.");
			return v.criarCopia();
		}
		
		/**
		 * Retorna uma cópia das variáveis como um HashMap separado
		 * por cores.
		 */
		public HashMap<Color, ArrayList<Variavel>> criarCopia() {
			HashMap<Color, ArrayList<Variavel>> ret = new
				HashMap<Color, ArrayList<Variavel>>();
			for (Variavel v : variaveis.values()) {
				Color cor = v.getCorTitulo();
				Variavel copia = v.criarCopia();
				if (ret.containsKey(cor)) {
					ret.get(cor).add(copia);
				} else {
					ArrayList<Variavel> novo = new ArrayList<Variavel>();
					novo.add(copia);
					ret.put(cor, novo);
				}
			}
			return ret;
		}
		
		public boolean contains(String nome) {
			return variaveis.containsKey(nome);
		}
	}
	
	protected class Setas {
		private HashMap<String, Seta> setas;
		
		/**
		 * Constroi um container vazio.
		 */
		public Setas(){
			this.setas = new HashMap<String, Seta>();
		}
		
		
		
		public HashMap<String, Seta> getSetas() {
			return setas;
		}



		/**
		 * Adiciona uma seta
		 * @param String nome a ser adicionado.
		 * @param Seta s a ser adicionada.
		 */
		public void adicionarSeta(String nome, Seta s){
			this.setas.put(nome, s);
		}
		
		/**
		 * Remove uma seta
		 */
		public Seta removerSeta(String nome){
			return setas.remove(nome);
		}
		
		/**
		 * Descobre se uma seta esta contida no conjunto
		 * @param nome
		 * @return
		 */
		public boolean contains(String nome) {
			return setas.containsKey(nome);
		}



		public Setas criarCopia() {
			Setas setasCopia = new Setas();
			for(Seta seta : setas.values()){
				Seta setaCopia = seta.criarCopia();
				setasCopia.adicionarSeta(seta.nome, setaCopia);
			}
			return setasCopia;
		}

	}
	
	/**
	 * Painel que mostra variáveis *estaticamente*, i.e. o conteúdo 
	 * delas *não* deve ser alterado. Este é o estado! =)
	 */
	protected class Painel extends JPanel {
		private HashMap<Color, ArrayList<Variavel>> mapaVariaveis;
		private ArrayList<Janela> janelas = new ArrayList<Janela>();
		private Setas setas;
		private List<Texto> textos;

		/**
		 * Cria um painel. 
		 * @see #criar()
		 */
		public Painel(Variaveis v,Setas setas, List<Texto> textos) {
			super();
			assert (v != null);
			this.mapaVariaveis = v.criarCopia();
			this.setas = setas.criarCopia();
			this.textos = Texto.copiarTextos(textos);
			setLayout(new GridBagLayout());
		}

		public void adicionarSeta(String nome, Seta s) {
			if(this.setas == null){
				this.setas = new Setas();
			}
			this.setas.adicionarSeta(nome, s);
			
		}
		
		public void adicionarTexto(Texto texto) {
			if(this.textos == null){
				this.textos = new ArrayList<Texto>();
			}
			this.textos.add(texto);
		}

		/**
		 * Cria os painéis interiores. Chame este método *após* adicionar
		 * este painel a outro componente.
		 * Desenha todas as variáveis na tela do mostrador
		 * @throws Exception 
		 * @throws TiraTeimaLanguageException 
		 */
		public void criar(){
			if (mapaVariaveis == null) return;
			
			//Absolute Positioning (sem layout para permitir o posicionamento pelo usuario)
			tudo.setLayout(null);
			tudo.removeAll();
			this.add(tudo);			

			Color[] cores = mapaVariaveis.keySet().toArray(new Color[] {});
			Arrays.sort(cores, comparadorColor);
			for (Color cor : cores) {
				Variavel[] vars = mapaVariaveis.get(cor).toArray(new Variavel[] {});
				Arrays.sort(vars, comparadorVariavel);			
				for (Variavel v : vars) {
					Janela j = new Janela(v);
					tudo.add(j);
					janelas.add(j);
					j.validate();
					Dimension size = v.dimensao;
					
					Point point = v.posicao;
					if(point == null) point = j.getLocation();
					
					j.setPreferredSize(size);
					j.setLocation(point);
					j.posicaoOriginal = point;
					if (this.setas.contains(v.nome)){
						Seta seta = setas.setas.get(v.nome);
						tudo.add(seta);
						seta.validate();
						Point posicaoOriginal = seta.calculaPosicaoOriginal(v);
						seta.posicaoOriginal = posicaoOriginal;
						seta.setLocation(posicaoOriginal);
						Integer posicaoSeta;
						Integer posicaoJanela = tudo.getComponentZOrder(j);
						//coloca a seta sobre a janela para que ela apareça
						if (posicaoJanela > 0){
							posicaoSeta = posicaoJanela - 1;
						} else {
							posicaoSeta = 0;
						}
						tudo.setComponentZOrder(seta, posicaoSeta);
					}
				}
			}
			for(Texto t : this.textos){
				tudo.add(t);
				t.setLocation(t.posicaoOriginal);
			}
		}

		/**
		 * Define a proporção de todas as janelas contidos neste painel.
		 * @param prop   nova propoprção.
		 */
		public void setProporcao(double prop) {
			for (Janela j : janelas){
				j.setProporcao(prop);
			}
			for (Seta s : setas.setas.values()){
				s.setProporcao(prop);
			}
			for (Texto t : textos){
				t.setProporcao(prop);
			}
			
			calculaZoom(janelas,setas,textos, prop);
			this.validate();
			this.validate();
			this.validate();
		}
	
		/**
		 * Reconfigura o tamanho da tela e o posicionamento dos objetos após o 
		 * zoom por causa do layout de posicionamento absoluto.
		 * @param janelas
		 * @param prop
		 */
		private void calculaZoom(ArrayList<Janela> janelas, Setas setas, List<Texto> textos, double prop){
			prop = Math.nextUp(prop);
			Dimension tamanhoReal = new Dimension(0,0);
			for (Janela j : janelas){				
				recalculaTamanhoTudo(tamanhoReal, j);				
				alteraPosicaoParaZoom(j,j.posicaoOriginal,prop);				
			}
			for (Seta s : setas.setas.values()){
				recalculaTamanhoTudo(tamanhoReal, s);
				alteraPosicaoParaZoom(s, s.posicaoOriginal, prop);								
			}	
			for(Texto t : textos){
				recalculaTamanhoTudo(tamanhoReal,t);
				alteraPosicaoParaZoom(t, t.posicaoOriginal, prop);
			}
			//Configura o tamanho visível da tela com todas as variáveis em consideração
			tudo.setPreferredSize(new Dimension(tamanhoReal));
			acaoZoom = null;
			validate();
			repaint();
		}

		/**
		 * Altera a posicao de um componente para se adaptar ao zoom. 
		 * Ele aumenta o x e o y proporcionalmente ao paramentro "proporcao"
		 * passado.
		 * @param componente - o que sera reajustado (ou nao)
		 * @param posicaoOriginal - posicao original do componente
		 * @param proporcao - proporcao atual
		 */
		private void alteraPosicaoParaZoom(JComponent componente, Point posicaoOriginal, Double proporcao) {
			int tmp;
			//Aumenta a distancia entre as janelas também
			if(acaoZoom != null){
				if(componente.getLocation().x > 0){
					tmp = componente.getLocation().y;
					if((acaoZoom == zoom.AUMENTA || acaoZoom == zoom.DIMINUI) && proporcao > 1){
						componente.setLocation((int) (posicaoOriginal.x * (proporcao)), tmp);
					}
				}
					
				if(componente.getLocation().y > 0){
					tmp = componente.getLocation().x;
					if((acaoZoom == zoom.AUMENTA || acaoZoom == zoom.DIMINUI) && proporcao > 1){
						componente.setLocation(tmp, (int) (posicaoOriginal.y * (proporcao)));
					}
				}
				
				//Reset de zoom
				if(acaoZoom == zoom.REINICIA){
					componente.setLocation(posicaoOriginal);
				}
				
			//Atualizacao se refere a uma nova tela. Manter zoom escolhido
			}else{
				if(componente.getLocation().x > 0){
					tmp = componente.getLocation().y;
					if(proporcao > 1)
						componente.setLocation((int) (componente.getLocation().x * (proporcao)), tmp);
				}
					
				if(componente.getLocation().y > 0){
					tmp = componente.getLocation().x;
					if(proporcao > 1)
						componente.setLocation(tmp, (int) (componente.getLocation().y * (proporcao)));
				}
				
				//Reset de zoom
				if(proporcao == 1){
					componente.setLocation(posicaoOriginal);
				}
			}
		}

		/**
		 * Altera o tamanho real do painel "tudo", que contem os elementos
		 * graficos do mostrador. 
		 * Se encontrar um tamanho maior, reestabelece o valor real, que sera 
		 * usado como parametro para determinar o tamanho do painel "tudo" 
		 * @param real
		 * @param componente
		 */
		private void recalculaTamanhoTudo(Dimension tamanhoReal, JComponent componente) {
			int novaAltura = (int) ((componente.getHeight() + componente.getY() + 100 ));
			int novaLargura = (int) (( componente.getWidth() + componente.getX() + 100));
			
			if(novaAltura > tamanhoReal.height) tamanhoReal.height = novaAltura;
			if(novaLargura > tamanhoReal.width) tamanhoReal.width = novaLargura;
		}
		
		public void setTextos(List<Texto> textos) {
			this.textos = textos;
		}
	}
	
	private class EstadoMostrador {
		public Painel painel;
		public Function function;
		public Object estadoFunction = null;
		
		public EstadoMostrador(Painel painel, Function function) {
			this.painel = painel;
			this.function = function;
			
			if (function != null)
				this.estadoFunction = function.getEstado();
		}
	}
	
	/**
	 * Remove a seta relativa a variável, caso haja se ela for um ponteiro.
	 * @param nome_var
	 */
	public Seta removerSeta(String nome_var) {
		if(setas.setas.containsKey(nome_var)){
			return setas.setas.remove(nome_var);
		} else {
			return null;
		}
	}

	public Integer quantidadeDeVariaveis() {
		return this.painelVars.janelas.size();
	}

	public void adicionarTexto(Texto texto) {
		if (function != null) {
			function.adicionarTexto(texto);
		} else {
			this.textos.add(texto);
		}
				
	}
}
