package stocks;

import java.util.ArrayList;
import java.util.List;

import stocks.interfaces.PositionStateListener;

public class Overview implements PositionStateListener{


    public int account = 0;

    public double investmentCapital = 0.0; // capital to invest when buying
    public double capital = 0.0; // capital in account
    
    public double profit = 0.0;
    public double fees = 0.0;
    
    public List<Position> positions = new ArrayList<Position>();
    
    public List<Position> oldPositions = new ArrayList<Position>();
    
    public Overview(){
	
    }

    public Overview(int account, double investmentCapital){
	
    }



    @Override
    public void onPositionClose(Position pos) {
	profit += pos.profit;
	fees += pos.fees;
	
	positions.remove(pos);
	oldPositions.add(pos);
    }

    
    
    
}
