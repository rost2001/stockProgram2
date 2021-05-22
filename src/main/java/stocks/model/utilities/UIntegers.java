package stocks.model.utilities;

import java.util.ArrayList;
import java.util.List;

public class UIntegers {


    public static double unfoldIntoNumber(String stringInteger) {

	double result = 0;

	if (stringInteger.toLowerCase().contains("m")) {
	    stringInteger.replaceAll("(?i)m", "");
	    result = Double.parseDouble(stringInteger);
	    result*=1000000;
	} else if  (stringInteger.toLowerCase().contains("k")) {
	    stringInteger.replaceAll("(?i)k", "");
	    result = Double.parseDouble(stringInteger);
	    result*=1000;
	} else if (stringInteger.toLowerCase().contains("b")) {
	    stringInteger.replaceAll("(?i)t", "");
	    result = Double.parseDouble(stringInteger);
	    result*=1000000000;
	} else if (stringInteger.toLowerCase().contains("t")) {
	    stringInteger.replaceAll("(?i)t", "");
	    result = Double.parseDouble(stringInteger);
	    result*=1000000000000d;
	} else {
	    result = Double.parseDouble(stringInteger);
	}

	return result;
    }
}
