package tirateima.controlador;

/**
 * Classe que modela um comando de jump para alterar a ordem de execução dos passos do 
 * Tira-Teima.
 * 
 * @author Vinícius
 *
 */
public class CommandJump extends Command {
	
	private String label;
	
	public CommandJump(String label){
		this.label = label;
	}
	
	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException {
		c.jump = Boolean.TRUE;
		c.jumpTo = this.label;
	}

}
