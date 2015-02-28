package com.josetheprogrammer.dia.view;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.mobs.MobType;

/**
 * This class will load all resources, other class can request resources from
 * this class
 * 
 */
public class Resources {
	private static HashMap<String, ImageIcon> images;
	private static HashMap<String, BufferedImage> spriteSheet;
	private static URL url;
	private static Boolean onWeb;

	public Resources(URL url) {
		images = new HashMap<String, ImageIcon>();
		spriteSheet = new HashMap<String, BufferedImage>();
		Resources.url = url;
		onWeb = true;
	}

	public Resources() {
		images = new HashMap<String, ImageIcon>();
		spriteSheet = new HashMap<String, BufferedImage>();
		onWeb = false;

		// try {
		// loadResources();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * Loads all resources in the image folder, will be adjusted in the future
	 * to only load the needed resources for a given stage
	 * 
	 * @throws IOException
	 */
	private void loadResources() throws IOException {
		// Load all the resources in the images folder
		File dir = new File("images");
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				System.out.println("Loaded " + file.getName());
				images.put(file.getName(), new ImageIcon(file.getPath()));
			} else {
				System.out.println("Skipped " + file.getName());
			}
		}

		// Load all the resources in the tileset folder
		dir = new File("tilesets");
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				System.out.println("Loaded " + file.getName());
				spriteSheet.put(file.getName(), ImageIO.read(file));
			} else {
				System.out.println("Skipped " + file.getName());
			}
		}
	}

	public static ImageIcon getImage(String folder, String str) {
		if (!onWeb) {
			ImageIcon image = null;
			if (images.containsKey(str)) {
				image = images.get(str);
			} else {
				File imageFile = new File("resources/" + folder + "/" + str);
				if (imageFile.exists()) {
					image = new ImageIcon(imageFile.getPath());
					images.put(str, image);
				}
			}
			return image;
		}

		URL imageURL = null;
		try {
			imageURL = new URL(url, "resources/" + folder + "/" + str);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new ImageIcon(imageURL);
	}
	
	public static ArrayList<String> getListOfTilesets(){
		ArrayList<String> blocks = new ArrayList<String>();
		// Load all the resources in the images folder
		File dir = new File("resources/tilesets");
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				blocks.add(file.getName());
			}
		}
		
		return blocks;
	}
	
	public static ArrayList<String> getListOfTraps(){
		ArrayList<String> blocks = new ArrayList<String>();
		// Load all the resources in the images folder
		File dir = new File("resources/traps");
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				blocks.add(file.getName());
			}
		}
		
		return blocks;
	}
	
	public static ArrayList<String> getListOfBlocks(){
		ArrayList<String> blocks = new ArrayList<String>();
		// Load all the resources in the images folder
		File dir = new File("resources/blocks");
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				blocks.add(file.getName());
			}
		}
		
		return blocks;
	}

	public static BufferedImage getSpriteSheet(String folder, String str) {
		if (!onWeb) {
			BufferedImage image = null;
			if (spriteSheet.containsKey(str)) {
				image = spriteSheet.get(str);
			} else {
				File imageFile = new File("resources/" + folder + "/" + str);
				if (imageFile.exists()) {
					try {
						image = ImageIO.read(imageFile);
					} catch (IOException e) {
					}
					spriteSheet.put(str, image);
				}
			}
			return image;
		}

		URL imageURL = null;
		try {
			imageURL = new URL(url, "resources/" + folder + "/" + str);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			return ImageIO.read(imageURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean SaveStage(Stage stage, String name) {
		try {
			File file = new File("stages/" + name + ".stage");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("0.1\n"); // version
			bw.write(stage.Serialize());
			bw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean LoadStage(String fileName, Stage stage) {
		File file = new File("stages/" + fileName);
		if (file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				scan.nextLine(); // version
				stage.setStageName(scan.nextLine());
				int width = Integer.parseInt(scan.nextLine());
				int height = Integer.parseInt(scan.nextLine());
				stage.changeStageDimensions(width, height);

				int startX = Integer.parseInt(scan.nextLine());
				int startY = Integer.parseInt(scan.nextLine());
				stage.setStartPoint(startX, startY);

				BlockType blockType = null;
				String blockName = "";
				Block block;

				ItemType itemType = null;
				String itemName = "";
				Item item;

				Mob mob;
				MobType mobType = null;
				String mobName = "";
				int mobX = 0;
				int mobY = 0;

				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {

						Scanner scanSection = new Scanner(scan.nextLine());
						scanSection.useDelimiter(";");

						blockType = null;
						blockName = "";

						if (scanSection.hasNext())
							blockType = BlockType.valueOf(scanSection.next());
						if (scanSection.hasNext())
							blockName = scanSection.next();

						if (blockType != null && blockName != "") {
							block = Creator.createBlock(blockType, blockName,
									true, stage);
							stage.setBlock(block, i, j);
						}

						scanSection = new Scanner(scan.nextLine());
						scanSection.useDelimiter(";");

						itemType = null;
						itemName = "";

						if (scanSection.hasNext())
							itemType = ItemType.valueOf(scanSection.next());
						if (scanSection.hasNext())
							itemName = scanSection.next();

						if (itemType != null && itemName != "") {
							// item = Creator
							// .createItem(itemType, itemName, stage);
							// stage.setItemByIndex(item, i, j);
						}
					}
				}

				while (scan.hasNextLine()) {
					Scanner scanSection = new Scanner(scan.nextLine());
					scanSection.useDelimiter(";");

					if (scanSection.hasNext())
						mobType = MobType.valueOf(scanSection.next());
					if (scanSection.hasNext())
						mobName = scanSection.next();
					if (scanSection.hasNext())
						mobX = Integer.parseInt(scanSection.next());
					if (scanSection.hasNext())
						mobY = Integer.parseInt(scanSection.next());

					if (mobType != null && mobName != "") {
						mob = Creator.createMob(mobType, mobName, stage, mobX,
								mobY);
						stage.addMob(mob);
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
