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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.EditibleItem;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.PlaceableBlock;
import com.josetheprogrammer.dia.items.PlaceableMob;
import com.josetheprogrammer.dia.listeners.EditorListener;
import com.josetheprogrammer.dia.mobs.MobType;

@SuppressWarnings("serial")
public class StageEditorMenu extends JPanel {
	private Game game;
	private EditibleItem placeableItem;
	private JLabel preview;
	private JList list;
	private SharedListSelectionHandler handler;
	private JScrollPane listScroller;
	private String name;
	private JButton add;
	private EditorMode mode;
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
		setup();
		this.game = game;
		this.mode = mode;
		setUpMode(mode);
		setVisible(true);
	}
	
	private void setup() {
		setLayout(null);
		setSize(480, 480);
		setLocation(640,0);
		//setResizable(false);
		//setTitle("Dia Editor");

		add = new JButton("Add to Inventory");
		add.setSize(142, 32);
		add.setLocation(16, 192);
		add.setVisible(true);
		add.addActionListener(addListener);
		//this.getContentPane().add(add);
		this.add(add);
	}

	private void setUpMode(EditorMode mode) {
		switch (mode) {
		case Block:
			setupBlockEditor();
			break;
		case Mob:
			setupMobEditor();
			break;
		default:
			break;

		}

	}

	private void setupBlockEditor() {
		placeableItem = new PlaceableBlock(BlockType.SOLID,
				"dungeon_tileset.png", true, game.getStage());
		previewIcon = new ImageIcon(placeableItem.getInventorySprite());
		preview = new JLabel(previewIcon);
		preview.setLocation(64, 64);
		preview.setSize(previewIcon.getIconWidth(), previewIcon.getIconWidth());
		//this.getContentPane().add(preview);
		this.add(preview);

		tilesetBox = new JCheckBox("Tileset");
		tilesetBox.setMnemonic(KeyEvent.VK_T);
		tilesetBox.setSelected(true);
		tileset = true;

		decorationBox = new JCheckBox("Decoration");
		decorationBox.setMnemonic(KeyEvent.VK_R);
		decorationBox.setSelected(false);

		// Register a listener for the check boxes.
		tilesetBox.addActionListener(checkboxListener);
		decorationBox.addActionListener(checkboxListener);
		tilesetBox.setLocation(16, 128);
		tilesetBox.setSize(80, 32);
		decorationBox.setLocation(16, 160);
		decorationBox.setSize(126, 32);
//		this.getContentPane().add(tilesetBox);
//		this.getContentPane().add(decorationBox);
		this.add(tilesetBox);
		this.add(decorationBox);
		buildBlockList();

	}

	private void setupMobEditor() {
		placeableItem = new PlaceableMob(MobType.SLIME, "slime",
				game.getStage());
		previewIcon = new ImageIcon(placeableItem.getInventorySprite());
		preview = new JLabel(previewIcon);
		preview.setLocation(64, 64);
		preview.setSize(previewIcon.getIconWidth(), previewIcon.getIconWidth());
		//this.getContentPane().add(preview);
		this.add(preview);
		buildMobList();

	}

	private void buildMobList() {
		list = new JList(Resources.getMobList().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		listScroller = new JScrollPane(list);
		listScroller.setSize(160, 208);
		listScroller.setLocation(206, 16);
		list.addListSelectionListener(handler);
//		this.getContentPane().add(listScroller);
//		this.getContentPane().repaint();
		this.add(listScroller);
		this.repaint();
	}

	private void buildBlockList() {
		ArrayList<String> stringList;
		if (tileset && decoration) {
			stringList = Resources.getList("decoration_tilesets");
		} else if (tileset) {
			stringList = Resources.getList("tilesets");
		} else if (decoration) {
			stringList = Resources.getList("decoration");
		} else {
			stringList = Resources.getList("blocks");
		}
		list = new JList(stringList.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		listScroller = new JScrollPane(list);
		listScroller.setSize(160, 208);
		listScroller.setLocation(206, 16);
		list.addListSelectionListener(handler);
//		this.getContentPane().add(listScroller);
//		this.getContentPane().repaint();
		this.add(listScroller);
		this.repaint();
	}

	private void rebuildBlockList() {
		ArrayList<String> stringList;
		if (tileset && decoration) {
			stringList = Resources.getList("decoration_tilesets");
		} else if (tileset) {
			stringList = Resources.getList("tilesets");
		} else if (decoration) {
			stringList = Resources.getList("decoration");
		} else {
			stringList = Resources.getList("blocks");
		}
		list.setListData(stringList.toArray());
	}

	private class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				name = (String) list.getSelectedValue();
				if (name != null) {
					switch (mode) {
					case Block:
						createPlaceableBlock(name);
						break;
					case Mob:
						createPlaceableMob(name);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	private void createPlaceableBlock(String name) {
		BlockType type = BlockType.SOLID;
		if (decoration) {
			type = BlockType.DECORATION;
		}
		placeableItem = new PlaceableBlock(type, name, tileset, game.getStage());
		previewIcon.setImage(placeableItem.getInventorySprite());
		previewIcon.getImage().flush();
		preview.setIcon(previewIcon);
		//this.getContentPane().repaint();
		this.repaint();
	}

	private void createPlaceableMob(String name) {
		MobType type = MobType.valueOf(name.toUpperCase());
		placeableItem = new PlaceableMob(type, name, game.getStage());
		previewIcon.setImage(placeableItem.getInventorySprite());
		previewIcon.getImage().flush();
		preview.setIcon(previewIcon);
		//this.getContentPane().repaint();
		this.repaint();

	}

	private class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (placeableItem != null) {
				game.getPlayer().addItemToInventory((Item) placeableItem);
			}
		}
	}

	private class CheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("Tileset")) {
				tileset = !tileset;
				rebuildBlockList();
			} else if (ae.getActionCommand().equals("Decoration")) {
				decoration = !decoration;
				rebuildBlockList();
			}
		}

	}
}
