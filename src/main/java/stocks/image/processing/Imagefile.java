package stocks.image.processing;

import java.awt.image.BufferedImage;



// not completely functioning
// A class for processing Image files or bufferedimages and holding related info
public class Imagefile {
	
	BufferedImage image;
	int xpos; int ypos;
	
	

	public Imagefile() {

	}
	
	

	
	

	// P� ett x,y v�rde i image2, b�rjar den leta efter image1 inuti image2. 
	protected boolean checkForImage(BufferedImage image, BufferedImage image2, int x, int y) {

		if(x+image.getWidth() >= image2.getWidth() || y+image.getHeight() >= image2.getHeight()){
			return false;
		}
		
		// Loops width and height of image
		for (int y2 = 0; y2 < image.getHeight(); y2++) {
			for (int x2 = 0; x2 < image.getWidth(); x2++) {

				if (image2.getRGB(x + x2, y + y2) != image.getRGB(x2, y2)) {
					return false;
				}

				/*// Ett s�tt kolla R G B individuellt ist�llet f�r RGB mot RGB
				 * if (( ((myRobot.capture.getRGB(myRobot.x + x2, myRobot.y + y2) >> 16) & 0xFF)
				 * != ((image.getRGB(x2, y2) >> 16) & 0xFF)) ||
				 * (((myRobot.capture.getRGB(myRobot.x + x2, myRobot.y + y2) >> 8) & 0xFF) !=
				 * ((image.getRGB(x2, y2) >> 8) & 0xFF)) || (((myRobot.capture.getRGB(myRobot.x
				 * + x2, myRobot.y + y2) >> 0) & 0xFF) != ((image.getRGB(x2, y2) >> 0) & 0xFF)))
				 * {
				 * 
				 * return false; }
				 */

			}
		}

		return true;
	}

	// Kollar 2 arrayer och returna True om allt st�mmer
	protected boolean checkColors(int[] colors, int[] pixels){

		//old boolean[] found = new boolean[colors.length];

		
		for (int i = 0; i < pixels.length; i++) {
			for(int i2 = 0; i2 < colors.length; i2++){
				if(pixels[i] != colors[i2]){

					return false;
				} 
			}
		}

		//old for(boolean b : found) if(!b) return false;
    	return true;
    	
	}

		// H�mtar pixlar(RGBs) fr�n BufferedImage till int array, b�rjar p� x,y f�r en viss m�ngd
	protected int[] getRGBs(int startx, int starty, int xAmount, int yAmount) {

			return image.getRGB(startx, starty, xAmount, yAmount, new int[0], 0, image.getWidth());
	}
	


}
