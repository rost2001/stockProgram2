package stocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.awaitility.Awaitility;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import stocks.data.Order;
import stocks.data.Overview;
import stocks.data.Position;
import stocks.data.Stock;
import stocks.data.interfaces.StockStateListener;
import stocks.selenium.ChromeBot;

/* TODO:
 * Custom buy orders in beginning
 * update price if you got in lower or higher than inputted
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


// ask for quick buy
// System.out.println("Please start your BankID app...");
// System.out.println("Logged in");
// bot.loginToAvanza();
/*
 * 	ChromeBot bot = new ChromeBot();
	bot.start(ChromeBot.useragent, ChromeBot.defaultWindowSize);
 */


/**
 * @author Fredde
 *
 */
public class MainStockConsole implements StockStateListener, NativeKeyListener {
    
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
    


    static Overview overview = new Overview();

    static Stock viewingStock;

    static boolean hotkeysOn = false;
    
    

    static Scanner sc = new Scanner(System.in);


    static ChromeBot sellBot = new ChromeBot();
    static ChromeBot buyBot = new ChromeBot();

    public static void main(String[] args) throws IOException, InterruptedException {
	// --------------------------------------------------------
	// For when the program terminates/exists/crashes/quits:
	
	Runtime.getRuntime().addShutdownHook(new Thread() {
	    @Override
	    public void run() {
		    buyBot.close();
		    sellBot.close();
		// Get all positions and save into a file
	        System.out.println("System was shutdown");
	    }
	});

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

	

	
	// --------------------------------------------------------
	// Setup
	
	System.out.print("Starting Buy bot...");
	buyBot.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	System.out.print("Trying to log in, please start BankID app...");
	//buyBot.loginToAvanza();
	System.out.println("OK!");

	
	System.out.print("Starting Sell bot...");
	sellBot.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	System.out.print("Trying to log in, please start BankID app...");
	//sellBot.loginToAvanza();
	System.out.println("OK!");
	
	
	
	viewingStock = new Stock("AAPL");

	viewingStock.updatePrice(250, tradingviewData, new MainStockConsole());
	viewingStock.updateSymbol(250, new MainStockConsole());
	
	
	//overview.account = account;
	//overview.investmentCapital = capital;

	//customOrderAndFile("e:/program/orders.txt");

	
	Timer t = new Timer();TimerTask tt2 = new TimerTask() {@Override public void run() {

		//----------------------------------------------------------

		consoleRefresh();

		//----------------------------------------------------------
	    };};t.scheduleAtFixedRate(tt2, 0, 500);



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
	
	String out = "";

	out += ("___________________________________");
	out += ("\n|Robot Stock Program 2");
	out += ("\n|Profit: " + String.format("%.0f", overview.profit) + " SEK");
	out += ("\n|Accumulated fees: " + String.format("%.0f", overview.fees) + " SEK"); // Rounds to 0 decimals
	out += ("\n|Show Hotkeys on/off: ctrl + alt + K");
	out += ("\n|__________________________________");

	if(hotkeysOn) {
	    out += ("\n|__________________________________");
	    out += ("\n|Hotkeys");
	    out += ("\n|Increment Captital 500: ctrl + alt + A");
	    out += ("\n|Decrement Captital 500: ctrl + alt + Z");
	    /* Not implemented yet
	    System.out.println("|Select All Capital in account = ctrl + alt + Q");
	     */
	    out += ("\n|Increment Account: ctrl + alt + S");
	    out += ("\n|Decrement Account: ctrl + alt + X");
	    out += ("\n|Buy: alt + B");
	    out += ("\n|Buy at mouse price: alt + B + D");
	    out += ("\n|Sell: alt + number");
	    out += ("\n|Sell at mouse price: alt + number + D");
	    out += ("\n|Exit Program: Esc");
	    out += ("\n|__________________________________");

	}
	out += ("\n|__________________________________");
	out += ("\n|Buy");
	//out += ("\n|Capital to invest: " + String.format("%.2f", overview.investmentCapital));
	out += ("\n|Account: " + "#" + overview.account);
	out += ("\n|__________________________________");
	out += ("\n|Positions: " + overview.positions.size());
	out += ("\n|__________________________________");

	for (int i = 0; i < overview.positions.size(); i++) {

	    out += ("\n|__________________________________");
	    out += ("\n|Buy Order: #" + (i+1) + "   (Completed/Pending)");
	    out += ("\n|symbol: " + overview.positions.get(i).openingOrder.stock.symbol);
	    out += ("\n|price: " + overview.positions.get(i).openingOrder.price + " USD");
	    out += ("\n|capital: " + String.format("%.2f", overview.positions.get(i).openingOrder.capital) + " SEK");
	    out += ("\n|shares: " + overview.positions.get(i).openingOrder.shares + "st");
	    out += ("\n|account: #" + overview.positions.get(i).openingOrder.account);


	    double profitPoints = overview.positions.get(i).openingOrder.stock.price - overview.positions.get(i).openingOrder.price;
	    double profitProcentage = (overview.positions.get(i).openingOrder.stock.price / overview.positions.get(i).openingOrder.price - 1)*100;

	    out += ("\n|------|");
	    out += ("\n|Current Price: " + overview.positions.get(i).openingOrder.stock.price);
	    
	    if(profitProcentage > 0) { //green

		out += ("\n|Profit:  " + ANSI_CYAN + String.format("%.2f", profitPoints) + ANSI_RESET + " (" + ANSI_CYAN +String.format("%.2f", profitProcentage) + "%" + ANSI_RESET + ")");
	    }else { //red

		out += ("\n|Profit:  " + ANSI_RED + String.format("%.2f", profitPoints) + ANSI_RESET + " (" + ANSI_RED +String.format("%.2f", profitProcentage) + "%" + ANSI_RESET + ")");
	    }
	    
	    out += ("\n|Capital: " + String.format("%.2f", (overview.positions.get(i).openingOrder.capital + (overview.positions.get(i).openingOrder.capital*(profitProcentage/100))) ) + " SEK");
	    out += ("\n|__________________________________");

	    
	}
	
	out += ("\n|__________________________________");
	out += ("\n|Currently watching: \n"
	    	+ 	       "|" + viewingStock.symbol + ": " + viewingStock.price);

	System.out.println(out);
    }


    public void customOrderAndInput() {

	//input
	System.out.println("			Custom Order");
	System.out.println("___________________________________");
	System.out.println("|");

	System.out.println("|Account:");
	while (!sc.hasNextInt()) {
	    System.out.println("Please enter the account as they appears listed in a number between 1-10");
	    sc.next();
	}
	int account = sc.nextInt();

	System.out.println("|Symbol:");
	String symbol = "";
	sc.nextLine();
	symbol = sc.nextLine();

	System.out.println("|#Shares:");
	while (!sc.hasNextInt()) {
	    System.out.println("Please enter the number of shares");
	    sc.next();
	}
	int shares = sc.nextInt();


	System.out.println("|Invested Capital: ");
	while (!sc.hasNextDouble()) {
	    System.out.println("Please enter the amount of capital invested");
	    sc.next();
	}
	double capital = sc.nextDouble();


	System.out.println("|Price bought in at: ");
	while (!sc.hasNextDouble()) {
	    System.out.println(
		    "Please enter the price you bought in at");
	    sc.next();
	}
	double price = sc.nextDouble();


	customOrder(symbol, shares, capital, price, account);
    }



    // Method for entering custom(existing) orders so that they can be sold through the program
    public static void customOrder(String symbol, int shares, double capitalInvested, double buyPrice, int account) {

	// custom making order without placing it
	Position pos = new Position();
	Order buy = null;

	//buy = new ChromeBotOrder(new Stock(symbol), capitalInvested, buyPrice, account, buyBot);

	buy.fee = capitalInvested * 0.005;
	buy.shares = shares;

	pos.openingOrder = buy;
	pos.fees += buy.fee;
	overview.positions.add(pos);


	pos.openingOrder.stock.updatePrice(200, true, new MainStockConsole());

	consoleRefresh();

    }


    public static void customOrderAndFile(String path) {
	try {

	    boolean fileExist = false;
	    File f;

	    /*
	    // Clears the input buffer first, get rid of stored hotkeys
	    sc.nextLine();

	    // input
	    do {
		System.out.println("Please enter the file Path:");
		f = new File(sc.nextLine());

		if (!f.getAbsolutePath().contains(".txt"));
		f = new File(f.getAbsolutePath() + ".txt");


		if(f.isFile())
		    fileExist = true;
		else
		    System.out.println("Couldn't find file.");
	    } while(!fileExist);

*/

	    // Parsing 
	    Files.lines(Path.of(new File(path).getAbsolutePath())).forEach(str -> {

		String symbol = str.split("Symbol:")[1].split(" ")[0];
		int shares = Integer.parseInt(str.split("Shares:")[1].split(" ")[0]);
		double capital = Double.parseDouble(str.split("Capital:")[1].split(" ")[0]);
		double price = Double.parseDouble(str.split("Price:")[1].split(" ")[0]);
		int account = Integer.parseInt(str.split("Account:")[1].split(" ")[0]);

		customOrder(symbol, shares, capital, price, account);
	    });
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    // --------------------------------------------------------
    // --------------------------------------------------------


    @Override
    public void onNewPrice(double price) {
    }


    @Override
    public void onNewSymbol(String symbol) {
	    try {
	    List<WebElement> el = new ArrayList<WebElement>();
	    // Söker efter aktien
	    buyBot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + symbol);
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (buyBot.findElements("//a").size() == 0);return true;
	    });

	    // Väljer Köp

		el = buyBot.findElements("//a");

	    el.get(1).sendKeys(Keys.ENTER);
	    
	    
	    List<WebElement> el2 = new ArrayList<WebElement>();
	    // Söker efter aktien
	    sellBot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + symbol);
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (sellBot.findElements("//a").size() == 0);return true;
	    });

	    // Väljer Sälj
	    el2 = sellBot.findElements("//a");
	    el2.get(2).sendKeys(Keys.ENTER);
	    
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
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
	if (altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B ) {

	    Position pos = new Position();
	    Order buy;

	   // if(dPressed)
		//buy = new ChromeBotOrder(new Stock(viewingStock.symbol, viewingStock.price), overview.investmentCapital, Image.getPriceAtMouse(), overview.account, buyBot);
	  //  else
	//	buy = new ChromeBotOrder(new Stock(viewingStock.symbol, viewingStock.price), overview.investmentCapital, viewingStock.price, overview.account, buyBot);


	//    pos.open(buy);
	    overview.positions.add(pos);
	    
	    pos.openingOrder.stock.updatePrice(200, true, new MainStockConsole());
	    
	    onNewSymbol(viewingStock.symbol);
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
	/*    if (index < overview.positions.size()) {

		if (dPressed) // Sell instead at mouse price
		    sell = new ChromeBotSell((ChromeBotOrder) overview.positions.get(index).openingOrder, Image.getPriceAtMouse(), sellBot);
		else
		    sell = new ChromeBotSell((ChromeBotOrder) overview.positions.get(index).openingOrder, viewingStock.price, sellBot);



		
		overview.positions.get(index).close(sell, overview);

		    overview.positions.get(index).openingOrder.stock.stopPriceUpdating();

		overview.investmentCapital = sell.capital;
		
		    onNewSymbol(viewingStock.symbol);*/
	    }


	



	// Increment capital by 1
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_A) {
	//    overview.investmentCapital += 1;
	}

	// Decrement capital by 1
	//if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_Z && overview.investmentCapital > 100) {
	  //  overview.investmentCapital -= 1;
	//}

	/*
	 * // Increment account 1 if (altPressed == true && ctrlPressed == true &&
	 * e.getKeyCode() == NativeKeyEvent.VC_S) { overview.account += 1; } //
	 * Decrement account 1 if (altPressed == true && ctrlPressed == true &&
	 * e.getKeyCode() == NativeKeyEvent.VC_X && overview.account > 1) {
	 * overview.account -= 1; }
	 * 
	 */
	// Remove position on interface only
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_R) {

	    int index = 0;

	    /*
	     * if(index < overview.positions.size()) {
	     * overview.positions.get(index).openingOrder.stock.stopPriceUpdating();
	     * overview.positions.remove(index); onNewSymbol(viewingStock.symbol); } }
	     */
	}
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_T) {

	 
	    /*
	     * overview.positions.add(overview.oldPositions.get(overview.oldPositions.size()
	     * -1));
	     * 
	     * overview.positions.get(0).openingOrder.stock.updatePrice(200, true, new
	     * MainStockConsole()); onNewSymbol(viewingStock.symbol);
	     */
	}
	
	
	
	// Enter custom order from input
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_Q) {
	    customOrderAndInput();
	}

	// Enter custom order from file
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_W) {
	   // customOrderAndFile();
	}



	// Turn Hotkeys on/off
	if (altPressed == true && ctrlPressed == true && e.getKeyCode() == NativeKeyEvent.VC_K) {
	    if (hotkeysOn)
		hotkeysOn = false;
	    else
		hotkeysOn = true;
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
