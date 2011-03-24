package tirateima.controlador;

import java.util.Stack;

import tirateima.gui.variaveis.Variavel;

/**
 * Remove uma variável e sua seta (se houver) do Tira-Teima
 * @author Vinícius
 *
 */
public class CommandRemoveVar extends Command {
	
	private Stack<Object> var_stack;
	
	public CommandRemoveVar(Stack<Object> var_stack) {
		this.var_stack = var_stack;
	}
	
		

	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {		
		//pega o nome da variável
		String nome = (String)var_stack.pop();
		//remove a seta se houver
		c.mostrador.armazenarSetaRemovida(c.mostrador.removerSeta(nome));
		//remove a variável e coloca a variável em uma pilha de variáveis excluídas
		c.mostrador.armazenarVariavelRemovida(c.mostrador.removerVariavel(nome));
		//restaura pilha
		var_stack.push(nome);
	}

	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		//restaura a última variável excluída
		c.mostrador.restaurarVariavelRemovida();
		c.mostrador.restaurarSetaRemovida();
	}

}
