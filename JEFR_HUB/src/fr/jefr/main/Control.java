package fr.jefr.main;


import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;


import fr.jefr.facialrec.Image;
import fr.jefr.facialrec.Recognition;
import fr.jefr.gui.Window;

public class Control {
	private String pathReference;
	private Recognition reco;
	private int indexCamera;
	private FrameGrabber cam;
	private boolean cont = true;
	private Window win;
	private OpenCVFrameConverter.ToIplImage converter;
	private OpenCVFrameConverter.ToMat convmat;
	private IplImage grabbedImage; 
	private Mat mat;
	private Image img;
	
	public Control(int indexCamera, Recognition reco, String pathReference){
		this.pathReference = pathReference;
		this.reco = reco;
		this.reco.setPathReference(this.pathReference);

		this.indexCamera = indexCamera;
		this.mat = new Mat();
		this.img = new Image();
		this.converter = new OpenCVFrameConverter.ToIplImage();
		this.convmat = new OpenCVFrameConverter.ToMat();
		try {
			this.openCam();
			this.reco.setGrey(this.getWidthFrame(), this.getHeightFrame());
			this.win = new Window("JEFR",this.getWidthFrame() * 2, this.getHeightFrame() + 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openCam() throws Exception{
		System.out.println("Index camera : " + this.indexCamera);
		System.out.print("Opening camera ... ");
		cam = FrameGrabber.createDefault(this.indexCamera);
		this.cam.start();
	}

	private void releaseCam()throws Exception{
		this.cam.stop();
	}

	private int getHeightFrame() throws Exception{
		System.out.println("height = " + this.cam.getImageHeight());
		return (this.cam.getImageHeight());
	}

	private int getWidthFrame() throws Exception{
		System.out.println("width = " + this.cam.getImageWidth());
		return (this.cam.getImageWidth());
	}

	public void exec() throws Exception{
		boolean visible = false;
		this.win.setVisible(true);
		while(cont){
			 this.mat = convmat.convert(this.cam.grab());
			 this.grabbedImage = this.converter.convert(this.converter.convert(this.img.reverseMat(this.mat)));
			 this.grabbedImage = this.reco.recognition(this.grabbedImage);
			 this.img.MatToBufferedImage(this.converter.convert(this.grabbedImage));
			 this.win.repaintScreen(this.img);
		}
		this.releaseCam();
	}
}
