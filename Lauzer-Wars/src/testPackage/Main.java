package testPackage;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;


public class Main {
	
	public Canvas canvas;
	
	public static void main(String[] args) {
		Main main = new Main();
//		main.canvas = new Canvas();
//		
//		main.canvas.setVisible(true);
		
		JFrame frame = new JFrame("Game");
		frame.setSize(600, 700);	
		frame.setBackground(new Color(200,100,100));
		frame.setVisible(true);
	}

}
