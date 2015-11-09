package fr.jefr.facialrec;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import org.opencv.core.Mat;

public class Image extends JPanel{
	BufferedImage img;

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.img, 0, 0, this);
	}

	public Mat reverseMat(Mat frame){
		Mat tmp = new Mat();
		int j = 0;
		frame.copyTo(tmp);
		for (int i = frame.width() -1; i >= 0; i--){
			frame.col(i).copyTo(tmp.col(j));
			j++;
		}
		return (tmp);
	}

	public void MatToBufferedImage(Mat frame) {
		int type = 0;
		if (frame.channels() == 1) {
			type = BufferedImage.TYPE_BYTE_GRAY;
		} else if (frame.channels() == 3) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
		WritableRaster raster = image.getRaster();
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
		byte[] data = dataBuffer.getData();
		frame.get(0, 0, data);
		this.img = image;
	}
}
