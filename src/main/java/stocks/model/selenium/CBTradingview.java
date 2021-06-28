package stocks.model.selenium;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import stocks.model.system.SRobot;

public class CBTradingview {

    private CBTradingview(){}

    public static void login(ChromeBot bot, String email, String password) throws Exception {
	bot.get("https://www.tradingview.com/");

	List<WebElement> el = new ArrayList<WebElement>();

	// cookie knapp
	el = bot.findElements("//button[contains(@class,'button-1iktpaT1 size-s-3mait84m intent-primary-1-IOYcbg appearance-default-dMjF_2Hu')]");
	Thread.sleep(3000);
	
	el.get(0).click();


	// sign in
	el = bot.findElements("//button[contains(@class,'tv-header__user-menu-button tv-header__user-menu-button--anonymous js-header-user-menu-button')]");
	el.get(0).click();
	
	
	
	el = bot.findElements("//div[contains(@class,'label-2IihgTnv')]");
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
    public static List<WebElement> getWatchList(ChromeBot bot) throws Exception{

	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();
	String xpathWatchlist = "//div[contains(@class,'symbol-EJ_LFrif')]";

	el = bot.findElements(xpathWatchlist);
	return el;
    }

    /* Returns a stockscreener list of stocks as elements, 1 per symbol, ordered as stockscreener
     * Expecting: stockscreener is showing
     */
    public enum Column{
	BY_CHG,
	BY_OPEN,
	BY_PREMARKET;

	public double intervalLow;
	public double intervalHigh;

	public static Column getColumn(Column col, double intervalLow, double intervalHigh){
	    Column col2 = col;
	    col2.intervalLow = intervalLow;
	    col2.intervalHigh = intervalHigh;
	    return col2;
	}

	public boolean equals(Column column) {
	    if(this.name().equalsIgnoreCase(column.name())) {
		return true;
	    }
	    return false;
	}
    }
    public static String[] getStockScreenerList(ChromeBot bot, int numberOfStocks, Column ...columns) throws Exception{

	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();
	String xpathScreenerList = "//tr[contains(@class,'tv-data-table__row tv-data-table__stroke tv-screener-table__result-row')]";
	//String xpathScreenerOpen = "//div[contains(@class,'tab-1fqQ8BUy apply-common-tooltip active-3fnMHeSg')]";
	String xpathScreenerClosed = "//div[contains(@class,'tab-1fqQ8BUy apply-common-tooltip')]";
	String xpathScreenerMaximize = "//button[contains(@class,'button-txUjKTqi maximizeButton-QKvf0eBk apply-common-tooltip')]";
	String xpathChg = "//th[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-change tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	String xpathOpen = "//th[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-change_from_open tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	String xpathPremarket = "//th[contains(@class,'tv-data-table__th tv-data-table__cell js-tv-data-table-th js-tv-data-table-th-premarket_change tv-data-table__sortable tv-screener-table__sortable tv-screener-table__th js-draggable')]";
	String xpathFilterButton = "//i[contains(@class,'js-filter-button tv-screener-table__filter-button')]";
	String xpathBetweenList = "//span[contains(@class,'tv-screener-inplace-editor__selectbox-caption')]";
	String xpathBetween = "//span[contains(@class,'tv-dropdown-behavior__item tv-control-select__option js-option')]";
	String xpathInputLow = "//input[contains(@class,'tv-screener-inplace-editor__filter-field-content--right tv-screener-inplace-editor__filter-between-input js-filter-operator-between-left')]";
	String xpathInputHigh = "//input[contains(@class,'tv-screener-inplace-editor__filter-field-content--right tv-screener-inplace-editor__filter-between-input js-filter-operator-between-right')]";
	String xpathStock = "//tr[contains(@class,'tv-data-table__row tv-data-table__stroke tv-screener-table__result-row')]";
	String xpathStockTable = "//table[contains(@class,'tv-data-table tv-screener-table tv-screener-table--fixed')]";

	// Screener is not open
	if(bot.elementExist(xpathScreenerClosed)) {
	    bot.findElements(xpathScreenerClosed).get(0).click();
	} 

	// Screener is not maximized
	if(bot.elementExist(xpathScreenerMaximize)) {
	    bot.findElements(xpathScreenerMaximize).get(0).click();
	} 


	for(Column col : columns) {

	    WebElement element;
	    // which column to sort by
	    if(col.equals(Column.BY_CHG)) {
		element = bot.findElements(xpathChg).get(0);
		element.findElements(By.cssSelector("i *")).get(0).click();
		Thread.sleep(200);
		bot.findElements(xpathBetweenList).get(0).click();
		bot.findElements(xpathBetween).get(4).click();
		bot.findElements(xpathInputLow).get(0).sendKeys(String.valueOf(col.intervalLow));
		bot.findElements(xpathInputHigh).get(0).sendKeys(String.valueOf(col.intervalHigh));

	    } else if (col == Column.BY_OPEN) {
		element = bot.findElements(xpathOpen).get(0);
		element.findElements(By.cssSelector("i *")).get(0).click();
	    } else if (col == Column.BY_PREMARKET) {
		element = bot.findElements(xpathPremarket).get(0);
		element.findElements(By.cssSelector("i *")).get(0).click();
	    }

	}

	Thread.sleep(2000);
	SRobot botAwt = new SRobot();
	botAwt.mouseMove(500, 500, 20);
	for(int i = 0; i < (numberOfStocks/150 - 1); i++) {
	    botAwt.mouseWheel(100);
	    Thread.sleep(2000);
	}


	// retrieves all stock names
	el = bot.findElements(xpathScreenerList);

	String[] symbols = new String[el.size()];
	for(int i = 0; i < el.size(); i++) {
	    symbols[i] = ChromeBot.getWords(el.get(i)).get(0);
	}

	return symbols;
    }


    public static String getSymbol(ChromeBot bot) throws Exception {
	bot.get("https://www.tradingview.com/chart/");
	List<WebElement> el = new ArrayList<WebElement>();

	// symbol
	el = bot.findElements("//div[contains(@class,'js-button-text text-1sK7vbvh text-3iiFkI3q')]");
	return el.get(0).getAttribute("innerText");
    }


    public enum Info{
	NAME,
	MKT,
	VOL,
	LAST,
	PROCENTAGE,
	SHARES,
	EMPLOYEES,
	HIGH;
    }
    public static Map<Info, String> getStockInfo(ChromeBot bot, Info ...types) throws Exception {
	String xpathmkt = "//span[contains(@class,'data-3iXCXbow')]";
	String xpathvol = "//span[contains(@class,'data-3iXCXbow')]";
	String xpathlast = "//span[contains(@class,'price-3PT2D-PK')]";
	String xpathprocentage = "//span[contains(@class,'change-3PT2D-PK')]";
	String xpathInfoButton = "//button[contains(@class,'button-29Bh2ACj button-XCmieq1Q')]";
	String xpathshares = "//span[contains(@class,'data-3iXCXbow')]";
	String xpathEmployeesTitle = "//span[contains(@class,'title-3iXCXbow')]";
	String xpathName = "//span[contains(@class,'title-2ahQmZbQ')]";
	String xpathHigh = "//span[contains(@class,'price-1c6h-6Rx')]";


	List<WebElement> el = new ArrayList<WebElement>(); // temp list for elements
	Map<Info, String> map = new LinkedHashMap<Info, String>(); // map to be return

	for (Info type : types) {
	    if(type.name().equals(Info.MKT.name())) {
		el = bot.findElements(xpathmkt);
		if (el.size() != 0)
		    map.put(type, el.get(2).getAttribute("innerText"));

	    } else if (type.name().equals(Info.VOL.name())) {
		el = bot.findElements(xpathvol);
		if (el.size() != 0)
		    map.put(type, el.get(0).getAttribute("innerText"));

	    } else if (type.name().equals(Info.LAST.name())) {
		el = bot.findElements(xpathlast);
		if (el.size() != 0)
		    map.put(type, el.get(0).getAttribute("innerText"));

	    } else if (type.name().equals(Info.PROCENTAGE.name())) {
		el = bot.findElements(xpathprocentage);
		if (el.size() != 0) {
		    map.put(type, el.get(1).getAttribute("innerText").replace("−", "-"));
		}

	    } else if (type.name().equals(Info.SHARES.name())) {
		bot.findElements(xpathInfoButton).get(0).click();
		el = bot.findElements(xpathshares);
		if(el.size() < 7) { // sometimes it needs to press a button to show
		    bot.findElements(xpathInfoButton).get(0).click();
		    el = bot.findElements(xpathshares);
		}
		if (el.size() != 0) // Sometimes there is no shares element
		    map.put(type, el.get(6).getAttribute("innerText"));

	    } else if (type.name().equals(Info.EMPLOYEES.name())) {
		el = bot.findElements(xpathshares);

		List<WebElement> el2 = bot.findElements(xpathEmployeesTitle);
		for(WebElement element : el2){ // check if it exist by the word "Employees"
		    if(element.getAttribute("innerText").toLowerCase().contains("Employees".toLowerCase())) {

			if(el.size() > 6) { // If button for more info is clicked then the index is different
			    map.put(type, el.get(8).getAttribute("innerText"));

			} else if (el.size() != 0) 
			    map.put(type, el.get(5).getAttribute("innerText"));
		    }
		}

	    } else if (type.name().equals(Info.NAME.name())) {
		el = bot.findElements(xpathName);
		map.put(type, el.get(0).getAttribute("innerText"));

	    } else if (type.name().equals(Info.HIGH.name())) {
		el = bot.findElements(xpathHigh);
		map.put(type, el.get(1).getAttribute("innerText"));

	    }
	}
	
	
	return map;
    }
    
    
}

