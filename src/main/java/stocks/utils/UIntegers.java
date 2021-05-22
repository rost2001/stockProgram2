package stocks.utils;

public class UIntegers {

    
    
    
    public static long unfoldIntoNumber(String stringInteger) {
	
	long result = 0;
	
	if (stringInteger.toLowerCase().contains("m")) {
	    stringInteger.replaceAll("(?i)m", "");
	    result = Long.parseLong(stringInteger);
	    result*=1000000;
	} else if  (stringInteger.toLowerCase().contains("k")) {
	    stringInteger.replaceAll("(?i)k", "");
	    result = Long.parseLong(stringInteger);
	    result*=1000;
	} else if (stringInteger.toLowerCase().contains("t")) {
	    stringInteger.replaceAll("(?i)t", "");
	    result = Long.parseLong(stringInteger);
	    result*=1000000000;
	}
	
	return result;
    }
}
