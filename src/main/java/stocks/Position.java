package stocks;

import stocks.Order.States;

public class Position {
    
    // info about position
    public double profit = 0.0;
    public double fees = 0.0;
    
    public Order openingOrder = null;
    public Order closingOrder = null;
    
    
    // Open an inital position
    public void open(Order order) {
	openingOrder = order;
	order.place();
	
	order.setState(States.NEW);
	fees += order.fee;
    }
    

    // Closing position by selling, provide Overview object or other that implements the interface if you want an update
    public boolean close(Order order) {
	closingOrder = order;
	boolean succeeded = order.place();

	fees += order.fee;
	profit += closingOrder.capital - openingOrder.capital;
	
	return succeeded;
    }


    public boolean cancelOrder(Order order) {
	
	return order.remove();
    }
    
}
