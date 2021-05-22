package stocks.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import stocks.model.utilities.USort;
import stocks.model.utilities.USort.SortOrder;

public class TestMapSort {

    public static void main(String[] args) {
	Map<String, Double> result = new LinkedHashMap<String ,Double>();

	result.put("#1f3e5r", 1.2);
	result.put("#6f3y4r", 100.2);
	result.put("#1d3h4r", 5034.36);
	result.put("#8e3e9d", 0.3);
	result.put("#313e4r", 0.003);

	result = USort.mapSort(SortOrder.NORMAL, result);
	
	for(Map.Entry<String, Double> entry : result.entrySet())
	    System.out.println(entry.getKey() + ": " + entry.getValue());
    
    
	 StringBuffer currentSymbol = new StringBuffer("Hello");
    }

}
