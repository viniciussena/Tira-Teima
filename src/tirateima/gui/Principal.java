/* Copyright (C) 2007  Felipe A. Lessa
 * Copyright (C) 2007  Renan Leandro Ferreira
 * Copyright (C) 2007  Luciano Henrique dos Santos
 * Copyright (C) 2009  Andrew Biller
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
package tirateima.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Reader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import tirateima.controlador.Controlador;
import tirateima.gui.alerta.Alerta;
import tirateima.gui.arquivos.ArquivoVisivelEventListener;
import tirateima.gui.arquivos.GerenciadorArquivos;
import tirateima.gui.console.Console;
import tirateima.gui.editortexto.EditorTexto;
import tirateima.gui.editortexto.Linguagem;
import tirateima.gui.variaveis.Mostrador;
import tirateima.gui.variaveis.Mostrador.zoom;
import tirateima.ui.Ajuda;


/**
 * 
 * Classe principal do AlgoStep. Cria e controla todas as outras classes.
 * Deve ser incluída num applet ou numa janela para ser útil.
 *
 * @see tirateima.main.Applet
 * @see tirateima.main.Programa
 *
 */
@SuppressWarnings("serial")
public class Principal extends JPanel {
	private EditorTexto editor = null;
	private Mostrador mostrador = null;
	private JButton btnReiniciar = null;
	private JButton btnProximo = null;
	private JButton btnAnterior = null;
	private Console console = null;
	private Alerta alerta = null;
	public Boolean jump = Boolean.FALSE; 
	public String jumpTo = null;	
	private GerenciadorArquivos ga = null;
	@SuppressWarnings("unused")
	private Controlador control = null;
	
	private JSplitPane spl_principal = null;
	private JSplitPane spl_painel_direita = null;
	private JSplitPane spl_desenho = null;
	private JPanel spl_painel_abaixo = null;
	private JPanel spl_barra_topo = null;
	private JScrollPane comentarioPainel = null;
	
	/**
	 * Cria e inicializa todos os componentes.
	 */
	public Principal() {
		inicializar();
		btnAnterior.setEnabled(false);
		btnProximo.setEnabled(false);
		btnReiniciar.setEnabled(false);
	}
	
	/**
	 * Cria e inicializa a interface.
	 * 
	 * @param arq_fonte Streamer com o texto do arquivo fonte.
	 * @param arq_texto Streamer com o texto do arquivo de instruções.
	 * 
	 * @throws Exception
	 */
	public Principal(Reader arq_fonte, Reader arq_texto, Linguagem linguagem) throws Exception{		
		inicializar();
		editor.getCaixaTexto().setText(arq_fonte,linguagem);
		
		control = new Controlador(
				arq_texto,
				mostrador,
				ga,
				editor,
				console,
				alerta,
				btnAnterior,
				btnProximo, 
				btnReiniciar);
		
		//Inicializa o número de linhas
			editor.setNumeracao(editor.getCaixaTexto().getTotalLinhas());
		
		// Evita que o comentário apareça logo na inicialização
			comentarioPainel.setVisible(false);
	}
	
	/**
	 * Retorna o componente mostrador.
	 */
	public Mostrador getMostrador() {
		if(mostrador == null){
			mostrador = new Mostrador();
			mostrador.setPreferredSize(new Dimension(400,400));
		}
		return mostrador;
	}
	
	/**
	 * Retorna o componente editor.
	 */
	public EditorTexto getEditor() {
		if(editor == null){
			editor = new EditorTexto();
		}
		return editor;
	}
	
	/**
	 * Retorna botão p/ estado anterior.
	 */
	public JButton getBtnAnterior(){
		if(btnAnterior == null){
			final ImageIcon iconeAnterior = new ImageIcon(getClass().getResource("/resources/anterior.png"));
			btnAnterior = new JButton("Anterior", iconeAnterior);
		}
		
		return btnAnterior;
	}
	
	/**
	 * Retorna botão p/ próximo estado.
	 */
	public JButton getBtnProximo(){
		if(btnProximo == null){
			btnProximo = new JButton("Próximo");
		}
		
		return btnProximo;
	}
	
	/**
	 * Vai para a primeira linha do editor
	 */
	public JButton getBtnReinicia(){
		if(btnReiniciar == null){
			final ImageIcon iconeReiniciar = new ImageIcon(getClass().getResource("/resources/reiniciar.png"));
			btnReiniciar = new JButton("Reiniciar", iconeReiniciar);
		}
		
		return btnReiniciar;
	}
	
	/**
	 * Para que seja possível tratar eventos de teclado
	 * no componente.
	 */
	public boolean isFocusable(){
		return true;
	}
	
	/**
	 * Define o layout da janela pricipal e suas dimensões proporcionais a janela
	 * Define o controle do tira-teima pelas setas direcionais 
	 * Define as posições de cada módulo do SplitPane
	 */
	private void inicializar(){
		setLayout(new BorderLayout());		
		spl_principal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
		                                  criarPainelDeTexto(), criarPainelDireita());
		spl_principal.setResizeWeight(0.3);
		add(getBarraTopo(), BorderLayout.PAGE_START);
		add(spl_principal, BorderLayout.CENTER);
		
		/* Controle via setas direcionais. */
		this.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){};
			
			public void keyReleased(KeyEvent e){
				switch(e.getKeyCode()){
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_RIGHT:
						btnProximo.doClick();
						break;
					case KeyEvent.VK_UP:
					case KeyEvent.VK_LEFT:
						btnAnterior.doClick();
						break;
				}
			}
			
			public void keyTyped(KeyEvent e){}
		});
		
		this.addAncestorListener(new AncestorListener(){
			public void ancestorAdded(AncestorEvent arg0) {
				spl_principal.setDividerLocation(0.3);
				spl_painel_direita.setDividerLocation(0.75);
				spl_desenho.setDividerLocation(0.7);
			}

			public void ancestorMoved(AncestorEvent arg0) {}

			public void ancestorRemoved(AncestorEvent arg0) {}
			
		});
	}
	
	/**
	 * Cria a barra de navegação do tira-teima
	 * Define controles de navegação e de zoom (botões de navegação, áudio e ajuda)
	 * 
	 * @return JPanel Object: spl_barra_topo (barra de navegação)
	 */
	private Component getBarraTopo() {
		if(spl_barra_topo == null){
			spl_barra_topo = new JPanel(new BorderLayout());
	
			// controles de navegação
			JPanel controleNavegacao = new JPanel();
			controleNavegacao.add(new JLabel("Navegação: "));
			controleNavegacao.add(getBtnReinicia());
			controleNavegacao.add(getBtnAnterior());
			controleNavegacao.add(getBtnProximo());
			
			// controles de zoom
			final ImageIcon iconeZoomMenos = new ImageIcon(getClass().getResource("/resources/zoom_out.png"));
			final JButton bMenos = new JButton(iconeZoomMenos);
			bMenos.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
			    	double valor = mostrador.getProporcao();
					if(valor > 0.2){
						mostrador.acaoZoom = zoom.DIMINUI;
						mostrador.setProporcao(mostrador.getProporcao() - 0.2);
					}
			    }
			});
			bMenos.setSize(bMenos.getWidth(), 5);
			bMenos.setFont(new Font("sansserif", Font.BOLD, 11));
			
			final ImageIcon iconeZoomReiniciar = new ImageIcon(getClass().getResource("/resources/zoom_reiniciar.png"));
			final JButton bZero = new JButton(iconeZoomReiniciar);
			bZero.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
			    	mostrador.acaoZoom = zoom.REINICIA;
			    	//tenta redefinir o tamanho da barra de rolagem
			    	mostrador.setProporcao(1.2);
			    	mostrador.setProporcao(0.8);
			    	//reinicia o zoom
			    	mostrador.setProporcao(1);
			    }
			});
			bZero.setSize(bMenos.getWidth(), 15);
			
			final ImageIcon iconeZoomMais = new ImageIcon(getClass().getResource("/resources/zoom_in.png"));
			final JButton bMais = new JButton(iconeZoomMais);
			bMais.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
			    	double valor = mostrador.getProporcao();
					if(valor > 0){
						mostrador.acaoZoom = zoom.AUMENTA;
						mostrador.setProporcao(mostrador.getProporcao() + 0.2);
					}
			    }
			});
			bMais.setSize(bMenos.getWidth(), 10);
			bMais.setFont(new Font("sansserif", Font.BOLD, 11));
			
			JPanel controleZoom = new JPanel();
			controleZoom.add(new JLabel("Zoom: "));
			controleZoom.add(bMenos);
			controleZoom.add(bZero);
			controleZoom.add(bMais);
			
			// controles diversos
			JPanel controleOutros = new JPanel();

			final ImageIcon iconeSomAtivo = new ImageIcon(getClass().getResource("/resources/som_ativo.png"));
			final ImageIcon iconeSomMudo = new ImageIcon(getClass().getResource("/resources/som_mudo.png"));
			JButton btnSom = new JButton("Som", iconeSomAtivo);
			btnSom.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
			    	JButton btn = (JButton) evt.getSource();
					if("Som".equals(btn.getText())){
						btn.setText("Mudo");
						btn.setIcon(iconeSomMudo);
					}else{
						btn.setText("Som");
						btn.setIcon(iconeSomAtivo);
					}
					alerta.setSomAtivado(!alerta.isSomAtivado());
			    }
			});
			controleOutros.add(btnSom);

			final ImageIcon iconeAjuda = new ImageIcon(getClass().getResource("/resources/ajuda.png"));
			JButton btnAjuda = new JButton("Ajuda", iconeAjuda);
			btnAjuda.addActionListener(new Ajuda());
			controleOutros.add(btnAjuda);
			
			spl_barra_topo.add(controleNavegacao, BorderLayout.LINE_START);
			spl_barra_topo.add(controleZoom, BorderLayout.CENTER);
			spl_barra_topo.add(controleOutros, BorderLayout.LINE_END);
		}
		
		return spl_barra_topo;
	}
	
	/**
	 * Instancia os componentes do lado direito do tira-teima e redimensiona-os
	 * na janela
	 * 
	 * @return JSplitPane Object: spl_painel_direita (console, alerta, mostrador, comentário)
	 */
	private JComponent criarPainelDireita(){
		final JScrollPane consolePanel = new JScrollPane(getConsole(),
    			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		 comentarioPainel = new JScrollPane(getAlerta(),
    			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		spl_painel_abaixo = new JPanel(new BorderLayout());
		spl_painel_abaixo.add(consolePanel, BorderLayout.CENTER);
		spl_painel_abaixo.add(comentarioPainel, BorderLayout.PAGE_END);
		comentarioPainel.setPreferredSize(new Dimension(300, 50));

		// Controla quando um alerta deve aparecer (som ou comentário)
		CaretListener mostrarAlertaListener = new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				Alerta alerta = (Alerta) arg0.getSource(); 
				
				//Mostra comentário se existir
				if ("".equals(alerta.getText())) {
					comentarioPainel.setVisible(false);
					consolePanel.setSize(consolePanel.getWidth(), consolePanel.getHeight() + comentarioPainel.getHeight());
				}else{
					comentarioPainel.setVisible(true);
				}
				// Força o repaint (afetado: alerta)
				spl_painel_abaixo.setVisible(false);
				spl_painel_abaixo.setVisible(true);
				

			}
		};
		getAlerta().addCaretListener(mostrarAlertaListener);
		
		
		spl_painel_direita = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                criarAreaDeDesenho(), spl_painel_abaixo
                );
		spl_painel_direita.setResizeWeight(0.75);
		spl_painel_direita.resetToPreferredSizes();
		
		console.setFont(new Font("Courier New", Font.BOLD, 14));		
		return spl_painel_direita;
	}
	
	/**
	 * Instancia e dimensiona o editor de texto
	 * 
	 * @return JPanel Object: p (painel com editor de texto)
	 */
	private JPanel criarPainelDeTexto(){
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		
		//Cria o editor de texto
		gb.anchor = GridBagConstraints.NORTHWEST;
		gb.fill = GridBagConstraints.HORIZONTAL;
		gb.gridx = 0;
		gb.gridy = 0;
		gb.gridwidth = 3;
		gb.gridheight = 1;
		gb.weightx = 1.0;
		gb.weighty = 1.0;
		gb.fill = GridBagConstraints.BOTH;
		p.add(getEditor(), gb);
		
		return p;
	}
	
	/**
	 * Instancia o painel mostrador e define a organização do painel dentro do mostrador
	 * Instancia o painel como um todo. Este engloba todo o conteúdo interno do painel descrito
	 * acima com toda sua organização
	 * 
	 * @return JPanel Object: mostradorCompleto (painel com o mostrador gráfico)
	 */
	private JPanel criarPainelMostrador(){
		JPanel modulo2 = new JPanel();
		modulo2.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		modulo2.add(getMostrador(), c);
		
		JPanel mostradorCompleto = new JPanel();
		mostradorCompleto.setLayout(new BoxLayout(mostradorCompleto, BoxLayout.PAGE_AXIS));
		mostradorCompleto.add(modulo2);
		mostradorCompleto.add(Box.createVerticalStrut(5));
		
		return mostradorCompleto;
	}
	
	/**
	 * Instancia a área de desenho
	 * 
	 * @return JSplitPane Object: spl_desenho (área de desenho)
	 */
	private JComponent criarAreaDeDesenho() {
		spl_desenho = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                criarPainelMostrador(), getGA());
		spl_desenho.setResizeWeight(0.7);
		spl_desenho.resetToPreferredSizes();
		return spl_desenho;
	}
	
	
	private GerenciadorArquivos getGA(){
		if(ga == null){
			ga = new GerenciadorArquivos();
			ga.setPreferredSize(new Dimension(400, 50));
			
			ga.addArquivoVisivelListener(new ArquivoVisivelEventListener() {

				public void ficouInvisivel() {
					spl_desenho.resetToPreferredSizes();
				}

				public void ficouVisivel() {
					spl_desenho.resetToPreferredSizes();
				}
				
			});
		}
		
		return ga;
	}
	
	/**
	 * Instancia o console
	 * 
	 * @return Console Object: console
	 */
	private Console getConsole(){
		if(console == null){
			console = new Console();
		}
		
		return console;
	}
	
	/**
	 * Instancia o alerta
	 * 
	 * @return Alerta Object: alerta
	 */
	private Alerta getAlerta(){
		if(alerta == null){
			alerta = new Alerta();
		}
		
		return alerta;
	}
}
