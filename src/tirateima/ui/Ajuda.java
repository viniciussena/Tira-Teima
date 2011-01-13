package tirateima.ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;

import tirateima.main.Applet;
import tirateima.main.AppletEscondida;


/**
 * Classe listener para o botao de ajuda
 * @author Andrew Biller
 *
 */
@SuppressWarnings("serial")
public class Ajuda extends Applet implements java.awt.event.ActionListener {
	
	/**
	 * Abre a url de ajuda se estiver rodando via applet ou application
	 * @param e Evento
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			Applet ap = Applet.getInstance();
			AppletEscondida ape = AppletEscondida.getInstance();
			
			if(ap != null || ape != null){
				//Abre via url
				System.out.println("Abrindo url no modo applet");
				if(ap != null){
					URL ajuda = new URL(ap.getDocumentBase(), "ajuda/ajuda.html");
					ap.getAppletContext().showDocument(ajuda,"_blank");
				}else{
					URL ajuda = new URL(ape.getDocumentBase(), "ajuda/ajuda.html");
					ape.getAppletContext().showDocument(ajuda,"_blank");
				}
			}else{
				//Abre localmente a página html
				System.out.println("Abrindo url no modo local");
				showInBrowser("file://" + getCurrentDir() + "/ajuda/ajuda.html");
				
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Não foi possível abrir a página de ajuda!", 
					"AlgoStep", JOptionPane.ERROR_MESSAGE);
			System.err.println(ex);
		}
	}

	
	/**
	 * Abre uma janela do browser com a url especificada.
	 * MAC e Win abre com o Browser padrao, no Linux ele tenta pelos browsers mais conhecidos.
	 * @param url
	 * @return False caso não seja possível abrir a url em algum browser
	 */
	private static boolean showInBrowser(String url){
    	String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try{
	        if (os.indexOf( "win" ) >= 0) {
	        	// this doesn't support showing urls in the form of "page.html#nameLink" 
	            rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
	        } else if (os.indexOf( "mac" ) >= 0) {
	            rt.exec( "open " + url);
	        } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
	        	// Do a best guess on unix until we get a platform independent way
	        	// Build a list of browsers to try, in this order.
	        	String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
	        			"netscape","opera","links","lynx"};
	        	
	        	// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
	        	StringBuffer cmd = new StringBuffer();
	        	for (int i=0; i<browsers.length; i++)
	        		cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
	        	
	        	rt.exec(new String[] { "sh", "-c", cmd.toString() });
	        } else {
	        	return false;
	        }
        }catch (IOException e){
        	return false;
        }
        return true;
    }
    
    
	/**
	 * Recupera o diretório do tirateima
	 * @return Uma String com diretório físico do tirateima.
	 */
    private String getCurrentDir() {
        File dir1 = new File (".");
        File dir2 = new File ("..");
        String dir = null;
        try {
          System.out.println ("Current dir : " + dir1.getCanonicalPath());
          System.out.println ("Parent  dir : " + dir2.getCanonicalPath());
          
          dir = dir1.getCanonicalPath();
          }
        catch(Exception e) {
          e.printStackTrace();
          }
		return dir;
    }
    
}
