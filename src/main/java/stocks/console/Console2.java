package stocks.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import stocks.ChromeBot;
import stocks.Stock;
import stocks.interfaces.StockStateListener;

public class Console2 implements StockStateListener, NativeKeyListener {
    
    
    static Stock viewingStock;

    static ChromeBot buyBot = new ChromeBot();
    static ChromeBot newsBot = new ChromeBot();
    
    public static void main( String... args ) {
	

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
	
	
	// buy and sell update
	// news + 15 recent stocks alert if they drop 10% within 5 min 
	// Alerta på nya stora som rör sig snabbt upp
	
	
	newsBot.start(ChromeBot.useragent, ChromeBot.defaultWindowSize, ChromeBot.headless);


	
	System.out.print("Starting Buy bot...");
	buyBot.start(ChromeBot.useragent, ChromeBot.defaultWindowSize);
	System.out.print("Trying to log in, please start BankID app...");
	buyBot.loginToAvanza("");
	System.out.println("OK!");



	
	viewingStock = new Stock("AAPL");

	viewingStock.updatePrice(250, true, new Console2());
	viewingStock.updateSymbol(20, new Console2());

	
	System.out.println(viewingStock.symbol);
    }

    @Override
    public void onNewPrice(double price) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onNewSymbol(String symbol) {

	    List<WebElement> el = new ArrayList<WebElement>();
	    List<WebElement> elTimeStamps = new ArrayList<WebElement>();

	try {


	    
	    // Aktier
	    buyBot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + symbol);


	    el = buyBot.findElements("//a");
	    el.get(1).sendKeys(Keys.ENTER);
	     



	    newsBot.driver.get("https://www.nasdaq.com/market-activity/stocks/" + symbol);

	    try {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    } catch (InterruptedException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }


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

   
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
	if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
	  //  buyBot.close();
	    newsBot.close();
	    System.exit(0); // Terminate program
	}
	
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
	// TODO Auto-generated method stub
	
    }

}