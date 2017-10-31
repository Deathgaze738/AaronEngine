package aaron.game.pokemonatb.manager;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ResourceManager {
	private Map<String, BufferedImage> images = new HashMap<>();
	private Map<String, BufferedImage> sprites = new HashMap<>();
	private Map<String, Clip> sounds =  new HashMap<>();
	private Map<String, Font> fonts = new HashMap<>();
	
	private static ResourceManager instance = null;
	
	public static ResourceManager getInstance(){
		if(instance == null){
			instance = new ResourceManager();
		}
		return instance;
	}
	
	//Temporary method
	public List<BufferedImage> getAnimation(String source, int[] sequence, int firstgid, int tileSize){
		List<BufferedImage> animation = new ArrayList<BufferedImage>();
		for(int frame : sequence){
			//System.out.println(source+"#"+frame);
			animation.add(getSprite(source, frame, firstgid, tileSize));
		}
		return animation;
	}
	
	//Overloaded method gets a sprite from a source image.
	public BufferedImage getSprite(String source, int spriteid, int firstgid, int tileSize){
		try{
			BufferedImage sprite = sprites.get(source+"#"+spriteid);
			if(sprite == null){
				BufferedImage sheet = getImage(source);
				int setWidth = sheet.getWidth();
				int tileX = ((spriteid - firstgid) * tileSize) % setWidth;
				int tileY = tileSize*(((spriteid - firstgid) * tileSize) / setWidth);
				//System.out.println((spriteid - firstgid) + " : " + tileX + " : " + tileY);
				sprite = sheet.getSubimage(tileX, tileY, tileSize, tileSize);
				sprites.put(source+"#"+spriteid, sprite);
			}
			return sprite;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage getImage(String source){
		try{
			BufferedImage image = images.get(source);
			if(image == null){
				image = ImageIO.read(new java.io.FileInputStream(source));
				images.put(source, image);
			}
			return image;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Clip getClip(String source){
		Clip clip = sounds.get(source);
		if(clip == null){
			try {
				File audioFile = new File(source);
				AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
				AudioFormat baseFormat = ais.getFormat();
				AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
				);
				AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
				clip = AudioSystem.getClip();
				clip.open(dais);
				sounds.put(source, clip);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return clip;
	}
	
	public Font getFont(String source) throws FileNotFoundException, IOException{
		Font font = fonts.get(source);
		if(font == null){
			fonts.put(source, font);
		}
		return font;
	}
	
	private ResourceManager(){}
	
	
}
