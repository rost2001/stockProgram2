package stocks;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;

import com.sun.jna.platform.win32.WinDef.HWND;

import stocks.image.processing.ocr.MainOcrImage;
import stocks.system.RobotAwt;
import stocks.system.WindowsNative;

//A class where different methods of selling can be added
public class Sell {
	
	/*Checks to implement:
	 * 
	 * 
	 */

    	public double total = 0;
    	
    	Sell(double total){
    	    this.total = total;
    	}
    
    
	
	// Creates a window and goes through it by using tabbing
	// shares is what amount of shares to sell, if 0 then it means "All shares"
    	// Long code, but increase timeBetweenKeys and it will go slow and read the code as just steps
	public static Sell sellUsingNewWindow(Buy buy) {
		try {
		
		
		Process p = Runtime.getRuntime().exec("cmd");
		PrintWriter stdin = new PrintWriter(p.getOutputStream());
		stdin.println("start chrome "
			+ "--app=\"data:text/html,<html><body><script>window.moveTo(0,0);"
			+ "window.resizeTo(800,1000);"
			+ "window.location='https://www.avanza.se/ab/sok/inline?query=" + buy.stock.getSymbol() + "';"
			+ "</script></body></html>\"");
		stdin.close();
		p.waitFor();
		

		
		RobotAwt bot = new RobotAwt();
		// For some reason it is needed a click when repeating buy and sell
	    	Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
		Thread.sleep(300);
		bot.mouseMove(100, 10);
	    	bot.mouseClick(InputEvent.getMaskForButton(1),10);
	    	
	    	// Moves mouse back to where it was before
	    	bot.mouseMove(mouseInfo.x, mouseInfo.y);
	    	
	    	// Increase to increase compatibility with lag/slow computer
		int timeBetweenKeys = 10; 

		// Get windows title and wait for window to load until it is correct one
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
			do {
				continue;

			} while (!WindowsNative.getActiveWindowTitle().split("\\?")[0]
					.contains("https://www.avanza.se/ab/sok/inline"));

			return true;
		});
		
		
		// System.out.println(windowTitle.split("\\?")[0]);
		// System.out.println(windowTitle);

		// 3 tabs + enter
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
		
		
		

		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
			do {
				continue;

			} while (WindowsNative.getActiveWindowTitle().split("\\|").length != 3
					|| !WindowsNative.getActiveWindowTitle().split("\\|")[2].contains(" Avanza"));

			return true;
		});
		// System.out.println(windowTitle.split("\\|")[2]);
		// System.out.println(windowTitle);

		// 20 tabs + enter
		for (int i = 0; i < 20; i++) {
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		}
		bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
		
		

		// All shares
		if(buy.shares == 0) {
		    
		// Hardcoded coordinates of the "välj alla"
	    	bot.mouseMove(220, 385, timeBetweenKeys);
	    	bot.mouseClick(InputEvent.getMaskForButton(1), timeBetweenKeys);
	    	
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		}
		else {
		// Amount of Shares: 1 tab + amount
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.typeText(String.valueOf(buy.shares), timeBetweenKeys);
		}
		
		
		// price: 1 tab + price
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.typeText(String.valueOf(buy.stock.getQuote().getPrice().doubleValue()), 100);
		
		// Sell button: 2 tabs + enter
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
		

		// Sell: Screenshot + tab + enter igen
		Thread.sleep(500); // Increase if it does not work
		BufferedImage screenShot = bot.takeScreenshot(160,200,500,650);
		bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
		bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
		
		// Close window afterwards
		HWND activeWindow = WindowsNative.getActiveWindow();
		Thread.sleep(2000);
		WindowsNative.closeWindow(activeWindow);
		
		// Get info from the order validation popup
		String ocr = MainOcrImage.ocr(screenShot);
		double total = Double.parseDouble(ocr.split("SEK")[2].split("belopp")[1].replace(",", "."));
		
		return new Sell(total);
		} catch (IOException | InterruptedException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
