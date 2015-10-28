package fr.jefr.main;

import org.opencv.videoio.VideoCapture;

import fr.jefr.facialrec.Camera;
import fr.jefr.gui.*;
import fr.jefr.param.ProgramArgs;

public class JEFR_main {	
	
	private static void loadOpenCv(){
		String end;
		try{
			System.out.print("Loading OpenCv ... ");
			System.loadLibrary("opencv_java300");
			end = "DONE";
		} catch (Exception e){
			end = "FAILED";
			e.printStackTrace();
		}
		System.out.println(end);
	}
	
	public static void main(String[] args) {
		ProgramArgs pa = new ProgramArgs(args);
		if (pa.askHelp())
			return;
		loadOpenCv();
		
		Camera cam = new Camera();
		cam.selectCamera();
		if(cam.getCamera_selected() == -10)
			return ;
		VideoCapture tmp = new VideoCapture();
		tmp.open(cam.getCamera_selected());
		System.out.println(tmp.isOpened());

		
		
		
		Window jefrWindow = new Window("JEFR", 800, 600);

		
	}

}
