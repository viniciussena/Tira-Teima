package tirateima.ui;

/**
 * Um ouvinte para eventos do MostradorModel.
 * 
 * @author Luciano Santos
 */
public interface MostradorModelListener {
	
	/**
	 * Chamado quando o conjunto de componentes é alterado ou quando
	 * o tamanho/posição dos componentes é alterada.
	 * 
	 * @param O evento.
	 */
	public void componentsChanged(MostradorModelEvent e);
}
