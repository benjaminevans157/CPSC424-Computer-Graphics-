package scenegraph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class Texture {
	
private static Map<String, com.jogamp.opengl.util.texture.Texture> textures = new HashMap<>();
	
	// Making these textures static and public
	public static com.jogamp.opengl.util.texture.Texture ambrosia;
	public static com.jogamp.opengl.util.texture.Texture bark;
	public static com.jogamp.opengl.util.texture.Texture maelstrm;
	public static com.jogamp.opengl.util.texture.Texture weave7;
	public static com.jogamp.opengl.util.texture.Texture towel;
	
	// A static block to initialize textures when the class is loaded
	static {
		ambrosia = getTexture("ambrosia", 0, 0, 0); // Default to no offset
		bark = getTexture("bark", 0, 0, 0);
		maelstrm = getTexture("maelstrm", 0, 0, 0);
		weave7 = getTexture("weave7", 0, 0, 0);
		towel = getTexture("towel", 0, 0, 0);
	}
	
	public void loadSpecificTextures(String folderPath, List<String> fileNames) {
		for (String fileName : fileNames) {
			File file = new File(folderPath + "/" + fileName);
			if (file.isFile() && fileName.endsWith(".jpg")) {
				BufferedImage img = loadImage(file.getPath());
				ImageUtil.flipImageVertically(img);
				com.jogamp.opengl.util.texture.Texture texture = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);				// adds images to hashmap with the file name without the extension (i.e.
				// "ambrosia" instead of "ambrosia.jpg", as the key)
				textures.put(getFileNameWithoutExtension(file), texture);
			}
		}
	}

	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	private String getFileNameWithoutExtension(File file) {
		String fileName = file.getName();
		if (fileName.indexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}

	public static com.jogamp.opengl.util.texture.Texture getTexture(String name, int redOffset, int greenOffset, int blueOffset) {
		BufferedImage img = loadImage("path_to_textures_folder/" + name + ".jpg");
		img = setOffsetColor(img, redOffset, greenOffset, blueOffset);
		ImageUtil.flipImageVertically(img);
		com.jogamp.opengl.util.texture.Texture texture = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
		textures.put(name, texture);
		return texture;
	}
	
	/**
     * Sets an offset color to the specified image. The result is a blending of
     * the image's original color and the offset color.
     *
     * @param img       The original image to be offset.
     * @param redOffset The red component of the offset color.
     * @param greenOffset The green component of the offset color.
     * @param blueOffset The blue component of the offset color.
     * @return The image blended with the offset color.
     */
	private static BufferedImage setOffsetColor(BufferedImage img, int redOffset, int greenOffset, int blueOffset) {
        BufferedImage offsetImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgba = img.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xFF;
                int red = (rgba >> 16) & 0xFF;
                int green = (rgba >> 8) & 0xFF;
                int blue = rgba & 0xFF;

                // Blend the colors
                red = (red + redOffset) / 2;
                green = (green + greenOffset) / 2;
                blue = (blue + blueOffset) / 2;

                int newRgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
                offsetImage.setRGB(x, y, newRgba);
            }
        }
        
        return offsetImage;
    }
}

