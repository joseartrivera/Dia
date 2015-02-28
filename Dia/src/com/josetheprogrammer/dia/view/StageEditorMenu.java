package com.josetheprogrammer.dia.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.PlaceableItem;

@SuppressWarnings("serial")
public class StageEditorMenu extends JFrame {
	private Game game;
	private PlaceableItem placeableItem;
	private JLabel preview;
	private JList list;
	private SharedListSelectionHandler handler;
	private JScrollPane listScroller;
	private String name;
	private JButton add;
	private AddButtonListener addListener;
	private CheckBoxListener checkboxListener;
	private ImageIcon previewIcon;
	private boolean tileset;
	private boolean decoration;
	private JCheckBox tilesetBox, decorationBox;

	public StageEditorMenu(Game game, EditorMode mode) {
		handler = new SharedListSelectionHandler();
		addListener = new AddButtonListener();
		checkboxListener = new CheckBoxListener();
		setUpWindow();
		this.game = game;
		setUpMode(mode);
		setVisible(true);
	}

	private void setUpMode(EditorMode mode) {
		switch (mode) {
		case Block:
			setupBlockEditor();

			break;
		default:
			break;

		}

	}

	private void setupBlockEditor() {
		placeableItem = new PlaceableItem(BlockType.SOLID,
				"dungeon_tileset.png", true, game.getStage());
		previewIcon = new ImageIcon(placeableItem.getInventorySprite());
		preview = new JLabel(previewIcon);
		preview.setLocation(64, 64);
		preview.setSize(previewIcon.getIconWidth(), previewIcon.getIconWidth());
		this.getContentPane().add(preview);

		tilesetBox = new JCheckBox("Tileset");
		tilesetBox.setMnemonic(KeyEvent.VK_T);
		tilesetBox.setSelected(true);
		tileset = true;

		decorationBox = new JCheckBox("Decoration");
		decorationBox.setMnemonic(KeyEvent.VK_D);
		decorationBox.setSelected(false);

		// Register a listener for the check boxes.
		tilesetBox.addActionListener(checkboxListener);
		decorationBox.addActionListener(checkboxListener);
		tilesetBox.setLocation(128, 128);
		tilesetBox.setSize(80, 32);
		this.getContentPane().add(tilesetBox);
		this.getContentPane().add(decorationBox);
		buildBlockList();

	}

	private void buildBlockList() {
		ArrayList<String> stringList;
		if (tileset) {
			stringList = Resources.getListOfTilesets();
		} else {
			stringList = Resources.getListOfBlocks();
		}
		list = new JList(stringList.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		listScroller = new JScrollPane(list);
		listScroller.setSize(150, 200);
		listScroller.setLocation(300, 64);
		list.addListSelectionListener(handler);
		this.getContentPane().add(listScroller);
		this.getContentPane().repaint();
	}
	private void rebuildBlockList(){
		ArrayList<String> stringList;
		if (tileset) {
			stringList = Resources.getListOfTilesets();
		} else {
			stringList = Resources.getListOfBlocks();
		}
		list.setListData(stringList.toArray());
		}

	private void setUpWindow() {
		setLayout(null);
		setSize(480, 360);
		setLocation(0, 0);
		setResizable(false);
		setTitle("Dia Editor");

		add = new JButton("Add to Inventory");
		add.setSize(129, 64);
		add.setLocation(64, 200);
		add.setVisible(true);
		add.addActionListener(addListener);
		this.getContentPane().add(add);
	}

	private class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				name = (String) list.getSelectedValue();
				if (name != null)
					createPlaceable(name);
			}
		}
	}

	private void createPlaceable(String name) {
		placeableItem = new PlaceableItem(BlockType.SOLID, name, tileset,
				game.getStage());
		previewIcon.setImage(placeableItem.getInventorySprite());
		previewIcon.getImage().flush();
		preview.setIcon(previewIcon);
		this.getContentPane().repaint();

	}

	private class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (placeableItem != null) {
				game.getPlayer().addItemToInventory(placeableItem);
			}
		}
	}

	private class CheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("Tileset")) {
				tileset = !tileset;
				rebuildBlockList();
				System.out.println("yeah");
			}
		}

	}
}
