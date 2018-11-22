import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ImageProcGUI extends JFrame
{
	private ImageProc img1;
	private JTextField inputText;
	private JLabel inputLabel;
	private JButton inputImg;
	private JButton filterImg;
	private JLabel filterLabel;
	private JComboBox filterCombo;
	private final String[] filters = 
		{"None","Invert Colors", "Black & White","Brighter","Color Sharpen"};
	
	public ImageProcGUI()
	{
		super("Image Processing");
		setLayout (new GridLayout(3,2) );
		
		getContentPane().setBackground(Color.BLACK);
		
		inputLabel = new JLabel("Input Image Name:");
		inputLabel.setForeground (Color.white);
		inputLabel.setToolTipText( "Enter the EXACT file name of the image in the textbox to the right" );
		add(inputLabel);
		
		inputText = new JTextField("");
		add(inputText);
		
		filterLabel = new JLabel("Choose a filter:");
		filterLabel.setForeground (Color.white);
		add(filterLabel);
		
		filterCombo = new JComboBox(filters);
		filterCombo.setMaximumRowCount(3);
		add(filterCombo);
		
		
		inputImg = new JButton("Click Here for Imported Image");
		add(inputImg);
		
		filterImg = new JButton("Click Here for Filtered Image");
		add(filterImg);
		
		//HANDLERS
		everythingHandler handler = new everythingHandler();
		inputText.addActionListener(handler);
		inputImg.addActionListener(handler);
		filterCombo.addActionListener(handler);
		filterImg.addActionListener(handler);
		
	}// end of constructor
	
	private class everythingHandler implements ActionListener
	{
		public void actionPerformed (ActionEvent event )
		{
			
			
			if(event.getSource() == inputText)
			{
					File i = new File(inputText.getText());
					if(i.exists()) 
					{
						inputImg.setEnabled(true);
					}
					else
						JOptionPane.showMessageDialog( null, "Your Input File entry is invalid", 
			                       "Error with the input", JOptionPane.ERROR_MESSAGE );
			}// end of inputText textbox
			
			else if (event.getSource() == inputImg)
			{
				File i = new File(inputText.getText());
				if(i.exists()) 
				{
					
					img1 = new ImageProc(inputText.getText());
				
					ImageView j = new ImageView(img1.getInF(), img1);
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Image Width: "+ img1.getImgWid()+" \nImage Height: "+img1.getImgHgt());
		
				}
				else
					JOptionPane.showMessageDialog( null, "Your Input File entry is invalid", 
		                       "Error with the input", JOptionPane.ERROR_MESSAGE );
			}// end of inputImg button
			
			else if ((event.getSource() == filterImg))	
			{
				File i = new File(inputText.getText());
				if(i.exists()) 
				{
				
					if( filterCombo.getSelectedIndex() == 1 ) // if Invert
					{
						img1 = new ImageProc(inputText.getText());
						img1.invertColor();						
						//img1.saveImg();
						
					
						ImageView j = new ImageView(img1.getOutF(), img1);
						j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						j.setSize(640, 400);
						j.setVisible(true);
						JOptionPane.showMessageDialog(j,"Your Image has it's colors inverted!"	);
					}//end of if
					else if(filterCombo.getSelectedIndex() == 2) // black/white
					{
						img1 = new ImageProc(inputText.getText());
						img1.blackWhite();
					
						ImageView j = new ImageView(img1.getOutF(), img1); // yyy
						j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						j.setSize(640, 400);
						j.setVisible(true);
						JOptionPane.showMessageDialog(j,"Your Image is now Black & White!");
					}//end of else if
					else if(filterCombo.getSelectedIndex() == 3)
					{
						img1 = new ImageProc(inputText.getText());
						img1.bright();
					
						ImageView j = new ImageView(img1.getOutF(), img1);
						j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						j.setSize(640, 400);
						j.setVisible(true);
						JOptionPane.showMessageDialog(j,"Your Image is now Brighter!");
					}//end of else if
					else if(filterCombo.getSelectedIndex() == 4)
					{
						ColorSharpen s = new ColorSharpen(inputText.getText());
						s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						s.setSize(350, 125);
						s.setVisible(true);
					}
				}//end of if image exists
				
				else if(!i.exists()) //if the input file doesn't exist
					JOptionPane.showMessageDialog( null, "Your Input File entry is invalid", 
		                       "Error with the input", JOptionPane.ERROR_MESSAGE );

			}//end of filterImg Button
			
		}//end of actionPerformed
		
	}//end of everythingHandler
	
}//end of class
