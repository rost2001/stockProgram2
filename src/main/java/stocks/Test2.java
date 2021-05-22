package stocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;

import stocks.selenium.CBTradingview;
import stocks.selenium.ChromeBot;
import stocks.selenium.CBTradingview.Info;
import stocks.system.Hotkey;
import stocks.utils.UFilings;
import stocks.utils.ULoggings;

public class Test2 {


    static ChromeBot tradingviewBot = new ChromeBot();
    
    public static void main(String[] args) throws IOException, Exception {
	

	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();

	//----
	//----
	
	
	Hotkey h2 = new Hotkey(() -> 
	{
	    tradingviewBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_ESCAPE);

	//----


	tradingviewBot.start(PageLoadStrategy.NORMAL, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	CBTradingview.login(
		tradingviewBot
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewUsername").get(0)
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewPassword").get(0)
		);
	
	List<WebElement> el = new ArrayList<WebElement>();
	el = CBTradingview.getWatchList(tradingviewBot);
	el.get(0).click();
	
	
	
	Thread.sleep(1000);

	String[] info = null;
	
	//info = CBTradingview.getStockInfo(tradingviewBot, Info.NAME, Info.EMPLOYEES, Info.PROCENTAGE, Info.SHARES, Info.MKT);
	
	for (String str : info) {
	    System.out.println(str);
	}
	
    }

}
