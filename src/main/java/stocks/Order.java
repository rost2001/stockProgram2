package stocks;


public abstract class Order{


    public Stock stock;
    
    // info about order
    public int shares = 0;
    public double capital = 0.0;
    public double price = 0.0;
    public int account = 0;
    
    public double fee = 0.0;
    
    

    public abstract void place();
    

}
