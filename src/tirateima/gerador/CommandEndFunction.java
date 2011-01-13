package tirateima.gerador;

/**
 * Modela um comando de fim de função.
 */
public class CommandEndFunction extends Command {
	/**
	 * Executa o comando de fim de função, chamando diretamente o mostrador. 
	 */
	public void execute(Gerador g) throws TiraTeimaLanguageException {
		g.mostrador.endFunction();
	}
}
