package stocks.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import stocks.data.Account;
import stocks.data.Order;
import stocks.data.Stock;
import stocks.data.interfaces.OrderStateListener;

public class CBOrder extends Order{
    
    Timer timerOrder = null;
    TimerTask taskOrder = null;
    private ChromeBot bot2 = null;

    private ChromeBot bot;
    private String stockName;
    List<WebElement> el = new ArrayList<WebElement>();

    public CBOrder(Stock stock, double capital, int shares, double price, Account account, Types type, ChromeBot buyBot, ChromeBot verifyBot, OrderStateListener ...osl) {
	super(stock, capital, shares, price, account, type, osl);
	this.bot = buyBot;
	this.bot2 = verifyBot;

	try {
	    // Prepare for next buy
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);

	    // Väljer Köp
	    el = bot.findElements("//a");
	    el.get(1).sendKeys(Keys.ENTER);
	}catch(Exception e) {
	    return;
	}
    }


    @Override
    public boolean place() {
	try {


	    /*
	    // Söker efter aktien
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (bot.findElements("//a").size() == 0);return true;
	    });

	    // Väljer Köp
	    el = bot.findElements("//a");
	    el.get(1).sendKeys(Keys.ENTER);
	     */


	    // used to check if the order went through
	    el = bot.findElements("//h1");
	    name = el.get(0).getAttribute("innerText");


	    // Konto öppna listan
	    if(type.equals(Types.BUY)) {
		el = bot.findElements("//div[contains(@class,'select-trigger')]");
		el.get(0).sendKeys(Keys.ENTER);




		// konto välj
		el = bot.findElements("//aza-select-option");
		for(int i = 0; i < el.size(); i++){
		    List<String> words = ChromeBot.getWords(el.get(i));
		    if(Integer.parseInt(words.get(0)) == account.number) {
			el.get(i).sendKeys(Keys.ENTER);
		    }
		}

	    }

	    /*
	    // konto välj
	    // för att börja räkna från längst upp för att det
	    // kan vara att kontot som är highlightat från början 
	    // int är detsamma varje gång
	    el.get(0).sendKeys(Keys.ARROW_UP);
	    el.get(0).sendKeys(Keys.ARROW_UP);
	    el.get(0).sendKeys(Keys.ARROW_UP);
	    el.get(0).sendKeys(Keys.ARROW_UP);
	    el.get(0).sendKeys(Keys.ARROW_UP);
	    el.get(0).sendKeys(Keys.ARROW_UP);



	    for (int i = 0; i < account - 1; i++) {
		el.get(0).sendKeys(Keys.ARROW_DOWN);
	    }

	    el.get(0).sendKeys(Keys.ENTER);


	    /*
	    //Konto stäng ned listan
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (bot.findElements("//div[contains(@class,'select-panel select-open ng-star-inserted')]").size() == 0);return true;
	    });
	    el = bot.findElements("//div[contains(@class,'select-panel select-open ng-star-inserted')]");
	    el.get(0).sendKeys(Keys.ENTER);

	     */

	    // Antal andelar
	    if(type.equals(Types.BUY)) {
		el = bot.findElements("//input[contains(@id,'buyAmount')]");
		el.get(0).sendKeys(String.valueOf(String.format("%.2f", this.capital)));
	    }
	    else {
		el = bot.findElements("//input[contains(@id,'sellVolume')]");
		el.get(0).sendKeys(String.valueOf(this.shares));
	    }


	    // Köp priset
	    if(type.equals(Types.BUY))
		el = bot.findElements("//input[contains(@id,'buyPrice')]");
	    else
		el = bot.findElements("//input[contains(@id,'sellPrice')]");
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(Keys.BACK_SPACE);
	    el.get(0).sendKeys(String.valueOf(this.price));


	    // Sälj/köpknapp
	    if(type.equals(Types.BUY))
		el = bot.findElements("//button[contains(@class,'form-submit-button aza-button cta-buy')]");
	    else
		el = bot.findElements("//button[contains(@class,'form-submit-button aza-button cta-sell')]");

	    el.get(0).sendKeys(Keys.ENTER);


	    // Sista popupen med info
	    el = bot.findElements("/html/body/aza-app/div/main/div/ng-component/aza-flow-page/div/div/ng-component/aza-flow-page-step/div/div/div[2]/div[1]/aza-card/form/div[2]/button");
	    // Infon från popupen
	    Thread.sleep(20);
	    WebElement form = bot.findElements("//form").get(1);
	    // sista enter tryck för att köpa
	    el.get(0).sendKeys(Keys.ENTER);




	    // varje ord för sig

	    List<String> words = ChromeBot.getWords(form);

	    // bläddrar igenom namnet på aktien
	    int i = 0;
	    for(;!words.get(i).equals("Antal");)
		i++;


	    // all info
	    double vaxlingskurs = Double.parseDouble(words.get(23+i).replaceAll(",", ".").replaceAll(" ", ""));
	    this.capital = Double.parseDouble(words.get(9+i).replaceAll(",", ".").replaceAll(" ", ""))* vaxlingskurs;
	    this.fee = Double.parseDouble(words.get(12+i).replaceAll(",", ".").replaceAll(" ", ""))*vaxlingskurs
		    + Double.parseDouble(words.get(19+i).replaceAll(",", ".").replaceAll(" ", ""))*vaxlingskurs;
	    this.price =  Double.parseDouble(words.get(4+i).replaceAll(",", ".").replaceAll(" ", ""));
	    this.shares = Integer.parseInt("Shares: " + words.get(1+i).replaceAll(",", ".").replaceAll(" ", ""));


	    /*
	    String[] str =  form.getAttribute("innerText").split(" |\n");


	    // Räkna up index för att antal ord i namnet
	    // på aktien är olika och behöver bläddras igenom först
	    int index = 0;
	    for(;!str[index].equalsIgnoreCase("Antal"); index++) {}


	    // Gör om till Integer/Double och ersätter ","med"." 
	    // och alla " "mellanrum och "\n" med ""inget
	    int antal = Integer.parseInt(str[index+1].replaceAll(",", ".").replaceAll(" |\n", ""));
	    double fee = Double.parseDouble(str[index+12].replaceAll(",", ".").replaceAll(" |\n", ""));
	    fee += Double.parseDouble(str[index+19].replaceAll(",", ".").replaceAll(" |\n", ""));

	    double vaxlingsKurs = Double.parseDouble(str[index+23].replaceAll(",", ".").replaceAll(" |\n", ""));
	    double capital = Double.parseDouble(str[index+9].replaceAll(",", ".").replaceAll(" |\n", "")) * vaxlingsKurs;

	     */

	    /*
	System.out.println("Antal: " + antal);
	System.out.println("Fee: " + fee);
	System.out.println("Växlingskurs: " + vaxlingsKurs);
	System.out.println("Capital: " + capital);
	     */


	    // return back, prepare for next buy
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);

	    // Väljer Köp/sälj
	    el = bot.findElements("//a");
	    if(type == Types.BUY)
		el.get(1).sendKeys(Keys.ENTER);
	    else
		el.get(2).sendKeys(Keys.ENTER);


	    // Checks the order so it is updated once it goes through or gets removed
	    verify();
	    


	    return true;
	}catch (Exception e) {
	    
	    // return back, prepare for next buy
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);

	    // Väljer Köp/sälj
	    try {
		el = bot.findElements("//a");
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    if(type == Types.BUY)
		el.get(1).sendKeys(Keys.ENTER);
	    else
		el.get(2).sendKeys(Keys.ENTER);
	    return false;
	}
    }


    @Override
    public boolean remove() {
	try {
	    // 
	    bot.driver.get("https://www.avanza.se/handla/pagaende.html");
	    el = bot.findElements("//a[contains(@class,'orderbook-name')]");

	    for(int i = 0; i < el.size(); i++) {
		if(stockName.equals(el.get(i).getAttribute("innerText"))) {
		    List<WebElement> removeButtons = bot.findElements("//button[contains(@class,'aza-button aza-icon-button ng-star-inserted')]");
		    removeButtons.get(i).sendKeys(Keys.ENTER);
		}
	    }


	    setState(States.REMOVED);


	    // return back, prepare for next buy
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);

	    // Väljer Köp/sälj
	    el = bot.findElements("//a");
	    if(type == Types.BUY)
		el.get(1).sendKeys(Keys.ENTER);
	    else
		el.get(2).sendKeys(Keys.ENTER);

	    for(OrderStateListener osl : osl)
		osl.onOrderRemoval(this);

	    return true;
	}catch (Exception e) {

	    // return back, prepare for next buy
	    bot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + stock.symbol);

	    // Väljer Köp/sälj
	    try {
		el = bot.findElements("//a");
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    if(type == Types.BUY)
		el.get(1).sendKeys(Keys.ENTER);
	    else
		el.get(2).sendKeys(Keys.ENTER);
	    return false;
	}
    }


    @Override
    public void verify() {

	if (timerOrder == null)
	    timerOrder = new Timer();

	if(taskOrder == null) {
	    taskOrder = new TimerTask() {
		@Override
		public void run() {
		    //----------------------------------------------------------
		    try {

			bot2.get("https://www.avanza.se/handla/pagaende.html");
			List<WebElement> el = new ArrayList<WebElement>();

			el = bot2.findElements("//a[contains(@class,'orderbook-name')]");

			boolean foundIt = false;
			for(int i = 0; i < el.size(); i++) {

			    if(el.get(i).getAttribute("innerText").equalsIgnoreCase(name)) {
				foundIt = true;

				if(state == States.REMOVED) {
				    state = States.PENDING; // något fel med att ta bort så återställ den
				    taskOrder.cancel();
				    
				    for(OrderStateListener osl : osl)
					osl.onOrderRemovalFailure(CBOrder.this);
				    
				} else if (state == States.NEW) {
				    state = States.PENDING; // den finns på marknaden
				    stock.stopUpdatingPrice();
				    taskOrder.cancel();
				    
				    for(OrderStateListener osl : osl)
					osl.onOrderPending(CBOrder.this);
				}
			    }
			}

			if(foundIt == false && state == States.PENDING) {
			    state = States.COMPLETED; // finns den inte där längre och är inte removed så är den completed
			    type = Types.SELL; // next time you will sell the position
			    stock.stopUpdatingPrice();
			    taskOrder.cancel();
			    
			    for(OrderStateListener osl : osl)
				osl.onOrderCompletion(CBOrder.this);
			}

		    }catch(Exception e) {
			return;
		    }
		    //----------------------------------------------------------
		};
	    };
	    timerOrder.scheduleAtFixedRate(taskOrder, 0, 200);

	}
	
    }



}
