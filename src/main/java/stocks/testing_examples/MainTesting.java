package stocks.testing_examples;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import stocks.image.processing.ocr.MainOcrImage;

public class MainTesting {


	    
	    public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	    { 


		/*

	    	RobotAwt bot = new RobotAwt();
		BufferedImage img1 = bot.takeScreenshot();
		
		BufferedImage img2 = ImageIO.read(new File("src\\main\\resources\\Images\\2.png"));
	    	BufferedImage img3 = ImageIO.read(new File("src\\main\\resources\\Images\\3.png"));
	    	
	    	
	    	int [] result1 = Image.checkForImage(img1, img2);
	    	int [] result2 = Image.checkForImage(img1, img3);
	    	System.out.println("img2: x: " + result1[0] + " y: " + result1[1]);
	    	System.out.println("img3: x: " + result2[0] + " y: " + result2[1]);


	    	
	    	
	    	int x = result1[0] + img2.getWidth();
	    	int y = result1[1];
	    	int w =  result2[0] - result1[0] - img2.getWidth();
	    	int h = img2.getHeight();
	    	
	    	BufferedImage img4 = img1.getSubimage(x, y, w, h);

	    	
	    	System.out.println("Cropped portion: x: " + x + " y: " + y + " w: " + w + " h: " + h);
	    	
	    	
	    	System.out.println("Ocr: " + MainOcrImage.ocr(img4));
	    	
	    	
	    	ImageIO.write(img4, "png", new File("src\\main\\resources\\Images\\4.png"));

	    	
	    	
	    	BufferedImage cropImg = Image.cropBetweenImagesHorizontal(bot.takeScreenshot(), img2, img3);
	    	
		System.out.println("Ocr: " + MainOcrImage.ocr(cropImg));
		
		*/
		
		Process p = Runtime.getRuntime().exec("cmd");
		PrintWriter stdin = new PrintWriter(p.getOutputStream());
		stdin.println("start chrome "
			+ "--app=\"data:text/html,<html><body><script>window.moveTo(0,0);"
			+ "window.resizeTo(800,1000);"
			+ "window.location='https://www.avanza.se/ab/sok/inline?query=" + "JAGX" + "';"
			+ "</script></body></html>\"");
		stdin.close();
		p.waitFor();
		
		

		BufferedImage img2 = ImageIO.read(new File("src\\main\\resources\\testing\\Images\\test3.png"));
		
		String ocr = null;
		List<Long> duration = new ArrayList<Long>();
		for (int i = 0; i < 3; i++) {
		long startTime = System.nanoTime();
		
	    	ocr = MainOcrImage.ocr(img2.getSubimage(140, 140, 500, 600));
		long elapsedTime = System.nanoTime() - startTime;
		duration.add(elapsedTime);
		
		}
		
		for(long i : duration)
		System.out.println("***** " + i/1000000 + " ms");
		System.out.println(ocr);
		
		//////////////////////////
		
		String shares = ocr.split("Antal ")[1].split("st")[0].replace(" ", "");
		
		String vaxlingskurs = ocr.split("vaxlingskurs ")[1].split("SEK")[0].replace(" ", "");
		if (!vaxlingskurs.contains(",")) {
		    vaxlingskurs = vaxlingskurs.charAt(0) + "." + vaxlingskurs.substring(1);
		} else{
		    vaxlingskurs.replace(",", ".");
		}
		
		String total = ocr.split("SEK")[2].split("belopp")[1].replace(",", ".").replace(" ", "");
		
		int shares1 = Integer.parseInt(shares);
		double vaxlingskurs1 = Double.parseDouble(vaxlingskurs);
		double total1 = Double.parseDouble(total);
		
		System.out.println("\n\n_________________________________________");
		System.out.println("Shares: " + shares1);
		System.out.println("Vaxlingskurs: " + vaxlingskurs1);
		System.out.println("Total exkl fee: " + (total1 + total1*0.005));
		


	        double input = 3.14159265359;
	        System.out.println("double : " + input);
	        System.out.println("double : " + String.format("%.2f", input));
	        System.out.format("double : %.0f", input);
		
	    	/*
	    	int n = 0;
	    	System.out.println(n);
	    	changeN(n);
	    	System.out.println(n);
	    	
	    	int n2[] = {0, 1};
	    	System.out.println(n2[0] +" " + n2[1]);
	    	changeN2(n2);
	    	System.out.println(n2[0] +" " + n2[1]);
	    */
	    }

		@SuppressWarnings("unused")
		private static void changeN2(int[] n2) {
			// I can never change the thing it self so that it also changes outside of the method scope
			// but I can change whats inside of the thing and then it will also have changed outside of the method scope
			
			//n2 = new int[]{1, 1}; // doesnt work
			
			n2[0] = 1; // does work
			
			
			// As to why 
			//https://softwareengineering.stackexchange.com/questions/286008/parameters-are-passed-by-value-but-editing-them-will-edit-the-actual-object-li
			
		}

		@SuppressWarnings("unused")
		private static void changeN(int n) {
			n = 1;
			
		}

}
