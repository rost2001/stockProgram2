package stocks.model.image;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import stocks.model.system.SRobot;



// Currently a Class for doing stuff with images
public class IImage {


    public static double getPriceAtMouse() {
	Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
	int x = mouseInfo.x;
	int y = mouseInfo.y;

	SRobot bot;
	BufferedImage screenshot = null;
	try {
	    bot = new SRobot();
	    screenshot = bot.takeScreenshot();
	} catch (AWTException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}


	for (; x < screenshot.getWidth(); x++) {

	    if (screenshot.getRGB(x, y) == new Color(76,82,94).getRGB()) {
		// subimage where the price label box is and ocr

		int endX = 0;
		for (int i = 0; screenshot.getRGB(x+53+i, y-21) == new Color(76,82,94).getRGB(); i++) {
		    endX++;
		}

		String ocr = ocr(screenshot.getSubimage(x+60, y-14, endX-22, 45-19));

		return Double.parseDouble(ocr.replace(" ", "").replace("\n", "").replace(" ", ""));
	    }

	}
	return -1;
    }


    // Crops an image out of the base image between 2 other smaller images horizontally
    public static BufferedImage cropBetweenImagesHorizontal(BufferedImage baseImg, BufferedImage img1, BufferedImage img2) {

	int [] result1 = IImage.checkForImage(baseImg, img1);
	int [] result2 = IImage.checkForImage(baseImg, img2);
	//System.out.println("img1: x: " + result1[0] + " y: " + result1[1]);
	//System.out.println("img2: x: " + result2[0] + " y: " + result2[1]);

	int x = result1[0] + img1.getWidth();
	int y = result1[1];
	int w =  result2[0] - result1[0] - img1.getWidth();
	int h = img1.getHeight();

	BufferedImage cropImg = baseImg.getSubimage(x, y, w, h);
	//System.out.println("Cropped portion: x: " + x + " y: " + y + " w: " + w + " h: " + h);

	return cropImg;
    }


    // Looks for image2 inside of image1. 
    // Returns an array of {x,y} where it found the image, 
    // {-1, -1} if it didnt, and {-2, -2} if it were an error
    public static int[] checkForImage(BufferedImage image1, BufferedImage image2) {

	// If the image2 is bigger than image1, then it cant be found in image1
	if (image2.getWidth() > image1.getWidth() || image2.getHeight() > image1.getHeight()) {
	    return new int[] { -2, -2 };
	}

	int x = 0;
	int y = 0;

	for (y = 0; y < image1.getHeight() - image2.getHeight(); y++) {
	    image1xloop: for (x = 0; x < image1.getWidth() - image2.getWidth(); x++) {

		for (int y2 = 0; y2 < image2.getHeight(); y2++) {
		    for (int x2 = 0; x2 < image2.getWidth(); x2++) {

			if (image1.getRGB(x + x2, y + y2) != image2.getRGB(x2, y2)) {

			    continue image1xloop;

			}

		    }
		}
		return new int[] { x, y };

	    }
	}
	return new int[] { -1, -1 };
    }

    // Kollar 2 arrayer och returna True om allt st�mmer
    public static boolean checkColors(int[] colors, int[] pixels) {

	// old boolean[] found = new boolean[colors.length];

	for (int i = 0; i < pixels.length; i++) {
	    for (int i2 = 0; i2 < colors.length; i2++) {
		if (pixels[i] != colors[i2]) {

		    return false;
		}
	    }
	}

	// old for(boolean b : found) if(!b) return false;
	return true;

    }

    // H�mtar pixlar(RGBs) fr�n BufferedImage till int array, b�rjar p� x,y f�r en viss m�ngd
    protected int[] getRGBs(BufferedImage image, int startx, int starty, int xAmount, int yAmount) {

	return image.getRGB(startx, starty, xAmount, yAmount, new int[0], 0, image.getWidth());
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



    // enkel ocr
    public static String ocr(BufferedImage image, int dpi) {
	Tesseract tesseract = new Tesseract();
	tesseract.setTessVariable("user_defined_dpi", String.valueOf(dpi));
	try {
	    //tesseract.setDatapath("src\\main\\resources\\tessdata");
	    File tessDataFolder = LoadLibs.extractTessResources("tessdata");

	    //Set the tessdata path
	    tesseract.setDatapath(tessDataFolder.getAbsolutePath());
	    String text = tesseract.doOCR(image);

	    return text;
	} catch (TesseractException e) {
	    e.printStackTrace();
	}
	return null;


    }
    
    public static String ocr(BufferedImage image) {
	return ocr(image, 70);
    }
    
    


}
