package com.josetheprogrammer.dia.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {
	private GameWindow gameWindow;
	private JButton newStory, loadStory, stageEditor;
	private ActionListener buttonListener;
	private JLabel title;

	/**
	 * Constructor for our MainMenu panel
	 */
	public MainMenu(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		setup();
		setVisible(true);
	}

	/**
	 * Setup the basics of our panel
	 */
	private void setup() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setSize(640, 480);
		setLocation(0, 0);
		
	    buttonListener = new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	          if (ae.getActionCommand().equals("New Story")){
	        	  newStory();
	          }
	          else if (ae.getActionCommand().equals("Stage Editor"))
	        	  newEditor();
	        }
	      };
	      
	    ImageIcon myPicture = Resources.getImage("dia_title.png");
	    title = new JLabel(myPicture);
	    title.setAlignmentX(CENTER_ALIGNMENT);

	    this.add(title);
	    this.add(Box.createRigidArea(new Dimension(0,126)));
		
		newStory = new JButton("New Story");
		newStory.setSize(360,32);
		newStory.addActionListener(buttonListener);
		newStory.setAlignmentX(CENTER_ALIGNMENT);
		this.add(newStory);
		
		loadStory = new JButton("Load Story");
		loadStory.setSize(360,32);
		loadStory.addActionListener(buttonListener);
		loadStory.setAlignmentX(CENTER_ALIGNMENT);
		this.add(loadStory);
		
		stageEditor = new JButton("Stage Editor");
		stageEditor.setSize(360,32);
		stageEditor.addActionListener(buttonListener);
		stageEditor.setAlignmentX(CENTER_ALIGNMENT);
		this.add(stageEditor);
	}
	
	private void newStory(){
		gameWindow.startNewGame();
	}
	
	private void newEditor(){
		gameWindow.startEditor();
	}
}
