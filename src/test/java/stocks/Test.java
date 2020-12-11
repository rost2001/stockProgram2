package stocks;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		 List<Long> list = new ArrayList<Long>();

		    int i= 0;
		    while(i <=100){

		        long authStartTime = System.nanoTime();
		        Thread.sleep(0);
		        long elapsedTime = System.nanoTime() - authStartTime;
		        System.out.println("*****"+elapsedTime/1000+ " mics");
		        list.add(elapsedTime);
		        i++;
		    }

		    long sum=0;
		    for(long l : list){
		        sum = sum + l;
		    }
		    System.out.println("********* Average elapsed time = "+sum/list.size()/1000+" micro seconds");
		    System.exit(0);

		print(1,2);

	}

	public static void print(int... a) {
		System.out.println("asd");

		for (int i : a)
			System.out.print(i + " ");
		
		if (a.length != 0)
			System.out.print("\n" + a[0] + " ");
		}
	}

