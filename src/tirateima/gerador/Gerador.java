package tirateima.gerador;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tirateima.controlador.Estado;
import tirateima.gui.alerta.Alerta;
import tirateima.gui.arquivos.GerenciadorArquivos;
import tirateima.gui.console.Console;
import tirateima.gui.editortexto.EditorTexto;
import tirateima.gui.variaveis.Mostrador;
import tirateima.parser.ParseException;
import tirateima.parser.TiraTeimaParser;

/**
 * Um gerador de estados para o tirateima. 
 * Ele chama o parser para pegar os passos do programa. Cria, então, uma lista de estados.
 * Esses estados posteriormente serão chamados pelo controlador para a execução do programa.
 * 
 * @author Luciano Santos
 * @author Andrew Biller
 */
public class Gerador {
	/** Os tipos declarados pelo usuário. */
	public Map<String, RecordDefinition> declared_records;
	
	/** As funções declaradas pelo usuário. */
	public Map<String, FunctionDeclaration> declared_functions;
	
	/** O Mostrador. */
	public Mostrador mostrador;
	
	/** O editor_texto. */
	public EditorTexto editor_texto;
	
	/** O console. */
	public Console console;
	
	/** Os alertas. */
	public Alerta alerta;
	
	/** O gerador de arquivos. */
	public GerenciadorArquivos ga;
	
	
	/**
	 * Constrói um novo gerador. Recebe cada uma dos componentes gráficos da tela do tirateima para poder setar seus estados.
	 * 
	 * @param mostrador
	 * @param editor
	 * @param console
	 * @param ga
	 */
	public Gerador(
			Mostrador mostrador,
			EditorTexto editor,
			Console console,
			Alerta alerta,
			GerenciadorArquivos ga) {
		
		this.mostrador = mostrador;
		this.editor_texto = editor;
		this.console = console;
		this.alerta = alerta;
		this.ga = ga;
	}
	
	/**
	 * Analisa um arquivo de comandos do Tira-Teima e gera a lista de estados.
	 * Recebe um arquivo reader a ser lido, inicializa o parser e chama o 
	 * parser para ler passo a passo o roteiro do programa.
	 *  
	 * @param reader
	 * @return List<Estado> uma lista de destados.
	 * @throws TiraTeimaLanguageException
	 * @throws ParseException
	 */
	public List<Estado> parse(Reader reader) throws TiraTeimaLanguageException, ParseException {
		declared_records = new HashMap<String, RecordDefinition>();
		declared_functions = new HashMap<String, FunctionDeclaration>();
		
		TiraTeimaParser parser = new TiraTeimaParser(reader);
		/** Inicializa a lista de estados e os passos a serem executados sobre a variável step */
		Step step;
		List<Estado> result = new ArrayList<Estado>();
		/** Enquanto o parser ler um passo e retorná-lo */
		while ((step = parser.step()) != null) {
			/** Imprime o passo para fins de depuração */
			System.out.println(step.toString());
			/** Para cada passo gerado */
			for (Command c : step.commands) {
				/** Executa os comandos contidos no passo */
				c.execute(this);
			}
			/** Salva o estado de cada componente para a linha informada, agregando mais um estado à lista de estados. */
			saveState(result, step.line);
		}
		
		return result;
	}
	
	/**
	 * Salva um estado na lsita de estados.
	 * Recebe a lista de estados e um número de linha para aquele estado, cria e salva o novo estado.
	 * @param states
	 * @param line
	 */
	private void saveState(List<Estado> states, int line) {
		/** Cria um novo estado */
		Estado e = new Estado();
		/** Coloca no estado criado a condição de cada elemento gráfico do tirateima. */
		editor_texto.getCaixaTexto().setMarcada(line);
		e.est_mostrador = mostrador.getEstado();
		e.est_editor = editor_texto.getEstado();
		e.est_console = console.getEstado();
		e.est_alerta = alerta.getEstado();
		e.est_ga = ga.getEstado();
		/** Adiciona o estado criado à lista de estados. */
		states.add(e);
	}
}
