package fr.jefr.facialrec;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;

public class Recognition {
	private CvHaarClassifierCascade faceDetector;
	private String pathReference = "";
	private CvMemStorage storage;
	private IplImage greyImage = null;
	FaceRecognizer reco;

	public Recognition(String pathReference){
		this.pathReference = pathReference;
		System.out.print("Loading CascadeClassifier ... ");
		File file = new File(Recognition.class.getResource("/haarcascade_frontalface_alt_old.xml").getPath());
		try{
			faceDetector = new CvHaarClassifierCascade(cvLoad(file.getAbsolutePath()));
			if (faceDetector.isNull())
				throw new Exception("Cannot load CascadeClassifier");
			else
				System.out.println("DONE");
		}catch(Exception e){
			System.out.println("FAILED");
			e.printStackTrace();
		}
		System.out.println("==> " + file); 
		this.storage = CvMemStorage.create();
		reco = new LBPHFaceRecognizer();
		FaceTrainLoader ftl = new FaceTrainLoader(pathReference);
		
	}

	public void setGrey(int width, int height){
		this.greyImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
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
			int x = r.x(), y = r.y(), w = r.width(), h = r.height();
			cvRectangle(img, cvPoint(x, y), cvPoint(x+w, y+h), CvScalar.RED, 1, CV_AA, 0);
			r.close();
		}
		return (img);
	}

//	public void setPathReference(String pathReference) {
//		this.pathReference = pathReference;
//	}

}
