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

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class readingTheNumberOfRings {
	//Orange
	//104
	//60
		static double max_value_Orange = 104 ; 
		static double low_Orange = 60;
		static double high_Orange = max_value_Orange;
		static Scalar loweO = new Scalar(Math.min(high_Orange-1,low_Orange));
		static Scalar highO = new Scalar(Math.max(high_Orange, low_Orange+1));

		//S channel
		//155
		//0
		//0
		//230
		static double max_value_S = 0; 
		static double low_S = 230;
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
		
		// one rings
		static int ORLC = 300000;
		static int ORHC = 400000;
		// four rings
		static int FRLC = 500000;
		static int FRHC = 700000;
		// no rings
		static int NRLC = 0;
		static int NRHC = 300;
		
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
	public static void HowMany() throws IOException {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat test = new Mat();
		//Mat scr =  random(test);// randomizing 
		Mat scr =  Imgcodecs.imread(ThirdRing);// to pick the image from a file
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
		Core.compare(mask,Slvl,Dst2,1);
		
		Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\Test.png", Dst2);
		//Imgcodecs.imwrite("C:\\Users\\ian\\Desktop\\filse\\si\\fr.png", fr);
		
		//Encoding the image
	      MatOfByte matOfByte = new MatOfByte();
	      Imgcodecs.imencode(".jpg", Dst2, matOfByte);
	      //Storing the encoded Mat in a byte array
	      byte[] byteArray = matOfByte.toArray();
	      //Preparing the Buffered Image
	      InputStream in = new ByteArrayInputStream(byteArray);
	      BufferedImage bufImage = ImageIO.read(in);
	     
		// counting the whit pixels to tell the numbers of the rings
		// will work with only one image
	    // BufferedImage img11 = ImageIO.read(new File("C:\\Users\\ian\\\\Desktop\\filse\\si\\Test.png"));
	      BufferedImage obj = bufImage;
	      int W1 = obj.getWidth();
	      int H1 = obj.getHeight();
	      int count = 0;
	      for (int j = 0; j < H1; j++) {
	          for (int i = 0; i < W1; i++) {
	        	    int pixel1 = obj.getRGB(i, j);
		            
		            Color color = new Color(pixel1, true);
		            
		            int red = color.getRed(); 
		           
		            if( red > 200 &&red <= 300) {
		            	count = count + 1;
		          System.out.println(count);
		           }
	          }  
	      }
	      
	      
	     
	      // on the first ring the the count number is 301617 so I rounded down to 300000 for the lowest the counts can be 
	      // on the three rings the count number is 543758 so I round down to 500000 since anything lower is the first ring 
	      // on the no rings the count number is 256 so i rounded up to 300 since if there was a ring it would be higher then 1000
	      			// ORLC	300000		  ORHC 400000
	      if( count > ORLC  && count < ORHC) {
	  		System.out.print("one ring");
	  	}			//	   FRLC 500000      FRHC 700000
	      else if(count > FRLC && count < FRHC ) {
	    	  System.out.println("three ring");
	      }		//      NRLC 0      NRHC 300
	     else if(count > NRLC && count < NRHC) {
	    	 System.out.println("there are no rings");
	     }
	     else {
	    	 System.out.println("the count number is higher then no rings ,but it is lower then one or three rings");
	     }
	      //958239 79629 33123
		}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HowMany();
	}

}
