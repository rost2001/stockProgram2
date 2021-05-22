package stocks.model.yahoo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class MainYahooTest {

	static Stock stock;
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// https://financequotes-api.com/
		// https://financequotes-api.com/javadoc/yahoofinance/Stock.html#getQuote--


		// Disable slf4j Logging, info: https://stackoverflow.com/questions/14544991/how-to-configure-slf4j-simple
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

		

	
	
		
		
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(3, -1);
		 
		Stock google = YahooFinance.get("ocgn");
		

		ArrayList<Long> times = new ArrayList<Long>();
		
		long startTime = 0;
		long estimatedTime = 0;
		List<HistoricalQuote> googleHistQuotes = null;
		for(int i = 0; i < 20; i++) {
		startTime = System.currentTimeMillis();
		googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
		estimatedTime = System.currentTimeMillis() - startTime;
		times.add(estimatedTime);
		}
		
		
		for(Long time : times)
		System.out.println("ms: **" + time);

		/*	DATE
		 *	DATA 1 LINE->>> 
		 * 	FREE LINE
		 */
		
		google.getStats().getShortRatio();
		google.getStats().getSharesFloat();
		google.getStats().getSharesOutstanding();
		google.getStats().getBookValuePerShare();
		google.getQuote().getVolume();

		/*
		 * Vol relative , so vol divided by shares = relative vol and vol times shares = oms�ttning
		 * Market cap at 1 dollar, so 1 dollar * shares = market cap normal, and actual market cap divided by that normal = market cap relative, show market cap and normal and relative   
		 * ranges of below 300-1000 above
		 * If number of shares is more than marketcap, oms�ttning(vol) compared to marketcap how many times is it traded whole, average vol compared to mkt
		 * Point system, +1 for anything that makes it easier to trade or better in some way, -1 for anything hard and risky
		 */

		for(int i = 0; i < googleHistQuotes.size() ;i++) {
		    System.out.println(i);
		System.out.println(googleHistQuotes.get(i).getDate().getTime());
		System.out.println(googleHistQuotes.get(i).getSymbol());
		System.out.println("High: " + String.format("%.4f", googleHistQuotes.get(i).getHigh()));
		System.out.println("Low: " + String.format("%.4f", googleHistQuotes.get(i).getLow()));
		System.out.println("Open: " + String.format("%.4f", googleHistQuotes.get(i).getOpen()));
		System.out.println("Close: " + String.format("%.4f", googleHistQuotes.get(i).getClose()));
		System.out.println();
		System.out.print("Day " + i + " | ");
		System.out.print(" Gap: " + String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() / googleHistQuotes.get(i).getLow().doubleValue() - 1)*100) + "%");
		System.out.print(" Low: " + String.format("%.0f", (googleHistQuotes.get(i).getLow().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
		System.out.print(" High: " + String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
		System.out.print(" Close: " + String.format("%.0f", (googleHistQuotes.get(i).getClose().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
		System.out.println();

		}
		
		
		
		}

}
