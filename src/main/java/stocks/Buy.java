package stocks;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import stocks.image.processing.ocr.MainOcrImage;
import stocks.system.RobotAwt;
import stocks.system.WindowsNative;
import yahoofinance.Stock;

import org.awaitility.Awaitility;

import com.sun.jna.platform.win32.WinDef.HWND;



// A class where different methods of buying can be added
public class Buy {

	/*
	 * Checks/error handling to implement: 
	 * +If not enough money in account 
	 * +If order doesnt go through 
	 * +To remove order 
	 * +If it does something too fast and get stuck or get stuck for any other reason 
	 * +If there is a swedish stock with same symbol
	 * +If it doesnt find the stock in avanza
	 * +If it doesnt find the stock on yahoo
	 * +If Alerts on avanza about some problem
	 * 
	 */
    
	public Stock stock;
    	public int shares = 0;
    	public double total = 0;
    	public int account = 0;

    	public Buy(int shares, double total, int account, Stock stock){
    	    this.stock = stock;
    	    this.shares = shares;
    	    this.total = total;
    	    this.account = account;
    	}
    
    
	static String windowTitle;

	// Creates a window and goes through it by using tabbing
	public static Buy buyUsingNewWindow(Stock stock, int capital, int account) {
		try {


			Process p = Runtime.getRuntime().exec("cmd");
			PrintWriter stdin = new PrintWriter(p.getOutputStream());
			stdin.println("start chrome "
				+ "--app=\"data:text/html,<html><body><script>window.moveTo(0,0);"
				+ "window.resizeTo(800,1000);"
				+ "window.location='https://www.avanza.se/ab/sok/inline?query=" + stock.getSymbol() + "';"
				+ "</script></body></html>\"");
			stdin.close();
			p.waitFor();

			// --------------------------------------------------------

			
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

			//windowTitle = WindowsNative.getActiveWindowTitle();
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

			// 2 tabs + enter
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
			

			// Konto välj: 10 pilar up + # pilar ned till konto man vill ha + 2 enter
			for (int i = 0; i < 10; i++) {
				bot.keyPress(KeyEvent.VK_UP, 10);
			}

			
			for (int i = 0; i < account - 1; i++) {
				bot.keyPress(KeyEvent.VK_DOWN, timeBetweenKeys);
			}
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);


			// Pris: 3 tabs + pris
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.typeText(String.valueOf(stock.getQuote().getPrice().doubleValue()), 100);
			

			// Kapital: 2 shift:tab + kapital
			bot.keyPress(KeyEvent.VK_SHIFT);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_SHIFT);
			bot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			bot.keyPress(KeyEvent.VK_SHIFT);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_SHIFT);
			bot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			bot.typeText(String.valueOf(capital), timeBetweenKeys);
			

			// Köp knapp: 4 tabs + enter
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
			

			// Köp: Screenshot + tab + enter igen
			Thread.sleep(500); // Increase if it does not work
			BufferedImage screenshot = bot.takeScreenshot(160,200,500,650);
			bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
			bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
			
			// Close window afterwards
			HWND activeWindow = WindowsNative.getActiveWindow();
			Thread.sleep(2000);
			WindowsNative.closeWindow(activeWindow);

			// Get info from the order validation popup
		    	ImageIO.write(screenshot, "png", new File("E:\\Program\\stockProgram\\src\\main\\resources\\Images\\9.png"));
			String ocr = MainOcrImage.ocr(screenshot);
			System.out.println(ocr);
			int shares = Integer.parseInt(ocr.split("Antal ")[1].split("st")[0].replace(" ", ""));
			double total = Double.parseDouble(ocr.split("SEK")[2].split("belopp")[1].replace(",", ".").replace(" ", ""));
			
			
			return new Buy(shares, total + total*0.005 ,account, stock);
		} catch (IOException | InterruptedException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
