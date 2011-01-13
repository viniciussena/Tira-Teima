package tirateima.gerador;


/**
 * Comando de criação de variável.
 * 
 * @author Luciano Santos
 */
public class CommandNewVar extends Command {
	private VarDefinition ref;
	
	public CommandNewVar(VarDefinition ref) {
		this.ref = ref;
	}
	/**
	 * Executa o comando de criação de variável adicionando a nova variável ao mostrador
	 */
	public void execute(Gerador g)
			throws TiraTeimaLanguageException {
		g.mostrador.adicionarVariavel(newVar(g, ref));
	}
}
