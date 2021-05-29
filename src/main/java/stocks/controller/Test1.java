package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;

import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.ChromeBot;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.system.Hotkey;
import stocks.model.utilities.UFilings;
import stocks.model.utilities.UIntegers;
import stocks.model.utilities.ULoggings;
import stocks.model.utilities.USort;
import stocks.model.utilities.USort.SortOrder;

public class Test1 {


    static ChromeBot tradingviewBot = new ChromeBot();
    static ChromeBot stocktwitsBot = new ChromeBot();
    static List<WebElement> el = new ArrayList<WebElement>();


    /* Initial info */
    //stocktwits info
    static List<String> watchers = new ArrayList<String>();
    // tradingview info
    static List<Map<Info, String>> stocks = new ArrayList<Map<Info, String>>();

    /* Seperated info */
    static Map<String, String> names = new LinkedHashMap<String, String>();
    static Map<String, Double> volumes = new LinkedHashMap<String, Double>();
    static Map<String, Double> volumeRevenues = new LinkedHashMap<String, Double>();
    static Map<String, Double> shares = new LinkedHashMap<String, Double>();
    static Map<String, Double> employees = new LinkedHashMap<String, Double>();
    static Map<String, Double> lasts = new LinkedHashMap<String, Double>();
    static Map<String, Double> procentages = new LinkedHashMap<String, Double>();
    static Map<String, Double> mkts = new LinkedHashMap<String, Double>();
    static Map<String, Double> highs = new LinkedHashMap<String, Double>();
    static Map<String, Double> differenceFromHigh = new LinkedHashMap<String, Double>();
    


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
	}
	, NativeKeyEvent.VC_F2);


	//----


	stocktwitsBot.start(PageLoadStrategy.NONE, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);



	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();

	String tradingviewUsername = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewUsername").get(0);
	String tradingviewPassword = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewPassword").get(0);
	CBTradingview.login(tradingviewBot, tradingviewUsername, tradingviewPassword);




	//https://www.youtube.com/watch?v=YDRNMAJo0MA&ab_channel=blondiebytesblondiebytes
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
		    Map<Info, String> tradingivewInfo = CBTradingview.getStockInfo
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
	
	
	Color[] colors = new Color[] {
		Colors.SILVER.getColorValue(),
		Colors.GRAY.getColorValue(),
		Colors.WHITE.getColorValue(),
		Colors.MAROON.getColorValue(),
		Colors.RED.getColorValue(),
		Colors.PURPLE.getColorValue(),
		Colors.FUCHSIA.getColorValue(),
		Colors.GREEN.getColorValue(),
		Colors.LIME.getColorValue(),
		Colors.OLIVE.getColorValue(),
		Colors.YELLOW.getColorValue(),
		Colors.NAVY.getColorValue(),
		Colors.BLUE.getColorValue(),
		Colors.TEAL.getColorValue(),
		Colors.AQUA.getColorValue()
	};

	int i = 0;
	int count = 1;
	for(Map<Info, String> stockDataMap : stocks) {
	    for (Map.Entry<Info, String> entry : stockDataMap.entrySet()) {
		
		// List distinctColors = getDistinctColors(int amount = stocks.size());
		
		/* 20 distinct + black/white distinct colors.
		 * 
		 * 			      Column
		 * times numbers like, (#number)ColoredText.
		 * 
		 */

		if(entry.getKey().name().equals("NAME")) {
		    names.put("(#"+count+")" + colors[i].asHex(), entry.getValue());
		    
		// map.put (color, value)
		
		} else if (entry.getKey().name().equals("MKT")) {
		    volumes.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("VOL")) {
		    highs.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("LAST")) {
		    shares.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("PROCENTAGE")) {
		    employees.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue().replaceAll("(|)", "")));
		} else if (entry.getKey().name().equals("SHARES")) {
		    lasts.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("EMPLOYEES")) {
		    procentages.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		} else if (entry.getKey().name().equals("HIGH")) {
		    mkts.put("(#"+count+")" + colors[i].asHex(), UIntegers.unfoldIntoNumber(entry.getValue()));
		}

	    }

	    
	    if(i == 19) {
		count++;
		i = 0;
		continue;
	    }
	    i++;
	}
	
	List<Double> lasts2 = new ArrayList<Double>();
	List<Double> highs2 = new ArrayList<Double>();
	List<Double> volumes2 = new ArrayList<Double>();

	for(Map.Entry<String, Double> entry : lasts.entrySet()) lasts2.add(entry.getValue()); 
	for(Map.Entry<String, Double> entry : highs.entrySet()) highs2.add(entry.getValue()); 
	for(Map.Entry<String, Double> entry : volumes.entrySet()) volumes2.add(entry.getValue()); 

	for(int n = 0; n < lasts2.size(); n++) {
	    volumeRevenues.put("(#"+count+")" + colors[i].asHex(), volumes2.get(n)*lasts2.get(n));
	    differenceFromHigh.put("(#"+count+")" + colors[i].asHex(), (highs2.get(n)-lasts2.get(n))/highs2.get(n)*100);
	}

    }



    static void sortInfo() {
	
	/* sorting maps */
	volumes = USort.mapSort(SortOrder.NORMAL, volumes);
	volumeRevenues = USort.mapSort(SortOrder.NORMAL, volumeRevenues);
	shares = USort.mapSort(SortOrder.REVERSE, shares);
	employees = USort.mapSort(SortOrder.NORMAL, employees);
	lasts = USort.mapSort(SortOrder.NORMAL, lasts);
	procentages = USort.mapSort(SortOrder.NORMAL, procentages);
	mkts = USort.mapSort(SortOrder.NORMAL, mkts);
	highs = USort.mapSort(SortOrder.NORMAL, highs);
	differenceFromHigh = USort.mapSort(SortOrder.MIDPOINT, differenceFromHigh);

    }

    static void printInfo() {
/* 
 * Springboot
 */
    }



}
