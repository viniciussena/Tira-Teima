package tirateima.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Um modelo para os dados do mostrador.
 * 
 * @author Luciano Santos
 */
public abstract class AbstractMostradorModel implements MostradorModel {
	protected Map<String, ComponenteTT> components;
	protected double proportion;

	protected List<MostradorModelListener> listeners;

	/**
	 * Cria um novo AbstractModel.
	 */
	public AbstractMostradorModel() {
		components = new HashMap<String, ComponenteTT>();
		proportion = 1.0;

		listeners = new ArrayList<MostradorModelListener>();
	}

	/**
	 * Cria um novo AbstractModel com os componentes dados.
	 * 
	 * @param components Os componentes, num mapeamento.
	 */
	public AbstractMostradorModel(Map<String, ComponenteTT> components) {
		assert (components != null);

		this.components = components;
	}

	/**
	 * Adiciona 
	 */
	public void addMostradorModelListener(MostradorModelListener l) {
		assert (l != null);

		listeners.add(l);
	}

	public void addComponent(ComponenteTT component) {
		assert (component != null);

		String name = component.getName();

		if (components.containsKey(name)) {
			ComponenteTT previous = components.get(name);
			components.put(name, component);
			fireComponentsChanged(MostradorModelEvent.ATUALIZADO, null, null,
					previous, component);
		}
	}
	
	public void removeComponent(ComponenteTT component) {
		
	}
	
	public void setProportion(double proportion) {
		assert (proportion >= 0.0 && proportion <= 10.0);

		if (this.proportion != proportion) {
			this.proportion = proportion;

		}
	}

	public double getProportion() {
		return proportion;
	}

	protected void fireComponentsChanged(int type, ComponenteTT added,
			ComponenteTT removed, ComponenteTT previous, ComponenteTT updated) {

		MostradorModelEvent e = new MostradorModelEvent(this, type, added,
				removed, previous, updated);

		for (MostradorModelListener listener : listeners) {
			listener.componentsChanged(e);
		}
	}
}
