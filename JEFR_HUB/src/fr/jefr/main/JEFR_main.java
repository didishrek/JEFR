package fr.jefr.main;


import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_objdetect;

import fr.jefr.facialrec.Recognition;
import fr.jefr.gui.*;
import fr.jefr.param.ProgramArgs;

public class JEFR_main {	

	private static void loadOpenCv(){
		String end;
		try{
			System.out.print("Loading Libraries ... ");
			//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Loader.load(opencv_objdetect.class);
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

		String pathReference = "";
		try{
			if (pa.getArgs().hasOption("d")){
				pathReference = pa.getArgs().getOptionValue("d");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("path => " + pathReference);

		Recognition reco = new Recognition();

		Control cont = new Control(indexCamera, reco, pathReference);
		//Control cont = new Control(indexCamera, null, pathReference);
		try {
			cont.exec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
