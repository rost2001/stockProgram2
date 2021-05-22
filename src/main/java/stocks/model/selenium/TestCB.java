package stocks.model.selenium;


import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

<<<<<<< Updated upstream:src/main/java/stocks/selenium/TestCB.java
import stocks.utils.ULogging;
=======
import stocks.model.utils.ULoggings;
>>>>>>> Stashed changes:src/main/java/stocks/model/selenium/TestCB.java


public class TestCB implements NativeKeyListener{


    static List<WebElement> el = new ArrayList<WebElement>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ChromeBot bot = new ChromeBot();

    /* A class that can be used to find and test xpaths 
     * 1.Start it, go to page, open f12
     * 2.use the arrow icon top left corner to find elements
     * 3.Use tag name and class name, into something like 
     * //div[contains(@class,'wrapper-asd3redtyyu lasd')] 
     * 4.press ctrl + alt + b to test it working
     * 
     */


    public static void main(String[] args) throws IOException, InterruptedException, AWTException {

	//-------------------------------------------------------
	ULogging.disableKeyLogging();


	try {
	    GlobalScreen.registerNativeHook();
	}
	catch (NativeHookException ex) {
	    System.err.println("There was a problem registering the native hook.");
	    System.err.println(ex.getMessage());

	    System.exit(1);
	}

	GlobalScreen.addNativeKeyListener(new TestCB());

	//-------------------------------------------------------


	bot.start(ChromeBot.USERAGENT,ChromeBot.DEFAULT_WINDOW_SIZE);


    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
	// TODO Auto-generated method stub

    }


    boolean ctrlPressed = false;
    boolean altPressed = false;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = true;
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = true;



	/* skriv en xpath och om den finns så visas ord för ord och index 2 dimensionellt
	 */
	if (ctrlPressed == true && altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B) {
	    try {

		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter line:");
		String str = "";

		str = br.readLine();

		//bot.setTimeout(1);
		if(bot.elementExist(str))
		el = bot.findElements(str);
		else {
		System.out.println("Could not find any element");
		return;
		}

		for(int i = 0; i < el.size(); i++) {
		    WebElement element = el.get(i);
		    
		    List<String> words = ChromeBot.getWords(element);

		    for(int n = 0; n < words.size(); n++) {
			System.out.println("["+i+"]" + "["+n+"]" + words.get(n));
		    }
		}



	    } catch (Exception ex) {
	    }

	}



	// click
	if(ctrlPressed == true && altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_A) {
		el.get(0).click();
	}




	if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
	    try {
		bot.close();
		GlobalScreen.unregisterNativeHook();
	    } catch (NativeHookException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}

    }



    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = false;
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = false;


    }





}
