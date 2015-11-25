package fr.jefr.facialrec;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceTrainLoader {
	private String train_directory = "";
	private MatVector images;
	private Mat position;
	private Vector<String> labels;
	private OpenCVFrameConverter.ToMat convmat;
	
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
		
		position = new Mat(allimg.length);
		convmat = new OpenCVFrameConverter.ToMat();
		images = new MatVector(allimg.length);
		System.out.println("Loading files ...");
		int i = 0;
		IplImage img; 
		for (File image : allimg) {
			try {
				img = cvLoadImage(image.getAbsolutePath(), CV_BGR2GRAY);
				labels.add(i, image.getName().substring(0, image.getName().length() - 4));
				System.out.println(image.getName());
				images.put((long)i, convmat.convert(convmat.convert(img)));

			} catch (Exception e) {
				e.printStackTrace();
			}
			++i;
		}
	}

	public MatVector getImages() {
		return images;
	}

	public String[] getLabels() {
		return (String [])labels.toArray();
	}

}
