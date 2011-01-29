package tirateima.controlador;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import tirateima.parser.ParseException;
import tirateima.parser.TiraTeimaParser;

/**
 * Um gerador de passos para o tirateima. 
 * 
 * Ele chama o parser para pegar os passos do programa. Cria, então, uma lista 
 * de passos.
 * 
 * Esses passos posteriormente serão chamados pelo controlador para a execução 
 * do programa.
 * 
 * @author Luciano Santos
 * @author Andrew Biller
 */
public class Gerador {	
	/**
	 * Analisa um arquivo de comandos do Tira-Teima e gera a lista de passos.
	 * Recebe um arquivo reader a ser lido, inicializa o parser e chama o 
	 * parser para ler passo a passo o roteiro do programa.
	 *  
	 * @param reader
	 * @return List<Step> uma lista de de passos.
	 * @throws TiraTeimaLanguageException
	 * @throws ParseException
	 */
	public List<Step> parse(Reader reader) throws TiraTeimaLanguageException, ParseException {		
		TiraTeimaParser parser = new TiraTeimaParser(reader);
		/** Inicializa a lista de estados e os passos a serem executados sobre a variável step */
		Step step;
		List<Step> passos = new ArrayList<Step>();
		/** Enquanto o parser ler um passo e retorná-lo */
		while ((step = parser.step()) != null) {
			/** Imprime o passo para fins de depuração */
			System.out.println(step.toString());
			/** Adiciona o estado criado à lista de estados. */
			passos.add(step);
		}
		return passos;
	}
}
