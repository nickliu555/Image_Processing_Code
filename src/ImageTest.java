

import java.io.File;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class ImageTest 
{
	public static void Read_Process_Write_Image( String inFile, String outFile )
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
			int           img_wid = bufImg0.getWidth ();
			int           img_hgt = bufImg0.getHeight();
			System.out.println( "Image Width = " + img_wid + ";    Image Height = " + img_hgt + ";" );
			
			
			// CASE #1 --- a test
			/*/
			// ========================================================================== //
			// get access to an array of bytes, which may be still in compression (BEGIN) //
			// ========================================================================== //
			//
			// =========================================================== //
			// READ:  MOVE bytes from a stream         to a buffered image //
			// WRITE: MOVE bytes from a buffered image to a stream         //
			// =========================================================== //
			//
			//
			// Create an INPUT STREAM of pixel bytes from the buffered image 
			// (with some header info) by interpreting the buffered image
			// based on a specific image file format (i.e., JPG herein).
			//
			// WRITE: MOVE bytes from the buffered image to the stream
			//
			ByteArrayOutputStream  byteStrm0 = new ByteArrayOutputStream();
			ImageIO.write( bufImg0, "jpg", byteStrm0 ); 
			byteStrm0.flush();
			System.out.println(  "size of byteStrm0 = "  +  byteStrm0.size()  );
			
			
			// allocate an array of PURE pixel bytes from the stream (of pixel bytes)
			byte [] imageBytes = byteStrm0.toByteArray();
			byteStrm0.close();
			System.out.println(  "length of imageBytes = "  +  imageBytes.length  );
			
			
			// ====================================== //
			// process the image array --- imageBytes //
			// ====================================== //
			
			
			// create an OUTPUT STREAM  of pixel bytes from the array of PURE
			// pixel bytes (already processed above)
			ByteArrayInputStream byteStrm1 = new ByteArrayInputStream( imageBytes );
			
			
			// create a buffered image from the OUTPUT stream
			//
			// READ:  MOVE bytes from the stream to the buffered image
			//
			BufferedImage bufImg1 = ImageIO.read( byteStrm1 );
			
			
			// write the buffered image to an OUTPUT image file (of type JPG)
			ImageIO.write(  bufImg1,  "jpg",  new File( outFile )  );
			
			// ========================================================================== //
			// get access to an array of bytes, which may be still in compression ( END ) //
			// ========================================================================== //
			//*/
			
			
			// ========================================================================== //
			// ========================================================================== //
			
			
			// CASE #2 --- the correct version
			//
			// =================================================== //
			// get access to a DEcompressed array of bytes (BEGIN) //
			// =================================================== //
			//
			// get access to the (Readable and Writable) raster of the image
			WritableRaster   rwRastr = bufImg0.getRaster();
			
			
			// use an array of pixels to hold the raster data (pixel values)
			int [] pixAray = new int [ img_wid * img_hgt * 3 ];
			int    pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				pixAray[ pixIndx + 0 ] = rwRastr.getSample( i,  j, 0 );
				pixAray[ pixIndx + 1 ] = rwRastr.getSample( i,  j, 1 );
				pixAray[ pixIndx + 2 ] = rwRastr.getSample( i,  j, 2 );
			}
			
			
			// process the array of pixels --- a naive filter herein
			pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				/*/ color to gray-scale
				int r = pixAray[ pixIndx + 0 ];
				int g = pixAray[ pixIndx + 1 ];
				int b = pixAray[ pixIndx + 2 ];
				int v = (int) ( r * 0.30 + g * 0.59 + b * 0.11 );
				pixAray[ pixIndx + 0 ] = v;
				pixAray[ pixIndx + 1 ] = v;
				pixAray[ pixIndx + 2 ] = v;
				//*/
				
				pixAray[ pixIndx + 0 ] = 255 - pixAray[ pixIndx + 0 ];
				pixAray[ pixIndx + 1 ] = 255 - pixAray[ pixIndx + 1 ];
				pixAray[ pixIndx + 2 ] = 255 - pixAray[ pixIndx + 2 ];
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
			
			
			// release the memory of the pixel array
			pixAray = null;
			
			// =================================================== //
			// get access to a DEcompressed array of bytes ( END ) //
			// =================================================== //
			//*/
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	public static void main( String [] argv )
	{
		Read_Process_Write_Image( "Input.jpg", "Output.jpg" );
		System.exit( 0 );
	}
}
