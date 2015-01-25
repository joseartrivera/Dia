package com.josetheprogrammer.dia.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class will load all resources, other class can request resources from
 * this class
 * 
 */
public class Resources{
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

		try {
			loadResources();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			//Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				System.out.println("Loaded " + file.getName());
				images.put(file.getName(), new ImageIcon(file.getPath()));
			} else {
				System.out.println("Skipped " + file.getName());
			}
		}

		//Load all the resources in the tileset folder
		dir = new File("tilesets");
		for (File file : dir.listFiles()) {
			//Regex to find png and gif files
			if (file.getName().matches(".*\\.(png|gif)")) {
				System.out.println("Loaded " + file.getName());
				spriteSheet.put(file.getName(), ImageIO.read(file));
			} else {
				System.out.println("Skipped " + file.getName());
			}
		}
	}
	
	
	public static ImageIcon getImage(String str) {
		if (!onWeb) return images.get(str);
		
		URL imageURL = null;
	    try {
	    	  imageURL = new URL(url, "images/" + str);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new ImageIcon(imageURL);
	}

	public static BufferedImage getSpriteSheet(String str) {
		if (!onWeb) return spriteSheet.get(str);
		
		URL imageURL = null;
	    try {
	    	  imageURL = new URL(url, "tilesets/" + str);
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
}
