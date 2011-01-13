package tirateima.gui.arquivos;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import tirateima.IEstado;


public class GerenciadorArquivos extends JComponent implements IEstado{
	private static final long serialVersionUID = 7976910393135080383L;
	
	private ArrayList<AbstractArquivo> arquivos = new ArrayList<AbstractArquivo>();
	private int arq_atual = -1;
	
	private JTabbedPane pPrincipal = null;
	
	private class EstadoGA{
		/* Arquivo sendo visualizado atualmente. */
		public int arq_atual;
		
		/* Lista dos arquivos neste GA. */
		public ArrayList<AbstractArquivo> arquivos;
		
		/* Lista dos estados de cada arquivo, na ordem. */
		public ArrayList<Object> estados;
		
		public EstadoGA(ArrayList<AbstractArquivo> arquivos, int arq_atual){
			assert arquivos != null;
			
			this.arq_atual = arq_atual;
			this.arquivos = arquivos;
			
			estados = new ArrayList<Object>();
			
			ListIterator<AbstractArquivo> i = arquivos.listIterator();
			while(i.hasNext()){
				estados.add(i.next().getEstado());
			}
		}		
	}
	
	public GerenciadorArquivos(){
		super();
		inicializar();
	}
	
	/**
	 * Adiciona um novo arquivo ao GA.
	 * 
	 * @param f Arquivo a ser adicionado.
	 */
	public void adicionarArquivo(AbstractArquivo f){
		assert(f != null);
		
		if(arquivos.size() == 0){
			arq_atual = 0;
		}else{
			if(buscarArquivo(f.getName()) >= 0){
				throw new IllegalArgumentException("Arquivo já existente!");
			}
		}
	
		arquivos.add(f);
		//atualizarVisualizacao();
	}
	
	/**
	 * Remove um arquivo do GA.
	 * 
	 * @param nome Nome do arquivo a ser removido.
	 */
	public void removerArquivo(String nome){
		assert(nome != null);
		
		int indice;
		if((indice = buscarArquivo(nome)) >= 0){
			arquivos.remove(indice);
			//atualizarVisualizacao();
		}else{
			throw new IllegalArgumentException("Arquivo não encontrado!");
		}
	}
	
	/**
	 * Retorna um arquivo deste GA.
	 * 
	 * @param nome Nome do arquivo.
	 * 
	 * @return Arquivo, se achar.
	 * @return null Se não achar.
	 */
	public AbstractArquivo getArquivo(String nome){
		assert(nome != null);
		
		int indice;
		if((indice = buscarArquivo(nome)) >= 0){
			setSelecionado(indice);
			return arquivos.get(indice);
		}
		
		return null;
	}
	
	public void setSelecionado(int indice) {
		//TODO testar indice
		arq_atual = indice;
	}
	
	//IEstado
	public Object getEstado(){
		if(arquivos.size() == 0){
			return null;
		}
		
		ArrayList<AbstractArquivo> temp = new ArrayList<AbstractArquivo>();
		temp.addAll(arquivos);
		
		return new EstadoGA(arquivos, arq_atual);
	}
	
	public void setEstado(Object estado){
		arquivos = new ArrayList<AbstractArquivo>();
		arq_atual = -1;
		
		if(estado != null){
			EstadoGA e = (EstadoGA) estado;
			arq_atual = e.arq_atual;
			arquivos = e.arquivos;
			ListIterator<AbstractArquivo> i = arquivos.listIterator();
			ListIterator<Object> j = e.estados.listIterator();
			while(i.hasNext()){
				i.next().setEstado(j.next());
			}
		}
		
		atualizarVisualizacao();
	}
	
	
	/**
	 * Retorna o índice de um arquivo na lista de arquivos.
	 * 
	 * @param nome Nome do arquivo a ser buscado.
	 * @return Índice do arquivo.
	 */
	protected int buscarArquivo(String nome){
		int indice = 0;
		boolean achou = false;
		
		ListIterator<AbstractArquivo> i = arquivos.listIterator();
		while(i.hasNext() && !achou){
			if(i.next().getName().equalsIgnoreCase(nome)){
				achou = true;
			}else{
				indice++;
			}
		}
		
		return achou ? indice : -1;
	}
	
	/**
	 * Inicializa a interface do GA.
	 */
	private void inicializar(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1.0;
		
		add(getPPrincipal(), gbc);
		
		setVisible(false);
	}
	
	private JTabbedPane getPPrincipal(){
		if(pPrincipal == null){
			pPrincipal = new JTabbedPane();
		}
		return pPrincipal;
	}
	
	/**
	 * Arruma a interface do GA.
	 */
	private void atualizarVisualizacao(){
		pPrincipal.removeAll();
		
		ListIterator<AbstractArquivo> i = arquivos.listIterator();
		while(i.hasNext()){
			pPrincipal.add(i.next());
		}
		
		if(arquivos.size() > 0){
			setVisible(true);
			pPrincipal.setSelectedIndex(arq_atual);
		}else{
			setVisible(false);
		}
	}
	
	public void setVisible(boolean visivel) {
		if (this.visivel != visivel) {
			this.visivel = visivel;
			super.setVisible(visivel);
			
			ListIterator<ArquivoVisivelEventListener> i = eventos.listIterator();
			if (visivel) {
				while(i.hasNext()) {
					i.next().ficouVisivel();
				}
			} else {
				while(i.hasNext()) {
					i.next().ficouInvisivel();
				}
			}
		}
	}
	
	public void addArquivoVisivelListener(ArquivoVisivelEventListener e) {
		eventos.add(e);
	}
	
	private boolean visivel;
	List<ArquivoVisivelEventListener> eventos = new ArrayList<ArquivoVisivelEventListener>();
	
	public Dimension getPreferredSize() {
		if (arquivos.size() > 0) {
			return arquivos.get(arq_atual).getPreferredSize();
		} else {
			return new Dimension(0, 300);
		}
	}
}
