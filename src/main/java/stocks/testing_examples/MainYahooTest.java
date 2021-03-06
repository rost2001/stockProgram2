package stocks.testing_examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import stocks.image.processing.Image;
import stocks.image.processing.ocr.MainOcrImage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainYahooTest {

	static Stock stock;
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// https://financequotes-api.com/
		// https://financequotes-api.com/javadoc/yahoofinance/Stock.html#getQuote--


		// Disable slf4j Logging, info: https://stackoverflow.com/questions/14544991/how-to-configure-slf4j-simple
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

		/*
	        
	        long startTime0 = System.nanoTime();
		stock = YahooFinance.get("AAPL"); // Only first time takes long
		long elapsedTime0 = System.nanoTime() - startTime0;
		stock.print();
		System.out.println("***** " + elapsedTime0/1000000 + " ms");
		
		
		//BigDecimal price = stock.getQuote().getPrice();
		//stock.print();
		long startTime = System.nanoTime();
		if(stock != null)
		System.out.println(stock.getQuote(true));
		
		
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("***** " + elapsedTime/1000000 + " ms");
		
	
		
		long startTime2 = System.nanoTime();
		
		stock = YahooFinance.get("JAGX");
	
		
		
		long elapsedTime2 = System.nanoTime() - startTime2;
		System.out.println("***** " + elapsedTime2/1000000 + " ms");
		
		
	
		

		long startTime3 = System.nanoTime();
		
		System.out.println(stock.getQuote().getPrice());
		
		stock.getQuote();
		
		long elapsedTime3 = System.nanoTime() - startTime3;
		System.out.println("***** " + elapsedTime3/1000000 + " ms");
		
		
		System.out.println(stock.getQuote().getSymbol());
		System.out.println(stock.getQuote());

		/////////////////////////////////////
		
		System.out.println("________________________________________\n\n");
		Stock stock2 = YahooFinance.get("AAPL"); 
		
		System.out.println(stock2);
		stock2.print();

		*/
		Thread.sleep(2000);
		
	    	//BufferedImage img3 = ImageIO.read(new File("src\\main\\resources\\Images\\0.png"));
	    	
	    //	System.out.print("2:" + MainOcrImage.ocr(img3));

		System.out.println(Image.getPriceAtMouse());

	    	BufferedImage img4 = ImageIO.read(new File("src\\main\\resources\\Images\\0.png"));
	    	
	    	System.out.print(MainOcrImage.ocr(img4));
	}

}
