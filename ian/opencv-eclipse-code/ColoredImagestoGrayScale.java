package imageProcessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ColoredImagestoGrayScale {
	      
	 
	   public static void main(String args[]) throws Exception {
		   // Loading the OpenCV core library
		      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		      // Reading the Image from the file and storing it in to a Matrix object
		      String file ="C:\\Users\\ian\\Desktop\\filse\\si\\test 1.2.png";
		      Mat src = Imgcodecs.imread(file);

		      // Creating an empty matrix to store the result
		      Mat dst = new Mat();

		      // Applying color map to an image
		      Imgproc.cvtColor(src, dst,Imgproc.COLOR_RGB2BGR);

		      // Writing the image
		      Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\help.jpg", dst);
		      System.out.println("Image processed");
		   
	   }

}
