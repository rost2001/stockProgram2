package stocks.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.ChromeBot;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.system.SHotkey;
import stocks.model.utilities.UFilings;
import stocks.model.utilities.ULoggings;
import stocks.model.utilities.USort;
import stocks.model.utilities.USort.SortOrder;
import stocks.model.utilities.UStrings;

@SpringBootApplication
@RestController
public class Test1{


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
    static Map<String, BigDecimal> volumes = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> volumeRev = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> shares = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> employees = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> lasts = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> procentages = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> mkts = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> highs = new LinkedHashMap<String, BigDecimal>();
    static Map<String, BigDecimal> highDiff = new LinkedHashMap<String, BigDecimal>();
    static Map<String, String> watchersMap = new LinkedHashMap<String, String>();

    
    /* Points */
    static Map<String, Integer> pointsMap = new LinkedHashMap<String, Integer>();


    public static void main(String[] args)  throws IOException, Exception  {



	//----
	//----

	SpringApplication.run(Test1.class, args);

	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();
	//Thread.sleep(10000);

	new SHotkey(() -> 
	{
	    tradingviewBot.close();
	    stocktwitsBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F4);



	//----


	stocktwitsBot.start(PageLoadStrategy.NONE, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);



	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();

	String tradingviewUsername = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewUsername").get(0);
	String tradingviewPassword = UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewPassword").get(0);
	CBTradingview.login(tradingviewBot, tradingviewUsername, tradingviewPassword);


	getInfo();
	seperateInfo();
	sortInfo();
	calculatePoints();

	System.out.println("Donqwee!");

    }





    static void getInfo(){
	try {

	    // Info from tradingview chart page
	    el = CBTradingview.getWatchList(tradingviewBot);


	    for (int i = 0; i < 10; i++) {
		if(i >= el.size()) break;

		el.get(i).click();
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

    static void seperateInfo() {


	Color[] colors = new Color[] {
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
	for(Map<Info, String> stockDataMap : stocks) {
	    for (Map.Entry<Info, String> entry : stockDataMap.entrySet()) {


		/* Sepererar infon i olika maps,
		 * lägger till en färg,
		 * tar också bort T,B,M,K förkortningen så att de kan bli sorterade,
		 */
		if(entry.getKey().name().equals("NAME")) {
		    names.put(colors[i].asHex(), entry.getValue());

		} else if (entry.getKey().name().equals("MKT")) {
		    BigDecimal mk = new BigDecimal(UStrings.unfoldNumber(entry.getValue().replace("—", "0")));
		    mkts.put(colors[i].asHex(), mk);

		} else if (entry.getKey().name().equals("VOL")) {
		    BigDecimal vol = new BigDecimal(UStrings.unfoldNumber(entry.getValue()));
		    volumes.put(colors[i].asHex(), vol);

		} else if (entry.getKey().name().equals("LAST")) {
		    BigDecimal la = new BigDecimal(UStrings.unfoldNumber(entry.getValue()));
		    lasts.put(colors[i].asHex(), la);

		} else if (entry.getKey().name().equals("PROCENTAGE")) {
		    BigDecimal proc = new BigDecimal(UStrings.unfoldNumber(entry.getValue().replaceAll("[()%]", "")));
		    procentages.put(colors[i].asHex(), proc);

		} else if (entry.getKey().name().equals("SHARES")) {
		    BigDecimal sh = new BigDecimal(UStrings.unfoldNumber(entry.getValue().replace("—", "0")));
		    shares.put(colors[i].asHex(), sh);

		} else if (entry.getKey().name().equals("EMPLOYEES")) {
		    BigDecimal emp = new BigDecimal(UStrings.unfoldNumber(entry.getValue().replace("—", "0")));
		    employees.put(colors[i].asHex(), emp);

		} else if (entry.getKey().name().equals("HIGH")) {
		    BigDecimal hi = new BigDecimal(UStrings.unfoldNumber(entry.getValue()));
		    highs.put(colors[i].asHex(), hi);
		}

		watchersMap.put(colors[i].asHex(), watchers.get(i));
	    }


	    if(i == 18) {
		i = 0;
		continue;
	    }
	    i++;
	}

	/* Räknar ut volym omsättning,
	 * och skillnaden från högsta topp,
	 * 
	 */
	List<BigDecimal> lasts2 = new ArrayList<BigDecimal>();
	List<BigDecimal> highs2 = new ArrayList<BigDecimal>();
	List<BigDecimal> volumes2 = new ArrayList<BigDecimal>();

	for(Map.Entry<String, BigDecimal> entry : lasts.entrySet()) lasts2.add(entry.getValue()); 
	for(Map.Entry<String, BigDecimal> entry : highs.entrySet()) highs2.add(entry.getValue()); 
	for(Map.Entry<String, BigDecimal> entry : volumes.entrySet()) volumes2.add(entry.getValue()); 

	i = 0;
	for(int n = 0; n < lasts2.size(); n++) {

	    BigDecimal volrev = volumes2.get(n).multiply(lasts2.get(n));
	    volumeRev.put(colors[i].asHex(), volrev);
	    BigDecimal highDist = highs2.get(n).subtract(lasts2.get(n));
	    highDist = highDist.divide(highs2.get(n), MathContext.DECIMAL128);
	    highDist = highDist.multiply(new BigDecimal("100"), MathContext.DECIMAL128);
	    highDiff.put(colors[i].asHex(), highDist);
	    if(i == 19) {
		i = 0;
		continue;
	    }
	    i++;
	}

    }




    static void sortInfo() {

	/* sorting maps */
	volumes = USort.mapSort(SortOrder.REVERSE, volumes);
	volumeRev = USort.mapSort(SortOrder.REVERSE, volumeRev);
	shares = USort.mapSort(SortOrder.NORMAL, shares);
	employees = USort.mapSort(SortOrder.REVERSE, employees);
	lasts = USort.mapSort(SortOrder.REVERSE, lasts);
	procentages = USort.mapSort(SortOrder.REVERSE, procentages);
	mkts = USort.mapSort(SortOrder.REVERSE, mkts);
	highs = USort.mapSort(SortOrder.REVERSE, highs);
	highDiff = USort.mapSort(SortOrder.MIDPOINT, highDiff);
	watchersMap = USort.mapSort(SortOrder.REVERSE, watchersMap);
    }

    
    static void calculatePoints() {
	

	/* Räknar poäng för varje column,
	 * 9p för längst upp, 0p om längst ned,
	 * läggs tillsammans med namnet i en map,
	 * 
	 */
		for (Map.Entry<String, String> entry : names.entrySet()) {

		    int points = 0; 
		    int i = 0;
		    for (Map.Entry<String, BigDecimal> entry2 : volumes.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : volumeRev.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : shares.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : employees.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : lasts.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : procentages.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : mkts.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : highs.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, BigDecimal> entry2 : highDiff.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    for (Map.Entry<String, String> entry2 : watchersMap.entrySet()) {
			
			if (entry.getKey().equals(entry2.getKey()))
			    break;
			
			i++;
		    }
		    points += 9 - i; i = 0;
		    
		    pointsMap.put(entry.getValue(), points);
		
	    }
	
    }

    @GetMapping("/")
    static String printInfo() {




	String str = "";

	// start grejjer
	str += "<!DOCTYPE html>\r\n" + 
		"<html xmlns:th\"https://www.thymeleaf.org\">\r\n" + 
		"\r\n" + 
		"<head>\r\n" + 
		"	<meta charset=\"UTF-8\">\r\n" + 
		"	<title>Title</title>\r\n" + 
		"	<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x\" crossorigin=\"anonymous\">\r\n" + 
		"</head>\r\n" + 
		"\r\n" + 
		"<body>";


	// table data
	str += "	<table class=\"table table-dark table-striped\">\r\n" + 
		"		<tr>";
	List<Map<?, ?>> data = Arrays.asList(
		names, volumes, volumeRev, 
		shares, employees, lasts, 
		procentages,mkts, highs, 
		highDiff, watchersMap);
	List<String> columnNames = Arrays.asList(
		"Names", "Volume", "Volume Rev",
		"Shares", "Employees", "Lasts",
		"Procentage", "Mkt", "High",
		"High Diff", "Watchers");

	for (int i = 0; i < data.size(); i++) {
	    str += "			<td>\r\n" + 
		    "				<table>\r\n" + 
		    "					<thead>\r\n" + 
		    "						<tr>\r\n" + 
		    "							<td>"+columnNames.get(i)+"</td>\r\n" + 
		    "						</tr>\r\n" + 
		    "					</thead>\r\n" + 
		    "					<tbody>";

	    for (Map.Entry<?, ?> entry : data.get(i).entrySet()) {

		if(i == data.size()-1) {
		    str += "	<tr>\r\n" + 
			    "	<td style=\"color:"+entry.getKey()+"\">"+entry.getValue()+"</td>\r\n" + 
			    "	</tr>";
		} else if (i == 0) {

		    str += "	<tr>\r\n" + 
			    "	<td style=\"color:"+entry.getKey()+"\">"+entry.getValue()+ " ("+pointsMap.get(entry.getValue())+"p)" +"</td>\r\n" + 
			    "	</tr>";
		} else if (i == 6) {

		    str += "	<tr>\r\n" + 
			    "	<td style=\"color:"+entry.getKey()+"\">"+UStrings.round(UStrings.foldNumber(((BigDecimal)entry.getValue()).toPlainString()),2)+"%"+"</td>\r\n" + 
			    "	</tr>";
		} else if (i == 9) {

		    str += "	<tr>\r\n" + 
			    "	<td style=\"color:"+entry.getKey()+"\">"+"-"+UStrings.round(UStrings.foldNumber(((BigDecimal)entry.getValue()).toPlainString()),2)+"%"+"</td>\r\n" + 
			    "	</tr>";
		} else {

		    str += "	<tr>\r\n" + 
			    "	<td style=\"color:"+entry.getKey()+"\">"+UStrings.round(UStrings.foldNumber(((BigDecimal)entry.getValue()).toPlainString()),2)+"</td>\r\n" + 
			    "	</tr>";
		}
	    }

	    str += "					</tbody>\r\n" + 
		    "				</table>\r\n" + 
		    "			</td>";

	}

	// slut grejjer
	str += "		</tr>\r\n" + 
		"	</table>\r\n" + 
		"\r\n" + 
		"</body>\r\n" + 
		"\r\n" + 
		"</html>";

	return str;
    }

}
