package stocks.model.yahoo;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooHistory {

    
    
    
    public static void main(String[] args) {

<<<<<<< Updated upstream:src/main/java/stocks/xtesting_examples/MainTest3.java
	ChromeBot bot = new ChromeBot();
	
	bot.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	
	CBTradingview.login(bot, "fredrik.jonsson.s@hotmail.com", "Uctrickme12");
	
	bot.setSize(1920, 1080);
	
	//--------
	ChromeBot bot2 = new ChromeBot();
	
	bot2.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE, ChromeBot.HEADLESS);
	String symbol = CBTradingview.getSymbol(bot);
	int watchers = CBStocktwits.getWatchers(bot2, symbol);
	
=======

    }
    
    
    /*
    public  getHistoricalQuotes(){
>>>>>>> Stashed changes:src/main/java/stocks/model/yahoo/YahooHistory.java
	
	return null;
    }
*/
    
    /*
     * 
	
	Calendar from = Calendar.getInstance();
	Calendar to = Calendar.getInstance();
	from.add(Calendar.MONTH, -5);
	
	 
	Stock google = YahooFinance.get(symbol);
	List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, Interval.DAILY);

	
	
	String history = "";
	for(int i = 0; i < googleHistQuotes.size() ;i++) {

	history += (googleHistQuotes.get(i).getDate().getTime());
	history += "<br/>";
	
	//history += ("Day " + i + " | ");
	history += (" Gap: " + String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() / googleHistQuotes.get(i).getLow().doubleValue() - 1)*100) + "%");
	history += (" Low: " + String.format("%.0f", (googleHistQuotes.get(i).getLow().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
	history += (" High: " + String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
	history += (" Close: " + String.format("%.0f", (googleHistQuotes.get(i).getClose().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
	history += (" Vol: " + googleHistQuotes.get(i).getVolume() );
	history += (" Close: " + String.format("%.0f", (googleHistQuotes.get(i).getClose().doubleValue() / googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%");
	history += "<br/>--------------------------------------";
	history += "<br/>";
	}
	/* Filesystem / data in file or database storage:
	 * 
	 * Store [symbol][watchers][marketcap][shares][vol][%,GapLowHighClose] -- retrieve maybe from tradingview of all below 1000 million marketcap
	 * Update (start of day)(or when running it), except "vol" needs to be updated realtime so headless sidewindow
	 * Periodic update (during day), can store to show things like vol before it can be retrieve in realtime like during loading of sidewindow
	 * 
	 * 
	 */
	
	
	Thread.sleep(500);

	JavascriptExecutor js = (JavascriptExecutor) bot.driver;
	js.executeScript("var nodes = document.evaluate(\"//div[contains(@class,'wrapper-1CeUhfBr')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"+ 
		"var mainDiv = nodes.snapshotItem(0).parentNode;"+ 
		""+ 
		"var tempNodes = document.evaluate(\"//div[contains(@class,'container-2kiEMFEI')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"+ 
		"var market = tempNodes.snapshotItem(0).cloneNode(true);"+ 
		""+ 
	//	"tempNodes = document.evaluate(\"//div[contains(@class,'lastPrice-xsmOoop4')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"+ 
	//	"var preMarket = tempNodes.snapshotItem(0).cloneNode(true);"+ 
		""+ 
		"keyNodes = document.evaluate(\"//div[contains(@class,'items-3OvCfNrj')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"+ 
		"var keyStats = keyNodes.snapshotItem(0).cloneNode(true);"+ 
		""+ 
		""+ 
		"" + 
		"	while (nodes.snapshotItem(0).firstChild) {"
		+ //"	    if(i !== 1){"+
		" 	    nodes.snapshotItem(0).removeChild(nodes.snapshotItem(0).lastChild);"+ 
		//"	    }"+ 
		"	};" +
		""+
		"mainDiv.innerHTML += 'Watchers: "+watchers+"';"+
		""+
		"	var childNodes = keyStats.childNodes;"+
		"	for (var i = 0; i < childNodes.length;i++) {"+
	//	"	    if(i !== 1){"+ 
		" 	    mainDiv.appendChild(childNodes.item(i));"+
	//	"	    }"+ 
		"	};"+
		""+
		""+
		"mainDiv.appendChild(market);"+
	//	"mainDiv.appendChild(preMarket);"+
		""+
		"mainDiv.innerHTML += '"+history+"';"+
	//	"mainDiv.innerHTML += ' Extra stuff" + 
	//	"<br/>asd';"+ 
		""+
//		"mainDiv.appendChild(keyStats);"+
		""+
		""+
		"");
     * 
     * 
     * 
     */


}
