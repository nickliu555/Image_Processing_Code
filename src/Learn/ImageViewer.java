package Learn;
import java.awt.Frame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;


public class ImageViewer extends Canvas
{
	private int   inset_w;
	private int   inset_h;
	private int   img_wid;
	private int   img_hgt;
	private Image thisImg;
	private Frame thisFrm;
	
	
	// specify a frame (window) to hold / host me or 'this' (a graphics display, embedded
	// in the frame) and attach an object of type Image to 'this' graphics display
	//
	public  void  setFrameImage( Frame  frm_obj,  Image img_obj, 
								 String s_title,  Color bkColor )
	{ 
		thisFrm = frm_obj; 	// the frame --- the container of the graphics display device
		thisImg = img_obj;	// the image --- to be displayed by a graphics display device
				
		
		if ( thisFrm == null || thisImg == null ) return;
			
		
		// the two functions below, getWidth( ... ) and getHeight( ... ), each require
		// a parameter of type ImageObserver ( = thisFrm here ) and the purpose is to ask
		// the frame / window to 'observe' (listen for, or WAIT FOR) the AVAILABILITY of
		// the image (to be shown by the graphics display embedded in the frame / window)
		//
		// this observation mechanism is intended to guarantee that the frame / window
		// will NOT allow the embedded graphics display to begin to draw the image UNTIL
		// the image (in the form of an array of pixels) has been 'loaded' (transferred)
		// to the graphics display
		//
		// Based on this observation scheme, IT SEEMS that an image of type Image is CLOSELY
		// RELATED to a graphics display.
		//
		img_wid = thisImg.getWidth ( thisFrm );
		img_hgt = thisImg.getHeight( thisFrm );
			  
		
		thisFrm.add( this );	   // embed this graphics display (a canvas) into this frame / window
		thisFrm.setVisible( true );		 // to show this frame / window
		inset_w = thisFrm.getInsets().left;	// the size of the left frame (the border of this window)
		inset_h = thisFrm.getInsets().top;	// the size of the top  frame (the border of this window)
		thisFrm.setSize( 1000, 1000 );	 // specify the size (width and height) of the frame / window
										 // used to host / hold the (graphics) display (of the image)
		
		thisFrm.setTitle( s_title );	 	// give a title for the frame / window
		thisFrm.setBackground( bkColor );	// set the background color for the frame / window
	}
		
	
	// a low-level graphics display device is used to draw something (e.g., an Image object)
	// onto the frame / window
	//
	public  void  paint( Graphics graphics )
	{
		super.paint( graphics );		// MUST: a call to the counterpart method of the parent class
		
		if ( thisImg != null )
		{
			// inset_w (left margin) and inset_h (top margin), both relative to the top-left
			// corner of the frame (a window), are used below to specify the top-left corner
			// of the image (to be shown / displayed within the frame)
			//
			graphics.drawImage( thisImg, inset_w, inset_h, img_wid, img_hgt, this );
		}
	}
}