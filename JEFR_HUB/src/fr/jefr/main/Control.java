package fr.jefr.main;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import fr.jefr.facialrec.Camera;
import fr.jefr.facialrec.Image;
import fr.jefr.facialrec.Recognition;
import fr.jefr.gui.Window;

public class Control {
	private String pathReference;
	private Recognition reco;
	private int indexCamera;
	private VideoCapture cam;
	//private Camera camera;
	private boolean cont = true;
	private Mat mat;
	private Window win;

	public Control(int indexCamera, Recognition reco, String pathReference){
		//this.camera = camera;
		this.pathReference = pathReference;
		this.reco = reco;
		this.reco.setPathReference(this.pathReference);
		this.indexCamera = indexCamera;
		mat = new Mat();
		try {
			this.openCam();
			this.win = new Window("JEFR",this.getWidthFrame() * 2, this.getHeightFrame() + 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openCam() throws Exception{
		System.out.println("Index camera : " + this.indexCamera);
		System.out.print("Opening camera ... ");
		this.cam = new VideoCapture();
		this.cam.open(this.indexCamera); //this.camera.getCamera_selected()
		if (this.cam.isOpened())
			System.out.println("DONE");
		else{
			System.out.println("FAILED");
			throw new Exception("Cannot open camera " + this.indexCamera);
		}

	}

	private void releaseCam(){
		this.cam.release();
	}

	private int getHeightFrame() throws Exception{
		if (cam.read(mat))
			return (mat.height());
		else
			throw new Exception("Cant read camera height.");
	}

	private int getWidthFrame() throws Exception{
		if (cam.read(mat))
			return (mat.width());
		else
			throw new Exception("Cant read camera width.");
	}

	public void exec() throws Exception{
		boolean visible = false;
		while(cont){
			if (cam.read(mat)){
				Image img = new Image();
				img.MatToBufferedImage(reco.recognition(img.reverseMat(mat)));
				win.repaintScreen(img);
				if (!visible){
					System.out.println("READY");
					win.setVisible(true);
				}	
				visible = true;
			}
			else{
				throw new Exception("Cannot read.");
			}
		}
		this.releaseCam();
	}
}
