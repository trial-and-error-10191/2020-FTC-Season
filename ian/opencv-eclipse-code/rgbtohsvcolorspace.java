package imageProcessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class rgbtohsvcolorspace {

	public rgbtohsvcolorspace() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	       Mat bgr = Imgcodecs.imread("C:\\Users\\ian\\Desktop\\filse\\si\\trump poster.png");
	       Mat hsv = new Mat();
	       Imgproc.cvtColor(bgr, hsv, Imgproc.COLOR_RGB2HSV);
	       Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\converting rgb to hvs color space .png", hsv);

	}

}
