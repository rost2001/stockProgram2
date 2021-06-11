package stocks.model.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class UStrings {

    /* Removes unwanted strings from a list of strings.
     * */

    public static void removeUnwantedStrings(List <String> strings, String ...unwantedStrings){

	for(int i = 0; i < strings.size(); i++)
	    for(int n = 0; n < unwantedStrings.length; n++) {
		if(strings.get(i).contains(unwantedStrings[n])) {
		    strings.remove(i);
		}
	    }
    }



    /* Wordwrapping a text at a length of characters.
     * */
    public static List<String> wordWrap(List<String> words, int lengthInCharacters) {
	List<String> wrappedWords = new ArrayList<String>();
	int wordLengthCount = 0;
	for (int i = 0; i < words.size(); i++) {

	    wordLengthCount += words.get(i).length() + 1;
	    wrappedWords.add(words.get(i) + " ");

	    if(i+1 != words.size() && wordLengthCount + words.get(i+1).length() > 40) {
		wrappedWords.add(words.get(i) + "\n");
		wrappedWords.remove(i);
		wordLengthCount = 0;
	    }
	}

	return wrappedWords;
    }

    public static String round(String number, int places) {
	if(!number.contains(".") || places > number.split("\\.")[1].length()) return number;

	if(number.substring(number.length()-1).toLowerCase().contains("T".toLowerCase())
		||number.substring(number.length()-1).toLowerCase().contains("B".toLowerCase())
		||number.substring(number.length()-1).toLowerCase().contains("M".toLowerCase())
		||number.substring(number.length()-1).toLowerCase().contains("K".toLowerCase())
		)
	    return number.split("\\.")[0] +"."+ number.split("\\.")[1].substring(0, places) + number.substring(number.length()-1);
	else
	    return number.split("\\.")[0] +"."+ number.split("\\.")[1].substring(0, places);
    }
    
    public static String unfoldNumber(String stringInteger) {

	BigDecimal result;

	if (stringInteger.toLowerCase().contains("m")) {
	    stringInteger = stringInteger.replaceAll("[mM]", "");
	    result = new BigDecimal(stringInteger);
	    result = result.multiply(new BigDecimal("1000000"));
	} else if  (stringInteger.toLowerCase().contains("k")) {
	    stringInteger = stringInteger.replaceAll("[kK]", "");
	    result = new BigDecimal(stringInteger);
	    result = result.multiply(new BigDecimal("1000"));
	} else if (stringInteger.toLowerCase().contains("b")) {
	    stringInteger = stringInteger.replaceAll("[bB]", "");
	    result = new BigDecimal(stringInteger);
	    result = result.multiply(new BigDecimal("1000000000"));
	} else if (stringInteger.toLowerCase().contains("t")) {
	    stringInteger = stringInteger.replaceAll("[tT]", "");
	    result = new BigDecimal(stringInteger);
	    result = result.multiply(new BigDecimal("1000000000000"));
	} else {
	    result = new BigDecimal(stringInteger);
	}

	return result.toPlainString();
    }

    public static String foldNumber(String number) {

	String string = number;
	if(string.length() < 4) return string;
	
	int length;

	// Dot or no dot
	if (string.contains(".")) {
	    length = string.split("\\.")[0].length();

	} else {
	    length = string.length();
	}

	string = string.replaceAll("\\.", "");
	int places = length/3; // floored
	int rest = length - places*3; // ex: 7/3 = 1 in rest, 8/3 = 2 in rest 9/3 = 0 in rest

	// Move the "." every 3*places
	if (rest == 0) { 
	     string = string.substring(0,3)+"."+string.substring(3);
	     places-=1;
	} else {
	     string = string.substring(0,rest)+"."+string.substring(rest);
	}

	// Add T,B,M,K at end
	if (places == 4) {
	    string += "T";
	} else if(places == 3) {
	    string += "B";
	} else if(places == 2) {
	    string += "M";
	} else if(places == 1) {
	    string += "K";
	}
	
	return string;
    }

}
