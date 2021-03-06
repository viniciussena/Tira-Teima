package tirateima.controlador;

import java.util.ArrayList;
import java.util.List;

import tirateima.gui.variaveis.Variavel;

/**
 * Comando de definição de novo tipo.
 * 
 * @author Luciano Santos
 */
public class CommandNewType extends Command {
	private String name;
	private List<VarDefinition> fields;
	
	public CommandNewType(String name, List<VarDefinition> fields) {
		this.name = name;
		this.fields = fields;
	}
	
	/**
	 * Executa o comando de novo tipo adicionando os campos e salvando o novo tipo no mapa de registros declarados.
	 */
	public void execute(Controlador c)
			throws TiraTeimaLanguageException {
		
		List<Variavel> vars = new ArrayList<Variavel>();
		for (VarDefinition ref : fields) {
			vars.add(newVar(c, ref));
		}
		
		c.declared_records.put(name, new RecordDefinition(name, vars));
	}
	
	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		// TODO Auto-generated method stub
		
	}
}
