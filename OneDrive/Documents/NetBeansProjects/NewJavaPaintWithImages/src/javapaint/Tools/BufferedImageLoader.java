package javapaint.Tools;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	public BufferedImage img;
	
	public BufferedImage loadImage (String path) throws IOException{
		
		img = ImageIO.read(getClass().getResourceAsStream(path));
		return img;
	}
}
