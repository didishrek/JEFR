package fr.jefr.facialrec;

import java.awt.Color;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Recognition {
	 CascadeClassifier faceDetector;
	 MatOfRect faceDetection;
	 
	 public Recognition(){
		 System.out.print("Loading CascadeClassifier ... ");
		 try{
			 faceDetector = new CascadeClassifier(Recognition.class.getResource("/fr/jefr/resources/haarcascade_frontalface_alt.xml").getPath());
			 if (faceDetector.empty())
				 throw new Exception("Cannot load CascadeClassifier");
			 else
				 System.out.println("DONE");
		 }catch(Exception e){
			 System.out.println("FAILED");
			 e.printStackTrace();
		 }
		 faceDetection = new MatOfRect();
	 }
	 
	 public Mat recognition(Mat frame){
		 faceDetector.detectMultiScale(frame, faceDetection);
		 for (Rect rect : faceDetection.toArray()) {
			 Imgproc.putText(frame, "test", new Point(rect.x, rect.y - 10), 0, 1.0, new Scalar(0, 255,0));
			 Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255,0));
          }
		 return (frame);
	 }
}
