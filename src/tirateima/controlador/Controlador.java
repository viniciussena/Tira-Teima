// Copyright (C) 2007  Luciano Santos e Ian Schechtman
package tirateima.controlador;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import tirateima.IEstado;
import tirateima.gerador.Gerador;
import tirateima.gerador.TiraTeimaLanguageException;
import tirateima.parser.ParseException;

/**
* Classe que fornece os métodos de controle da execução do programa tirateima
* Carrega cada estado que será mostrado em tela a partir de botões definidos e a lista de estados.
* @Author Luciano Santos e Ian Schechtman
*/
public class Controlador {
	/*Botões de navegação.*/
	private JButton btnAnt;
	private JButton btnProx;
	private JButton btnReiniciar;
	private JButton btnPular;
	private JTextField txtLinha;
	
	private IEstado mostrador;
	private IEstado editor;
	private IEstado console;
	private IEstado alerta;
	private IEstado ga;
	
	/* Variáveis que controlam tanto se há jump (desvio de execução) quanto para onde esse é o 
	 * jump, caso haja */
	private Boolean jump;
	private String jumpTo;
	
	/*Lista dos estados.*/
	List<Estado> estados = new ArrayList<Estado>();
	
	/*Indice para controle da lista de estados. */
	private int indice;
	/**
	*Método contrutor único/default.
	*@param estados Array contendo a lista de estados que o programa executará.
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
	public Controlador(Gerador gerador,
					Reader arq_texto,
	                IEstado mostrador, IEstado ga,
	                IEstado editor, IEstado console,
	                IEstado alerta,
	                JButton btnAnt, JButton btnProx,
	                JButton btnReiniciar, JButton btnPular, JTextField txtLinha) throws TiraTeimaLanguageException, ParseException {
		
		//cria a lista de estados com label, nro de liha e passo a ser executado
	    this.estados = gerador.parse(arq_texto);
	
	    indice = -1;
	
	    this.mostrador = mostrador;
	    this.ga = ga;
	    this.editor = editor;
	    this.console = console;
	    this.alerta = alerta;
	    
	    this.jump = Boolean.FALSE;
	    this.jumpTo = null;
	    
	    mostrador.setEstado(null);
	    ga.setEstado(null);
        editor.setEstado(null);
        console.setEstado(null);
        alerta.setEstado(null);
        
	    /*Botão para estado anterior.*/
	    this.btnAnt = btnAnt;
	    /*Cria evento.*/
	    btnAnt.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        antEstado();
		    }
		});
	
        /*Botão para próximo estado.*/
        this.btnProx = btnProx;
        /*Cria evento.*/
        btnProx.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        proxEstado();
		    }
		});
        
        /*Botão para reiniciar.*/
        this.btnReiniciar = btnReiniciar;
        /*Cria evento.*/
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		indice = 0;
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
		btnAnt.setEnabled(indice > 0);
		btnReiniciar.setEnabled(indice > 0);
		btnPular.setEnabled(true);
		
		/* Ativar/desativar botão "Próximo" */
		btnProx.setEnabled(indice < estados.size()-1);
		
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
		editor.setEstado(e.est_editor);
		console.setEstado(e.est_console);
		alerta.setEstado(e.est_alerta);
		ga.setEstado(e.est_ga);
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
	* @see setEstado
	*/
	public void proxEstado (){
		Estado e = null;
		if(jump == Boolean.TRUE){
			e = procuraEstadoPorLabel();
			indice = estados.indexOf(e);
		}
		else if(indice < estados.size()-1){
			e = (Estado) estados.get(++indice);
		}
		setEstado(e);
	}
	
	/** Procura o estado com um label indicado. */
	private Estado procuraEstadoPorLabel() {
		String labelEstado;
		for (Estado e : estados) {
			if (e.est_label != null){
				labelEstado = (String)e.est_label;
				if(labelEstado.equals(jumpTo)){
					return e;
				}
			}
		}
		return null;
	}
	/**
	* Define o estado anterior decrementando índice e chama método setEstado
	* @see setEstado
	*/
	public void antEstado (){
		if((indice > 0)){
			Estado e = (Estado) estados.get(--indice);
			setEstado(e);
		}
	}
	/**
	* Pula para o estado informado correspondente à linha que usuário colocou no campo txtLinha
	*/
	public void pularEstado (){
		try{
			int linhaUsuario = Integer.valueOf(txtLinha.getText());
			if(linhaUsuario>0){
				Integer estado;
				for(int contador=0; contador < estados.size(); contador++){
					estado = (Integer) estados.get(contador).est_editor;
					if(linhaUsuario <= estado){
						setEstado(contador);
						indice = contador;
						break;
					}
				}
				ajustarBotoes();
			}
		}catch (Exception e) {
			// Não é número, ignorar
		}
	}
	/**
	* Utiliza o numero da linha passada para setar o estado correspondente e define seus atributos.
	* @param linha Linha correspondente ao estado que será carregado.
	*/
	public void setEstado (int linha){
		if((linha >= 0)){
			Estado e = (Estado) estados.get(linha);
			setEstado(e);
		}
	}
	
}
