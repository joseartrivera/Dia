package com.josetheprogrammer.dia.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.items.EditibleItem;
import com.josetheprogrammer.dia.items.Item;

public class EditorListener implements MouseListener, MouseMotionListener {

	private Game game;
	private Player player;
	public int speed, xSpeed, ySpeed, x1, y1, x2, y2;

	public EditorListener(Game game, int speed) {
		this.game = game;
		this.speed = speed;
		this.player = game.getPlayer();
	}

	@SuppressWarnings("serial")
	public void setKeys(InputMap input, ActionMap action) {

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");

		action.put("up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ySpeed = -speed;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "uprelease");

		action.put("uprelease", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (ySpeed < 0)
					ySpeed = 0;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");

		action.put("right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				xSpeed = speed;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),
				"rightrelease");

		action.put("rightrelease", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (xSpeed > 0)
					xSpeed = 0;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");

		action.put("left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				xSpeed = -speed;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftrelease");

		action.put("leftrelease", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (xSpeed < 0)
					xSpeed = 0;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "down");

		action.put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ySpeed = speed;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "downrelease");

		action.put("downrelease", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (ySpeed > 0)
					ySpeed = 0;
			}
		});

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "1");
		action.put("1", new selectInventoryItem(0));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "2");
		action.put("2", new selectInventoryItem(1));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "3");
		action.put("3", new selectInventoryItem(2));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "4");
		action.put("4", new selectInventoryItem(3));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), "5");
		action.put("5", new selectInventoryItem(4));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), "6");
		action.put("6", new selectInventoryItem(5));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), "7");
		action.put("7", new selectInventoryItem(6));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), "8");
		action.put("8", new selectInventoryItem(7));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), "9");
		action.put("9", new selectInventoryItem(8));

		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), "0");
		action.put("0", new selectInventoryItem(9));

	}

	@SuppressWarnings("serial")
	private class selectInventoryItem extends AbstractAction {
		private int index;

		public selectInventoryItem(int index) {
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			player.setSelectedItem(index);

		}
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		place(mouse);
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseDragged(MouseEvent mouse) {
		place(mouse);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void place(MouseEvent mouse) {
		Player player = game.getPlayer();
		EditibleItem item;
		if (mouse.getButton() == MouseEvent.BUTTON1
				&& player.getEquippedItem() != null) {
			item = (EditibleItem) player.getEquippedItem();
			item.generatePlaceable(game.getStage(), mouse.getX() + x1,
					mouse.getY() + y1);

			((Item) item).useItem();
		} else if (mouse.getButton() == MouseEvent.BUTTON3
				&& player.getEquippedItem() != null) {
			game.getStage().editorDelete(mouse.getX() + x1, mouse.getY() + y1);
		}
	}
}
