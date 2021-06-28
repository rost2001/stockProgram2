package stocks.controller;

import java.util.ArrayList;
import java.util.List;

import stocks.model.utilities.USort;

public class TestListMidSort {

    public static void main(String[] args) {
	List<Integer> list = new ArrayList<Integer>();

	list.add(1);
	list.add(2);
	list.add(3);
	list.add(4);
	list.add(5);
	list.add(6);
	list.add(7);
	list.add(8);
	list.add(9);
	list.add(10);
	list.add(11);
	list.add(12);
	
	// even/odd
	list.add(13);
	
	list = USort.midPointOutwardSort(list);
	
	for(int n : list)
	    System.out.println(n);
	
	
	
    }

}
