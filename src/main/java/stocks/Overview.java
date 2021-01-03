package stocks;

import java.util.ArrayList;
import java.util.List;

import stocks.interfaces.OrderStateListener;

public class Overview implements OrderStateListener{

    public Account account;

    public double investmentCapital = 0.0; // capital to invest when buying
    public double capital = 0.0; // capital in account
    
    public double profit = 0.0;
    public double fees = 0.0;
    
    public List<Position> positions = new ArrayList<Position>();

    ChromeBot bot;
    
    public Overview() {
	
    }
    
    public Overview(ChromeBot bot){
	this.bot = bot;
    }

    public Overview(int account, double investmentCapital, ChromeBot bot){
	this.bot = bot;
    }
    

    @Override
    public void onOrderPending(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderCompletion(Order order) {

	for (Position pos : positions) {
	    fees += pos.fees;
	    profit += pos.profit;
	}
	
    }

    @Override
    public void onOrderRemoval(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderRemovalFailure(Order order) {
	// TODO Auto-generated method stub
	
    }
    
}
