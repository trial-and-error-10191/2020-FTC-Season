package imageProcessing;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class splitingTheRGBImage {
	static List<Mat> rgb_planes = new ArrayList<Mat>(3);

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String file = "C:\\Users\\ian\\Desktop\\filse\\si\\ColorTest.png";
        
        Mat RGBsrc = Imgcodecs.imread(file);
        Mat t = new Mat();
        Imgproc.cvtColor(RGBsrc, t,Imgproc.COLOR_RGB2BGR);
        
        List<Mat> RGB2 = new ArrayList<Mat>(3);
        Core.split(RGBsrc, RGB2);
        Mat[] array2 = RGB2.toArray(new Mat[RGB2.size()]);
       
        
        
       // Imgproc.cvtColor(array2[2], t,Imgproc.COLOR_RGB2BGR);
        
       // Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\test.png",t);
        
       // Mat t = new Mat();
        
        Mat R = array2[2];// R channel
		Mat G = array2[1];// G channel
		Mat B = array2[0];// B channel
		
		
        Mat dst = new Mat();
		
		Imgproc.threshold(R,dst,100,255, Imgproc.THRESH_BINARY);
		
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\46.png", dst);
		
		//Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\R2.png",R);
		//Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\G2.png",G);
		//Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\B2.png",B);
        
		
        
	}
}

