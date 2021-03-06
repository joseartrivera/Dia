package com.josetheprogrammer.dia.gameObjects;

import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.view.Resources;

/**
 * This class represents the player's inventory. Carries Items and currently
 * equipped item.
 * 
 * @author Jose Rivera
 * 
 */

public class PlayerInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Total size of inventory
	private int size;

	// Amount of items occupying inventory
	private int items;

	// Currently selected item index
	private int selectedIndex;

	// Array of items represents the inventory
	Item[] inventory;

	transient private ImageIcon inventoryLeft;
	transient private ImageIcon inventoryRight;
	transient private ImageIcon inventorySlot;
	transient private ImageIcon selectedSlot;

	/**
	 * Creates an inventory with the given size
	 * 
	 * @param size
	 */
	public PlayerInventory(int size) {
		this.size = size;
		items = 0;
		inventory = new Item[size];
		setSelectedIndex(0);

		loadResources();
	}

	private void loadResources() {
		inventorySlot = Resources.getImage("images", "inventory_slot.png");
		selectedSlot = Resources.getImage("images", "inventory_selected.png");
		inventoryRight = Resources.getImage("images", "inventory_right.png");
		inventoryLeft = Resources.getImage("images", "inventory_left.png");
	}

	/**
	 * Adds an item to the inventory
	 * 
	 * @param item
	 * @return
	 */
	public boolean addItem(Item item) {
		if (items < size) {
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] == null) {
					inventory[i] = item;
					items++;
					return true;
				}
			}
		}
		return false;
	}

	public ImageIcon getSprite(int index) {
		return inventorySlot;
	}

	public ImageIcon getSelectedSprite(int index) {
		return selectedSlot;
	}

	/**
	 * Returns item at given index
	 * 
	 * @param index
	 * @return
	 */
	public Item getItemAtIndex(int index) {
		if (index >= size)
			return null;
		else
			return inventory[index];
	}

	/**
	 * Returns the first item of this type. Returns null if none is found.
	 * 
	 * @param type
	 * @return
	 */
	public Item getItem(ItemType type) {
		for (Item item : inventory) {
			if (item != null && item.getItemType() == type) {
				return item;
			}
		}

		return null;
	}

	/**
	 * Removes item at an index
	 * 
	 * @param index
	 */
	public void removeItemByIndex(int index) {
		if (index >= size)
			return;
		else {
			inventory[index] = null;
			items--;
		}
	}

	/**
	 * Get the total amount of items in the inventory.
	 * 
	 * @return
	 */
	public int getAmountOfItems() {
		return items;
	}

	/**
	 * Get the size of the inventory
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Increases inventory by 1 slot
	 */
	public void upgradeSize() {
		Item[] newInventory = new Item[size + 1];
		for (int i = 0; i < size; i++) {
			newInventory[i] = inventory[i];
		}
		size++;
		inventory = newInventory;
	}

	public void setSize(int newSize) {
		Item[] newInventory = new Item[newSize];
		for (int i = 0; i < newSize && i < size; i++) {
			newInventory[i] = inventory[i];
		}
		size = newSize;
		inventory = newInventory;
	}

	/**
	 * Swap two items in the inventory
	 * 
	 * @param index1
	 * @param index2
	 */
	public void swapItems(int index1, int index2) {
		if (index1 < size && index2 < size) {
			Item item1 = inventory[index1];
			Item item2 = inventory[index2];

			inventory[index1] = item2;
			inventory[index2] = item1;
		}
	}

	/**
	 * Returns the item currently selected in the inventory
	 * 
	 * @return
	 */
	public Item getSelectedItem() {
		return inventory[selectedIndex];
	}

	/**
	 * Returns the index which is currently selected
	 * 
	 * @return
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * Sets the selected index to the given index
	 * 
	 * @param selectedIndex
	 */
	public void setSelectedIndex(int selectedIndex) {
		if (selectedIndex < size)
			this.selectedIndex = selectedIndex;
	}

	public void update() {
		for (Item item : inventory) {
			if (item != null)
				item.update();
		}
	}

	public ImageIcon getInventoryRightImage() {
		return inventoryRight;
	}

	public ImageIcon getInventoryLeftImage() {
		return inventoryLeft;
	}

	public boolean isFull() {
		return items == size;
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		loadResources();
	}
}
