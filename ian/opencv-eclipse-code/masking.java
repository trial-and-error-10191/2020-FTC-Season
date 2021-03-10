package imageProcessing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class masking {

	public masking() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Loading the OpenCV core library
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      //Reading the input image
	      String file = "C:\\\\Users\\\\ian\\\\Desktop\\\\filse\\\\si\\\\trump poster.png";
	      Mat src = Imgcodecs.imread(file);
	      //Creating an empty matrix to store the result
	      Mat dst = new Mat();
	      //Creating kernel1
	      Mat kernel1 = new Mat(3, 3, CvType.CV_8S);
	      int row = 0, col = 0;
	      kernel1.put(row, col, 0, -1, 0, -1, 5, -1, 0, -1, 0);
	      //Creating kernel2
	      Mat kernel2 = Mat.ones(2,2, CvType.CV_32F);
	      
	      for(int i = 0; i<kernel2.rows(); i++) {
	         for(int j = 0; j<kernel2.cols(); j++) {
	            double[] m = kernel2.get(i, j);
	            for(int k = 1; k<m.length; k++) {
	               m[k] = m[k]/(2 * 2);
	            }
	            kernel2.put(i,j, m);
	         }
	      }
	      //Filtering the image using kernel1
	      //Imgproc.filter2D(src, dst, -1, kernel1);
	     // HighGui.imshow("Mask Example1", dst);
	      
	      
	       //dst = new Mat();
	      //Filtering the image using kernel2
	     Imgproc.filter2D(src, dst, -1, kernel2);
	     HighGui.imshow("Mask Example2", dst);
	     HighGui.waitKey();
	     Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\death.jpg", dst);
	      System.out.println("Image processed");
	}

}
