package stocks.data;

import stocks.data.Order.Types;
import stocks.data.interfaces.OrderStateListener;

public class Position implements OrderStateListener{
    
    // info about position
    public double profit = 0.0;
    public double fees = 0.0;
    
    public Order openingOrder = null;
    public Order closingOrder = null;
    
    
    // Open an inital position
    public boolean open(Order order) {
	openingOrder = order;
	return order.place();
    }
    

    // Closing position by selling, provide Overview object or other that implements the interface if you want an update
    public boolean close(Order order) {
	closingOrder = order;
	return order.place();
    }


    public boolean cancelOrder(Order order) {
	
	return order.remove();
    }


    @Override
    public void onOrderPending(Order order) {

	fees += order.fee;
	
    }


    @Override
    public void onOrderCompletion(Order order) {
	fees += order.fee;
	if(order.type == Types.SELL)
	profit += closingOrder.capital - openingOrder.capital;
    }


    @Override
    public void onOrderRemoval(Order order) {
	// TODO Auto-generated method stub
	
    }


    @Override
    public void onOrderRemovalFailure(Order order) {
	// TODO Auto-generated method stub
	
    }
    
    public String toString() {
	return "Position";
    }
    
}
