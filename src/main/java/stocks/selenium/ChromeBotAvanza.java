package stocks.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ChromeBotAvanza {
    
    private ChromeBotAvanza(){}
    
    public static void loginToAvanza(ChromeBot bot, String personnummer)  throws Exception{

 	    bot.get("https://www.avanza.se");
 	    // godkänna cookies knapp
 	    List<WebElement> el = new ArrayList<WebElement>();
 	    el = bot.findElements("//button[contains(@class,'ng-tns-c44-3 aza-button cta-primary')]");
 	    el.get(0).sendKeys(Keys.ENTER);

 	    // login in knapp
 	    el = bot.findElements("//button[contains(@class,'aza-button aza-text-button aza-text-primary ng-star-inserted')]");
 	    el.get(0).sendKeys(Keys.ENTER);

 	    // personnummer fält
 	    el = bot.findElements( "/html/body/aza-app/aza-right-overlay-area/aside/ng-component/aza-login-overlay/aza-right-overlay-template/main/div/aza-login/div/aza-toggle-switch-view/div/aza-bank-id/form/div[1]/div/input");
 	    el.get(0).sendKeys(personnummer);

 	    // mobilt bankid knapp
 	    el = bot.findElements( "/html/body/aza-app/aza-right-overlay-area/aside/ng-component/aza-login-overlay/aza-right-overlay-template/main/div/aza-login/div/aza-toggle-switch-view/div/aza-bank-id/form/div[1]/div/button[1]");
 	    el.get(0).sendKeys(Keys.ENTER);

 	    // Kollar sidan efter inloggning
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (bot.driver.getCurrentUrl().equalsIgnoreCase("https://www.avanza.se/hem/senaste.html")); return true;});
	    

     }

}
