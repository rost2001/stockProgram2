package stocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import stocks.data.FakeOrder;
import stocks.data.Order;
import stocks.data.Overview;
import stocks.data.Position;
import stocks.data.Stock;
import stocks.data.interfaces.OrderStateListener;
import stocks.data.interfaces.StockStateListener;
import stocks.selenium.ChromeBot;
import stocks.selenium.ChromeBotAvanza;
import stocks.selenium.ChromeBotStocktwits;
import stocks.selenium.ChromeBotTradingview;

public class Console2 implements StockStateListener, NativeKeyListener, OrderStateListener {
    
    
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
    
    static Console2 console2 = new Console2();
    static Stock viewingStock;

    static ChromeBot buyBot = new ChromeBot();
    static ChromeBot newsBot2 = new ChromeBot();
    static ChromeBot newsBot3 = new ChromeBot();
    
    
    
    static Overview overview = new Overview();
    static Position currentPosition = new Position();
    
    static double startingCapital = 100;
    static double currentCapital = 100;
    static double fees = 0.0;
    static double profit = 0.0;
    
    public static void main( String... args ) throws Exception {
	

	// Disable slf4j Logging
	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

	// Disables Top4j logging
	// Clear previous logging configurations.
	LogManager.getLogManager().reset();

	// Get the logger and turns it off
	Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	logger.setLevel(Level.OFF);
	
	
	try {
	    GlobalScreen.registerNativeHook(); // Connecting system libs with org.jnativehook lib
	} catch (NativeHookException ex) {
	    System.err.println("There was a problem registering the native hook.");
	    System.err.println(ex.getMessage());

	    System.exit(1);
	}

	GlobalScreen.addNativeKeyListener(new Console2()); // adding this class to be used when an key event happens

   
	
	//--------------------------------------------------------------------------------------
	

	newsBot2.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	newsBot3.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	
	
	newsBot2.driver.get("https://stocktwits.com/symbol/" + "AAPL");
	List<WebElement> el = new ArrayList<WebElement>();
	el = newsBot2.findElements("//button[contains(@id,'onetrust-accept-btn-handler')]");
	el.get(0).click();
	
	
	newsBot3.driver.get("https://finance.yahoo.com/quote/" + "AAPL");
	el = newsBot3.findElements("//button[contains(@class,'btn primary')]");
	el.get(0).click();


	System.out.print("Starting Buy bot...");
	buyBot.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	System.out.print("Trying to log in, please start BankID app...");
	
	
	ChromeBotAvanza.loginToAvanza(buyBot);
	
	
	System.out.println("OK!");


	
	viewingStock = new Stock("AAPL");

	viewingStock.updatePrice(250, true, new Console2());
	viewingStock.updateSymbol(100, new Console2());

	
	
	// timer 1-2 sec or every 30 sec
	// check all-above 15% & if any unmarked -> clear orange list-mark all return as orange
	// steps:
	/* 1. retrieve list of screener, 
	 *    foreach
	 *    check chg% 10% & open% 15% above (choose one)
	 *    return all above
	 *     
	 * 2. press button and press clear list and click ok
	 * 3. Mark all that is in returned list
	 * 
	 */
	
	
	// My own watchlist display:
	/*
	 * Displays watchlist or colored watchlist
	 * Add stocks from screen to watchlist
	 * Display stats from stocktwits, message count and watchers
	 * Display average move in % per day for the 5,10,20 days
	 * 
	 */
	
	
	// buy and sell update
	// news + 15 recent stocks alert if they drop 10% within 5 min 
	// Alerta på nya stora som rör sig snabbt upp
	
	    // non pro window update symbol
	    // quick sell, with manuell order checking
	    // quick alert on new very big movers
	    // news page update
	
	
	
	/*
	 * Check all above 7% in chg% and open%
	 * for each ->
	 * check stocktwits watchers and pressreleases on yahoo maybe, https://seekingalpha.com/symbol/SNDL maybe
	 * Get average gain or move per day on 5,10,20 days, yahoo historical quotes and date
	 * or from high of day to high of next day
	 * or from low of day to high of day, and listing 10 numbers for 10 days
	 * 
	 * 
	 * Modify tradingview price section, to show price and then news only
	 * https://stackoverflow.com/questions/8473024/selenium-can-i-set-any-of-the-attribute-value-of-a-webelement-in-selenium
	 * 
	 * WebDriver driver; // Assigned elsewhere
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("document.getElementById('//id of element').setAttribute('attr', '10')");


WebElement element = ...
((JavascriptExecutor)driver).executeScript(
  "var ele=arguments[0]; ele.innerHTML = 'my new content';", element);
  
  
  var myobj = document.getElementById("demo");
myobj.remove();
  
  which means also listener is possible this way
  
  
  Have stocks shown on tradingview aswell, with averages, and clickable link to change stocks by triggering the right script on the page 
	 */
    }

    @Override
    public void onNewPrice(double price) {

    }

    @Override
    public void onNewSymbol(String symbol) {

	    List<WebElement> el = new ArrayList<WebElement>();
	    List<WebElement> elTimeStamps = new ArrayList<WebElement>();

	try {


	    
	    Thread thread1 = new Thread() {
		    public void run() {
			    // Aktier
			    buyBot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + symbol);

			    List<WebElement> el = new ArrayList<WebElement>();
			    try {
				el = buyBot.findElements("//a");
			    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    el.get(1).sendKeys(Keys.ENTER);
		    }  
		};

		thread1.start();

	     
	    
	    
	    Thread thread2 = new Thread() {
		    public void run() {
			    newsBot2.driver.get("https://stocktwits.com/symbol/" + symbol);
			
		    }  
		};

		thread2.start();

		
		
	  Thread  thread3 = new Thread() {
		    public void run() {
		        newsBot3.driver.get("https://finance.yahoo.com/quote/" + symbol);
		    }  
		};

		thread3.start();

	    
	    
	    consoleRefresh();
/*
	    newsBot.driver.get("https://www.nasdaq.com/market-activity/stocks/" + symbol);

	
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	

	    // news
	    el = newsBot.findElements("//a[contains(@class,'news-two-column__link')]");
	    // date
	    elTimeStamps = newsBot.findElements("//div[contains(@class,'news-two-column__card-meta')]");
	    
	    // for every news or for 3 pieces of news right now
	    for(int i = 0; i < 3; i++) {

		// news
		List<String> unwantedNews = Arrays.asList("Week Ahead",
			"STOCKS ON THE MOVE",
			"Health Care Sector Update");
		el = removeUnwantedNews(unwantedNews, el);
		List<String> words = ChromeBot.getWords(el.get(i));
		words = wordWrap(words, 40);

		// date
		String timeStamp = elTimeStamps.get(i).getAttribute("innerText");





		System.out.println("----------------------");
		System.out.println(symbol + " * " + timeStamp.replace("•", "*"));
		System.out.println("----");

		for(String str : words)
		    System.out.print(str);
		System.out.println("\n----------------------");

	    }
*/

	}catch(Exception e) {
	    return;
	}


    }

    
    public List<WebElement> removeUnwantedNews(List<String> unwantedNews, List<WebElement> news){
	List<WebElement> result = new ArrayList<WebElement>();
	
	firstLoop:
	for(int i = 0; i < news.size(); i++) {
	 
	    for(int n = 0; n < unwantedNews.size(); n++) {
		if(news.get(i).getAttribute("innerText").toLowerCase().contains(unwantedNews.get(n).toLowerCase())) {
		    
		    continue firstLoop;
		}
	    }
	    
	    result.add(news.get(i));
	}
	
	return result;
    }
 
    public List<String> wordWrap(List<String> words, int lengthInCharacters) {
	List<String> wrappedWords = new ArrayList<String>();
	int wordLengthCount = 0;
	for (int i = 0; i < words.size(); i++) {
	    
	    	wordLengthCount += words.get(i).length() + 1;
		wrappedWords.add(words.get(i) + " ");
		
		if(i+1 != words.size() && wordLengthCount + words.get(i+1).length() > 40) {
		    wrappedWords.add(words.get(i) + "\n");
		    wrappedWords.remove(i);
		    wordLengthCount = 0;
		}
	}
	
	return wrappedWords;
    }
    
    

    static void consoleRefresh(){

	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (InterruptedException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	String out = "";

	out += ("___________________________________");
	
	if(currentPosition.openingOrder == null)
	    profit = currentCapital - startingCapital;
	
	out += ("\n|Profit: " + String.format("%.2f", profit - fees) + " USD");
	out += ("\n|Accumulated fees: " + String.format("%.2f", fees) + " USD"); // Rounds to 0 decimals
	out += ("\n|Starting Capital: " + String.format("%.2f", startingCapital));
	out += ("\n|Current Capital: " + String.format("%.2f", currentCapital));
		if(currentPosition.openingOrder != null)
		    out += (" + " + currentPosition);
		    
	out += ("\n|__________________________________");
	out += ("\n|Position: ");
	out += ("\n|__________________________________");

	for (int i = 0; i < overview.positions.size(); i++) {

	    out += ("\n|__________________________________");
	    out += ("\n|Buy Order: #" + (i+1) + "   (Completed/Pending)");
	    out += ("\n|symbol: " + overview.positions.get(i).openingOrder.stock.symbol);
	    out += ("\n|price: " + overview.positions.get(i).openingOrder.price + " USD");
	    out += ("\n|capital: " + String.format("%.2f", overview.positions.get(i).openingOrder.capital) + " USD");
	    out += ("\n|shares: " + overview.positions.get(i).openingOrder.shares + "st");


	    double profitPoints = overview.positions.get(i).openingOrder.stock.price - overview.positions.get(i).openingOrder.price;
	    double profitProcentage = (overview.positions.get(i).openingOrder.stock.price / overview.positions.get(i).openingOrder.price - 1)*100;

	    out += ("\n|------|");
	    out += ("\n|Current Price: " + overview.positions.get(i).openingOrder.stock.price);
	    
	    if(profitProcentage > 0) { //green

		out += ("\n|Profit:  " + ANSI_CYAN + String.format("%.2f", profitPoints) + ANSI_RESET + " (" + ANSI_CYAN +String.format("%.2f", profitProcentage) + "%" + ANSI_RESET + ")");
	    }else { //red

		out += ("\n|Profit:  " + ANSI_RED + String.format("%.2f", profitPoints) + ANSI_RESET + " (" + ANSI_RED +String.format("%.2f", profitProcentage) + "%" + ANSI_RESET + ")");
	    }
	    
	    out += ("\n|Capital: " + String.format("%.2f", overview.positions.get(i).openingOrder.stock.price*overview.positions.get(i).openingOrder.shares) + " USD");
	    out += ("\n|__________________________________");

	    
	}
	out += ("\n|" + viewingStock.symbol);
	out += ("\n|__________________________________");
	System.out.println(out);
    }



    boolean ctrlPressed = false;
    boolean altPressed = false;

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = true;
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = true;


	

	if(e.getKeyCode() == NativeKeyEvent.VC_F4) {
	    
	    Stock stock = new Stock(viewingStock.symbol, viewingStock.price);
	    stock.updatePrice(250, true, this);
	    FakeOrder buy = new FakeOrder(stock, currentCapital, 0, viewingStock.price, null, Order.Types.BUY, overview, currentPosition, this);
	    
	    currentPosition = new Position();
	    currentPosition.open(buy);
	    
	    overview.positions.add(currentPosition);
	    
	    currentCapital -= buy.capital;
	}

	
	if(e.getKeyCode() == NativeKeyEvent.VC_F2) {
	    // https://stackoverflow.com/questions/17758411/java-creating-a-new-thread/17758416
	    Stock stock = new Stock(currentPosition.openingOrder.stock.symbol, currentPosition.openingOrder.stock.price);
	    stock.updatePrice(250, true, this);
	    FakeOrder sell = new FakeOrder(stock, currentPosition.openingOrder.capital, currentPosition.openingOrder.shares, viewingStock.price, null, Order.Types.SELL, overview, currentPosition, this);
	    
	    currentPosition.close(sell);
	    
	    overview.positions.remove(currentPosition);
	    fees += sell.fee;
	    fees += currentPosition.openingOrder.fee;
	    
	    currentCapital += sell.capital;
	    currentPosition = new Position();
	}




	if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
	    buyBot.close();
	    newsBot2.close();
	    newsBot3.close();
	    System.exit(0); // Terminate program
	}

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = false;
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = false;
    }

    @Override
    public void onOrderPending(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderCompletion(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderRemoval(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderRemovalFailure(Order order) {
	// TODO Auto-generated method stub
	
    }



}