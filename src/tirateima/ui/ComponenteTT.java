package tirateima.ui;

import javax.swing.JComponent;

/**
 * Um componente genérico do TiraTeima.
 * 
 * @author Luciano Santos
 */
public abstract class ComponenteTT extends JComponent {
	private static final long serialVersionUID = 1L;
	
	public ComponenteTT(String name) {
		setName(name);
	}
}
