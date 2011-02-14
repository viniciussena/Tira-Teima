package tirateima.controlador;


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
	@Override
	public void execute(Controlador c)
			throws TiraTeimaLanguageException {
		c.mostrador.adicionarVariavel(newVar(c, ref));
	}
	
	/**
	 * Desfaz o comando de criação de variável removendo a nova variável do mostrador
	 */
	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		c.mostrador.removerVariavel(ref.getName());		
	}
}
