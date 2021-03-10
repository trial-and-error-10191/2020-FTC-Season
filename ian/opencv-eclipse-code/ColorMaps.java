package imageProcessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ColorMaps {
	public static void main(String args[]) {
	      // Loading the OpenCV core library
	      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	      // Reading the Image from the file and storing it in to a Matrix object
	      String file ="C:\\Users\\ian\\Desktop\\filse\\si\\trump poster.png";
	      Mat src = Imgcodecs.imread(file);

	      // Creating an empty matrix to store the result
	      Mat dst = new Mat();

	      // Applying color map to an image
	      Imgproc.applyColorMap(src, dst, Imgproc.COLORMAP_HOT);


	      // Writing the image
	      Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\colormap_hot.jpg", dst);
	      System.out.println("Image processed");
	   }
}
