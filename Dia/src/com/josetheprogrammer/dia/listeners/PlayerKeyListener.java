package com.josetheprogrammer.dia.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.items.Item;

/**
 * Used to listen for user input to control the player
 * 
 * @author Jose Rivera
 * 
 */
public class PlayerKeyListener implements KeyListener, MouseListener {

	private Player player;

	public PlayerKeyListener(Player player) {
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent key) {

		switch (key.getKeyChar()) {
		case 'w':
			player.setJumping(true);
			break;
		case 'a':
			player.setRunning(true);
			player.setAction(Direction.FACE_LEFT);
			break;
		case 'd':
			player.setRunning(true);
			player.setAction(Direction.FACE_RIGHT);
			break;
		case 'e':
			Item get = player.getStage().getItemAt(player.getX() + 8,
					player.getY() + 8);
			if (get != null) {
				if (!player.getInventory().isFull()) {
					get.setPlayer(player);
					player.getStage().setItem(null, player.getX() + 8,
							player.getY() + 8);
					player.addItemToInventory(get);
				}
			}
			break;
		case '1':
			player.setSelectedItem(0);
			break;
		case '2':
			player.setSelectedItem(1);
			break;
		case '3':
			player.setSelectedItem(2);
			break;
		case '4':
			player.setSelectedItem(3);
			break;
		case '5':
			player.setSelectedItem(4);
			break;
		case '6':
			player.setSelectedItem(5);
			break;
		case '7':
			player.setSelectedItem(6);
			break;
		case '8':
			player.setSelectedItem(7);
			break;
		case '9':
			player.setSelectedItem(8);
			break;
		case '0':
			player.setSelectedItem(9);
		default:
			break;
		}

		switch (key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			player.setRunning(true);
			player.setAction(Direction.FACE_LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRunning(true);
			player.setAction(Direction.FACE_RIGHT);
			break;
		case KeyEvent.VK_UP:
			player.setJumping(true);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		switch (key.getKeyChar()) {
		case 'a':
			if (player.getDirection() != Direction.FACE_RIGHT)
				player.setRunning(false);
			break;
		case 'd':
			if (player.getDirection() != Direction.FACE_LEFT)
				player.setRunning(false);
			break;
		default:
			break;
		}

		switch (key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (player.getDirection() != Direction.FACE_RIGHT)
				player.setRunning(false);
			break;
		case KeyEvent.VK_RIGHT:
			if (player.getDirection() != Direction.FACE_LEFT)
				player.setRunning(false);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent key) {
		switch (key.getKeyChar()) {
		case 'j':
			if (player.getEquippedItem() != null)
				player.getEquippedItem().useItem();
			break;
		case 'k':
			if (player.getEquippedItem() != null)
				player.getEquippedItem().altUseItem();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		if (mouse.getButton() == MouseEvent.BUTTON1
				&& player.getEquippedItem() != null) {
			player.getEquippedItem().useItem();
		} else if (mouse.getButton() == MouseEvent.BUTTON3
				&& player.getEquippedItem() != null) {
			player.getEquippedItem().altUseItem();
		}

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
	public void mouseReleased(MouseEvent mouse) {

	}

}
