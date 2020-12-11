package stocks.testing_examples;

public class MainTesting {


	    
	    public static void main(String[] args) 
	    { 
	       
	    	
	    	
	    	int n = 0;
	    	System.out.println(n);
	    	changeN(n);
	    	System.out.println(n);
	    	
	    	int n2[] = {0, 1};
	    	System.out.println(n2[0] +" " + n2[1]);
	    	changeN2(n2);
	    	System.out.println(n2[0] +" " + n2[1]);
	    }

		private static void changeN2(int[] n2) {
			// I can never change the thing it self so that it also changes outside of the method scope
			// but I can change whats inside of the thing and then it will also have changed outside of the method scope
			
			//n2 = new int[]{1, 1}; // doesnt work
			
			n2[0] = 1; // does work
			
			
			// As to why 
			//https://softwareengineering.stackexchange.com/questions/286008/parameters-are-passed-by-value-but-editing-them-will-edit-the-actual-object-li
			
		}

		private static void changeN(int n) {
			n = 1;
			
		}

}
