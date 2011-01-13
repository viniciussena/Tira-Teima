package tirateima.gui.arquivos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tirateima.gui.Constantes;
import tirateima.gui.variaveis.Janela;
import tirateima.gui.variaveis.Variavel;


/**
 * Modela um arquivo binário de Pascal.
 * 
 * @author Luciano
 */
public class VarFile extends AbstractArquivo{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Modela um estado de VarFile
	 * 
	 * @author Luciano
	 */
	private class EstadoVarFile {
		/* O arquivo */
		public File file = null;
		
		public boolean is_opened = false;
		
		public boolean read_only = true;
		
		/* Indice do registro atual */
		public int atual;
		
		/* Registros no arquivo*/
		public List<Variavel> registros = new ArrayList<Variavel>();
	}
	
	private Variavel tipo = null;
	private ArrayList<Variavel> buffer = null;
	private int atual = 0;
	private JPanel pVisualizacao = null;
	
	private int num_max_reg = 5;
	
	private Color cor_fundo = Constantes.COR_FUNDO_NORMAL;
	
	/** Cor do registro atual. */
	public static final Color COR_ATUAL = Color.red;
	
	/**
	 * Constrói uma nova variável do tipo arquivo binário (file)
	 * de Pascal, associando-a a um tipo de registro.
	 * 
	 * @param nome Nome da variável.
	 * @param tipo Tipo dos registros nesse arquivo (deve derivar de Variavel).
	 * 
	 * @throws Exception
	 */
	public VarFile(String nome, Variavel tipo){
		super(nome);
		assert tipo != null;
		
		this.tipo = tipo.criarCopia();
		this.buffer = new ArrayList<Variavel>();
		
		inicializar();
	}
	
	public void reset() throws Exception{
		open();
		read_only = true;
	}
	
	public void rewrite() throws Exception {
		if(is_opened){
			close();
		}
		
		buffer = new ArrayList<Variavel>();
		is_opened = true;
		read_only = false;
	}
	
	/**
	 * Move para o registro informado.
	 * 
	 * @param registro Registro a ser atingido.
	 */
	public void seek(int registro){
		if(!is_opened){
			throw new RuntimeException("Arquivo fechado!");
		}
		
		if((registro < 1) || (registro > buffer.size())){
			throw new IllegalArgumentException("Registro inexistente: " + registro + "!");
		}
		
		atual = registro - 1;
	}
	
	public Object read() throws IOException{
		if(eof()) throw new IOException("Fim de arquivo atingido.");
		Variavel v = buffer.get(atual).criarCopia();
		atual++;
		return v.getValor();
	}
	
	public void write(Variavel v) {
		if(read_only) {
			throw new RuntimeException("Arquivo aberto em modo de leitura");
		}
		
		buffer.add(v.criarCopia());
	}
	
	public boolean eof() {
		return atual == buffer.size();
	}
	
	public void close() throws IOException {
		file = null;
		buffer = null;
		is_opened = false;
	}
	
	public int getNumMaxRegistros(){
		return num_max_reg;
	}
	
	public void setNumMaxRegistros(int num){
		if(num > 0){
			num_max_reg = num;
		}
	}
	
	public Dimension getMinimumSize(){
		if (buffer != null) {
			if (buffer.size() > 0) {
				Dimension tj = buffer.get(0).getSize();
				return new Dimension(tj.width * buffer.size(), tj.height + 20);
			} else {
				return new Dimension(0, 0);
			}
		} else {
			return new Dimension(0, 0);
		}
	}
	
	public Dimension getMaximumSize(){
		Dimension d = getMinimumSize();
		return new Dimension(d.width * 10, d.height * 10);
	}
	
	public Dimension getPreferredSize(){
		return getMinimumSize();
	}
	
	private void inicializar(){
		setLayout(new BorderLayout());
		add(getSpPrincipal(), BorderLayout.CENTER);
	}
	
	private JScrollPane getSpPrincipal(){
		if(spPrincipal == null){
			spPrincipal = new JScrollPane(getPVisualizacao(), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		return spPrincipal;
	}
	
	private JPanel getPVisualizacao(){
		if(pVisualizacao == null){
			pVisualizacao = new JPanel();
			pVisualizacao.setLayout(new GridBagLayout());
			pVisualizacao.setBackground(cor_fundo);
		}
		return pVisualizacao;
	}
	
	/**
	 * Abre o arquivo e carrega o buffer.
	 * 
	 * @throws Exception Se não for possível obter um Reader
	 * para este arquivo.
	 */
	private void open() throws Exception{
		if(file == null){
			throw new IOException("Nenhum nome associado ao arquivo!");
		}
		
		if(is_opened){
			close();
		}
		
		//Lê os registros
		BufferedReader bf = new BufferedReader(getReader());
		Variavel temp = tipo.criarCopia();
		buffer = new ArrayList<Variavel>();
		while(temp.readData(bf) == 0){
			buffer.add(temp);
			temp = tipo.criarCopia();
		}
		bf.close();
		is_opened = true;
		if(buffer.size() > 0){
			seek(1);
		}
	}
	
	private void atualizarVisualizacao(){
		pVisualizacao.removeAll();
		
		if(is_opened){
			GridBagConstraints gb = new GridBagConstraints();
			gb.insets = new Insets(1, 1, 1, 1);
			gb.fill = GridBagConstraints.BOTH;
			gb.anchor = GridBagConstraints.NORTHWEST;
			gb.weightx = gb.weighty = 1.0;
			gb.gridheight = gb.gridwidth = 1;
			gb.gridy = gb.gridx = 0;
			
			//Adiciona cada registro
			int i = 1;
			ListIterator<Variavel> it = buffer.listIterator();
			Variavel temp;
			Janela jatual = null;
			
			while(it.hasNext()){
				temp = it.next();
				Janela j = new Janela(temp);
				if(atual == i - 1){
					j.setCorFundo(COR_ATUAL);
					jatual = j;
				}
				j.setTitulo(String.valueOf(i));
				pVisualizacao.add(j, gb);
				gb.gridx++;
				i++;
			}
			
			//Adiciona fim de arquivo
			if(read_only) {
				Variavel v = new RegistroEOF(tipo);
				Janela j = new Janela(v);
				pVisualizacao.add(j, gb);
				if(jatual == null) jatual = j;
			}
			
			//Redimensiona
			pVisualizacao.setSize(getMinimumSize());
			spPrincipal.setSize(pVisualizacao.getSize());
			
			setSize(getMinimumSize());
			revalidate();
			repaint();
			
			//Move para registro atual
			Rectangle r = new Rectangle(pVisualizacao.getX() + jatual.getX(), jatual.getY(),
					jatual.getWidth(), jatual.getHeight());
			if(atual  <= buffer.size() - num_max_reg / 2){
				r.width += jatual.getWidth() * (getNumMaxRegistros()/2);
			}
			spPrincipal.getViewport().scrollRectToVisible(r);
		}
	}
	
	public Color getCorTitulo(){
		return Color.blue;
	}
	
	//IEstado
	public Object getEstado(){
		EstadoVarFile e = new EstadoVarFile();
		e.file = file;
		e.is_opened = is_opened;
		e.read_only = read_only;
		e.registros.addAll(buffer);
		e.atual = atual;
		return e;
	}
	
	public void setEstado(Object estado){
		buffer = null;
		file = null;
		is_opened = false;
		atual = 0;
		
		if(estado != null){
			EstadoVarFile e = (EstadoVarFile) estado;
			file = e.file;
			if(is_opened = e.is_opened){
				buffer = new ArrayList<Variavel>();
				buffer.addAll(e.registros);
				atual = e.atual;
			}
			read_only = e.read_only;
		}
		
		atualizarVisualizacao();
	}
}
