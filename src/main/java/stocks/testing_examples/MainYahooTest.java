package stocks.testing_examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainYahooTest {

	static Stock stock;
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// https://financequotes-api.com/
		// https://financequotes-api.com/javadoc/yahoofinance/Stock.html#getQuote--


		// Disable slf4j Logging, info: https://stackoverflow.com/questions/14544991/how-to-configure-slf4j-simple
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

		
		String str= "SNDL";
		Process p = Runtime.getRuntime().exec("cmd");
		 PrintWriter stdin = new PrintWriter(p.getOutputStream());
	        stdin.println("start chrome --app=https://www.avanza.se/ab/sok/inline?query=" + str + "");
	        stdin.close();
	        p.waitFor();
		
		
	        
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

		
	}

}
