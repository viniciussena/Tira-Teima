package tirateima.ui;

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import tirateima.main.Applet;
import tirateima.main.AppletEscondida;
 

/**
 * Classe usada para tocar um som local em uma nova Thread (formato de som: .wav) 
 * (nao funciona na versao applet por causa da permissao do sandbox)
 * Uso: new Som("sons/arquivo.wav").start()
 * fonte: http://www.anyexample.com/programming/java/java_play_wav_sound_file.xml
 * 
 * Em caso de falha uma mensagem no syserr é lançada, nao impedindo a execucao do programa
 * @since 19/06/2009
 */
public class Som extends Thread {
 
	private String filename;
	private Position curPosition;
	public boolean tocarSomPadrao;
	private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
	public static final String SOM_PADRAO_URI = "sons/beep.wav";
	
 
	enum Position {
		LEFT, RIGHT, NORMAL
	};
	
	public Som(boolean somPadraoBeep) {
		tocarSomPadrao = somPadraoBeep;
		curPosition = Position.NORMAL;
	}
	
	public Som(String wavfile) {
		filename = wavfile;
		curPosition = Position.NORMAL;
	}
 
	public Som(String wavfile, Position p) {
		filename = wavfile;
		curPosition = p;
	}
 
	public void run() {
		
			try{
				if(tocarSomPadrao){
					filename = "resources/beep.wav";
				}
				Applet ap = Applet.getInstance();
				AppletEscondida ape = AppletEscondida.getInstance();
				
				if(ap != null || ape != null){
					//Toca no modo applet (sandbox)
					System.out.println("Tocando som via applet");
					AudioClip audioClip;
					if(ap != null){
						audioClip = ap.getAudioClip(ap.getCodeBase(), filename);
					}else{
						audioClip = ape.getAudioClip(ape.getCodeBase(), filename);
					}
					audioClip.play();
				}else{
					//Toca no modo application
					System.out.println("Tocando som via application");
					URL local = getClass().getResource("/" + filename);
					filename = local.toString().replace("file:/", "").replace("%20", " ");
					playLocal();
				}
			}catch(Exception ex){
				System.err.println("Não foi possível toca o som: " + ex.getMessage());
			}

 
 
	}

	private void playLocal() {
		File soundFile = new File(filename);
		if (!soundFile.exists()) {
			System.err.println("Wave file not found: " + filename);
			return;
		}
 
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
 
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
 
		if (auline.isControlSupported(FloatControl.Type.PAN)) {
			FloatControl pan = (FloatControl) auline
					.getControl(FloatControl.Type.PAN);
			if (curPosition == Position.RIGHT)
				pan.setValue(1.0f);
			else if (curPosition == Position.LEFT)
				pan.setValue(-1.0f);
		} 
 
		auline.start();
		int nBytesRead = 0;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}
	}
}
