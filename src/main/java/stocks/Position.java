package stocks;

import stocks.interfaces.PositionStateListener;

public class Position {
    
    // info about position
    public double profit = 0.0;
    double fees = 0.0;

    public Order openingOrder;
    public Order closingOrder;
    
    
    // Open an inital position
    public void open(Order order) {
	openingOrder = order;
	order.place();
	
	fees += order.fee;
    }
    
    // add more to the position
    public void add(Order order) {
	System.err.println("Adding to position is not implemented");
    }
    
    // Closing position by selling, provide Overview object or other that implements the interface if want an update
    // otherwise set psl to "null"
    public void close(Order order, PositionStateListener psl) {
	closingOrder = order;
	order.place();
	
	fees += order.fee;
	profit += closingOrder.capital - openingOrder.capital;
	
	if(psl != null)
	    psl.onPositionClose(this);
    }
    
    // Closing a position partially
    public void close(Order order, int shares) {
	System.err.println("Partially closing a position is not implemented yet");
    }
    
    
    
}
