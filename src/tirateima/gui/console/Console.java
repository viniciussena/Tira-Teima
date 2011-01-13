package tirateima.gui.console;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JTextArea;

import tirateima.IEstado;


@SuppressWarnings("serial")
public class Console extends JTextArea implements IEstado{
	/*Total de linhas no texto.*/
	private int linhas_max = 100;
	
	/*Largura máxima das linhas em caracteres.*/
	private int larg_max = 80;
	
	/*Linhas de texto.*/
	private ArrayList<String> linhas;
	
	/**
	 * Seta valores ao construtor da superclasse
	 */
	public Console(){
		super();
		linhas = new ArrayList<String>();
		setBackground(Color.black);
		setForeground(Color.white);
		setEditable(false);
	}
	
	/**
	 * Recebe a string a ser mostrada no console e seta ela
	 * 
	 * @param s
	 */
	public Console(String s){
		this();
		setText(s);
	}
	
	public int getLinhasMax(){
		return linhas_max;
	}
	
	/**
	 * Restringe o intervalo no qual irá variar a quantidade de linhas
	 * 
	 * @param linhas: quantidade de linhas do console
	 */
	public void setLinhasMax(int linhas){
		if((linhas < 10) || (linhas > 500)){
			throw new IllegalArgumentException(
				"Número máximo de linhas deve estar entre 10 e 500.");
		}
		linhas_max = linhas;
	}
	
	public int getLargMax(){
		return larg_max;
	}
	
	/**
	 * Restringe o intervalo no qual irá variar a largura do console
	 * 
	 * @param caracteres: quantidade de caracteres mostrados em uma linha
	 */
	public void setLargMax(int caracteres){
		if((caracteres < 10) || (caracteres > 200)){
			throw new IllegalArgumentException(
				"Largura máxima em caracteres deve estar entre 10 e 200.");
		}
		larg_max = caracteres;
	}
	
	/**
	 * Garante o funcionamento correto das linhas do console
	 * 
	 * @param s
	 */
	public void print(String s){
		/**
		 * pega a última linha e adiciona o texto
		 */
		String temp = linhas.size() > 0 ?
				linhas.get(linhas.size() - 1) + s:
				s;
		
		/**
		 * garante não repetir a última linha
		 */
		if(linhas.size() > 0){
			linhas.remove(linhas.size() - 1);
		}
		
		/**
		 * gera e adiciona as linhas, se necessário
		 */
		linhas.addAll(encontrarLinhas(temp));
		
		/**
		 * garante que o total de linhas não ultrapasse o máximo definido
		 */
		if(linhas.size() > linhas_max){
			ArrayList<String> temp_list = new ArrayList<String>();
			temp_list.addAll(linhas.subList(
					linhas.size() - larg_max,
					linhas.size() - 1));
		}
		
		/**
		 * mostra o novo texto
		 */
		organizarTexto();
	}
	
	
	public void print(char[] ca){
		print(new String(ca));
	}
	
	public void print(char c){
		print("" + c);
	}
	
	public void print(int i){
		print(Integer.toString(i));
	}
	
	public void print(byte b){
		print(Byte.toString(b));
	}
	
	public void print(float f){
		print(Float.toString(f));
	}
	
	public void print(double d){
		print(Double.toString(d));
	}
	
	public void print(Object o){
		print(o.toString());
	}

	public void println(String s){
		print(s + '\n');
	}
	
	public void println(){
		println("");
	}
	
	public void println(char[] ca){
		println(new String(ca));
	}
	
	public void println(char c){
		println("" + c);
	}
	
	public void println(int i){
		println(Integer.toString(i));
	}
	
	public void println(byte b){
		println(Byte.toString(b));
	}
	
	public void println(float f){
		println(Float.toString(f));
	}
	
	public void println(double d){
		println(Double.toString(d));
	}
	
	public void println(Object o){
		println(o.toString());
	}
	
	public void clear(){
		setText("");
	}
	
	
	public void setText(String s){
		linhas = encontrarLinhas(s);
		organizarTexto();
	}
	
	/**
	 * Recebe um objeto e tenta transforma-lo em string, caso ele seja diferente de NULL
	 * Se o parametro for NULL ele atribui uma string vazia ao setText utilizando o Clear()
	 * 
	 * @param Objetc o
	 */
	public void setEstado(Object o){
		if(o != null){
			try{
				String s = (String) o;
				setText(s);
			}catch(Exception e){
				e.printStackTrace();
				clear();
			}
		}else{
			clear();
		}
	}
	
	public Object getEstado(){
		return getText();
	}
	
	/**
	 * Esse metodo recebe uma string, separa ela em um array de char e concatena esses chars
	 * utilizando a classe StringBuffer e transformando-a em string novamente
	 * 
	 * Feito isso, o metodo grava essa string em uma lista de strings, retornando-as
	 * 
	 * @param s
	 * @return ArrayList<String> lista
	 */
	private ArrayList<String> encontrarLinhas(String s){
		ArrayList<String> lista = new ArrayList<String>();
		char array[] = s.toCharArray();
		for(int i = 0; i < s.length(); i++){
			StringBuffer temp = new StringBuffer();
			for(int j = 0; (j < larg_max)&&(i < s.length()); j++, i++){
				char a = array[i];
				temp.append(a);
				if(a == '\n') break;
			}
			lista.add(temp.toString());
		}
		return lista;
	}
	
	private void organizarTexto(){
		StringBuffer temp = new StringBuffer();
		
		ListIterator<String> i = linhas.listIterator();
		while(i.hasNext()){
			temp.append(i.next());
		}
		super.setText(temp.toString());
		setCaretPosition(getText().length());
	}
}
