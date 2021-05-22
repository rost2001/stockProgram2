package stocks.utils;

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

}
