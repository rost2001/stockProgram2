package stocks.model.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class CBAvanza {
    
    private CBAvanza(){}
    
    public static void loginToAvanza(ChromeBot bot)  throws Exception{

 	    bot.get("https://www.avanza.se");
 	    // godk�nna cookies knapp
 	    List<WebElement> el = new ArrayList<WebElement>();
 	    el = bot.findElements("//button[contains(@class,'ng-tns-c44-3 aza-button cta-primary')]");
 	    el.get(0).sendKeys(Keys.ENTER);

 	    // login in knapp
 	    el = bot.findElements("//button[contains(@class,'aza-button aza-text-button aza-text-primary ng-star-inserted')]");
 	    el.get(0).sendKeys(Keys.ENTER);

 	    // bankid
	    el = bot.findElements("//button[contains(@class,'aza-button cta-secondary')]");
	    el.get(0).sendKeys(Keys.ENTER);

 	    // Kollar sidan efter inloggning
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (bot.driver.getCurrentUrl().equalsIgnoreCase("https://www.avanza.se/hem/senaste.html")); return true;});
	    

     }

}
