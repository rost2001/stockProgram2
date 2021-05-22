package stocks.model.utils;

public class UConsoles {

    
    
    public static String fillSpace(String stringPiece, int fullLength) {

	int missingLength = fullLength - stringPiece.length();
	
	for (int n = 0; n < missingLength; n++)
	    stringPiece+= " ";
	
	return stringPiece;
    }
    
    
}
