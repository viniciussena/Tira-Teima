package tirateima.gerador;

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
	public void execute(Gerador g)
			throws TiraTeimaLanguageException {
		
		if (g.declared_records.containsKey(name))
			gerarErro("Tipo '" + name + "' redeclarado!");
		
		List<Variavel> vars = new ArrayList<Variavel>();
		for (VarDefinition ref : fields) {
			vars.add(newVar(g, ref));
		}
		
		g.declared_records.put(name, new RecordDefinition(name, vars));
	}
}
