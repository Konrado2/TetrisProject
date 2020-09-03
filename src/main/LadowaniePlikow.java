package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class LadowaniePlikow { 
	public static BufferedImage ladujObraz(String sciezka) {
		try {
			return ImageIO.read(LadowaniePlikow.class.getResource(sciezka));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
		
	}
	public static Clip zaladujDzwiek(String plikDzwiekowy){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(LadowaniePlikow.class.getResource(plikDzwiekowy)));
			return clip;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
