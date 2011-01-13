package tirateima.gui.arquivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * Modela um leitor, que lê dado correspondente de um
 * arquivo texto.
 * 
 * @author Luciano Santos
 */
public interface IDataReader {
	/**
	 * Lê os dados do arquivo e altera internamente.
	 * 
	 * @param r Fonte dos dados.
	 * 
	 * @return 0, se tudo ocorreu bem, -1, se atingiu
	 * o final do arquivo.
	 * 
	 * @throws IOException Se algum erro de IO ocorrer
	 * ou o registro estiver incompleto no arquivo.
	 */
	public int readData(BufferedReader r) throws IOException;
	
	/**
	 * Grava os dados da variável no arquivo.
	 * 
	 * Usar com cuidado! O dado pode ter um valor variável e,
	 * portanto, os registros que vierem depois podem ficar
	 * corrompidos. O ideal é só escrever no final do arquivo.
	 * 
	 * @param w Stream de escrita para o arquivo.
	 * @throws IOException
	 */
	public void writeData(Writer w) throws IOException;
}
