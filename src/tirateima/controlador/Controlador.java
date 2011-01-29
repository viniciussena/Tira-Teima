// Copyright (C) 2007  Luciano Santos e Ian Schechtman
package tirateima.controlador;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

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
	private JButton btnPular;
	private JTextField txtLinha;
	
	/** Lista sequencial dos passos fornecidos.*/
	List<Step> passos;
	
	/** Pilha de estados que guarda a ordem de execução do código. */
	Stack<Estado> estados;
	
	/** É o estado atual do Tira-Teima*/
	Estado estadoAtual;
	
	/** Indice para controle da lista de estados. */
	private int indiceAtual;
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
	                JButton btnReiniciar, JButton btnPular, JTextField txtLinha) throws TiraTeimaLanguageException, ParseException {
		
		//recebe o gerador
		gerador = new Gerador();
		//cria a lista de estados com label, nro de liha e passo a ser executado
	    passos = this.gerador.parse(arq_texto);
	    //inicializa a pilha de estados executados
	    estados = new Stack<Estado>();
	    //inicializa o estado atual
	    estadoAtual = null;
	    //inicializa os registros e as funções
	    declared_records = new HashMap<String, RecordDefinition>();
		declared_functions = new HashMap<String, FunctionDeclaration>();
	
	    indiceAtual = -1;
	
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
        		indiceAtual = 0;
        		ajustarBotoes();
        		setEstado(0); //Reinicia
        	}
        });
        
        /*Botão para pular linha.*/
        this.btnPular = btnPular;
        /*Cria evento.*/
        btnPular.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		pularEstado();
        	}
        });
        
        /*Caixa de texto para pular linha.*/
        this.txtLinha = txtLinha;
        /*Cria evento. Le botao 'enter' */
        txtLinha.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		pularEstado();
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
		btnAnt.setEnabled(indiceAtual > 0);
		btnReiniciar.setEnabled(indiceAtual > 0);
		btnPular.setEnabled(true);
		
		/* Ativar/desativar botão "Próximo" */
		btnProx.setEnabled(indiceAtual < passos.size()-1);
		
		/* Definir título do botão "Próximo" */
		btnProx.setText(indiceAtual < 0 ? "Iniciar" : "Próximo");
		final ImageIcon iconeIniciar = new ImageIcon(getClass().getResource("/resources/iniciar.png"));
		final ImageIcon iconeProximo = new ImageIcon(getClass().getResource("/resources/proximo.png"));
		btnProx.setIcon(indiceAtual < 0 ? iconeIniciar : iconeProximo);
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
		jump = (Boolean)e.est_jump;
		jumpTo = (String)e.est_label;
		prepararProximoPasso(e);
		ajustarBotoes();
	}
	/**
	 * Prepara o próximo passo marcando (agendando) para dar um jump (desvio na linha de 
	 * execução) caso seja necessário.
	 * @param Estado e
	 */
    private void prepararProximoPasso(Estado e) {
    	//se o próximo for jump, prepara para trocar de estado
    	if ((Boolean)e.est_jump == Boolean.TRUE){
    		this.jump = Boolean.TRUE;
    		this.jumpTo = (String)e.est_jumpTo;
    	}
    	//se o próximo não for jump, limpa as variáveis de jump
    	else{
    		this.jump = Boolean.FALSE;
    		this.jumpTo = null;
    	}
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
//TODO: refazer a lógica de passagem de estado.
//		/*Coloca o estado atual na pilha, caso haja, para ser possível retornar a ele.*/
//		if(estadoAtual != null){
//			estados.push(estadoAtual);
//		}
//		
//		/*Seleciona o estado*/
//		//Se for jump, pega o estado pelo label.
//		if(jump == Boolean.TRUE){
//			passoAtual = procuraPassoPorLabel();
//			indiceAtual = passos.indexOf(passoAtual);
//		}
//		//Se não for jump, pega o próximo estado da lista.
//		else if(indiceAtual < passos.size()-1){
//			estadoAtual = (Estado) passos.get(++indiceAtual);
//		}
//		
//		/*Executa os commandos relativos ao passo do estado.*/
//		Step passoEstado = (Step)estadoAtual.est_passo;
//		for(Command c : passoEstado.commands){
//			c.execute(this);
//		}
//		
//		/*Salva o estado*/
//		saveState(estadoAtual, passoEstado);	
//		
//		/*Seta o estado no Tira-Teima*/		
//		setEstado(estadoAtual);
	}
	
	/** Procura o passo com um label indicado. */
	private Step procuraPassoPorLabel() {
		String labelPasso;
		for (Step p : passos) {
			if (p.label != null){
				labelPasso = (String)p.label;
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
// TODO: refazer a lógica de ant estado
//		/*Retira o estado da pilha*/
//		estadoAtual = estados.pop();
//		indiceAtual = passos.indexOf(estadoAtual);
//		
//		/*Seta o estado no Tira-Teima*/		
//		setEstado(estadoAtual);
	}
	/**
	* Pula para o estado informado correspondente à linha que usuário colocou no campo txtLinha
	*/
	public void pularEstado (){
//TODO: refazer a lógica de pular estado.
//		try{
//			int linhaUsuario = Integer.valueOf(txtLinha.getText());
//			if(linhaUsuario>0){
//				Integer estado;
//				for(int contador=0; contador < passos.size(); contador++){
//					estado = (Integer) passos.get(contador).est_editor;
//					if(linhaUsuario <= estado){
//						setEstado(contador);
//						indiceAtual = contador;
//						break;
//					}
//				}
//				ajustarBotoes();
//			}
//		}catch (Exception e) {
//			// Não é número, ignorar
//		}
	}
	/**
	* Utiliza o numero da linha passada para setar o estado correspondente e define seus atributos.
	* @param linha Linha correspondente ao estado que será carregado.
	*/
	public void setEstado (int linha){
//TODO: refazer a lógica de setar estado
//		if((linha >= 0)){
//			Estado e = (Estado) passos.get(linha);
//			setEstado(e);
//		}
	}
	
	/**
	 * Salva um estado de acordo com os atributos setados no passo e no gerador.
	 * @param states
	 * @param line
	 * @throws TiraTeimaLanguageException 
	 */
	public void saveState(Estado e, Step step){
		/** Coloca no estado criado a condição de cada elemento gráfico do tirateima. */
		editorTexto.getCaixaTexto().setMarcada(step.line);
		e.est_passo = step;
		e.est_mostrador = mostrador.getEstado();
		e.est_editor = editorTexto.getEstado();
		e.est_console = console.getEstado();
		e.est_alerta = alerta.getEstado();
		e.est_ga = ga.getEstado();
		e.est_label = step.label;
		if(jumpTo != null){
			e.est_jumpTo = jumpTo;
		}
		e.est_jump = jump.booleanValue();
		/** Limpa os atributos de jump para a criação dos próximos estados */
		jump = Boolean.FALSE;
		jumpTo = null;
	}
	
}
