package Learn;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class LabelFrame extends JFrame 
{
	private JLabel label1, label2, label3, label4;
	private JTextField textField1;
	private JButton button1;
	private JRadioButton nuetral, good, bad;
	private ButtonGroup radioGroup;
	private JComboBox listy;
	private final String[] hoursSlept = {"9+ Hours","8 Hours", "7 Hours", "6- Hours"};
	final Color[] colors =
	{
			Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
	};
	private Color pastColor = Color.DARK_GRAY;
	
	
	public LabelFrame()
	{
		super("Simple Greeting"); //The Title of the window
		setLayout( new FlowLayout() );
		
		
		//Label
		label1 = new JLabel("What is your name: "); //Displays this text
		label1.setToolTipText( "Please type in your name in the blank" ); //This shows up when mouse is on text
		add(label1);
		//TextField
		textField1 = new JTextField("Enter Your Name Here"); //Displays a textbox for user to enter in
		add( textField1 );
		
		
		//button
		button1 = new JButton("Click Here");
		button1.setToolTipText("Click here to enter");
		add(button1);
		
		//radiobuttons
		label3 = new JLabel("How are you feeling today?");
		label3.setToolTipText( "Please choose an option" );
		add(label3);
		nuetral = new JRadioButton("Nuetral");
		good = new JRadioButton("Good");
		bad = new JRadioButton("Bad");
		add( nuetral);
		add(good);
		add(bad);
		radioGroup = new ButtonGroup();
		radioGroup.add(nuetral);
		radioGroup.add(good);
		radioGroup.add(bad);
		
		//ComboBox
		label4 = new JLabel("How many hours have you slept today? ");
		label4.setToolTipText( "Please choose the correct amount of hours slept" );
		add(label4);
		listy = new JComboBox(hoursSlept);
		listy.setMaximumRowCount(4);
		add(listy);
		
		
		//register event handlers
		everythingHandler handler = new everythingHandler();
		textField1.addActionListener(handler);
		button1.addActionListener(handler);
		nuetral.addActionListener(handler);
		good.addActionListener(handler);
		bad.addActionListener(handler);
		listy.addActionListener(handler);
		
		
	}
	
	//class for event handling
	private class everythingHandler implements ActionListener
	{
		public void actionPerformed (ActionEvent event )
		{
			String str = "";//declare string to display
			int num = 0; //declare int to display
			Font font = null;
			
			if(event.getSource() == textField1)
			{
				str = String.format("Nice to meet you, %s", event.getActionCommand());
			}
			else if(event.getSource() == button1)
			{
				str = String.format("Nice to meet you, "+ textField1.getText(), event.getActionCommand());
				font = new Font("Serif",Font.ITALIC, 14);
				button1.setFont(font);
			}
			//radiobutton
			else if( nuetral.isSelected() )
			{
				str = String.format("Ok", event.getActionCommand());
				radioGroup.clearSelection();
			}
			else if( good.isSelected() )
			{
				str = String.format("That's awesome :)", event.getActionCommand());
				radioGroup.clearSelection();
			}
			else if( bad.isSelected() )
			{
				str = String.format("I hope you feel better :(", event.getActionCommand());
				radioGroup.clearSelection();
			}
			
			//ComboBox
			else if( listy.getSelectedIndex() == 0)
				str = String.format("You are sleeping too much", event.getActionCommand());
			else if (listy.getSelectedIndex() == 1)
				str = String.format("8 hours is a decent amount of sleep :)", event.getActionCommand());
			else if (listy.getSelectedIndex() == 2)
				str = String.format("7 hours is a decent amount of sleep :)", event.getActionCommand());
			else if (listy.getSelectedIndex() == 3)
				str = String.format("You need to sleep more", event.getActionCommand());
			
			
			//set Background to a different random color as user presses enter in any fashion
			int colorIndex = (int)(Math.random()*9);
			while(colors[colorIndex] == pastColor) //while the new color is the same as the original color
			{
				colorIndex = (int)(Math.random()*9); //keep changing the color till it is different
			}
			pastColor = colors[colorIndex];
				
			getContentPane().setBackground(colors[colorIndex]); //set the background color
			

			//display JTextField content
			JOptionPane.showMessageDialog(LabelFrame.this,str);
			
		}
		
		
	}// end of everthingHandler
	
	
	
}
