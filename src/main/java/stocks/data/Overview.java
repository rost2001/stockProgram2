package stocks.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import stocks.Console2;
import stocks.data.interfaces.OrderStateListener;
import stocks.selenium.ChromeBot;
import stocks.utils.UStyle;

public class Overview implements OrderStateListener{

    
    
    public Account account;
    
    static Stock viewingStock;
    static Position currentPosition = new Position();
    

    public static double startingCapital = 100;
    public static double currentCapital = 100;
    public static double fees = 0.0;
    public static double profit = 0.0;
    
    public static List<Position> positions = new ArrayList<Position>();


    
    public Overview() {
	
    }
    
    public void buy () {
	
	    Stock stock = new Stock(viewingStock.symbol, viewingStock.price);
	    //stock.updatePrice(1000, true, this);
	    //FakeOrder buy = new FakeOrder(stock, currentCapital, 0, viewingStock.price, null, Order.Types.BUY, overview, currentPosition, this);
	    
	    currentPosition = new Position();
	    //currentPosition.open(buy);
	    
	    positions.add(currentPosition);
	    
	    //currentCapital -= buy.capital;
    }
    
    public void sell () {
	
	    // https://stackoverflow.com/questions/17758411/java-creating-a-new-thread/17758416
	    Stock stock = new Stock(currentPosition.openingOrder.stock.symbol, currentPosition.openingOrder.stock.price);
	  //  FakeOrder sell = new FakeOrder(stock, currentPosition.openingOrder.capital, currentPosition.openingOrder.shares, viewingStock.price, null, Order.Types.SELL, overview, currentPosition, this);
	    
	    //currentPosition.close(sell);
	    
	    positions.remove(currentPosition);
	    //fees += sell.fee;
	    fees += currentPosition.openingOrder.fee;
	    
	    //currentCapital += sell.capital;
	    currentPosition = new Position();
    }
    

    @Override
    public void onOrderPending(Order order) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onOrderCompletion(Order order) {
	fees = 0; profit = 0;

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
    
    static void consoleRefresh(){

	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (InterruptedException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String out = "";

	out += ("___________________________________");

	if(currentPosition.openingOrder == null)
	    profit = currentCapital - startingCapital;

	out += ("\n|Profit: " + String.format("%.2f", profit - fees) + " USD");
	out += ("\n|Accumulated fees: " + String.format("%.2f", fees) + " USD"); // Rounds to 0 decimals
	out += ("\n|Starting Capital: " + String.format("%.2f", startingCapital));
	out += ("\n|Current Capital: " + String.format("%.2f", currentCapital));
	if(currentPosition.openingOrder != null)
	    out += (" + " + currentPosition);

	out += ("\n|__________________________________");
	out += ("\n|Position: ");
	out += ("\n|__________________________________");

	for (int i = 0; i < positions.size(); i++) {

	    out += ("\n|__________________________________");
	    out += ("\n|Buy Order: #" + (i+1) + "   (Completed/Pending)");
	    out += ("\n|symbol: " + positions.get(i).openingOrder.stock.symbol);
	    out += ("\n|price: " + positions.get(i).openingOrder.price + " USD");
	    out += ("\n|capital: " + String.format("%.2f", positions.get(i).openingOrder.capital) + " USD");
	    out += ("\n|shares: " + positions.get(i).openingOrder.shares + "st");


	    double profitPoints = positions.get(i).openingOrder.stock.price - positions.get(i).openingOrder.price;
	    double profitProcentage = (positions.get(i).openingOrder.stock.price / positions.get(i).openingOrder.price - 1)*100;

	    out += ("\n|------|");
	    out += ("\n|Current Price: " + positions.get(i).openingOrder.stock.price);

	    if(profitProcentage > 0) { //green

		out += ("\n|Profit:  " + UStyle.ANSI_CYAN + String.format("%.2f", profitPoints) + UStyle.ANSI_RESET + " (" + UStyle.ANSI_CYAN +String.format("%.2f", profitProcentage) + "%" + UStyle.ANSI_RESET + ")");
	    }else { //red

		out += ("\n|Profit:  " + UStyle.ANSI_RED + String.format("%.2f", profitPoints) + UStyle.ANSI_RESET + " (" + UStyle.ANSI_RED +String.format("%.2f", profitProcentage) + "%" + UStyle.ANSI_RESET + ")");
	    }

	    out += ("\n|Capital: " + String.format("%.2f", positions.get(i).openingOrder.stock.price*positions.get(i).openingOrder.shares) + " USD");
	    out += ("\n|__________________________________");


	}
	out += ("\n|" + viewingStock.symbol);
	out += ("\n|__________________________________");
	System.out.println(out);
    }
}
