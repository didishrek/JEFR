package fr.jefr.main;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.opencv.videoio.VideoCapture;

import fr.jefr.facialrec.Camera;
import fr.jefr.facialrec.Recognition;
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
		
		int indexCamera = 0;
		try{
			if (pa.getArgs().hasOption("c")){
				indexCamera = Integer.parseInt(pa.getArgs().getOptionValue("c"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		Recognition reco = new Recognition();
		
		Control cont = new Control(indexCamera, reco);
		try {
			cont.exec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
