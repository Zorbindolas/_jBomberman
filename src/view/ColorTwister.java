package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 *This class encapsulates color manipulation algorithms. 
 */
public class ColorTwister {
	/**
	 * Return the complementary color from the one in input.
	 * With this method I can find an acceptable color to match an unknown one
	 * (as one chosen by User).
	 * @param color color whose complement you want
	 * @return new completary color
	 */
    public static Color getComplementary(Color color) {
        return new Color(
        		255 - color.getRed(), 
        		255 - color.getGreen(),
                255 - color.getBlue());
    }
    /**
     * Return a gray-scale version of the input image.
     * This method works bitwise on each pixel. Alpha channel is preserved.
     * @param image starting image
     * @return gray-scale version image
     */
	public static BufferedImage grayer(BufferedImage image) {
		
		BufferedImage grayImage = new BufferedImage(
				image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		int alpha,rgb,r,g,b;
		rgb = r = g = b = 0;
		for(int y=0; y<image.getHeight(); y++) {
			for(int x=0; x<image.getWidth(); x++) {
				rgb = (int)(image.getRGB(x, y)); // 4 bytes : alpha, R, G ,B
				alpha = ((rgb >>24) & 0xFF); // alpha is the combination of the most significant byte
				r = ((rgb >>16) & 0xFF);  // & is bitwise(bit-a-bit). 16 bits is a right shift by 2 byte.
				g = ((rgb >>8) & 0xFF); //... that is: rgb & 0x0000FF00
				b = rgb & 0xFF;
				rgb = (int)((r+g+b)/3);
				rgb = (alpha<<24) | (rgb<<16) | (rgb<<8) | rgb; // I must preserve the alpha channel as it is
				grayImage.setRGB(x, y, rgb);
			}
		}
		return grayImage;
	}
	/**
	 * Return an image in which the pixels of the starting one are all white.
     * This method works bitwise on each pixel. Alpha channel is preserved.
	 * @param image starting image
	 * @return whitened version image
	 */
	public static BufferedImage whitener(BufferedImage image) {
		
		BufferedImage whiteImage = new BufferedImage(
				image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		int rgb = 0;
		for(int y=0; y<image.getHeight(); y++) {
			for(int x=0; x<image.getWidth(); x++) {
				rgb = (int)(image.getRGB(x, y)); // int is 32 bits
				rgb |= 0x00FFFFFF; // a white pixel is FF, that is 1111 1111
				whiteImage.setRGB(x, y, rgb);
			}
		}
		return whiteImage;
	}
}
