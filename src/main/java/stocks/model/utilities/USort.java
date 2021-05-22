package stocks.model.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class USort {


    public enum SortOrder{
	NORMAL,
	REVERSE;
    }
    @SuppressWarnings("unchecked")
    public static <K,V> Map<K,V> mapSort(SortOrder sortOrder, Map<K,V> map) {
	Map<K,V> result = new LinkedHashMap<K,V>();

	// empty key list, copying over values of map into a list
	List<K> keys = new ArrayList<K>();
	List<V> values = new ArrayList<V>(map.values());

	// sorting normally, or reversed.
	if(sortOrder.name().equals(SortOrder.NORMAL.name()))
	    Collections.sort((List<Double>) values);
	else if (sortOrder.name().equals(SortOrder.REVERSE.name())) {
	    Collections.sort((List<Double>) values);
	    Collections.reverse((List<Double>) values);
	}

	// manually checking values of list towards those in map
	// if it matches then get the corresponding key,
	// and put that key into the corresponding index of the key list
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

	// make a new map by using the lists, and then return it
	for(int i = 0; i < keys.size(); i++) {
	    result.put(keys.get(i), values.get(i));
	}
	return result;
    }
    
    public static <T> List<T> midPointOutwardSort(List<T> list){
  	List<T> result = new ArrayList<T>();

  	if ( (list.size() & 1) == 0 ) 
  	{ 
  	    for(int i = 0; i < (list.size()/2); i++) 
  	    {
  		result.add(list.get(list.size()/2+i));
  		result.add(list.get(list.size()/2-i-1));
  	    }
  	} 
  	else 
  	{ 
  	    result.add(list.get((list.size()-1)/2));
  	    for(int i = 0; i < ((list.size()-1)/2); i++) 
  	    {
  		result.add(list.get((list.size()-1)/2+i+1));
  		result.add(list.get((list.size()-1)/2-i-1));
  	    }
  	}

  	return result;
      }

}
