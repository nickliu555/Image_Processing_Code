import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageProc 
{

	private int img_wid;
	private int img_hgt;
	private WritableRaster rwRastr;
	private int [] pixAray;
	private BufferedImage bufImg0;
	private String inFile;
	private String outFile;
	private Image image;
	
	public ImageProc(String inF, String outF)
	{
		try
		{
			inFile = inF;
			outFile = outF;
			
			bufImg0 = ImageIO.read(  new  File( inFile )  );
			img_wid = bufImg0.getWidth ();
			img_hgt = bufImg0.getHeight();
			rwRastr = bufImg0.getRaster();
			pixAray = new int [ img_wid * img_hgt * 3 ];
			int    pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				pixAray[ pixIndx + 0 ] = rwRastr.getSample( i,  j, 0 );
				pixAray[ pixIndx + 1 ] = rwRastr.getSample( i,  j, 1 );
				pixAray[ pixIndx + 2 ] = rwRastr.getSample( i,  j, 2 );
			}
		}//end of try
		
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
	}//End of double parameterconstructor 
	
	
	public ImageProc(String inF)
	{
		try
		{
			inFile = inF;
			outFile = "Output.jpg";
			
			bufImg0 = ImageIO.read(  new  File( inFile )  );
			img_wid = bufImg0.getWidth ();
			img_hgt = bufImg0.getHeight();
			rwRastr = bufImg0.getRaster();
			pixAray = new int [ img_wid * img_hgt * 3 ];
			int    pixIndx = 0;
			for ( int j = 0; j < img_hgt; j ++ )
			for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
			{
				pixAray[ pixIndx + 0 ] = rwRastr.getSample( i,  j, 0 );
				pixAray[ pixIndx + 1 ] = rwRastr.getSample( i,  j, 1 );
				pixAray[ pixIndx + 2 ] = rwRastr.getSample( i,  j, 2 );
			}
		}//end of try
		
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}// end of single parameter constructor
	
	
	public int getImgWid()
	{
		return img_wid;
	}
	
	public int getImgHgt()
	{
		return img_hgt;
	}
	
	public String getInF()
	{
		return inFile;
	}
	
	public String getOutF()
	{
		return outFile;
	}
	
	public int [] getPixAray()
	{
		return pixAray;
	}
	
	public Image getImageFromArray(int[] pixels, int width, int height) 
	{
		for ( int i = 0; i < width * height; i ++ )
		{
			pixels[ i * 3     ] = 255;
			pixels[ i * 3 + 1 ] = 0;
			pixels[ i * 3 + 2 ] = 0;
		}
		
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = (WritableRaster) bufImage.getData();
        raster.setPixels(0,0,width,height,pixels);
        
        Image image = null;
        
        try
		{
        	File  tempFile = new File( "temp.jpg" );
        	ImageIO.write( bufImage, "jpg", tempFile );
        	image  = ImageIO.read( new File("temp.jpg") ); // Opening again as an Image
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
        
        return image;
        
    }
	
	 public byte[] intToByte(int[]src) 
     {
         int srcLength = src.length;
         byte[]dst = new byte[srcLength << 2];
         
         for (int i=0; i<srcLength; i++) {
             int x = src[i];
             int j = i << 2;
             dst[j++] = (byte) ((x >>> 0) & 0xff);           
             dst[j++] = (byte) ((x >>> 8) & 0xff);
             dst[j++] = (byte) ((x >>> 16) & 0xff);
             dst[j++] = (byte) ((x >>> 24) & 0xff);
         }
         return dst;
     }
	
	
	public void invertColor()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			
			pixAray[ pixIndx + 0 ] = 255 - pixAray[ pixIndx + 0 ];
			pixAray[ pixIndx + 1 ] = 255 - pixAray[ pixIndx + 1 ];
			pixAray[ pixIndx + 2 ] = 255 - pixAray[ pixIndx + 2 ];
		}
		
		
		
	}//end of invertColor
	
	public void blackWhite()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			int r = pixAray[ pixIndx + 0 ];
			int g = pixAray[ pixIndx + 1 ];
			int b = pixAray[ pixIndx + 2 ];
			int v = (int) ( r * 0.30 + g * 0.59 + b * 0.11 );
			pixAray[ pixIndx + 0 ] = v;
			pixAray[ pixIndx + 1 ] = v;
			pixAray[ pixIndx + 2 ] = v;
		}
		
		
		
	}//end of blackWhite
	
	public void bright()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			pixAray[ pixIndx + 0 ] = 100 + pixAray[ pixIndx + 0 ];
			if(pixAray[ pixIndx + 0 ] > 255) //clamp high
				pixAray[ pixIndx + 0 ] = 255;
			else if(pixAray[ pixIndx + 0 ] < 0)//clamp low
				pixAray[ pixIndx + 0 ] = 0;
			
			pixAray[ pixIndx + 1 ] = 100 + pixAray[ pixIndx + 1 ];
			if(pixAray[ pixIndx + 1 ] > 255) //clamp high
				pixAray[ pixIndx + 1 ] = 255;
			else if(pixAray[ pixIndx + 1 ] < 0)//clamp low
				pixAray[ pixIndx + 1 ] = 0;
			
			pixAray[ pixIndx + 2 ] = 100 + pixAray[ pixIndx + 2 ];
			if(pixAray[ pixIndx + 2 ] > 255)//clamp high
				pixAray[ pixIndx + 2 ] = 255;
			else if(pixAray[ pixIndx + 2 ] < 0)//clamp low
				pixAray[ pixIndx + 2 ] = 0;
	
		}
		
		
	}//end of bright
	
	public void redSharpen()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			pixAray[ pixIndx + 0 ] = 100 + pixAray[ pixIndx + 0 ];
			if(pixAray[ pixIndx + 0 ] > 255) //clamp high
				pixAray[ pixIndx + 0 ] = 255;
			else if(pixAray[ pixIndx + 0 ] < 0)//clamp low
				pixAray[ pixIndx + 0 ] = 0;
			
			pixAray[ pixIndx + 1 ] = pixAray[ pixIndx + 1 ];
			
			pixAray[ pixIndx + 2 ] = pixAray[ pixIndx + 2 ];
	
		}
		
		
	}// end of redSharpen
	
	public void greenSharpen()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			pixAray[ pixIndx + 0 ] = pixAray[ pixIndx + 0 ];
			
			pixAray[ pixIndx + 1 ] = 100 + pixAray[ pixIndx + 1 ];
			if(pixAray[ pixIndx + 1 ] > 255) //clamp high
				pixAray[ pixIndx + 1 ] = 255;
			else if(pixAray[ pixIndx + 1 ] < 0)//clamp low
				pixAray[ pixIndx + 1 ] = 0;
			
			pixAray[ pixIndx + 2 ] = pixAray[ pixIndx + 2 ];
	
		}
		
		
	}// end of greenSharpen
	
	public void blueSharpen()
	{
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			pixAray[ pixIndx + 0 ] = pixAray[ pixIndx + 0 ];
			
			pixAray[ pixIndx + 1 ] = pixAray[ pixIndx + 1 ];
			
			pixAray[ pixIndx + 2 ] = 100 + pixAray[ pixIndx + 2 ];
			if(pixAray[ pixIndx + 2 ] > 255)//clamp high
				pixAray[ pixIndx + 2 ] = 255;
			else if(pixAray[ pixIndx + 2 ] < 0)//clamp low
				pixAray[ pixIndx + 2 ] = 0;
	
		}
		
		
	}//end of blueSharpen
	
	public void saveImg(String outputFile)
	{
		// use the modified array of pixels to update the raster
		int pixIndx = 0;
		for ( int j = 0; j < img_hgt; j ++ )
		for ( int i = 0; i < img_wid; i ++, pixIndx += 3 )
		{
			rwRastr.setSample(  i,  j,  0,  pixAray[ pixIndx + 0 ]  );
			rwRastr.setSample(  i,  j,  1,  pixAray[ pixIndx + 1 ]  );
			rwRastr.setSample(  i,  j,  2,  pixAray[ pixIndx + 2 ]  );
		}
		//actually save the image
		try
		{
			ImageIO.write(  bufImg0,  "jpg",  new File( outputFile )  );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
