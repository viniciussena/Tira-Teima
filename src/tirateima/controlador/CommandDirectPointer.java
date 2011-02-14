package tirateima.controlador;

import java.awt.Point;
import java.util.Stack;

public class CommandDirectPointer extends Command {
	
	private Stack<Object> var_stack;
	Point posicaoApontada;

	public CommandDirectPointer(Stack<Object> var_stack, Point posicaoApontada) {
		this.var_stack = var_stack;
		this.posicaoApontada = posicaoApontada;
	}

	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException {
		criarSeta(c.mostrador, var_stack, posicaoApontada);
	}
	
	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		removerSeta(c.mostrador, var_stack);
		
	}

}
