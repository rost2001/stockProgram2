package stocks.console;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import stocks.BuyWithRobotAwt;
import stocks.Order;
import stocks.Overview;
import stocks.Position;
import stocks.SellWithRobotAwt;
import stocks.Stock;
import stocks.image.processing.Image;
import stocks.interfaces.StockStateListener;

/* TODO:
 * Custom buy orders in beginning
 * Swedish support
 * Custom price sell
 * Account overview in beginning, and existing orders overview that were not bought during this runtime
 * Completed or pending order check
 * https://devalpha.io/
 * https://stockanalysis.com/
 * 
 * Buy and sell Swedish market
 * Buy and sell Extension + http server
 */



public class MainStockConsole implements StockStateListener, NativeKeyListener {


    static Overview overview = new Overview();

    static Stock viewingStock;

    static boolean hotkeysOn = false;


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
	int account = sc.nextInt();

	System.out.print("|Capital: ");
	while (!sc.hasNextInt()) {
	    System.out.println(
		    "Please enter an integer, a non decimal number of capital to invest each time of buy");
	    sc.next();
	}
	double capital = sc.nextInt();


	System.out.println("|Use data from the tradingview window instead of yahoo?");
	String tradingviewDataOnOff = "";
	do {

	    System.out.println("\"y\" or \"n\"");

	    tradingviewDataOnOff = sc.next();
	} while(!tradingviewDataOnOff.matches("y|n|Y|N"));


	boolean tradingviewData = false;

	if(tradingviewDataOnOff.matches("y|Y")) 
	    tradingviewData = true;



	sc.close();


	// --------------------------------------------------------
	// Setup
	viewingStock = new Stock("AAPL");

	viewingStock.updatePrice(new MainStockConsole(), 50, tradingviewData);
	viewingStock.updateSymbol(new MainStockConsole(), 50);
	
	
	overview.account = account;
	overview.investmentCapital = capital;
	

	consoleRefresh();
    }


    static void consoleRefresh(){


	// refreshes console by new lines, in case if one does not use cmd
	//   System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (InterruptedException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	System.out.println("___________________________________");
	System.out.println("|Robot Stock Program 1");
	System.out.println("|Profit: " + String.format("%.0f", overview.profit) + " SEK");
	System.out.println("|Accumulated fees: " + String.format("%.0f", overview.fees) + " SEK"); // Rounds to 0 decimals
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
	    System.out.println("|Buy at mouse price: alt + B + D");
	    System.out.println("|Sell: alt + number");
	    System.out.println("|Sell at mouse price: alt + number + D");
	    System.out.println("|Exit Program: Esc");
	    System.out.println("|__________________________________");

	}
	System.out.println("|__________________________________");
	System.out.println("|Buy");
	System.out.println("|Capital to invest: " + overview.investmentCapital);
	System.out.println("|Account: " + "#" + overview.account);
	System.out.println("|__________________________________");
	System.out.println("|Positions: " + overview.positions.size());
	System.out.println("|__________________________________");

	for (int i = 0; i < overview.positions.size(); i++) {

	    System.out.println("|__________________________________");
	    System.out.println("|Buy Order: #" + (i+1) + "   (Completed/Pending)");
	    System.out.println("|Symbol: " + overview.positions.get(i).openingOrder.stock.symbol);
	    System.out.println("|Price: " + overview.positions.get(i).openingOrder.price + " SEK");
	    System.out.println("|capital: " + overview.positions.get(i).openingOrder.capital + " SEK");
	    System.out.println("|Shares: " + overview.positions.get(i).openingOrder.shares + "st");
	    System.out.println("|account: #" + overview.positions.get(i).openingOrder.account);


	    double profitPoints = overview.positions.get(i).openingOrder.stock.price - overview.positions.get(i).openingOrder.price;
	    double profitProcentage = (overview.positions.get(i).openingOrder.stock.price / overview.positions.get(i).openingOrder.price - 1)*100;

	    System.out.println("|------|");
	    System.out.println("|Current Price: " + overview.positions.get(i).openingOrder.stock.price);
	    System.out.println("|Profit:  " + String.format("%.2f", profitPoints) + " (" + String.format("%.2f", profitProcentage) + "%)");
	    System.out.println("|__________________________________");

	    
	}
	
	    System.out.println("|__________________________________");
	    System.out.println("|Currently watching: \n"
	    	+ 	       "|" + viewingStock.symbol + ": " + viewingStock.price);

    }

    // --------------------------------------------------------
    // --------------------------------------------------------


    @Override
    public void onNewPrice(Stock stock) {
	consoleRefresh();
    }


    @Override
    public void onNewTradingviewWindow(Stock stock) {
	consoleRefresh();
    }


    // --------------------------------------------------------
    // NativeKeyListener:
    boolean altPressed = false;
    boolean ctrlPressed = false;
    boolean dPressed = false;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = true;
	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = true;
	if (e.getKeyCode() == NativeKeyEvent.VC_D)
	    dPressed = true;



	// Buy
	if (altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B) {

	    Position pos = new Position();
	    Order buy;

	    if(dPressed)
		buy = new BuyWithRobotAwt(new Stock(viewingStock.symbol, viewingStock.price), overview.investmentCapital, Image.getPriceAtMouse(), overview.account);
	    else
		buy = new BuyWithRobotAwt(new Stock(viewingStock.symbol, viewingStock.price), overview.investmentCapital, viewingStock.price, overview.account);


	    pos.open(buy);
	    overview.positions.add(pos);

	    consoleRefresh();

	}

	// Sell a registered buy
	if (altPressed == true 
		&& ( e.getKeyCode() >= 2 && e.getKeyCode() <= 10)
		) {

	    // keycodes are 1 more than index in value, like VC_1 has 0x02 in hexa, so -1
	    // and the first buy in the list has an index of 0 but corresponds to alt + 1,
	    // so another -1 to match
	    int index = e.getKeyCode() - 2;

	    Order sell;

	    // If no orders to sell then no sell
	    if (index < overview.positions.size()) {

		if (dPressed) // Sell instead at mouse price
		    sell = new SellWithRobotAwt((BuyWithRobotAwt) overview.positions.get(index).openingOrder, viewingStock.price);
		else
		    sell = new SellWithRobotAwt((BuyWithRobotAwt) overview.positions.get(index).openingOrder, Image.getPriceAtMouse());


		overview.positions.get(index).close(sell, overview);

	    }

	    consoleRefresh();

	}



	// Increment capital to invest 500, max last even 500 before total capital in account
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_A) {
	    overview.investmentCapital += 500;
	    consoleRefresh();
	}

	// Decrement capital to invest 500, min 500
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_Z && overview.investmentCapital > 500) {
	    overview.investmentCapital -= 500;
	    consoleRefresh();
	}

	// Increment account 1
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_S) {
	    overview.account += 1;
	    consoleRefresh();
	}
	// Decrement account 1
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_X && overview.account > 1) {
	    overview.account -= 1;
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

	// For when the key is no longer pressed
	if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
	    altPressed = false;
	if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
	    ctrlPressed = false;
	if (e.getKeyCode() == NativeKeyEvent.VC_D)
	    dPressed = false;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent ne) {
	// TODO Auto-generated method stub

    }



}
