package stocks.model.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class USort {


    public enum SortOrder{
	NORMAL,
	REVERSE,
	MIDPOINT;
    }
    @SuppressWarnings("unchecked")
    public static <K,V> Map<K,V> mapSort(SortOrder sortOrder, Map<K,V> map) {
	Map<K,V> result = new LinkedHashMap<K,V>();

	// An empty key list, copying over values of map into a value list
	List<K> keys = new ArrayList<K>();
	List<V> values = new ArrayList<V>(map.values());

	/* sorting values normally, or reversed, or midpoint. */
	if(sortOrder.name().equals(SortOrder.NORMAL.name()))
	    Collections.sort((List<Double>) values);
	else if (sortOrder.name().equals(SortOrder.REVERSE.name())) 
	{
	    Collections.sort((List<Double>) values);
	    Collections.reverse((List<Double>) values);
	} 
	else if (sortOrder.name().equals(SortOrder.MIDPOINT.name())) 
	{
	    Collections.sort((List<Double>) values);
	    values = (List<V>) midPointOutwardSort((List<Double>) values);
	}

	/* Checking first index of value. When it is found in the map,
	   it puts the mapped key into the empty key list,
	   so that there is a key list that is in same order as the value list,
	   cause the sorting changes the order.
	*/
	for(int i = 0; i < values.size(); i++)
	{
	    for (Map.Entry<K, V> entry : map.entrySet()) 
	    {

		if (entry.getValue() == values.get(i)) {
		    keys.add(entry.getKey());
		    continue;
		}

	    }
	}

	// make a new map by using the order of the lists, and then returns it
	for(int i = 0; i < keys.size(); i++) {
	    result.put(keys.get(i), values.get(i));
	}
	return result;
    }

    /* Sorting from middle and outwards,
     * if even: 7,6 8,5 9,4 10,3 11,2 12,1
     * Or if odd: 7, 8,6 9,5 10,4 11,3 12,2 13,1 
     * 
     */
    public static <T> List<T> midPointOutwardSort(List<T> list){
	List<T> result = new ArrayList<T>();

	if ( (list.size() & 1) == 0 ) // even
	{ 
	    for(int i = 0; i < (list.size()/2); i++) 
	    {		// no number in middle
		result.add(list.get(list.size()/2+i)); // middle + i 
		result.add(list.get(list.size()/2-i-1)); // middle - 1 - i
	    }
	} 
	else // odd
	{ 
	    result.add(list.get((list.size()-1)/2)); // middle number
	    for(int i = 0; i < ((list.size()-1)/2); i++) // for each number from middle
	    {
		result.add(list.get((list.size()-1)/2+i+1)); // middle + 1 + i
		result.add(list.get((list.size()-1)/2-i-1)); // middle - 1 - i
	    }
	}

	return result;
    }


}
