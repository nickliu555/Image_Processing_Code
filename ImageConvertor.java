import java.awt.*;							// MUST for Image
import java.awt.Component;
import java.awt.image.*;					// NOT necessary for Image
import java.awt.image.MemoryImageSource;
import javax.swing.JButton;					// for JButton (actually for calling method createImage


public class ImageConvertor
{
	// create and return an array of int-based 4-component (4-channel: alpha, red, green, blue) 
	// pixels from the input: width, height, an array of int-based 3-component (3-channel: red,
	// green, blue) pixels, and array of int-based 1-component (1-channel: alpha --- inverse of
	// transparency) pixels.
	//
	// RAP: Raw Array of Pixels
	//
	static int [] getRAP( int img_wid, int img_hgt, int [] rgb_ary, int [] alphAry )
	{
		// allocate an array for int-based 4-component pixels: to be filled in
		int [] rapAray = new int [ img_wid * img_hgt * 4 ];
		
		int    rgb_idx = 0;	// 3 channels for each pixel --- for the input  RGB  array
		int    rgbaIdx = 0;	// 4 channels for each pixel --- for the output RGBA array
		for (  int i = 0;  i < img_wid * img_hgt;  i ++, rgb_idx += 3, rgbaIdx += 4  )
		{
			rapAray[ rgbaIdx     ] = alphAry[i];			 	// alpha = 255 - transparency
			rapAray[ rgbaIdx + 1 ] = rgb_ary[ rgb_idx     ]; 	// red
			rapAray[ rgbaIdx + 2 ] = rgb_ary[ rgb_idx + 1 ];	// green
			rapAray[ rgbaIdx + 3 ] = rgb_ary[ rgb_idx + 2 ];	// blue
		}
		
		return rapAray;
	}
	
	
	// create and return an array of int-based 4-component (4-channel: alpha, red, green, blue) 
	// pixels from the input: width, height, and an array of int-based 3-component (3-channel:
	// red, green, blue) pixels.
	//
	// RAP: Raw Array of Pixels
	//
	static int [] getRAP( int img_wid, int img_hgt, int [] rgb_ary )
	{
		// allocate an array for int-based 4-component pixels: to be filled in
		int [] rapAray = new int [ img_wid * img_hgt * 4 ];
				
		int    rgb_idx = 0;	// 3 channels for each pixel --- for the input  RGB  array
		int    rgbaIdx = 0;	// 4 channels for each pixel --- for the output RGBA array
		for (  int i = 0;  i < img_wid * img_hgt;  i ++, rgb_idx += 3, rgbaIdx += 4  )
		{
			rapAray[ rgbaIdx     ] = 255;			 			// alpha = 255 - transparency: but FIXED here
			rapAray[ rgbaIdx + 1 ] = rgb_ary[ rgb_idx     ]; 	// red
			rapAray[ rgbaIdx + 2 ] = rgb_ary[ rgb_idx + 1 ];	// green
			rapAray[ rgbaIdx + 3 ] = rgb_ary[ rgb_idx + 2 ];	// blue
		}
				
		return rapAray;
	}
	
	
	// create and return an array of Formatted Array of Pixels (FAP) from
	// a Raw Array of Pixels (RAP: 4 ints for each pixel ---- alpha, red, 
	// green blue).
	//
	// FAP: one int (with 4 unsigned bytes) for each pixel
	//      where the 4 (unsigned) bytes, in order, refer to alpha, red, green, and blue
	// 
	// FAP is the internal format used by Java for an in-memory array of pixels
	//
	static int [] RAP2FAP( int img_wid, int img_hgt, int [] rgbaRAP )
	{
		int [] fapAray = new int[ img_wid * img_hgt ];

		int    rgbaIdx = 0;	// 4 channels for each pixel: the input array rgbaRAP
		for (  int i = 0;  i < img_wid * img_hgt;  i ++, rgbaIdx += 4  )
		{
			// 4 bytes from left (the highest) to right (the lowest): alpha, red, green, blue
			fapAray[i] = (    (  rgbaRAP[ rgbaIdx     ]  <<  24  )    &    0xFF000000    ) 	// byte #0
		               | (    (  rgbaRAP[ rgbaIdx + 1 ]  <<  16  )    &    0x00FF0000    )	// byte #1
		               | (    (  rgbaRAP[ rgbaIdx + 2 ]  <<   8  )    &    0x0000FF00    )	// byte #2
		               | (       rgbaRAP[ rgbaIdx + 3 ]               &    0x000000FF    );	// byte #3
		}
		      
		return fapAray;
	}
	
	
	// create and return a Raw Array of Pixels (RAP: 4 ints for each pixel --- 
	// alpha, red, green, blue) from the input: width, height, and an FAP.
	//
	// FAP: one int (with 4 unsigned bytes) for each pixel
	//      where the 4 (unsigned) bytes, in order, refer to alpha, red, green, and blue
	// 
	// FAP is the internal format used by Java for an in-memory array of pixels
	//
	static int [] FAP2RAP( int img_wid, int img_hgt, int [] fapAray )
	{
		// allocate an array for int-based 4-component pixels: to be filled in
		int [] rapAray = new int [ img_wid * img_hgt * 4 ];
		
		int    rgbaIdx = 0;	// 4 channels for each pixel: the output array rapAray
		for (  int i = 0;  i < img_wid * img_hgt;  i ++, rgbaIdx += 4  )
		{
	        rapAray[ rgbaIdx     ] = ( fapAray[i] >> 24 ) & 0xFF;	// alpha
	        rapAray[ rgbaIdx + 1 ] = ( fapAray[i] >> 16 ) & 0xFF;	// red
	        rapAray[ rgbaIdx + 2 ] = ( fapAray[i] >>  8 ) & 0xFF;	// green
	        rapAray[ rgbaIdx + 3 ] =   fapAray[i]         & 0xFF;	// blue
	    }
		
		return rapAray;
	}
	
	
	// create and return a simplified Raw Array of Pixels (RAP: 3 ints for each pixel --- 
	// red, green, blue) from the input: width, height, and an FAP.
	//
	// FAP: one int (with 4 unsigned bytes) for each pixel
	//      where the 4 (unsigned) bytes, in order, refer to alpha, red, green, and blue
	// 
	// FAP is the internal format used by Java for an in-memory array of pixels
	//
	static int [] FAP2RAP_RGB( int img_wid, int img_hgt, int [] fapAray )
	{
		// allocate an array for int-based 3-component pixels: to be filled in
		int [] rgbAray = new int [ img_wid * img_hgt * 3 ];
			
		int    rgbaIdx = 0;	// 3 channels for each pixel: the output array rapAray
		for (  int i = 0;  i < img_wid * img_hgt;  i ++, rgbaIdx += 3  )
		{
		    rgbAray[ rgbaIdx     ] = ( fapAray[i] >> 16 ) & 0xFF;	// red
		    rgbAray[ rgbaIdx + 1 ] = ( fapAray[i] >>  8 ) & 0xFF;	// green
		    rgbAray[ rgbaIdx + 2 ] =   fapAray[i]         & 0xFF;	// blue
		}
			
		return rgbAray;
	}
	
	
	// create and return an object of type Image from an Formatted Array pf Pixels
	// FAP: one int (with 4 unsigned bytes) for each pixel
	//      where the 4 (unsigned) bytes, in order, refer to alpha, red, green, and blue
	// 
	// FAP is the internal format used by Java for an in-memory array of pixels
	//
	static Image createImage( int img_wid, int img_hgt, int [] fapAray )
	{
		MemoryImageSource mem_img = new MemoryImageSource
									    ( img_wid, img_hgt, fapAray, 0, img_wid );
		
		// Component is an ABSTRACT class and hence can NOT be directly used to call
		// method createImage. Thus, a non-abstract sub-class of Component needs to 
		// be created below in a way to call this method
		//
		JButton  butnObj = new JButton();
		return   butnObj.createImage( mem_img );
	}
}