import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class ColorSharpen extends JFrame
{
	private JLabel question;
	private JRadioButton red, green, blue, purple, gold;
	private ButtonGroup colorGroup;
	private JButton confirm;
	private String inp, outp;
	
	public ColorSharpen(String i)
	{
		super("Color Sharpen");
		setLayout( new FlowLayout() );
		
		inp = i;
		
		question = new JLabel("Which Color do you want to sharpen in your image?");
		question.setToolTipText( "Please choose a color" );
		add(question);
		red = new JRadioButton("Red");
		green = new JRadioButton("Green");
		blue = new JRadioButton("Blue");
		purple = new JRadioButton("Purple");
		gold = new JRadioButton("Gold");
		add( red);
		add(green);
		add(blue);
		add(purple);
		add(gold);
		colorGroup = new ButtonGroup();
		colorGroup.add(red);
		colorGroup.add(green);
		colorGroup.add(blue);
		colorGroup.add(purple);
		colorGroup.add(gold);
		
		confirm = new JButton("Confirm");
		add(confirm);
		
		radioButtonHandler handler = new radioButtonHandler();
		confirm.addActionListener(handler);


		
	}//end of constructor class
	
	private class radioButtonHandler implements ActionListener
	{
		public void actionPerformed (ActionEvent event )
		{
			if(event.getSource() == confirm)
			{
				if(red.isSelected())
				{
					ImageProc img1 = new ImageProc(inp, outp);
					img1.redSharpen();
					//img1.saveImg();
					
					ImageView j = new ImageView(img1.getOutF(), img1); // yyy
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Your Image is now sharpened red!");
				}
				else if(green.isSelected())
				{
					ImageProc img1 = new ImageProc(inp, outp);
					img1.greenSharpen();
					//img1.saveImg();
					
					ImageView j = new ImageView(img1.getOutF(), img1); // yyy
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Your Image is now sharpened green!");
				}
				else if(blue.isSelected())
				{
					ImageProc img1 = new ImageProc(inp, outp);
					img1.blueSharpen();
					//img1.saveImg();
					
					ImageView j = new ImageView(img1.getOutF(), img1); // yyy
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Your Image is now sharpened blue!");
				}
				else if(purple.isSelected())
				{
					ImageProc img1 = new ImageProc(inp, outp);
					img1.blueSharpen();
					img1.redSharpen();
					//img1.saveImg();
					
					ImageView j = new ImageView(img1.getOutF(), img1); // yyy
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Your Image is now sharpened purple!");
				}
				else if(gold.isSelected())
				{
					ImageProc img1 = new ImageProc(inp, outp);
					img1.redSharpen();
					img1.greenSharpen();
					//img1.saveImg();
					
					ImageView j = new ImageView(img1.getOutF(), img1); // yyy
					j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					j.setSize(640, 400);
					j.setVisible(true);
					JOptionPane.showMessageDialog(j,"Your Image is now sharpened gold!");
				}
				
			}
		}// end of actionPerformed
	}//end of radioButtonHandler
	
	
}//end of class
