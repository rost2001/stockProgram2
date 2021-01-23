package stocks.data.interfaces;

import stocks.data.Order;

public interface OrderStateListener {

    
    void onOrderPending(Order order);
    
    void onOrderCompletion(Order order);
    
    void onOrderRemoval(Order order);
    
    void onOrderRemovalFailure(Order order);
    
}
