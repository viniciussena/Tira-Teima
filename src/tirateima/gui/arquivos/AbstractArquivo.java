package tirateima.gui.arquivos;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import tirateima.IEstado;
import tirateima.gui.Constantes;


public abstract class AbstractArquivo extends JComponent implements IEstado {
	private static final long serialVersionUID = 1L;

	public static String url_base = null;
	
	protected FileBorder borda = null;
	protected String nome = null;
	protected String titulo = null;
	protected File file = null;
	protected boolean read_only = false;
	protected boolean is_opened = false;
	protected int atual = 0;
	protected JScrollPane spPrincipal = null;
	
	protected Color cor_fundo = Constantes.COR_FUNDO_NORMAL;
	
	/** Cor do registro atual. */
	public static final Color COR_ATUAL = Color.red;
	
	
	/**
	 * Constrói uma nova variável do tipo arquivo.
	 * 
	 * @param nome Nome da variável.
	 */
	protected AbstractArquivo(String nome){
		if(nome == null){
			throw new IllegalArgumentException("Nome inválido!");
		}
		
		this.nome = nome;
		this.titulo = this.nome;
		borda = new FileBorder(titulo, getCorTitulo());
		super.setBorder(borda);
		this.file = null;
		
		setBackground(Color.white);
	}
	
	public String getName(){
		return nome;
	}
	
	/**
	 * Associa o nome de um arquivo físico a esse
	 * arquivo.
	 * 
	 * @param nome_arq Nome do arquivo físico.
	 */
	public void assign(String nome_arq){
		file = nome_arq == null ? null : new File(nome_arq);
		titulo = nome + (file == null ? "" : " : '" + file.getName() + "'");
		borda.setTitulo(titulo);
	}
	
	/**
	 * Abre o arquivo para leitura.
	 * 
	 * @throws Exception Se o arquivo não existir ou não
	 * estiver pronto para ser aberto.
	 */
	public abstract void reset() throws Exception;
	
	/**
	 * Abre o arquivo para leitura.
	 * 
	 * Este método não cria um arquivo físico.
	 */
	public abstract void rewrite() throws Exception;
	
	/**
	 * Lê um registro do arquivo.
	 * 
	 * @return O valor armazenado no registro.
	 * 
	 * @throws IOException Se o arquivo não estiver aberto ou o fim
	 * de arquivo for atingido.
	 */
	public abstract Object read() throws IOException;
	
	/**
	 * Verifica se atingiu o final do arquivo.
	 * 
	 * @return true, se atingiu o final do arquivo.
	 */
	public abstract boolean eof();
	
	/**
	 * Fecha o arquivo de modo seguro.
	 * 
	 * @throws IOException
	 */
	public abstract void close() throws IOException;
	
	/**
	 * Verifica se existe um arquivo físico associado a este arquivo.
	 * 
	 * @return true, se estiver associado a um arquivo físico.
	 */
	public boolean isAssigned(){
		return file != null;
	}
	
	public abstract Color getCorTitulo();
	
	public Reader getReader() throws Exception{
		if(url_base == null){
			return new FileReader(file);
		}else{
			URL url = new URL(url_base + file.getName());
			return new InputStreamReader(url.openConnection().getInputStream());
		}
	}
	
	public Border getBorder(){
		return borda;
	}
	
	public void setBorder(Border border){}
	
	public void finalize(){
		try{
			close();
		}catch(Exception e){}
	}
}
