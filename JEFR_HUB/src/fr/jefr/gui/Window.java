package fr.jefr.gui;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3701951983418439832L;
	private JPanel contentPane;
	private JPanel screen;
	private JPanel info;
	
	public Window(String title, int sizex, int sizey){
		String end;
		System.out.print("Create Window : " + title + " ... ");
		try{
			setSize(sizex, sizey);
			setTitle(title);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
			setContentPane(contentPane);
			contentPane.setLayout(new GridLayout(1, 0, 0, 0));
			
			screen = new JPanel();
			contentPane.add(screen);
			
			info = new JPanel();
			contentPane.add(info);
			end = "DONE";
		} catch (Exception e){
			end = "FAILED";
			e.printStackTrace();
		}
		System.out.println(end);
	}

	public JPanel getScreen() {
		return screen;
	}

	public JPanel getInfo() {
		return info;
	}
	
//	public void repaintContentPane(){
//		this.contentPane.repaint();
//	}
	
	
}
