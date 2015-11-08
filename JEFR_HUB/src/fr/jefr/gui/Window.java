package fr.jefr.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fr.jefr.facialrec.Image;


public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3701951983418439832L;
	private JPanel contentPane;
	private JPanel board;
	private JPanel info;
	private JPanel screen;
	
	public Window(String title, int sizex, int sizey){
		String end;
		System.out.print("Create Window : " + title + " ... ");
		try{
			setSize(sizex, sizey);
			//setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			setTitle(title);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
			setContentPane(contentPane);
			contentPane.setLayout(new GridLayout(1, 0, 0, 0));
			
			board = new JPanel();
			contentPane.add(board);
			board.setLayout(new BorderLayout(0, 0));
			
			screen = new JPanel();
			screen.setLayout(new GridLayout(1, 2));
			board.add(screen, BorderLayout.CENTER);
			
			info = new JPanel();
			contentPane.add(info);
			end = "DONE";
		} catch (Exception e){
			end = "FAILED";
			e.printStackTrace();
		}
		System.out.println(end);
	}

	public void repaintScreen(Image img){
		this.screen.removeAll();
		this.screen.add(img);
		this.screen.revalidate();
		this.screen.repaint();
		this.contentPane.repaint();
	}
	
	public JPanel getScreen() {
		return screen;
	}

	public JPanel getBoard() {
		return board;
	}

	public JPanel getInfo() {
		return info;
	}
	
//	public void repaintContentPane(){
//		this.contentPane.repaint();
//	}
	
	
}
