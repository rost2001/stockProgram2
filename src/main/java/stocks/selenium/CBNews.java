package stocks.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class CBNews {

    private CBNews(){}


    public static void getStocktwits(ChromeBot bot, String symbol) throws Exception{
	List<WebElement> el = new ArrayList<WebElement>();
	bot.get("https://stocktwits.com/symbol/" + symbol);

	el = bot.findElements("//button[contains(@class,'osano-cm-accept-all osano-cm-buttons__button osano-cm-button osano-cm-button--type_accept')]");
	el.get(0).click();

    }


    public static List<String> getSecFilings(ChromeBot bot, String symbol) throws Exception{

	bot.get("https://stocktwits.com/symbol/" + symbol);

	return null;
    }

    public static void getYahoo(ChromeBot bot, String symbol) throws Exception {
	List<WebElement> el = new ArrayList<WebElement>();
	bot.get("https://finance.yahoo.com/quote/" + symbol);
	el = bot.findElements("//button[contains(@class,'btn primary')]");
	el.get(0).click();

    }

}
