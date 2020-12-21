package stocks.interfaces;

import stocks.Position;

public interface PositionStateListener {

    
    // When a position has closed
    void onPositionClose(Position pos);
    
}
