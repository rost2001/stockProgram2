package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;

import stocks.model.selenium.CBAvanza;
import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.selenium.ChromeBot;
import stocks.model.system.SHotkey;
import stocks.model.system.SStock;
import stocks.model.utilities.UFiling;
import stocks.model.utilities.ULogging;

public class Test2 {



    static ChromeBot tradingviewBot = new ChromeBot();
    static ChromeBot buyBot = new ChromeBot();


    static List<WebElement> el = new ArrayList<WebElement>();


    static StringBuffer currentSymbol = new StringBuffer("Hello");

    public static void main(String[] args) throws IOException, Exception {

	ULogging.disableKeyLogging();
	ULogging.disableSlf4j();

	//----
	//----

	SHotkey h2 = new SHotkey(() -> 
	{
	    tradingviewBot.close();
	    buyBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F6);

	//----


	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();
	
	String tradingviewUsername = UFiling.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewUsername").get(0);
	String tradingviewPassword = UFiling.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewPassword").get(0);
	CBTradingview.login(tradingviewBot, tradingviewUsername, tradingviewPassword);

	//-

	buyBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	CBAvanza.loginToAvanza(buyBot);


	SStock.checkSymbol(250, currentSymbol, (String newSymbol) -> 
	{
	    
	    CBAvanza.getStockBuyPage(buyBot, newSymbol);
	    
	});


    }
}