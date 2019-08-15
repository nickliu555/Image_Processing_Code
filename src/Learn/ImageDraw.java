package Learn;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ImageDraw  extends JPanel
{
	private int pointCount = 0;
	
	//array of 10000 java.awt.Point references
	private Point[] points = new Point[10000];
	
	public ImageDraw()
	{
		addMouseMotionListener //handle frame mouse motion event
		(
				new MouseMotionAdapter() //anonymous inner class
				{
					//store drag coordinates and repaint
					public void mouseDragged(MouseEvent event) 
					{
						if(pointCount < points.length)
						{
							points[pointCount] = event.getPoint();
							++pointCount;
							repaint();
						}
					}// end of  mouseDragged
				}// end of  MouseMotionAdapter
				
		); // end to addMouseMotionListener
	}// end of ImageDraw Constructor
	
	//draw ovals in a 4-by-4 bounding box at specified locations on window
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //clears drawing area
		
		//draw for all points in array
		for(int i=0; i<pointCount; i++)
			g.fillOval(points[i].x, points[i].y, 4, 4);
	}// end of paintComponent
	
}// end of ImageDraw
