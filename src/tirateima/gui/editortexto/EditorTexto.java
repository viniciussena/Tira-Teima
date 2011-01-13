package tirateima.gui.editortexto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import tirateima.IEstado;


/**
 * Modela o editor, com a caixa de texto e dois botoes de seta.
 *
 * @author Luciano Santos
 */
@SuppressWarnings("serial")
public class EditorTexto extends JPanel implements IEstado{	
	CaixaTexto texto; //caixa de texto com o codigo fonte
	private JTextArea numeracao = null;
	
	/**
	 * Cria novo EditorTexto.
	 */
	public EditorTexto(){
		construir("");
	}
	
	/**
	 * Constroi novo EditorTexto.
	 * 
	 * @param texto Texto a ser inserido na caixa de texto.
	 */
	public EditorTexto(String texto){
		construir(texto);
	}
	
	public  CaixaTexto getCaixaTexto(){
		return texto;
	}
	
	public void setEstado(Object o){
		if(o != null){
			Integer temp = (Integer) o;
			int linha = temp.intValue();
			texto.setMarcada(linha);
			performScroll(linha);
			performScrollNumeracao(linha);
		}else{
			texto.setMarcada(-1);
		}
	}
	
	private void performScrollNumeracao(int linha) {
		Rectangle view = getCaixaTexto().getVisibleRect();
		double linhaReal = (linha * 16) - 16; //16 é uma altura aproximada em pixels de uma linha
		double novoY = 0;
		double magicNumber;
		
		//A parte visivel é maior que a parte escondida?
		if(view.getHeight() > (getCaixaTexto().getHeight() - view.getHeight())){
			magicNumber = getCaixaTexto().getHeight() / 2;
			if(linhaReal > magicNumber){
				novoY = linhaReal - magicNumber;
			}
		}else{
			magicNumber = view.getHeight() / 2;
			if(linhaReal > magicNumber){
				novoY = linhaReal -  magicNumber - 16;
			}
		}
		view.setRect( 0, novoY, view.getWidth(), view.getHeight());
		getCaixaTexto().scrollRectToVisible(view);
	}

	/**
	 * Sempre mostra a linha marcada rolando a caixa de texto quando necessário
	 * @param linha
	 */
	private void performScroll(int linha)
	{
		Rectangle view = getCaixaTexto().getVisibleRect();
		double linhaReal = (linha * 16) - 16; //16 é uma altura aproximada em pixels de uma linha
		double novoY = 0;
		double magicNumber;
		
		//A parte visivel é maior que a parte escondida?
		if(view.getHeight() > (getCaixaTexto().getHeight() - view.getHeight())){
			magicNumber = getCaixaTexto().getHeight() / 2;
			if(linhaReal > magicNumber){
				novoY = linhaReal - magicNumber;
			}
		}else{
			magicNumber = view.getHeight() / 2;
			if(linhaReal > magicNumber){
				novoY = linhaReal -  magicNumber - 16;
			}
		}
		view.setRect( 0, novoY, view.getWidth(), view.getHeight());
		getCaixaTexto().scrollRectToVisible(view);
	}

	public Object getEstado(){
		int linha = texto.getMarcada();
		Integer temp = new Integer(linha);
		return temp;
	}
	
	/**
	 * Constroi o componente posicionando os objetos na tela.
	 * 
	 * @param textoinicial Texto inicial da caixa de texto
	 */
	private void construir(String textoinicial){
		JPanel painel = new JPanel();
		painel.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		
		/*Adiciona a numeração...*/
		gb.anchor = GridBagConstraints.NORTHWEST;
		gb.fill = GridBagConstraints.VERTICAL;
		gb.gridx = 0;
		gb.gridy = 0;
		gb.gridheight = gb.gridwidth = 1;
		gb.weightx = 0;
		gb.weighty = 1;
		construirNumeracao();
//		numeracao.setBorder(BorderFactory.createLineBorder(new Color(130, 135, 144), 1));
		painel.add(numeracao, gb);
		
		gb.gridx = 1;
		JSeparator jSeparator = new JSeparator(JSeparator.VERTICAL);
		jSeparator.setPreferredSize(new Dimension(1, this.getHeight()));
		painel.add(jSeparator);
		
		/*Cria e adiciona a caixa de texto...*/
		texto = new CaixaTexto(textoinicial);
		texto.setEditable(false);
		gb.anchor = GridBagConstraints.NORTHWEST;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridx = 2;
		gb.gridy = 0;
		gb.gridheight = gb.gridwidth = 1;
		gb.weightx = 1;
		gb.weighty = 1;
//		texto.setBorder(BorderFactory.createLineBorder(new Color(130, 135, 144), 1));
		painel.add(texto, gb);
		
		/*Cria o componente com a barra de rolagem...*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.anchor = GridBagConstraints.NORTHWEST;
		gb2.fill = GridBagConstraints.BOTH;
		gb2.gridx = gb2.gridy = 0;
		gb2.gridwidth = gb2.gridheight = 1;
		gb2.weightx = gb2.weighty = 1;
		add(new JScrollPane(painel), gb2);
	}
	
	/**
	 * Constrói a barra lateral com a numeração do código
	 */
	private void construirNumeracao(){
		numeracao = new JTextArea();
		numeracao.setForeground(Color.GRAY);
		numeracao.setBackground(new Color(255, 255, 255));
		numeracao.setFont(new Font("monospaced", Font.PLAIN, 12));
		numeracao.setText("");
		numeracao.setEnabled(false);
	}
	
	public void setNumeracao(int linhas){
		StringBuffer sb = new StringBuffer();
		for(int i=1; i <= linhas; i++){
			sb.append(i + " \n");
		}
		numeracao.setText(sb.toString());
	}
	
}
