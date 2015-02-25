package com.josetheprogrammer.dia.view;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.PlaceableItem;

@SuppressWarnings("serial")
public class StageEditorMenu extends JFrame {
	private Game game;
	private PlaceableItem placeableItem;
	private JLabel preview;
	private JList list;

	public StageEditorMenu(Game game, EditorMode mode) {

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
				"dungeon_tileset.png", game.getStage());
		ImageIcon previewIcon = new ImageIcon(
				placeableItem.getInventorySprite());
		preview = new JLabel(previewIcon);
		preview.setLocation(64, 64);
		preview.setSize(previewIcon.getIconWidth(), previewIcon.getIconWidth());
		this.getContentPane().add(preview);
		buildBlockList();

	}

	private void buildBlockList(){
		String[] blockList = Resources.getListOfBlocks();
		String[] yeah = {"ds","Fds","fds"};
		list = new JList(yeah); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setSize(60,60);
		this.getContentPane().add(listScroller);
	}
	private void setUpWindow() {
		setLayout(null);
		setSize(480, 360);
		setLocation(0, 0);
		setResizable(false);
		setTitle("Dia Editor");
	}
}
