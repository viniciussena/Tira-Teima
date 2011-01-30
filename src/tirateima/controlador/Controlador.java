// Copyright (C) 2007  Luciano Santos e Ian Schechtman
package tirateima.controlador;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import tirateima.gui.alerta.Alerta;
import tirateima.gui.arquivos.GerenciadorArquivos;
import tirateima.gui.console.Console;
import tirateima.gui.editortexto.EditorTexto;
import tirateima.gui.variaveis.Mostrador;
import tirateima.parser.ParseException;

/**
* Classe que fornece os métodos de controle da execução do programa tirateima
* Carrega cada estado que será mostrado em tela a partir de botões definidos e a lista de estados.
* @Author Luciano Santos e Ian Schechtman
*/
public class Controlador {
	/** Gerador utilizado para criar estados. */
	Gerador gerador;	
	
	/** Editor de texto.*/
	public EditorTexto editorTexto;
	
	/** O Mostrador. */
	public Mostrador mostrador;
	
	/** O console. */
	public Console console;
	
	/** Os alertas. */
	public Alerta alerta;
	
	/** O gerador de arquivos. */
	public GerenciadorArquivos ga;

	/** Indica se há jump ou não no passo */
	public Boolean jump;
	
	/** Indica para qual estado ir em caso de haver jump no passo */ 
	public String jumpTo;
	
	public HashMap<String, RecordDefinition> declared_records;
	public HashMap<String, FunctionDeclaration> declared_functions;
	
	/** Botões de navegação. */
	private JButton btnAnt;
	private JButton btnProx;
	private JButton btnReiniciar;
	
	/** Lista sequencial dos passos fornecidos.*/
	private List<Step> passos;
	
	/** Pilha de estados que guarda a ordem de execução do código. */
	private Stack<Estado> estados;
	
	/** É o estado atual do Tira-Teima*/
	private Estado estado;
	
	/** Indice para controle da lista de estados. */
	private int indice;
	/**
	*Método contrutor único/default.
	*@param passos Array contendo a lista de estados que o programa executará.
	*@param mostrador
	*@param ga Objeto gráfico onde o programa desenhará o menu
	*@param editor
	*@param console
	*@param alerta
	*@param btnAnt Botao que retorna ao estado anterior
	*@param btnProx Botao que anvança ao próximo estado
	*@param btnReiniciar Botao que reinicia a execução
	*@param btnPular Botao para pular à linha informado pelo usuário na caixa txtLinha
	*@param txtLinha Caixa de texto que serve de argumento ao botao pular linha.
	 * @throws ParseException 
	 * @throws TiraTeimaLanguageException 
	*/
	public Controlador(Reader arq_texto,
	                Mostrador mostrador, GerenciadorArquivos ga,
	                EditorTexto editorTexto, Console console,
	                Alerta alerta,
	                JButton btnAnt, JButton btnProx,
	                JButton btnReiniciar) throws TiraTeimaLanguageException, ParseException {
		
		//recebe o gerador
		gerador = new Gerador();
		//cria a lista de estados com label, nro de liha e passo a ser executado
	    passos = this.gerador.parse(arq_texto);
	    //inicializa a pilha de estados executados
	    estados = new Stack<Estado>();
	    //inicializa o estado atual
	    estado = null;
	    //inicializa os registros e as funções
	    declared_records = new HashMap<String, RecordDefinition>();
		declared_functions = new HashMap<String, FunctionDeclaration>();
	
	    indice = -1;
	
	    this.mostrador = mostrador;
	    this.ga = ga;
	    this.editorTexto = editorTexto;
	    this.console = console;
	    this.alerta = alerta;
	    
	    this.jump = Boolean.FALSE;
	    this.jumpTo = null;
	    
	    mostrador.setEstado(null);
	    ga.setEstado(null);
        editorTexto.setEstado(null);
        console.setEstado(null);
        alerta.setEstado(null);
        
	    /*Botão para estado anterior.*/
	    this.btnAnt = btnAnt;
	    /*Cria evento.*/
	    btnAnt.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        try {
					antEstado();
				} catch (TiraTeimaLanguageException e) {
					e.printStackTrace();
				}
		    }
		});
	
        /*Botão para próximo estado.*/
        this.btnProx = btnProx;
        /*Cria evento.*/
        btnProx.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        try {
					proxEstado();
				} catch (TiraTeimaLanguageException e) {
					e.printStackTrace();
				}
		    }
		});
        
        /*Botão para reiniciar.*/
        this.btnReiniciar = btnReiniciar;
        /*Cria evento.*/
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		indice = -1;
        		ajustarBotoes();
        		//Reseta os valores do controlador.
        		setEstado(new Estado());
        		estado = null;
        		jump = Boolean.FALSE;
        		jumpTo = null;
        	}
        });
        ajustarBotoes();
	}
	/** 
	* Método ajusta os botoes do controle.
	* Botão Anterior, Reiniciar -> Ativa quando estiver depois do primeiro estado.
	* Botão Pular -> Define de forma se ativado ou desativado
	* Botão Próximo -> No primeiro estado define como o rótulo "Iniciar" e enquanto não estiver no último estado fica ativo com o Rótulo "Próximo"
	*/
	private void ajustarBotoes(){
		/* Ativar/desativar botão "Anterior" */
		btnAnt.setEnabled(indice > 0);
		btnReiniciar.setEnabled(indice > 0);
		
		/* Ativar/desativar botão "Próximo" */
		btnProx.setEnabled(indice < passos.size()-1);
		
		/* Definir título do botão "Próximo" */
		btnProx.setText(indice < 0 ? "Iniciar" : "Próximo");
		final ImageIcon iconeIniciar = new ImageIcon(getClass().getResource("/resources/iniciar.png"));
		final ImageIcon iconeProximo = new ImageIcon(getClass().getResource("/resources/proximo.png"));
		btnProx.setIcon(indice < 0 ? iconeIniciar : iconeProximo);
	}
    /**
	* Define todos os atributos do objeto estado passado como parâmetro.
	* @param e Objeto do tipo Estado que terá seus atributos definidos.
	*/    
	private void setEstado(Estado e){
		mostrador.setEstado(e.est_mostrador);
		editorTexto.setEstado(e.est_editor);
		console.setEstado(e.est_console);
		alerta.setEstado(e.est_alerta);
		ga.setEstado(e.est_ga);
		if(e.est_jump != null){
			jump = (Boolean)e.est_jump;
		}
		else{
			jump = Boolean.FALSE;
		}
		jumpTo = (String)e.est_jumpTo;
		ajustarBotoes();
	}
	
	/**
	 * Define o próximo estado incrementando índice e chama método setEstado. 
	 * 
	 * Caso o fluxo de execução sofra desvio, vai para o estado de label indicado.
	 * Caso o fluxo de execução não tenha sofrido desvio (jump), passa ao estado seguinte.
	 * 
	 *  
	 * @throws TiraTeimaLanguageException 
	 * 
	 * @see setEstado
	 */
	public void proxEstado () throws TiraTeimaLanguageException{
		/*Coloca o estado atual na pilha, caso haja, para ser possível retornar a ele.*/
		if(estado != null){
			estados.push(estado);
		}
		
		/*Cria um novo estado*/
		estado = new Estado();
		
		/*Seleciona o passo*/
		Step passo;
		//Se for jump, pega o passo pelo label.
		if(jump == Boolean.TRUE){
			passo = procuraPassoPorLabel();
			indice = passos.indexOf(passo);
			//Limpa os atributos de jump
			jump = Boolean.FALSE;
			jumpTo = null;
		}
		//Se não for jump, pega o próximo passo da lista.
		else if(indice < passos.size()-1){
			passo = passos.get(++indice);
		}
		//Caso contrário, apenas inicializa a variável, pois os passos acabaram.
		else{
			passo = null;
		}
		
		//Caso os passos não tenham acabado
		if(passo != null){
			/*Executa os commandos relativos ao passo selecionado.*/
			for(Command c : passo.commands){
				c.execute(this);
			}
			
			/*Destaca a linha do editor correspondente ao passo.*/
			editorTexto.getCaixaTexto().setMarcada(passo.line);
			
			/*Salva o estado atual do Tira-Teima*/
			saveState(estado, passo);
			
			/*Seta o estado*/
			setEstado(estado);
		}
		//Ajusta os botões
		ajustarBotoes();
	}
	
	/** Procura o passo com um label indicado. 
	 * @return Step passo */
	private Step procuraPassoPorLabel() {
		String labelPasso;
		for (Step p : passos) {
			if (p.label != null){
				labelPasso = p.label;
				if(labelPasso.equals(jumpTo)){
					return p;
				}
			}
		}
		return null;
	}
	/**
	 * Define o estado anterior decrementando índice e chama método setEstado
	 * @throws TiraTeimaLanguageException 
	 * @see setEstado
	 */
	public void antEstado () throws TiraTeimaLanguageException{
		/*Retira o estado da pilha*/
		estado = estados.pop();
		indice = passos.indexOf(estado.est_passo);
		
		/*Seta o estado no Tira-Teima*/		
		setEstado(estado);
	}
	
	/**
	 * Salva um estado de acordo com os atributos setados no passo e no gerador.
	 * @param Estado e
	 * @param Step passo
	 */
	public void saveState(Estado e, Step passo){
		/** Coloca no estado criado a condição de cada elemento gráfico do tirateima. */
		e.est_passo = passo;
		e.est_mostrador = mostrador.getEstado();
		e.est_editor = editorTexto.getEstado();
		e.est_console = console.getEstado();
		e.est_alerta = alerta.getEstado();
		e.est_ga = ga.getEstado();
		e.est_label = passo.label;
		if(jumpTo != null){
			e.est_jumpTo = jumpTo;
		}
		e.est_jump = jump.booleanValue();
	}	
}
