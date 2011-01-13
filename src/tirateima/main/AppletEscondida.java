/**
 * 
 */
package tirateima.main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tirateima.gui.arquivos.AbstractArquivo;

/**
 * Esta é uma classe temporária para testar applet com
 * janela escondidas.
 * 
 * @author Luciano
 */
@SuppressWarnings("serial")
public class AppletEscondida extends Applet {
	private static AppletEscondida instance = null;
	private JButton btnAbrir;
	private Programa janela;
	
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
	public static AppletEscondida getInstance(){
		return instance;
	}
	
	/**
	 * Retorna um Reader correspondente a uma URL (relativa ou absoluta).
	 */
	public Reader getArquivo(URL url) throws Exception {
		return new InputStreamReader(url.openConnection().getInputStream());
	}	
	
	@Override
	public void init() {	
		try {
			instance = this;
			AbstractArquivo.url_base = normalizarURL("").toString();
			
			URL url_fonte = normalizarURL(getParameter("arq_fonte"));
			URL url_texto = normalizarURL(getParameter("arq_texto"));
			
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
			
			janela = new Programa(getArquivo(url_fonte), getArquivo(url_texto));
			
			janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			janela.setSize(largura, altura);
			
			setLayout(new BorderLayout());
			add(getBtnAbrir(getParameter("nome")), BorderLayout.CENTER);
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Tira-Teima", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JButton getBtnAbrir(String nome){
		if(nome == null) nome = "Teste";
		
		if(btnAbrir == null){
			btnAbrir = new JButton(nome);
			btnAbrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					janela.setVisible(true);
				}
			});
		}
		
		return btnAbrir;
	}	
}
