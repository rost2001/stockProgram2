package stocks.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class ChromeBotTradingview {

    private ChromeBotTradingview(){}



    public static void login(ChromeBot bot, String email, String password) throws Exception {
	bot.get("https://www.tradingview.com/");

	List<WebElement> el = new ArrayList<WebElement>();

	// cookie knapp
	el = bot.findElements("//button[contains(@class,'button-1iktpaT1 size-s-3mait84m intent-primary-1-IOYcbg appearance-default-dMjF_2Hu')]");
	el.get(0).click();


	// sign in
	el = bot.findElements("//a[contains(@class,'tv-header__link tv-header__link--signin js-header__signin')]");
	el.get(0).click();

	// email
	el = bot.findElements("//span[contains(@class,'tv-signin-dialog__social tv-signin-dialog__toggle-email js-show-email')]");
	el.get(0).click();


	// username/email
	el = bot.findElements("//input[contains(@class,'tv-control-material-input tv-signin-dialog__input tv-control-material-input__control')]");
	el.get(0).sendKeys(email);

	// password
	//el = bot.findElements("//input[contains(@class,'tv-control-material-input tv-signin-dialog__input tv-control-material-input__control')]");
	el.get(1).sendKeys(password);

	// sign in
	el = bot.findElements("//button[contains(@class,'tv-button tv-button--size_large tv-button--primary tv-button--loader')]");
	el.get(0).click();

	// wait a little to ensure it login in first, and then go into chart page
	Thread.sleep(1000);
	bot.get("https://www.tradingview.com/chart/");
    }
    
    /* Returns a watchlist as web elements, 1 per symbol, ordered as in watchlist
     * Expecting: watchlist is showing
     */
    public static List<WebElement> getWatchList(ChromeBot bot, String name) throws Exception{
	
	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();
	String xpathWatchlist = "//div[contains(@class,'wrap-ZwpHWy6f')]";
	//String xpathWatchlistOpen = "//div[contains(@class,'button-3SuA46Ww isTab-1dbyVeUX isActive-1D4aU96I isGrayed-3O5VgbN4 apply-common-tooltip common-tooltip-vertical')]";
	String xpathWatchlistClosed = "//div[contains(@class,'button-3SuA46Ww isTab-1dbyVeUX apply-common-tooltip common-tooltip-vertical')]";
	
	// watchlist is not open
	if(bot.elementExist(xpathWatchlistClosed)) {
	    bot.findElements(xpathWatchlistClosed).get(0).click();
	} 
	
	el = bot.findElements(xpathWatchlist);
	return el;
    }

    /* Returns a stockscreener list of stocks as elements, 1 per symbol, ordered as stockscreener
     * Expecting: stockscreener is showing
     */
    enum ColumnFilter{
        BY_CHG,
        BY_OPEN,
        BY_PREMARKET;
    }
    public static List<WebElement> getStockScreenerList(ChromeBot bot, String name, ColumnFilter col) throws Exception{
	
	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();
	String xpathScreenerList = "//tr[contains(@class,'tv-data-table__row tv-data-table__stroke tv-screener-table__result-row')]";
	//String xpathScreenerOpen = "//div[contains(@class,'tab-1fqQ8BUy apply-common-tooltip active-3fnMHeSg')]";
	String xpathScreenerClosed = "//div[contains(@class,'tab-1fqQ8BUy apply-common-tooltip')]";
	String xpathChg = "//div[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-change tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	String xpathOpen = "//div[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-change_from_open tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	String xpathPremarket = "//div[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-premarket_change tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	
	
	// Screener is not open
	if(bot.elementExist(xpathScreenerClosed)) {
	    bot.findElements(xpathScreenerClosed).get(0).click();
	} 
	
	// which column to sort by
	if(col == ColumnFilter.BY_CHG) {
	    bot.findElements(xpathChg).get(0).click();
	} else if (col == ColumnFilter.BY_OPEN) {
	    bot.findElements(xpathOpen).get(0).click();
	} else if (col == ColumnFilter.BY_PREMARKET) {
	    bot.findElements(xpathPremarket).get(0).click();
	}
	
	el = bot.findElements(xpathScreenerList);
	return el;
    }

    
    public static String getSymbol(ChromeBot bot) throws Exception {
	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();
	
	// symbol
	el = bot.findElements("//div[contains(@class,'js-button-text text-1sK7vbvh text-3iiFkI3q')]");
	return el.get(0).getAttribute("innerText");
    }
}
