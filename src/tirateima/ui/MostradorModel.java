package tirateima.ui;

/**
 * Um modelo que controla os componentes no em um mostrador.
 * 
 * @author Luciano Santos
 */
public interface MostradorModel {

	/**
	 * Adiciona um componente a este modelo.
	 * 
	 * Se o componente já existe, ele será substituído.
	 * 
	 * @param component O componente.
	 */
	public void addComponent(ComponenteTT component);
	
	/**
	 * Remove um componente do modelo.
	 * 
	 * @param component
	 */
	public void removeComponent(ComponenteTT component);
	
	/**
	 * Adiciona um listener ao modelo.
	 * 
	 * @param l O listener.
	 */
	public void addMostradorModelListener(MostradorModelListener l);
	
	/**
	 * Define a proporção para os componentes neste modelo.
	 * 
	 * @param proportion
	 */
	public void setProportion(double proportion);
	
	/**
	 * Retorna a proporção para os componentes neste modelo.
	 * 
	 * @return
	 */
	public double getProportion();
}
