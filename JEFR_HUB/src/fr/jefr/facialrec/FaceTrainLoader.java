package fr.jefr.facialrec;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceTrainLoader {
	private String train_directory = "";
	private MatVector images;
	private Vector<String> labels;
	private Java2DFrameConverter fconv;
	private OpenCVFrameConverter.ToIplImage converter;
	
	public FaceTrainLoader(String dir){
		this.train_directory = dir;
		labels = new Vector<String>();
		File directory = new File(this.train_directory);
		
		FilenameFilter jpgFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().endsWith(".jpg");
			}
		};

		File[] allimg = directory.listFiles(jpgFilter);

		if (allimg == null){
			System.out.println("pas reussie a charger les images.");
		}
		
		images = new MatVector(allimg.length);
		
		int i = 0;
		IplImage img; 
		for (File image : allimg) {
			BufferedImage buffimg;
			try {
				img = cvLoadImage(image.getAbsolutePath(), CV_BGR2GRAY);
				labels.add(i, image.getName());
				System.out.println(image.getName());
				//grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);

				//cvCvtColor(img, grayImg, CV_BGR2GRAY);

				//images.put(i, img);

				//labels[counter] = label;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++i;
		}

	}

}
