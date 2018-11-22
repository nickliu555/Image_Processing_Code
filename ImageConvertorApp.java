

import java.awt.*;				// MUST for Image

import javax.swing.*;			// MUST for JFrame , but NOT necessary for Frame

import java.util.*;				// for Scanner (nextLine) 
import java.io.File;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;







import Learn.ImageViewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class ImageConvertorApp 
{
	private int    img_wid;
	private int    img_hgt;
	private int [] pixAray;
	
	public int     getImageWidth () {  return  img_wid;  }
	public int     getImageHeight() {  return  img_hgt;  }
	public int  [] getRAP_RGB()     {  return  pixAray;  }
	
	public void Read_Process_Write_Image( String inFile, String outFile )
	{
		try // the 'try ...... catch' structure seems to be a MUST for file I/O
		{
			// confirm the input and output file names
			System.out.print( "Input  Image File Name: " );
			System.out.println( inFile  );
			
			System.out.print( "Output Image File Name: " );
			System.out.println( outFile );
			
			
			// Get / read / load a buffered image from the INPUT image file.
			// A buffered image object provides access to some
			// information of the image including the width and height.
			//
			BufferedImage bufImg0 = ImageIO.read(  new  File( inFile )  );
			img_wid = bufImg0.getWidth ();
			img_hgt = bufImg0.getHeight();
			System.out.println( "Image Width = " + img_wid + ";    Image Height = " + img_hgt + ";" );
			
			
			// get access to the (Readable and Writable) raster of the image
			WritableRaster   rwRastr = bufImg0.getRaster();
			
			
			// use an array of pixels to hold the raster data (pixel values)
			pixAray = new int [ img_wid * img_hgt * 3 ];
			int    pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				pixAray[ pixIndx + 0 ] = rwRastr.getSample( i,  j, 0 );
				pixAray[ pixIndx + 1 ] = rwRastr.getSample( i,  j, 1 );
				pixAray[ pixIndx + 2 ] = rwRastr.getSample( i,  j, 2 );
			}
			
			
			// use the modified array of pixels to update the raster
			pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				rwRastr.setSample(  i,  j,  0,  pixAray[ pixIndx + 0 ]  );
				rwRastr.setSample(  i,  j,  1,  pixAray[ pixIndx + 1 ]  );
				rwRastr.setSample(  i,  j,  2,  pixAray[ pixIndx + 2 ]  );
			}
			
			
			// save the image to a JPG file
			ImageIO.write(  bufImg0,  "jpg",  new File( outFile )  );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	
	// =================================== //
	// load, process, and display an image //
	// =================================== //
	
	
	public static void main( String [] argv )
	{
		int	[]      rap_img;
		int []      fap_img;
		Image       img_obj;
		JFrame	    frm_obj = new JFrame();
		ImageViewer	iViewer = new ImageViewer();
		Scanner     console = new Scanner( System.in );
		ImageConvertorApp	  imgConv = new ImageConvertorApp();
		
		
		// #1: process the first image and show the result
		imgConv.Read_Process_Write_Image( "Flower_01_input.jpg", "Flower_01_output.jpg" );
		rap_img = ImageConvertor.getRAP
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  imgConv.getRAP_RGB()  );
		fap_img = ImageConvertor.RAP2FAP    
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  rap_img  );
		img_obj = ImageConvertor.createImage
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  fap_img  );
		
		iViewer.setFrameImage( frm_obj,  img_obj, "Image Processor" , Color.yellow );
		iViewer.repaint();
		
		// after pressing the ENTER key, the second image will be processed
		System.out.print( "Press ENTER to continue with a new image ...... " );
		console.nextLine();
		
		// #2: process the second the image and show the result
		imgConv.Read_Process_Write_Image( "Flower_02_input.jpg", "Flower_02_output.jpg" );
		rap_img = ImageConvertor.getRAP
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  imgConv.getRAP_RGB()  );
		fap_img = ImageConvertor.RAP2FAP    
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  rap_img  );
		img_obj = ImageConvertor.createImage
				  (  imgConv.getImageWidth(),  imgConv.getImageHeight(),  fap_img  );
		
		iViewer.setFrameImage( frm_obj,  img_obj, "Image Processor" , Color.yellow );
		iViewer.repaint();
	}
	
}
	