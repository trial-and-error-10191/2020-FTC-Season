package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class addpixel {

	 public static void main(String args[])throws IOException {
	      //Reading the image
	      File file= new File("C:\\Users\\ian\\Desktop\\filse\\si\\BGR.png");
	      BufferedImage img = ImageIO.read(file);
	      for (int y = 0; y < img.getHeight(); y++) {
	         for (int x = 0; x < img.getWidth(); x++) {
	            //Retrieving contents of a pixel
	            int pixel = img.getRGB(x,y);
	            //Creating a Color object from pixel value
	            Color color = new Color(pixel, true);
	            //Retrieving the R G B values
	            int red = color.getRed();
	            int green = color.getGreen();
	            int blue = color.getBlue();
	            //Modifying the RGB values
	            green = 130;
	            blue = 130;
	            red = 255;
	            //Creating new Color object
	            color = new Color(red, green, blue);
	            //Setting new Color object to the image
	            img.setRGB(x, y, color.getRGB());
	            
	            
	         
	         }
	      }
	      file = new File("C:\\Users\\ian\\Desktop\\filse\\si\\help.jpg");
	      ImageIO.write(img, "jpg", file);
	      System.out.println("Done...");
	 }
}
	      


