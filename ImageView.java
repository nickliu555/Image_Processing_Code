import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;


public class ImageView extends JFrame
{
	private ImageIcon image;
	private JLabel label;
	private JMenuItem save;
	JPopupMenu menu = new JPopupMenu("Testing");
	ImageProc temp;

	public ImageView(String file, ImageProc imgProc )
	{
		super("Image View");
		
		temp = imgProc;
		
		int [] rgbaRAP = ImageConvertor.getRAP
				        (  imgProc.getImgWid(), imgProc.getImgHgt(),  imgProc.getPixAray()  );
		int [] fap_img = ImageConvertor.RAP2FAP    
				        (  imgProc.getImgWid(), imgProc.getImgHgt(),  rgbaRAP  );
		Image  img_obj = ImageConvertor.createImage
				        (  imgProc.getImgWid(), imgProc.getImgHgt(),  fap_img  );
		
		Box box = Box.createHorizontalBox();
		image = new ImageIcon( img_obj );
		label = new JLabel(image);
		label.setToolTipText("Right click to save the image");
	    box.add(new JScrollPane(label));
	    add(box);
	    
	    label.addMouseListener( new RightClicker() );
	    
	    pressHandler handler = new pressHandler();
	    save = new JMenuItem("Save Image");
	    save.addActionListener(handler);
	    
	    menu.add(save);
	}
	
	

	private class RightClicker extends MouseAdapter 
	{
		JPopupMenu menu = new JPopupMenu();
		
        public void mousePressed( MouseEvent e ) 
        {
            if ( e.isMetaDown() ) 
            {
                menu.setVisible(true);
                menu.add(save);
                menu.show(e.getComponent(), e.getX(), e.getY());
                      
            }
            else 
            	menu.setVisible(false);
        }// end of mousePressed
	}// end of RightClicker
	
	
	private class pressHandler implements ActionListener
	{
		public void actionPerformed (ActionEvent event )
		{
			String outputFile = JOptionPane.showInputDialog ( "Enter desired output location:" ); 
			
			if ((outputFile != null) && (outputFile.length() > 0)) 
			{
				File i = new File(outputFile);
				if(!i.exists())
				{
					temp.saveImg(outputFile);
					JOptionPane.showMessageDialog(null,"This Image is saves as "+outputFile);
				}
				else
				{
					JOptionPane.showMessageDialog( null, "This image can't be saved since the output file name already exists", 
		                       "Error with Output file name", JOptionPane.ERROR_MESSAGE );
				}
			}
		}
	}
	
}//end of class
