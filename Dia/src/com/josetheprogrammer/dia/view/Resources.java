package com.josetheprogrammer.dia.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Stage;

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
				File imageFile = new File("resources" + File.separator + folder
						+ File.separator + str);
				if (imageFile.exists()) {
					image = new ImageIcon(imageFile.getPath());
					images.put(str, image);
				}
			}
			return image;
		}

		URL imageURL = null;
		try {
			imageURL = new URL(url, "resources" + File.separator + folder
					+ File.separator + str);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new ImageIcon(imageURL);
	}

	public static ArrayList<String> getList(String folder) {
		ArrayList<String> list = new ArrayList<String>();
		// Load all the resources in the images folder
		File dir = new File("resources" + File.separator + folder);
		for (File file : dir.listFiles()) {
			// Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				list.add(file.getName());
			}
		}

		return list;
	}

	public static ArrayList<String> getMobList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("slime");
		list.add("spider");
		list.add("redspider");
		list.add("hollow");
		return list;
	}

	public static ArrayList<String> getItemList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("sword.gif");
		list.add("fireball.gif");
		list.add("gun.png");
		list.add("Dobble_axe.png");
		list.add("spear.png");
		list.add("shield.png");
		list.add("potion_1.png");
		list.add("potion_2.png");
		list.add("float_coin.gif");
		return list;
	}

	public static BufferedImage getSpriteSheet(String folder, String str) {
		if (!onWeb) {
			BufferedImage image = null;
			if (spriteSheet.containsKey(str)) {
				image = spriteSheet.get(str);
			} else {
				File imageFile = new File("resources" + File.separator + folder
						+ File.separator + str);
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
			imageURL = new URL(url, "resources" + File.separator + folder
					+ File.separator + str);
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

	public static boolean SaveStage(Stage stage, String name, String dir) {
		try {
			File file = new File(dir + File.separator + name);
			if (!file.exists()) {
				if (!file.createNewFile()) {
					return false;
				}

			}
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(stage);
			out.close();
			fileOut.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Stage LoadStage(String name, String directory) {
		Stage stage = null;
		File file = new File(directory + File.separator + name);
		if (!file.exists()) {
			return null;
		}
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			stage = (Stage) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Stage not found");
			c.printStackTrace();
			return null;
		}
		return stage;
	}

}
