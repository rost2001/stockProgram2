package stocks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.openqa.selenium.WebElement;

import stocks.data.interfaces.OrderStateListener;
import stocks.selenium.ChromeBot;

public abstract class Order{
    
    public OrderStateListener[] osl;

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


    public Order(Stock stock, double capital, int shares, double price, Account account, Types type, OrderStateListener ...osl) {
	this.stock = stock;
	this.capital = capital;
	this.shares = shares;
	this.price = price;
	this.account = account;

	this.state = States.NEW;
	this.type = type;
	
	this.osl = osl;
    }
//    https://stackoverflow.com/questions/37263466/partial-implementation-of-an-abstract-method/37263503
    public abstract boolean place();


    public abstract boolean remove();

    public abstract void verify();

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


    
}
