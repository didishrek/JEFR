package fr.jefr.facialrec;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

public class Recognition {
	private CvHaarClassifierCascade faceDetector;
	private CascadeClassifier face_cascade;
	private String pathReference = "";
	private CvMemStorage storage;
	private IplImage greyImage = null;
	private Mat grayImage = null;
	private FaceRecognizer reco;
	private FaceTrainLoader ftl;

	public Recognition(String pathReference){
		this.pathReference = pathReference;
		System.out.print("Loading CascadeClassifier ... ");
		File file = new File(Recognition.class.getResource("/haarcascade_frontalface_alt.xml").getPath());
		try{
			face_cascade = new CascadeClassifier();
			face_cascade.load(file.getAbsolutePath());
			if (face_cascade.isNull())
				throw new Exception("Cannot load CascadeClassifier");
			else
				System.out.println("DONE");			
			//faceDetector = new CvHaarClassifierCascade(cvLoad(file.getAbsolutePath()));
//			if (faceDetector.isNull())
//				throw new Exception("Cannot load CascadeClassifier");
//			else
//				System.out.println("DONE");
		}catch(Exception e){
			System.out.println("FAILED");
			e.printStackTrace();
		}
		System.out.println("==> " + file); 
		this.storage = CvMemStorage.create();
		//reco = createEigenFaceRecognizer();
		reco = createLBPHFaceRecognizer();
		//reco = createFisherFaceRecognizer();
		ftl = new FaceTrainLoader(pathReference);
		reco.train(ftl.getImages(), ftl.getPosition());
	}

	public void setGrey(int width, int height){
		this.greyImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
		this.grayImage = new Mat();
	}

	public IplImage recognition(IplImage img) throws Exception{
		if (this.greyImage == null)
			throw new Exception("Grey Image not initialized yet");
		cvClearMemStorage(storage);
		cvCvtColor(img, greyImage, CV_BGR2GRAY);
		
		CvSeq faces = cvHaarDetectObjects(greyImage, this.faceDetector, storage,
				1.1, 3, CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);
		int total = faces.total();
		for (int i = 0; i < total; i++) {
			CvRect r = new CvRect(cvGetSeqElem(faces, i));
			int predict = -1;
			predict = reco.predict(null);
			int x = r.x(), y = r.y(), w = r.width(), h = r.height();
			cvRectangle(img, cvPoint(x, y), cvPoint(x+w, y+h), CvScalar.RED, 1, CV_AA, 0);
			System.out.println(r);
			System.out.println(predict);
			try {
				r.close();
			} catch (java.lang.Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		return (img);
	}
	
	public Mat recognition(Mat img){
		 cvtColor(img, grayImage, COLOR_BGRA2GRAY);
         equalizeHist(grayImage, grayImage);
         
         Point p = new Point();
         RectVector faces = new RectVector();
         //face_cascade.detectMultiScale(grayImage, faces, 1.1, 3, CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH, new Size(20, 20), new Size(1000,1000));
         face_cascade.detectMultiScale(grayImage, faces);
         
         for (int i = 0; i < faces.size(); i++) {
             Rect face_i = faces.get(i);

             Mat face = new Mat(grayImage, face_i);
             int prediction = reco.predict(face);
             rectangle(img, face_i, new Scalar(0, 255, 0, 1));
             String box_text = "Prediction = " +  ftl.getName(prediction) + " / " + prediction;
             int pos_x = Math.max(face_i.tl().x() - 10, 0);
             int pos_y = Math.max(face_i.tl().y() - 10, 0);
             putText(img, box_text, new Point(pos_x, pos_y),
                     FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
         }
		return (img);
	}
	
}
