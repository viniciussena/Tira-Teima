package tirateima.controlador;

import java.util.ArrayList;
import java.util.List;

import tirateima.gui.variaveis.Variavel;

/**
 * Contém uma definição de tipo (registro) do Tira-Teima.
 * 
 * @author Luciano Santos
 */
public class RecordDefinition {
	/** O nome do tipo. */
	public String name;
	
	/** A lista de campos do tipo. */
	public List<Variavel> fields;
	
	/**
	 * Cria uma nova definição de tipo.
	 * 
	 * @param name
	 * @param fields
	 */
	public RecordDefinition(String name, List<Variavel> fields) {
		this.name = name;
		this.fields = fields;
	}
	
	/**
	 * Cria uma cópia dessa definição de tipo.
	 */
	public RecordDefinition clone() {
		String name_aux = new String(name);
		List<Variavel> fields_aux = new ArrayList<Variavel>();
		
		for (Variavel v : fields) {
			fields_aux.add(v.criarCopia());
		}
		
		return new RecordDefinition(name_aux, fields_aux);
	}
}
