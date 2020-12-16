package stocks.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import stocks.Buy;
import stocks.Sell;
import stocks.StockInfo;
import stocks.interfaces.ScheduledTasksListener;
import yahoofinance.Stock;

/* TODO:
 * Custom buy orders in beginning
 * Swedish support
 * Custom price sell
 * Acoount overview in beginning, and existing orders overview
 * 
 */

public class MainStockConsole implements ScheduledTasksListener, NativeKeyListener {

	static StockInfo stockInfo;
	
	static int account = 0;
	static int capital = 0;
	static double profit = 0;
	static double fees = 0;
	static String symbol = "AAPL";
	static boolean hotkeysOn = false;
	
	static List<Buy> buys = new ArrayList<Buy>();
	static Buy buy = null;
	static Sell sell = null;

	public static void main(String[] args) throws IOException, InterruptedException {

		// --------------------------------------------------------
		// Logging Configuartion:

		// Disable slf4j Logging
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

		// Disables Top4j logging
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger and turns it off
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// --------------------------------------------------------
		// Starting Keylistener:

		try {
			GlobalScreen.registerNativeHook(); // Connecting system libs with org.jnativehook lib
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new MainStockConsole()); // adding this class to be used when an key event happens
		

		// --------------------------------------------------------
		// Console input

		Scanner sc = new Scanner(System.in);


		System.out.println("			Welcome");
		System.out.println("___________________________________");
		System.out.println("|Robot Stock Program 1");

		System.out.print("|Account:");
		while (!sc.hasNextInt()) {
		    System.out.println("Please enter the account as they appears listed in a number between 1-10");
		    sc.next();
		}
		account = sc.nextInt();

		System.out.print("|Capital: ");
		while (!sc.hasNextInt()) {
		    System.out.println(
			    "Please enter an integer, a non decimal number of capital to invest each time of buy");
		    sc.next();
		}
		capital = sc.nextInt();

		/*

		System.out.print("|Starting Symbol?: leave blank for none");
		symbol = sc.nextLine();

*/
		System.out.println("|___________________________________");

		sc.close();

		consoleRefresh();

		// --------------------------------------------------------
		// Setup

        
		stockInfo = new StockInfo(new MainStockConsole(), symbol.toUpperCase());
		stockInfo.updatePrice(200);
		stockInfo.updateStockWindow(200);

		
		/*
		// Update console info every 1 seconds to get updates on price on the different buys
		Timer t = new Timer();
		TimerTask tt2 = new TimerTask() {
		    @Override
		    public void run() {
			
			consoleRefresh();
		    };
		};
		t.scheduleAtFixedRate(tt2, 0, 1000);

*/
	    }
	
	static ProcessBuilder p = new ProcessBuilder("cmd", "/c", "cls");
	static void consoleRefresh(){

	
	    // refreshes console by new lines, in case if one does not use cmd
	 //   System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	    try {
		p.inheritIO().start().waitFor();
	    } catch (InterruptedException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    System.out.println("___________________________________");
	    System.out.println("|Robot Stock Program 1");
	    System.out.println("|Profit: " + String.format("%.0f", profit) + " SEK");
	    System.out.println("|Accumulated fees: " + String.format("%.0f", fees) + " SEK"); // Rounds to 0 decimals
	    System.out.println("|Show Hotkeys on/off: ctrl + alt + K");
	    System.out.println("|__________________________________");

	    if(hotkeysOn) {
	    System.out.println("|__________________________________");
	    System.out.println("|Hotkeys");
	    System.out.println("|Increment Captital 500: ctrl + alt + A");
	    System.out.println("|Decrement Captital 500: ctrl + alt + Z");
	    /* Not implemented yet
	    System.out.println("|Select All Capital in account = ctrl + alt + Q");
	    */
	    System.out.println("|Increment Account: ctrl + alt + S");
	    System.out.println("|Decrement Account: ctrl + alt + X");
	    System.out.println("|Buy: alt + B");
	    System.out.println("|Sell: alt + number");
	    System.out.println("|Exit Program: Esc");
	    System.out.println("|__________________________________");

	    }
	    System.out.println("|__________________________________");
	    System.out.println("|Buy");
	    System.out.println("|Capital to invest: " + capital);
	    System.out.println("|Account: " + "#" + account);
	    System.out.println("|__________________________________");

	    for (int i = 0; i < buys.size(); i++) {

		System.out.println("|__________________________________");
		System.out.println("|Buy order: #" + (i+1) + "   (Completed)");
		System.out.println("|Symbol: " + buys.get(i).stock);
		//System.out.println("|Total: " + buys.get(i).total + " SEK");
		System.out.println("|Shares: " + buys.get(i).shares + "st");
		System.out.println("|account: #" + buys.get(i).account);
		System.out.println("|__________________________________");

	    }

	}

	// --------------------------------------------------------
	// --------------------------------------------------------
	// ScheduledTasksListener:
	@Override
	public void onNewPrice(Stock stock) {

	    // Two ways of accessing the object stock
	    System.out.print("\r" + stockInfo.getStock() + "                  ");
	    // or
	    // System.out.println(stock);

	}

	@Override
	public void onNewTradingviewWindow(Stock stock) {

		System.out.print("\rStock: " + stockInfo.getStock() + "                  ");
		// or
		// System.out.println("New Stock: " + stock);

	}

	// --------------------------------------------------------
	// NativeKeyListener:
	boolean altPressed = false;
	boolean ctrlPressed = false;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
			altPressed = true;
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
			ctrlPressed = true;
		
		
		if (ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_N)
		    System.out.println("Hello");
		    
		// Buy
		if (altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B) {
			 buy = Buy.buyUsingNewWindow(stockInfo.getStock(), capital, account);
			 buys.add(buy);
	
			 fees += buy.total * 0.005;// 0.0% fee every buy and sell
			 consoleRefresh();
		}
		
		// Sell a registered buy
		if (altPressed == true 
			&& ( e.getKeyCode() == NativeKeyEvent.VC_1
			|| e.getKeyCode() == NativeKeyEvent.VC_2
			|| e.getKeyCode() == NativeKeyEvent.VC_3
			|| e.getKeyCode() == NativeKeyEvent.VC_4
			|| e.getKeyCode() == NativeKeyEvent.VC_5
			|| e.getKeyCode() == NativeKeyEvent.VC_6
			|| e.getKeyCode() == NativeKeyEvent.VC_7
			|| e.getKeyCode() == NativeKeyEvent.VC_8
			|| e.getKeyCode() == NativeKeyEvent.VC_9)
			) {
		    
		    	// keycodes are 1 more than index in value, like VC_1 has 0x02 in hexa, so -1
		    	// and the first buy in the list has an index of 0 but corresponds to alt + 1, so another -1 to match
		    	int index = e.getKeyCode()-2;
		    
		    	if (index < buys.size()) { // checks if trying to sell something that doesnt exist
		        sell = Sell.sellUsingNewWindow(buys.get(index)); 
		        profit += sell.total - buys.get(index).total;
		        fees += sell.total * 0.005;
		        buys.remove(index);
		    	consoleRefresh();
		    	}
		}

		
		// Increment capital to invest 500, max last even 500 before total capital in account
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_A) {
			capital += 500;
		    	consoleRefresh();
		}
		
		// Decrement capital to invest 500, min 500
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_Z && capital > 500) {
			capital -= 500;
		    	consoleRefresh();
		}
		
		/* Not implemented yet
		// Sets capital to 0, 0 = "All of capital in account"
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_Q) {
			capital = 0;
		    	consoleRefresh();
		}
		*/
		
		
		// Increment account 1
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_S) {
		    	account += 1;
		    	consoleRefresh();
		}
		// Decrement account 1
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_X && account > 1) {
		    	account -= 1;
		    	consoleRefresh();
		}
		
		// Turn Hotkeys on/off
		if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_K) {
		    	if (hotkeysOn)
		    	    hotkeysOn = false;
		    	else
		    	    hotkeysOn = true;
		    	consoleRefresh();
		}
		
		
		
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			System.exit(0); // Terminate program
		}

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

		// if alt is released before pressing the other key
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
			altPressed = false;
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
			ctrlPressed = false;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent ne) {
		// TODO Auto-generated method stub

	}
}
