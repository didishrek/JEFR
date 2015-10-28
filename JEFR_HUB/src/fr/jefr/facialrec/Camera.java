package fr.jefr.facialrec;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.opencv.videoio.VideoCapture;

public class Camera {
	private List <String> cam;
	private int camera_selected;
	private int count_camera;

	public Camera(){
		camera_selected = -1;
		count_camera = -1;
	}
	
	private int countCamera(){
		cam = new ArrayList<String>();
		int i = 0;
		VideoCapture tmp = new VideoCapture();
		while (tmp.open(i)){
			cam.add(Integer.toString(i));
			tmp.release();
			//System.out.println(tmp.isOpened());
			i++;
		}
		count_camera = i;
		return (count_camera);
	}
	
	public void selectCamera(){
		if (this.countCamera() > 1){
			System.out.print("Camera selection ... ");
			String rep = (String)JOptionPane.showInputDialog(null, 
					"Select a camera", 
					"Camera selection", 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					cam.toArray(), 
					cam.toArray()[0]);
			System.out.println("DONE");
			if (rep != null)
				this.camera_selected = Integer.parseInt(rep);
			else
				this.camera_selected = -10;
		} else if (count_camera == 1){
			JOptionPane.showMessageDialog(null, "Only one camera found, this one will be use", "Information", JOptionPane.INFORMATION_MESSAGE);
			this.camera_selected = 0;
		}else{
			JOptionPane.showMessageDialog(null, "No Camera found, the program will exit", "Information", JOptionPane.WARNING_MESSAGE);
			this.camera_selected = -10;
		}
	}

	public int getCamera_selected() {
		return camera_selected;
	}
}
