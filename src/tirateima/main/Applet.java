package tirateima.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tirateima.gui.Principal;
import tirateima.gui.arquivos.AbstractArquivo;
import tirateima.gui.editortexto.Linguagem;


/**
           Exemplo de tag para se colocar no HTML:

<applet code="br.unb.cic.algostep.main.Applet" archive="tirateima.jar" height="600" width="800">

<param name="arq_fonte" value="[nome do arquivo fonte]">
<param name="arq_texto" value="[nome do arquivo txt]">
<param name="modo" value="['janela' ou 'applet']">

</applet>
 */
@SuppressWarnings("serial")
public class Applet extends java.applet.Applet {
	private static Applet instance = null;
	/** Um cache com as entradas que nós já vimos até agora. Salvamos
	 *  a URL do arquivo de passos como chave. */
	private HashMap<URL, Principal> entradas
		= new HashMap<URL, Principal>();
	
	/** Um cache de janelas (visíveis ou não). Usado apenas pelo applet. */
	private HashMap<URL, Programa> janelas = null;
	
	/**
	 * Normaliza uma URL. 
	 */
	public URL normalizarURL(String url) throws MalformedURLException {	
		if (!url.toLowerCase().startsWith("http://"))
			return new URL(getCodeBase() + url);
		else
			return new URL(url);
	}
	
	/**
	 * Retorna uma referencia do applet, caso tenha sido iniciado nesse modo
	 * @return
	 */
	public static Applet getInstance(){
		return instance;
	}
	
	/**
	 * Retorna um Reader correspondente a uma URL (relativa ou absoluta).
	 */
	public Reader getArquivo(URL url) throws Exception {
		return new InputStreamReader(url.openConnection().getInputStream());
	}
	
	/**
	 * Prepara em background uma entrada. Isso significa baixar todos os
	 * arquivos e interpretá-los.
	 */
	public void prepararEntrada(String str_fonte, String str_texto)
			throws MalformedURLException 
	{
		prepararEntrada(normalizarURL(str_fonte), normalizarURL(str_texto), null);
	}
	
	/**
	 * Prepara em background uma entrada. Isso significa baixar todos os
	 * arquivos e interpretá-los.
	 */
	private void prepararEntrada(final URL url_fonte, final URL url_texto,final Linguagem linguagem) {
		new Thread(new Runnable() {
			public void run() {
				Principal p;
				try {
					p = new Principal(getArquivo(url_fonte), getArquivo(url_texto),linguagem);
				} catch (Exception e) {
					e.printStackTrace();
					p = null;
				}
				synchronized (entradas) {
					if (!entradas.containsKey(url_texto))
						entradas.put(url_texto, p);
					entradas.notifyAll(); // se algum thread estiver esperando
				};
			}
		}).start();
	}
	
	/**
	 * Retorna uma entrada que foi preparada anteriormente. Caso a preparação
	 * em background ainda não tenha sido concluída, este método bloqueia.
	 * 
	 * Retorna 'null' quando houver um erro na preparação da entrada.
	 * Se a url não tiver sido preparada, ele não retorna *nunca*.
	 */
	private Principal getEntrada(URL url_texto) {
		while (true) {
			synchronized (entradas) {
				if (entradas.containsKey(url_texto))
					return entradas.get(url_texto);
				else
					try {
						entradas.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return null;
					}
			}
		}
	}
	
	/**
	 * Abre uma janela com uma entrada previamente preparada.
	 * Pode ser chamado quantas vezes quantas forem desejadas após
	 * a entrada ter sido preparada.
	 */
	public void abrirEntrada(String str_fonte, String str_texto)
			throws MalformedURLException 
	{
		// Não usamos str_fonte mas pegamos como argumento para caso no
		// futuro ele seja útil nós não tenhamos que reescrever todos
		// os JavaScripts.
		if (str_fonte != str_texto) return;
		abrirEntrada(normalizarURL(str_texto));
	}
	
	/**
	 * Abre uma janela com uma entrada previamente preparada.
	 */
	private void abrirEntrada(final URL url_texto) {
		Principal principal = getEntrada(url_texto);
		Programa p;
		synchronized (janelas) {
			if (janelas.containsKey(url_texto))
				p = janelas.get(url_texto);
			else {
				// Pegamos 'principal' fora do synchronized para
				// diminuir o tempo que gastamos aqui. Não faz mal
				// pegá-lo no outro caso acima.
				p = new Programa(principal);
				p.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				janelas.put(url_texto, p);
			}
		}
		p.setVisible(true);
		p.toFront();
	}
	
	
	@Override
	public void init() {
		try {
			instance = this;
			AbstractArquivo.url_base = normalizarURL("").toString();
			
			String modo = getParameter("modo").toLowerCase();
			if (modo.equals("janela") || modo.equals("applet")) {
				//Pega a linguagem
				Linguagem linguagem = null;
				if(getParameter("arq_fonte").endsWith(".c") || getParameter("arq_fonte").endsWith(".C"))
					linguagem = Linguagem.C;
				else if(getParameter("arq_fonte").endsWith(".pas") || getParameter("arq_fonte").endsWith(".PAS"))
					linguagem = Linguagem.PASCAL;
				//Pega a url dos arquivos
				URL url_fonte = normalizarURL(getParameter("arq_fonte"));
				URL url_texto = normalizarURL(getParameter("arq_texto"));
				
				prepararEntrada(url_fonte, url_texto,linguagem);
				Principal principal = getEntrada(url_texto);
				if (principal == null)
					throw new Exception("Erro na preparação, veja o Console Java");
				
				if (modo.equals("janela")) {
					/* Pega largura e altura da janela.
					 * Se não existirem, usa valores padrão.
					 */
					int largura, altura;
					
					try{
						String l_temp = getParameter("largura");
						largura = l_temp != null ?
								Integer.parseInt(l_temp) :
								800;
						
						String a_temp = getParameter("altura");
						altura = l_temp != null ?
								Integer.parseInt(a_temp) :
								800;
					}catch(NumberFormatException e){
						largura = 800;
						altura = 600;
					}
					
					Programa p = new Programa(principal);
					p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					p.setSize(largura, altura);
					p.setVisible(true);
				} else {
					GridBagConstraints gbc = new GridBagConstraints();
					setLayout(new GridBagLayout());
					gbc.anchor = GridBagConstraints.CENTER;
					gbc.fill = GridBagConstraints.BOTH;
					gbc.weightx = gbc.weighty = 1.0;
					add(principal);
				}
			} else if (modo.equals("escondido")) {
				// Não faz nada =).
				// No modo 'escondido' nós esperamos sermos chamados pra agir,
				// e não o contrário. Veja 'prepararEntrada(String, String)'
				// e 'mostrarEntrada(String, String)' (as versões que recebem
				// URLs desses métodos não são destinadas à interface com
				// código JavaScript).
				
				// Como precaução, vamos nos assegurar que não nos foi dado
				// nenhum arquivo direto como argumento.
				if (getParameter("arq_fonte") != null 
						|| getParameter("arq_texto") != null)
					throw new Exception("No modo \"escondido\" não devem ser" +
							"especificados os parâmetros \"arq_fonte\" e" +
							"\"arq_texto\".");
				
				// Como mais precaução, criaremos 'janelas' aqui para que o
				// applet nunca seja usado como 'escondido' sem estar escondido.
				janelas = new HashMap<URL, Programa>();
			} else {
				throw new Exception("Modo \"" + modo + "\" desconhecido.");
			}
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Tira-Teima", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
