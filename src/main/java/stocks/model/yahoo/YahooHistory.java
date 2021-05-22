package stocks.model.yahoo;
/*
 * package stocks.yahoo;
 * 
 * import java.util.ArrayList; import java.util.Calendar; import java.util.List;
 * import java.util.Map; import java.util.Map.Entry;
 * 
 * import org.openqa.selenium.JavascriptExecutor;
 * 
 * import yahoofinance.Stock; import yahoofinance.YahooFinance; import
 * yahoofinance.histquotes.HistoricalQuote; import
 * yahoofinance.histquotes.Interval;
 * 
 * public class YahooHistory {
 * 
 * 
 * 
 * 
 * public static void main(String[] args) {
 * 
 * 
 * }
 * 
 * 
 * 
 * public getHistoricalQuotes(){
 * 
 * return null; }
 * 
 * 
 * 
 * 
 * Calendar from = Calendar.getInstance(); Calendar to = Calendar.getInstance();
 * from.add(Calendar.MONTH, -5);
 * 
 * 
 * Stock google = YahooFinance.get(symbol); List<HistoricalQuote>
 * googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
 * 
 * 
 * 
 * String history = ""; for(int i = 0; i < googleHistQuotes.size() ;i++) {
 * 
 * history += (googleHistQuotes.get(i).getDate().getTime()); history += "<br/>";
 * 
 * //history += ("Day " + i + " | "); history += (" Gap: " +
 * String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() /
 * googleHistQuotes.get(i).getLow().doubleValue() - 1)*100) + "%"); history +=
 * (" Low: " + String.format("%.0f",
 * (googleHistQuotes.get(i).getLow().doubleValue() /
 * googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
 * (" High: " + String.format("%.0f",
 * (googleHistQuotes.get(i).getHigh().doubleValue() /
 * googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
 * (" Close: " + String.format("%.0f",
 * (googleHistQuotes.get(i).getClose().doubleValue() /
 * googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
 * (" Vol: " + googleHistQuotes.get(i).getVolume() ); history += (" Close: " +
 * String.format("%.0f", (googleHistQuotes.get(i).getClose().doubleValue() /
 * googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
 * "<br/>--------------------------------------"; history += "<br/>"; }
 * 
 * // part 1. retrieves ALT // part 2. removes // part 3. adds
 * 
 * 
 * Thread.sleep(500);
 * 
 * JavascriptExecutor js = (JavascriptExecutor) bot.driver; js.
 * executeScript("var nodes = document.evaluate(\"//div[contains(@class,'wrapper-1CeUhfBr')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"
 * + "var mainDiv = nodes.snapshotItem(0).parentNode;"+ ""+
 * "var tempNodes = document.evaluate(\"//div[contains(@class,'container-2kiEMFEI')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"
 * + "var market = tempNodes.snapshotItem(0).cloneNode(true);"+ ""+ //
 * "tempNodes = document.evaluate(\"//div[contains(@class,'lastPrice-xsmOoop4')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"
 * + // "var preMarket = tempNodes.snapshotItem(0).cloneNode(true);"+ ""+
 * "keyNodes = document.evaluate(\"//div[contains(@class,'items-3OvCfNrj')]\",document.documentElement, null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);"
 * + "var keyStats = keyNodes.snapshotItem(0).cloneNode(true);"+ ""+ ""+ "" +
 * "	while (nodes.snapshotItem(0).firstChild) {" +
 * //"	    if(i !== 1){"+
 * " 	    nodes.snapshotItem(0).removeChild(nodes.snapshotItem(0).lastChild);"
 * + //"	    }"+ "	};" + ""+
 * "mainDiv.innerHTML += 'Watchers: "+watchers+"';"+ ""+
 * "	var childNodes = keyStats.childNodes;"+
 * "	for (var i = 0; i < childNodes.length;i++) {"+ //
 * "	    if(i !== 1){"+ " 	    mainDiv.appendChild(childNodes.item(i));"+
 * // "	    }"+ "	};"+ ""+ ""+ "mainDiv.appendChild(market);"+ //
 * "mainDiv.appendChild(preMarket);"+ ""+ "mainDiv.innerHTML += '"+history+"';"+
 * // "mainDiv.innerHTML += ' Extra stuff" + // "<br/>asd';"+ ""+ //
 * "mainDiv.appendChild(keyStats);"+ ""+ ""+ "");
 * 
 * 
 * //Thread.sleep(10000); //bot.close(); }
 * 
 * 
 * 
 * 
 * 
 * String[] symbols = new String[] {"AMD", "zom", "izea", "bngo", "OCGN",
 * "AAPL", "GOOGL", "ACOR", "CEI", "ZOOM"};
 * 
 * 
 * // Can also be done with explicit from, to and Interval parameters Calendar
 * from = Calendar.getInstance(); Calendar to = Calendar.getInstance();
 * from.add(Calendar.MONTH, -10);
 * 
 * 
 * Map<String, Stock> stocks = null; long startTime =
 * System.currentTimeMillis(); stocks = YahooFinance.get(symbols); long
 * estimatedTime = System.currentTimeMillis() - startTime;
 * System.out.println("ms: **" + estimatedTime);
 * 
 * 
 * 
 * ArrayList<Stock> stored = new ArrayList<Stock>(); ArrayList<Stock> active =
 * new ArrayList<Stock>();
 * 
 * ArrayList<Stock> temp = new ArrayList<Stock>(); Stock intel =
 * stocks.get("OCGN"); for (Entry<String, Stock> entry : stocks.entrySet()) {
 * System.out.println(entry.getKey() + "/" + entry.getValue());
 * 
 * for(int i = 0; i < stored.size();i++) {
 * 
 * // also maybe checking old store of ones not to show but store for later use,
 * so if it pops up again no need to get new historical quote // maybe just
 * checking towards stored, and add to active which ones mathes, and get the
 * missing ones add to store replace active with temp temp gets all matches in
 * stored added to it, and all none matches gets retrieved and then added
 * 
 * if(entry.getKey().equalsIgnoreCase(stored.get(i).getSymbol()) )
 * temp.add(entry.getValue()); else if(i < stored.size()-1) { // get historical
 * quotes // new array or instance of data class to hold the data // add to temp
 * }
 * 
 * } } stored = temp;
 * 
 * // 3 parts, 1. get a list of stocks from screener or watchlist, // 2. compare
 * to stored and store it, // 3. retrieve formatted data for all that didn't
 * exist in store and print in 3 lines[date,data,empty,---] Other: Jnativehook
 * instead of periodic
 * 
 * Check if it exist in list and only get the ones that doesnt exist, maybe just
 * add or check to list as hashlist,' not needed when small but probably later
 * when big check and confirm the order of the list to match the order as
 * appeared in list on tradingview screener or watchlist or what it is -- with
 * data, calculate the relative data, format it and print it
 * 
 * 
 * 
 * 
 * 
 * List<HistoricalQuote> googleHistQuotes = intel.getHistory();
 * 
 * 
 * 
 * 
 * 
 * for(int i = 0; i < googleHistQuotes.size(); i++) {
 * System.out.println(googleHistQuotes.get(i).getDate().getTime());
 * System.out.println(googleHistQuotes.get(i).getLow());
 * System.out.println("-----------------------------"); }
 * 
 * }
 * 
 * 
 * 
 * }
 */