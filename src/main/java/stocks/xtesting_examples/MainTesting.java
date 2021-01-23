package stocks.xtesting_examples;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.awaitility.Awaitility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import stocks.selenium.ChromeBot;
import stocks.system.WindowsNative;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class MainTesting {

   // https://docs.oracle.com/javase/specs/jls/se8/html/jls-1.html
	    
	    public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	    { 


		String[] symbols = new String[] {"AMD", "zom", "izea", "bngo", "OCGN", "AAPL", "GOOGL", "ACOR", "CEI", "ZOOM"};

		// Can also be done with explicit from, to and Interval parameters
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(3, -10);
		
		Map<String, Stock> stocks = null;
		long startTime = System.currentTimeMillis();
		stocks = YahooFinance.get(symbols);
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("ms: **" + estimatedTime);
		
		
		ArrayList<Stock> stored = new ArrayList<Stock>();
		ArrayList<Stock> active = new ArrayList<Stock>();
		
		ArrayList<Stock> temp = new ArrayList<Stock>();
		Stock intel = stocks.get("OCGN");
			for (Entry<String, Stock> entry  :  stocks.entrySet()) {
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			    
			    for(int i = 0; i < stored.size();i++) {
				
				// also maybe checking old store of ones not to show but store for later use, so if it pops up again no need to get new historical quote
				// maybe just checking towards stored, and add to active which ones mathes, and get the missing ones
				/*add to store
				 * replace active with temp
				 * temp gets all matches in stored added to it, and all none matches gets retrieved and then added
				 */
				if(entry.getKey().equalsIgnoreCase(stored.get(i).getSymbol()) ) 
				    temp.add(entry.getValue());
				else  if(i < stored.size()-1) {
				    // get historical quotes
				    // new array or instance of data class to hold the data
				    // add to temp
				}
				
			    }
			}
			stored = temp;

		// 3 parts, 1. get a list of stocks from screener or watchlist,  
		// 2. compare to stored and store it,
		// 3. retrieve formatted data for all that didn't exist in store and print in 3 lines[date,data,empty,---]
		/*Other:
		 * Jnativehook instead of periodic
		 * 
		 * Check if it exist in list and only get the ones that doesnt exist, maybe just add or check to list as hashlist,' not needed when small but probably later when big
		 * check and confirm the order of the list to match the order as appeared in list on tradingview screener or watchlist or what it is
		 * --
		 * with data, calculate the relative data, format it and print it
		 * 
		 * 
		 */
		 

		List<HistoricalQuote> googleHistQuotes = intel.getHistory();


		
		

		for(int i = 0; i < googleHistQuotes.size(); i++) {
		System.out.println(googleHistQuotes.get(i).getDate().getTime());
		System.out.println(googleHistQuotes.get(i).getLow());
		System.out.println("-----------------------------");
		}
		
	    }



}
