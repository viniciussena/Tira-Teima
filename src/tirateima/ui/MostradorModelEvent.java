package tirateima.ui;

import java.util.EventObject;

/**
 * Um evento lançado pelo mostrador.
 * 
 * @author Luciano Santos
 */
public class MostradorModelEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public static final int INSERIDO = 0;
	public static final int REMOVIDO = 1;
	public static final int ATUALIZADO = 2;
	public static final int REPOSICIONADO = 3;

	private int type;
	private ComponenteTT added;
	private ComponenteTT removed;
	private ComponenteTT previous;
	private ComponenteTT updated;
	/**
	 * Método Contrutor (?)
	 * @param source
	 * @param type
	 */
	public MostradorModelEvent(MostradorModel source, int type) {
		this(source, type, null, null, null, null);
	}

	public MostradorModelEvent(
			MostradorModel source,
			int type,
			ComponenteTT added,
			ComponenteTT removed,
			ComponenteTT previous,
			ComponenteTT updated) {

		super(source);

		assert (type >= INSERIDO && type <= REPOSICIONADO);

		this.type = type;
		this.added = added;
		this.removed = removed;
		this.previous = previous;
		this.updated = updated;
	}

	public int getType() {
		return type;
	}
	
	/**
	 * Retorna o componente adicionado, ou null, se nenhum componente foi
	 * adicionado.
	 * 
	 * @return
	 */
	public ComponenteTT getAdded() {
		return added;
	}
	
	/**
	 * Retorna o componente removido, ou null, se nenhum componente foi
	 * removido.
	 * 
	 * @return
	 */
	public ComponenteTT getRemoved() {
		return removed;
	}

	/**
	 * Retorna o componente anterior à atualização, ou null, se nenhum
	 * componente foi atualizado.
	 * 
	 * @return
	 */
	public ComponenteTT getPrevious() {
		return previous;
	}

	/**
	 * Retorna o componente atual, depois da atualização, ou null, se nenhum
	 * componente foi atualizado.
	 * 
	 * @return
	 */
	public ComponenteTT getUpdated() {
		return updated;
	}
}
