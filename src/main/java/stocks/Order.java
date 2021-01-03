package stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.WebElement;

import com.sun.jna.platform.win32.WinDef.HWND;

import stocks.Order.Types;
import stocks.interfaces.OrderStateListener;
import stocks.interfaces.StockStateListener;
import stocks.system.WindowsNative;

public abstract class Order{
    
    public OrderStateListener[] osl;

    Timer timerOrder = null;
    TimerTask taskOrder = null;
    static ChromeBot bot = null;

    public States state;
    public Types type;

    public Stock stock;
    public String name;

    // info about order
    public int shares = 0;
    public double capital = 0.0;
    public double price = 0.0;
    public Account account = null;

    public double fee = 0.0;


    public Order(Stock stock, double capital, double price, Account account, OrderStateListener ...osl) {
	this.stock = stock;
	this.capital = capital;
	this.price = price;
	this.account = account;
	
	this.osl = osl;
    }

    public abstract boolean place();


    public abstract boolean remove();



    public enum Types{
	BUY ("BUY"),
	SELL ("SELL");

	public final String type;

	private Types(String string) {
	    type = string;
	}


	public String toString() {
	    return type;
	}

    }
    public void setType(Types type) {
	this.type = type;
    }


    public enum States{
	COMPLETED ("COMPLETED"),
	PENDING ("PENDING"),
	REMOVED ("REMVOED"),
	NEW ("NEW");

	public final String state;

	States(String string) {
	    state = string;
	}


	public String toString() {
	    return state;
	}

    }
    public void setState(States state) {
	this.state = state;
    }


    // Checks every *ms if an order has gone through or not
    // or if the order has been removed on avanzas side
    // needs to be checked cause we do not control what happens on their side
    // we only control if we managed to click the buttons successfully or not on our side
    public void updateOrder(int time) {
	
	if(bot == null) {
	    bot = new ChromeBot();
	    bot.start(ChromeBot.headless,ChromeBot.defaultWindowSize,ChromeBot.useragent);
	    bot.driver.get("https://www.avanza.se/handla/pagaende.html");
	}

	if (timerOrder == null)
	    timerOrder = new Timer();

	if(taskOrder == null) {
	    taskOrder = new TimerTask() {
		@Override
		public void run() {
		    //----------------------------------------------------------
		    try {


			List<WebElement> el = new ArrayList<WebElement>();

			el = bot.findElements("//a[contains(@class,'orderbook-name')]");

			boolean foundIt = false;
			for(int i = 0; i < el.size(); i++) {

			    if(el.get(i).getAttribute("innerText").equalsIgnoreCase(name)) {
				foundIt = true;

				if(state == States.REMOVED) {
				    state = States.PENDING; // något fel med att ta bort så återställ den
				    taskOrder.cancel();
				    
				    for(OrderStateListener osl : osl)
					osl.onOrderRemovalFailure(Order.this);
				    
				} else if (state == States.NEW) {
				    state = States.PENDING; // den finns på marknaden
				    taskOrder.cancel();
				    
				    for(OrderStateListener osl : osl)
					osl.onOrderPending(Order.this);
				}
			    }
			}

			if(foundIt == false && state == States.PENDING) {
			    state = States.COMPLETED; // finns den inte där längre och är inte removed så är den completed
			    type = Types.SELL; // next time you sell the position
			    taskOrder.cancel();
			    
			    for(OrderStateListener osl : osl)
				osl.onOrderCompletion(Order.this);
			}

		    }catch(Exception e) {
			return;
		    }
		    //----------------------------------------------------------
		};
	    };
	    timerOrder.scheduleAtFixedRate(taskOrder, 0, time);

	}
    }

    
}
