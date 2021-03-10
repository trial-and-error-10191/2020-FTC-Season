package imageProcessing;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class readingedges {
																//
	static String file1 = "C:\\Users\\ian\\Desktop\\filse\\si\\test 2.png";
	static String file2 = "C:\\Users\\ian\\Desktop\\filse\\si\\test 6.png";
	
	public static void read() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		ORB orb = ORB.create();
		Mat scr =  Imgcodecs.imread(file1);
		Mat descriptors1 = new Mat();
		Imgproc.cvtColor(scr, scr, Imgproc.COLOR_RGB2GRAY);
		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
		orb.detect(scr, keypoints1);
		orb.compute(scr, keypoints1, descriptors1);
		
		Scalar BLUE = new Scalar(255,0,0);
		
	    Mat outputImage = new Mat();
	    Mat scr2 =  keypoints1;
	    Features2d.drawKeypoints(scr, keypoints1, outputImage,BLUE);
	    String filename = "C:\\Users\\ian\\Desktop\\filse\\si\\test1.png";
	   
	    System.out.println(String.format("Writing %s...", filename));
	    Imgcodecs.imwrite(filename, outputImage);
		
	}
	public static void read2()throws IOException {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		ORB orb = ORB.create();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		
		
		Mat scr =  Imgcodecs.imread(file1);
		Mat descriptors1 = new Mat();
		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
		Imgproc.cvtColor(scr, scr, Imgproc.COLOR_RGB2GRAY);
		
		orb.detect(scr, keypoints1);
		orb.compute(scr, keypoints1, descriptors1);
		
		Mat scr2 =  Imgcodecs.imread(file2);
		Mat descriptors2 = new Mat();
		MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
		Imgproc.cvtColor(scr2, scr2, Imgproc.COLOR_RGB2GRAY);
		
		orb.detect(scr2, keypoints2);
		orb.compute(scr2, keypoints2, descriptors2);
		
		MatOfDMatch matches = new MatOfDMatch();
	    matcher.match(descriptors1, descriptors2, matches);
	    
	    
	    Scalar BLUE = new Scalar(255,0,0);
	    Scalar GREEN = new Scalar(0,255,0);
	    Mat output = new Mat();
	   
	    

	    List<DMatch> matchesList = matches.toList();
	    Double max_dist = 0.0;
	    Double min_dist = 100.0;
	   // System.out.println("hi2 "+matchesList);
	    for(int i = 0;i < matchesList.size(); i++){
	       Double dist = (double) matchesList.get(i).distance;
	     // System.out.println("hi "+dist);
	        
	       // System.out.println(dist);
	        if (dist < min_dist) {
	        	
	        	min_dist = dist;
	        }
	            
	        
	        if ( dist > max_dist) {
	        	 max_dist = dist;
	        }
	     
	    }



	    LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
	    for(int i = 0;i < matchesList.size(); i++){
	    	System.out.println("hi "+ matchesList.get(i).distance);
	        if (matchesList.get(i).distance <= (min_dist*1.5) )
	        	
	            good_matches.addLast(matchesList.get(i));
	    }

	    
	    
	    MatOfDMatch goodMatches = new MatOfDMatch();
	    goodMatches.fromList(good_matches);
	    
	    System.out.println(matches.size() + " " + goodMatches.size());

	    Mat outputImg = new Mat();
	    MatOfByte drawnMatches = new MatOfByte();
	    Features2d.drawMatches(scr, keypoints1, scr2, keypoints2, goodMatches, outputImg, BLUE, GREEN, drawnMatches, 0);

	    
	   
	   
	    //Features2d.drawMatches(scr,keypoints1,scr2,keypoints2,matches,output,BLUE,GREEN);
	    String filename = "C:\\Users\\ian\\Desktop\\filse\\si\\test4.png";
	    Imgcodecs.imwrite(filename, outputImg);
		
	    
	    
		//# create BFMatcher object
		//bf = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)

		//# Match descriptors.
		//matches = bf.match(des1,des2)
		
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//read();
		read2();
	}

}
