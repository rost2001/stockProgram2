package stocks.testing_examples;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.awaitility.Awaitility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import stocks.ChromeBot;
import stocks.image.processing.ocr.MainOcrImage;
import stocks.system.WindowsNative;

public class MainTesting {

   // https://docs.oracle.com/javase/specs/jls/se8/html/jls-1.html
	    
	    public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	    { 



		
		ChromeBot bot = new ChromeBot();
		bot.start(ChromeBot.useragent, ChromeBot.defaultWindowSize);
		

		
		System.out.println("Please start your BankID app...");
		
		//bot.loginToAvanza();
		
		
		System.out.println("Logged in");
		
		
		bot.driver.get("https://www.avanza.se/min-ekonomi/oversikt.html");
		
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
			do {continue;} while (bot.findElements("//aza-account").size() == 0);return true;
		});

		List<WebElement> el = bot.findElements("//aza-account");
		
		for(WebElement element : el) {
		    System.out.println("0: "+element.getAttribute("innerText").split("\n")[0]);
		    System.out.println("1: "+element.getAttribute("innerText").split("\n")[1]);
		    System.out.println("2: "+element.getAttribute("innerText").split("\n")[2]);
		    System.out.println("3: "+element.getAttribute("innerText").split("\n")[3]);
		    
		}
		
		//--------------------------------------------------------------
		
		bot.driver.get("https://www.avanza.se/ab/sok/inline?query=jagx");
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
			do {continue;} while (bot.findElements("//a").size() == 0);return true;
		});
		
		el = bot.findElements("//a");
		el.get(1).sendKeys(Keys.ENTER);
		
		

		
		Thread.sleep(10000);
		bot.driver.quit();
		}

	    
	    }




