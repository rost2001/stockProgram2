package stocks.data;

import stocks.data.interfaces.OrderStateListener;

public class FakeOrder extends Order {
    
    public static void main( String... args ) {
    
	
    }

    public FakeOrder(Stock stock, double capital, int shares, double price, Account account, Types type, OrderStateListener ...osl) {
	super(stock, capital, shares, price, account, type, osl);
    }

    @Override
    public boolean place() {

	if(type == Types.BUY) {
	    shares = (int) Math.floor(capital/price);
	}else {


	}
	capital = shares*price;
	fee = capital*0.01;

	
	if(type == Types.SELL) {
	stock.stopUpdatingPrice();
	}
	
	setState(States.COMPLETED);

	/*
	if (timerOrder == null)
	    timerOrder = new Timer();

	if(taskOrder == null) {
	    taskOrder = new TimerTask() {
		@Override
		public void run() {
		    //----------------------------------------------------------
		    try {


			if(type == Types.BUY) {
			    if(stock.price < price) {
				setState(States.COMPLETED);
				taskOrder.cancel();
			    }
			} else {
			    if(stock.price > price) {
				setState(States.COMPLETED);
				taskOrder.cancel();
				stock.stopUpdatingPrice();
			    }
			}


		    }catch(Exception e) {
			return;
		    }
		    //----------------------------------------------------------
		};
	    };
	    timerOrder.scheduleAtFixedRate(taskOrder, 0, 50);

	}
	
	
*/
	for(OrderStateListener osl : osl)
	    osl.onOrderCompletion(this);

	return true;
    }

    @Override
    public boolean remove() {
	
	return true;
    }

    @Override
    public void verify() {
	// TODO Auto-generated method stub
	
    }

}
