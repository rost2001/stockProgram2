package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.ChromeBot;
import stocks.model.system.SHotkey;
import stocks.model.utilities.UFiling;
import stocks.model.utilities.ULogging;

public class JavascriptTest {


    static ChromeBot tradingviewBot = new ChromeBot();
    static List<WebElement> el = new ArrayList<WebElement>();

    public static void main(String[] args) throws IOException, Exception {
	ULogging.disableKeyLogging();
	ULogging.disableSlf4j();

	//----
	//----

	SHotkey h2 = new SHotkey(() -> 
	{
	    tradingviewBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F6);

	//----

	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();

	String tradingviewUsername = UFiling.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewUsername").get(0);
	String tradingviewPassword = UFiling.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt", "tradingviewPassword").get(0);
	CBTradingview.login(tradingviewBot, tradingviewUsername, tradingviewPassword);


	String xpath = "//div[contains(@class,'wrapper-3OdqYJdx')]";
	el = tradingviewBot.findElements(xpath);

	//tradingviewBot.removeElement(el.get(0));


	//el.get(0).sendKeys(Keys.CONTROL +"t");



	String tab1 = tradingviewBot.getHandle();
	String tab2 = tradingviewBot.newTab();

	tradingviewBot.switchTo(tab2);
	tradingviewBot.get("https://www.youtube.com/",0);

	tradingviewBot.switchTo(tab1);
	tradingviewBot.get("https://www.youtube.com/",0);



	System.out.println("Done!");
    }

}
