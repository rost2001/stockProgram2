package stocks.xtesting_examples;
import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class Test13213 {

    public enum State{
	COMPLETED(1, 2),
	PENDING(1,2),
	REMOVED(1,6),
	NEW(5,123);

	public double low;
	public double high;
	public double middle;

	State (double low, double high){
	    
	    
	}
    }
    

    

    public static void main(String[] args) {

	State state = State.NEW;

	state.high = 123;
	
	
	System.out.println(state);
	System.out.println(state);
    }


}
