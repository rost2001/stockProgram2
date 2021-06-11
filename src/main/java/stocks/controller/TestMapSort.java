package stocks.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

import stocks.model.utilities.UIntegers;
import stocks.model.utilities.USort;
import stocks.model.utilities.USort.SortOrder;
import stocks.model.utilities.UStrings;

public class TestMapSort {

    public static void main(String[] args) {
	Map<String, Double> result = new LinkedHashMap<String ,Double>();

	result.put("#1f3e5r", 1.2);
	result.put("#6f3y4r", 100.2);
	result.put("#1d3h4r", 5034.36);
	result.put("#8e3e9d", 0.3);
	result.put("#313e4r", 0.003);

	result = USort.mapSort(SortOrder.REVERSE, result);
	
	for(Map.Entry<String, Double> entry : result.entrySet())
	    System.out.println(entry.getKey() + ": " + entry.getValue());
    
    
	
	BigDecimal bd = new BigDecimal("13.111111111");
	BigDecimal bd2 = new BigDecimal("1231.333333333333");
	
	BigDecimal print;
	print = bd.divide(bd2, MathContext.DECIMAL128);
	
	bd = bd.round(new MathContext(4, RoundingMode.HALF_UP));
	
	//bd2 = bd2.setScale(2, RoundingMode.HALF_EVEN);
	
	System.out.println(print.toPlainString());
	System.out.println(bd.toPlainString());
	System.out.println(bd2.setScale(3, RoundingMode.HALF_EVEN).toPlainString());
	System.out.println(bd2.toPlainString());
	 //a.divide(b, 2, RoundingMode.HALF_UP) a.divide(b, MathContext.DECIMAL128)
	
	
	System.out.println(9/3);
	System.out.println(9/3);
	System.out.println(9/3);
	
	String str = "123456789";
	
	String res = str.substring(0,2+3)+"."+str.substring(2+3);
	
	System.out.println(res);
	System.out.println(str);
	
	
	BigDecimal bd3 = new BigDecimal("12.123");
	String print2 = UStrings.foldNumber(bd3.toPlainString());
	System.out.println(print2);
	System.out.println(UStrings.round(print2, 2));
	System.out.println(print2);
	
	
	print2 = UStrings.unfoldNumber(print2);
	System.out.println(UStrings.round(print2, 4));
	
	
	print2 = UStrings.foldNumber(bd3.toPlainString());
	System.out.println(UStrings.round(print2, 2));
    }
    
  

}
