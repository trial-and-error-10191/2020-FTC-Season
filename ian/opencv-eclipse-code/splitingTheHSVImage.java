package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class splitingTheHSVImage {

	
	// for blue  max_value_H = 30,  low_H = 1 have to make sure that they all have the same H value 
	// for green max_value_H = 59,  low_H = 49; 
	// for red   max_value_H = 121, low_H = 120
	// for orange max_value_H =104, low_H = 60 may pick up a slices of red 
	
	//red
	static double max_value_Red =121 ; 
	static double low_Red = 120;
	static double high_Red = max_value_Red;
	static Scalar loweR = new Scalar(Math.min(high_Red-1,low_Red));
	static Scalar highR = new Scalar(Math.max(high_Red, low_Red+1));
	//Blue
	static double max_value_Blue =30 ; 
	static double low_Blue = 1;
	static double high_Blue = max_value_Blue;
	static Scalar loweB = new Scalar(Math.min(high_Blue-1,low_Blue));
	static Scalar highB = new Scalar(Math.max(high_Blue, low_Blue+1));
	//Green
	static double max_value_Green =59 ; 
	static double low_Green = 49;
	static double high_Green = max_value_Green;
	static Scalar loweG = new Scalar(Math.min(high_Green-1,low_Green));
	static Scalar highG = new Scalar(Math.max(high_Green, low_Green+1));
	//Orange
	static double max_value_Orange = 104 ; 
	static double low_Orange = 60;
	static double high_Orange = max_value_Orange;
	static Scalar loweO = new Scalar(Math.min(high_Orange-1,low_Orange));
	static Scalar highO = new Scalar(Math.max(high_Orange, low_Orange+1));

	//S channel
	static double max_value_S = 155; 
	static double low_S = 0;
	static Scalar lowS = new Scalar(Math.min(max_value_S-1,low_S));
	static Scalar highS = new Scalar(Math.max(max_value_S, low_S+1));
	
	static double max_value_V = 100; 
	static double low_V = 54;
	static Scalar lowV = new Scalar(Math.min(max_value_V-1,low_V));
	static Scalar highV = new Scalar(Math.max(max_value_V, low_V+1));
	
	//TestImage3.png
	
	static String file = "C:\\Users\\ian\\Desktop\\filse\\si\\images.png";
	
	static String FirstRing = "C:\\Users\\ian\\Desktop\\filse\\si\\firstring.png";
	static String NoRings = "C:\\Users\\ian\\Desktop\\filse\\si\\NoRings.png";
	static String SecondRing = "C:\\Users\\ian\\Desktop\\filse\\si\\secondring.png";
	static String ThirdRing = "C:\\Users\\ian\\Desktop\\filse\\si\\thirdring.png";
	
	
	static String test1 = "C:\\Users\\ian\\Desktop\\filse\\si\\test1.png";
	static String test2 = "C:\\Users\\ian\\Desktop\\filse\\si\\test2.png";
	static String test3= "C:\\Users\\ian\\Desktop\\filse\\si\\test3.png";
	static String test4 = "C:\\Users\\ian\\Desktop\\filse\\si\\test4.png";
	
	
	
	
	static String TestImage1 = "C:\\Users\\ian\\Desktop\\filse\\si\\TestImage1.png";
	static String TestImage2 = "C:\\Users\\ian\\Desktop\\filse\\si\\TestImage2.png";
	static String TestImage3 = "C:\\Users\\ian\\Desktop\\filse\\si\\TestImage3.png";
	
	
	
	
	public static void RGB2BGR() {
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat RBGsrc = Imgcodecs.imread(file);
	Mat BGRsrc = new Mat();
	Imgproc.cvtColor(RBGsrc, BGRsrc,Imgproc.COLOR_RGB2BGR);
	Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\Test69.png", BGRsrc);
	}
	public static void Red(){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat HSVsrc = new Mat();   
	    Mat BGRsrc = new Mat();
	    Mat mask = new Mat();
	    List<Mat> RGB2 = new ArrayList<Mat>(3);
        Mat RBGsrc = Imgcodecs.imread(file);
	    Imgproc.cvtColor(RBGsrc, BGRsrc,Imgproc.COLOR_RGB2BGR);
	    Imgproc.cvtColor(BGRsrc, HSVsrc,Imgproc.COLOR_BGR2HSV);
        Core.split(HSVsrc, RGB2);
		Mat[] array21 = RGB2.toArray(new Mat[RGB2.size()]);
		Mat H = array21[0];// H channel
		Core.inRange(H,loweR,highR,mask);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\RedImage.png", mask);
	}
	public static void Blue(){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat HSVsrc = new Mat();   
	    Mat BGRsrc = new Mat();
	    Mat mask = new Mat();
	    List<Mat> RGB2 = new ArrayList<Mat>(3);
        Mat RBGsrc = Imgcodecs.imread(file);
	    Imgproc.cvtColor(RBGsrc, BGRsrc,Imgproc.COLOR_RGB2BGR);
	    Imgproc.cvtColor(BGRsrc, HSVsrc,Imgproc.COLOR_BGR2HSV);
	    Core.split(HSVsrc, RGB2);
		Mat[] array21 = RGB2.toArray(new Mat[RGB2.size()]);
		Mat H = array21[0];// H channel
		Core.inRange(H,loweB,highB,mask);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\BlueImage.png", mask);
	}
	public static void Green(){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat HSVsrc = new Mat();   
	    Mat BGRsrc = new Mat();
	    Mat mask = new Mat();
	    List<Mat> RGB2 = new ArrayList<Mat>(3);
        Mat RBGsrc = Imgcodecs.imread(file);
	    Imgproc.cvtColor(RBGsrc, BGRsrc,Imgproc.COLOR_RGB2BGR);
	    Imgproc.cvtColor(BGRsrc, HSVsrc,Imgproc.COLOR_BGR2HSV);
	    Core.split(HSVsrc, RGB2);
		Mat[] array21 = RGB2.toArray(new Mat[RGB2.size()]);
		Mat H = array21[0];// H channel
		Core.inRange(H,loweG,highG,mask);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\GreenImage.png", mask);
	}
	public static void Orange(){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat HSVsrc = new Mat();
	    Mat mask = new Mat();
	    Mat Slvl = new Mat();
	    Mat Dst2 = new Mat();
	    Mat Vlvl = new Mat();
	    List<Mat> RGB2 = new ArrayList<Mat>(3);
        Mat RBGsrc = Imgcodecs.imread(file);
	    Imgproc.cvtColor(RBGsrc, HSVsrc,Imgproc.COLOR_RGB2HSV);
	    Core.split(HSVsrc, RGB2);
		Mat[] array21 = RGB2.toArray(new Mat[RGB2.size()]);
		Mat H = array21[0];// H channel
		Mat S = array21[1];
		Mat V = array21[2];
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\HT.png", H);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\ST.png", S);
		Core.inRange(H,loweO,highO,mask);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\OrangeImage.png", mask);
		Core.inRange(S,lowS,highS,Slvl);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\Slvl.png", Slvl);
		Core.inRange(V,lowV,highV,Vlvl);
		Core.compare(H,Slvl,Dst2,2);
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\TestImage71.png", Dst2);
	}
//to get a random image of the rings
	public static Mat random(Mat RBGsrc) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Random rand = new Random();
		
		int j = rand.nextInt(3);
		for(int i = j; i < 3;) {
			if(i == 0) {
			RBGsrc =  Imgcodecs.imread(FirstRing);
			return RBGsrc;
			}
			if(i == 1) {
				RBGsrc =  Imgcodecs.imread(ThirdRing);
				return RBGsrc;
			}
			if(i == 2) {
				RBGsrc =  Imgcodecs.imread(NoRings);
				return RBGsrc;
			}
		}
		return RBGsrc;
		
		
	}
	 public static BufferedImage Mat2BufferedImage(Mat mat) throws IOException{
	      //Encoding the image
	      MatOfByte matOfByte = new MatOfByte();
	      Imgcodecs.imencode(".jpg", mat, matOfByte);
	      //Storing the encoded Mat in a byte array
	      byte[] byteArray = matOfByte.toArray();
	      //Preparing the Buffered Image
	      InputStream in = new ByteArrayInputStream(byteArray);
	      BufferedImage bufImage = ImageIO.read(in);
	      return bufImage;
	   }
	public static void HowMany() throws IOException {
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat test = new Mat();
	Mat scr =  random(test);// randomizing 
	//Mat scr =  Imgcodecs.imread(NoRings);// to pick the image from a file
    Mat mask = new Mat();
	Mat Slvl = new Mat();
	Mat HSVsrc = new Mat();
    Mat Dst2 = new Mat();
   // Mat fr = Imgcodecs.imread(TestImage1);
   
	List<Mat> RGB2 = new ArrayList<Mat>(3);	
	Imgproc.cvtColor(scr, HSVsrc,Imgproc.COLOR_RGB2HSV);
	Core.split(HSVsrc, RGB2);
	Mat[] array21 = RGB2.toArray(new Mat[RGB2.size()]);
	Mat H = array21[0];// H channel
	Mat S = array21[1];
	Core.inRange(H,loweO,highO,mask);
	Core.inRange(S,lowS,highS,Slvl);
	Core.compare(H,Slvl,Dst2,2);
	Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\Test.png", Dst2);
	//Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\fr.png", fr);
	
	boolean f = false;
	// Comparing the mask of the one ring and the mask of a random images to tell how many rings are there
	// it will not work if there is not a mask to compare to
	if(f == true) {
	BufferedImage img1 = ImageIO.read(new File("C:\\Users\\ian\\\\Desktop\\filse\\si\\Test.png"));
    BufferedImage img2 = ImageIO.read(new File("C:\\Users\\ian\\Desktop\\filse\\si\\fr.png"));
      int w1 = img1.getWidth();
      int w2 = img2.getWidth();
      int h1 = img1.getHeight();
      int h2 = img2.getHeight();
      if ((w1!=w2)||(h1!=h2)) {
         System.out.println("Both images should have same dimwnsions");
      } else {
         long diff = 0;
         for (int j = 0; j < h1; j++) {
            for (int i = 0; i < w1; i++) {
               //Getting the RGB values of a pixel
               int pixel1 = img1.getRGB(i, j);
               Color color1 = new Color(pixel1, true);
               int r1 = color1.getRed();
               int g1 = color1.getGreen();
               int b1 = color1.getBlue();
               int pixel2 = img2.getRGB(i, j);
               Color color2 = new Color(pixel2, true);
               int r2 = color2.getRed();
               int g2 = color2.getGreen();
               int b2= color2.getBlue();
               //sum of differences of RGB values of the two images
               long data = Math.abs(r1-r2)+Math.abs(g1-g2)+ Math.abs(b1-b2);
               diff = diff+data;
            }
         }
         double avg = diff/(w1*h1*3);
         System.out.println(avg);
         double percentage = (avg/255)*100;
        
         System.out.println("Difference: "+percentage);
         
         if(percentage == 0.0) {
        	 System.out.println("it is one ring");
         }
         else if (percentage > 5.86 && percentage < 5.9) {
        	 System.out.println("it is two rings");
         }
         else if (percentage > 5.0 && percentage < 5.1) {
        	 System.out.println("it is three rings");
         }
         else {
        	 System.out.println("something is off about the image");
         }
         
      }
	}
	// counting the whit pixels to tell the numbers of the rings
	// will work with only one image
	boolean t = true;
	if(t == true) {
     // BufferedImage img11 = ImageIO.read(new File("C:\\Users\\ian\\\\Desktop\\filse\\si\\Test.png"));
      BufferedImage obj = Mat2BufferedImage(Dst2);
      int W1 = obj.getWidth();
      int H1 = obj.getHeight();
      int count = 0;
      for (int j = 0; j < H1; j++) {
          for (int i = 0; i < W1; i++) {
        	    int pixel1 = obj.getRGB(i, j);
	            
	            Color color = new Color(pixel1, true);
	            
	            int red = color.getRed();
	            int green = color.getGreen();
	            int blue = color.getBlue(); 
	           
	            if( red > 200 &&red <= 300 && green > 200 && green <= 300 && blue > 200 && blue <= 300) {
	            	count = count + 1;
	          // System.out.println(count);
	           }
          }  
      }
      // on the first ring the the count number is 301617 so I rounded down to 300000 for the lowest the counts can be 
      // on the three rings the count number is 543758 so I round down to 500000 since anything lower is the first ring 
      // on the no rings the count number is 256 so i rounded up to 300 since if there was a ring it would be higher then 1000
      //
      
      if( count > 300000  && count < 400000) {
  		System.out.print("one ring");
  	}
      else if(count > 500000 && count < 700000 ) {
    	  System.out.println("three ring");
      }
     else if(count > 0 && count < 300) {
    	 System.out.println("there are no rings");
     }
     else {
    	 System.out.println("the count number is higher then no rings ,but it is lower then one or three rings");
     }
	
	}
	}
	public static void main(String[] args) throws IOException {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		//RGB2BGR();
		//Red();
		//Blue();
		//Green();
		//Orange();
		HowMany();
	
	    
		
	}
}
