package stocks.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class CBStocktwits {
    
    private CBStocktwits(){}
    
    public static int getWatchers(ChromeBot bot, String symbol) throws Exception{

 	    bot.get("https://stocktwits.com/symbol/" + symbol);
 	    
 	    // godkänna cookies knapp
 	    List<WebElement> el = new ArrayList<WebElement>();
 	    el = bot.findElements("//div[contains(@class,'st_HebiDD2 st_yCtClNI st_2mehCkH st_3PPn_WF st_jGV698i st_PLa30pM st_2HqScKh')]");
 	    int watchers = Integer.parseInt(el.get(0).getAttribute("innerText").replaceAll(",| ", "")); 

 
 	    return watchers;
     }

}
