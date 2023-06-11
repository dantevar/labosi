package mySinGA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class CircleWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Jedinka jedinka;
	public CircleWindow() throws IOException {
		
		jedinka = GA.run();
		for(int i = 0; i < jedinka.x.size(); i++) {
			System.out.println(jedinka.x.get(i) +" , " + jedinka.y.get(i) + " : "
		+ (jedinka.x.get(i) * jedinka.x.get(i) + jedinka.y.get(i)* jedinka.y.get(i)) );
		}
		System.out.println(jedinka.err);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100,100);
		setSize(500, 500);
		setTitle("CIRCLE");

		initGUI();
	}

	private void initGUI() throws IOException {
		
		JPanel panel = new JPanel() {

	    	private static final long serialVersionUID = 1L;

	    	public void paintComponent(Graphics g) {
	    		
	    		g.setColor(Color.black);
	    		
	    		for(int i = 0; i < jedinka.x.size(); i++) {
	    			double x = jedinka.x.get(i) *150 + 250;
	    			double y = jedinka.y.get(i) *150 + 250 ;
	    			g.fillOval((int)x,(int) y, 10, 10);
	    		}
	    	

	    	}

	    	            
	    	        
	    	    
	    };
	    
		panel.setLayout(null);
        getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
	    		
	}
}
