package stocks.testing_examples;

public class MainTest3 {



    private States state;


    public static void main(String[] args) {

	MainTest3 asd = new MainTest3();

	System.out.println(Modes.mode1);

	System.out.println(asd.state);
	
	asd.setState(States.COMPLETED);
	
	System.out.println(asd.state);
	
	asd.setState(States.REMOVED);
	
	System.out.println(asd.state);

	
	asd.setState(States.PENDING);
	
	System.out.println(asd.state);
	
	
	System.out.println(States.COMPLETED);
	System.out.println(States.COMPLETED.name()) ;
	 


	System.out.println(asd);
    }



    public States getState() {
	return state;
    }


    public void setState(States state) {
	this.state = state;
    }

    public enum Modes {
	mode1 ("Fancy Mode 1"),
	mode2 ("Fancy Mode 2"),
	mode3 ("Fancy Mode 3");

	private final String name;       

	private Modes(String s) {
	    name = s;
	}

	public boolean equals(String otherName) {
	    // (otherName == null) check is not needed because name.equals(null) returns false 
	    return name.equals(otherName);
	}

	public String toString() {
	    return this.name;
	}
    }

    public enum States{
	COMPLETED ("COMPLETED"),
	PENDING ("PENDING"),
	REMOVED ("REMVOED");

	public final String state;

	States(String string) {
	    state = string;
	}


	public String toString() {
	    return state;
	}

    }

}
