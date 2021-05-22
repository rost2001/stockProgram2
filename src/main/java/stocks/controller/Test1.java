package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;

import stocks.model.selenium.CBAvanza;
import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.ChromeBot;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.system.Hotkey;
import stocks.model.system.Stock;
import stocks.model.utilities.UFilings;
import stocks.model.utilities.UIntegers;
import stocks.model.utilities.ULoggings;
import stocks.model.utilities.USort;

public class Test1 {


    static ChromeBot tradingviewBot = new ChromeBot();
    static ChromeBot stocktwitsBot = new ChromeBot();
    static List<WebElement> el = new ArrayList<WebElement>();


    /* Initial info */
    //stocktwits info
    static List<String> watchers = new ArrayList<String>();
    // tradingview info
    static Map<Info, String> tradingivewInfo = null;
    static List<Map<Info, String>> stocks = new ArrayList<Map<Info, String>>();

    /* Seperated info */
    static List<String> names = new ArrayList<String>();
    static List<Double> volumes = new ArrayList<Double>();
    static List<Double> volumeRevenues = new ArrayList<Double>();
    static List<Double> shares = new ArrayList<Double>();
    static List<Double> employees = new ArrayList<Double>();
    static List<Double> lasts = new ArrayList<Double>();
    static List<Double> procentages = new ArrayList<Double>();
    static List<Double> mkts = new ArrayList<Double>();
    static List<Double> highs = new ArrayList<Double>();
    static List<Double> highDrops = new ArrayList<Double>();


    public static void main(String[] args) throws IOException, Exception {

	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();

	//----
	//----

	new Hotkey(() -> 
	{
	    tradingviewBot.close();
	    stocktwitsBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F4);


	new Hotkey(() -> 
	{
	    getInfo();
	    fixInfo();
	    sortInfo();
	    printInfo();
	}
	, NativeKeyEvent.VC_F2);


	//----


	stocktwitsBot.start(PageLoadStrategy.NONE, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);



	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();

	String tradingviewUsername = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewUsername").get(0);
	String tradingviewPassword = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewPassword").get(0);
	CBTradingview.login(tradingviewBot, tradingviewUsername, tradingviewPassword);





    }


    /* Print.
     * Calculate.
     * Sort.
     * Coloring.
     * PointSystem.
     * 
     * ** Adapt points, after winning and losing stocks, as they turn out, high/low points supposedly.
     * 
     * * get watchlist info.
     */


    /* Pattern and candles, and javafx, and draw on tradingview
     * https://stackoverflow.com/questions/55214628/draw-on-a-canvas-using-selenium --- draw on canvas
     * 
     * 
     */

    static void getInfo(){
	try {

	    // Info from tradingview chart page
	    el = CBTradingview.getWatchList(tradingviewBot);


	    for (WebElement element : el) {
		element.click();

		Thread.sleep(100);
		tradingivewInfo = CBTradingview.getStockInfo
			(
				tradingviewBot,
				Info.NAME,
				Info.PROCENTAGE, 
				Info.LAST, 
				Info.VOL, 
				Info.SHARES, 
				Info.MKT, 
				Info.EMPLOYEES,
				Info.HIGH
				);

		stocks.add(tradingivewInfo);
	    }


	    // Watchers info from stocktwits
	    for(Map<Info, String> information : stocks) {
		for (Map.Entry<Info, String> entry : information.entrySet()) {

		    if(entry.getKey().name().equals("NAME")) {
			watchers.add(CBStocktwits.getWatchers(stocktwitsBot, entry.getValue()));
		    }

		}

	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    static void fixInfo() {

	for(Map<Info, String> information : stocks) {
	    for (Map.Entry<Info, String> entry : information.entrySet()) {
		
		// color

		if(entry.getKey().name().equals("NAME")) {
		    names.add(entry.getValue());
		} else if (entry.getKey().name().equals("MKT")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("VOL")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("LAST")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("PROCENTAGE")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue().replaceAll("(|)", "")));
		} else if (entry.getKey().name().equals("SHARES")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("EMPLOYEES")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("HIGH")) {
		    mkts.add(UIntegers.unfoldIntoNumber(entry.getValue()));;
		}

	    }

	}


	for(int i = 0; i < names.size(); i++) {
	    volumeRevenues.add(volumes.get(i)*lasts.get(i));
	    highDrops.add(highs.get(i)-lasts.get(i));
	}


    }



    static void sortInfo() {

	Collections.sort(volumes);
	Collections.sort(volumeRevenues);
	Collections.sort(shares);
	Collections.sort(employees);
	Collections.sort(lasts);
	Collections.sort(procentages);
	Collections.sort(mkts);
	Collections.sort(highs);
	Collections.sort(highDrops);

	Collections.reverse(shares);
	highDrops = USort.midPointOutwardSort(highDrops);
	
    }

    static void printInfo() {
	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (InterruptedException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String out = "";

	//

	System.out.println(out);
    }



}
