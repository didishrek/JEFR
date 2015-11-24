package fr.jefr.facialrec;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;


public class Image extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9177571833881594042L;
	protected BufferedImage img;
	private Java2DFrameConverter fconv;
	
	public Image(){
		fconv = new Java2DFrameConverter();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.img, 0, 0, this);
	}

	public Mat reverseMat(Mat frame){
		Mat tmp = new Mat();
		int j = 0;
		frame.copyTo(tmp);
		for (int i = frame.arrayWidth() -1; i >= 0; i--){
			frame.col(i).copyTo(tmp.col(j));
			j++;
		}
		return (tmp);
	}

	public void MatToBufferedImage(Frame frame) {
		this.img = fconv.convert(frame);
	}
}
