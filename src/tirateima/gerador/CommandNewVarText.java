package tirateima.gerador;

import tirateima.gui.arquivos.VarText;

/**
 * Comando de criação de arquivo texto (simbólico).
 * 
 * @author Luciano Santos
 */
public class CommandNewVarText extends Command {
	private String name;
	
	public CommandNewVarText(String name) {
		this.name = name;
	}
	
	/**
	 * Executa o comando de nova variável de texto, adicionando a variável criada ao gerenciador de arquivo.
	 */
	public void execute(Gerador g)
			throws TiraTeimaLanguageException {
		VarText arq = new VarText(name);
		g.ga.adicionarArquivo(arq);
	}
}
