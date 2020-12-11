package stocks.console;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import stocks.StockInfo;
import stocks.interfaces.ScheduledTasksListener;
import yahoofinance.Stock;

public class MainStockRobot1 implements ScheduledTasksListener, NativeKeyListener {

	static StockInfo stockInfo;

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

		GlobalScreen.addNativeKeyListener(new MainStockRobot1()); // adding this class to be used when an key event
																	// happens

		// --------------------------------------------------------
		// Setup
		
		stockInfo = new StockInfo(new MainStockRobot1(), "AAPL");
		stockInfo.updatePrice(200);
		stockInfo.updateStockWindow(200);

		/*
		 * 
		 * String symbol= "SNDL"; Process p = Runtime.getRuntime().exec("cmd");
		 * PrintWriter stdin = new PrintWriter(p.getOutputStream()); stdin.
		 * println("start chrome --new-window \"https://www.avanza.se/ab/sok/inline?query="
		 * + symbol + "\""); stdin.close(); p.waitFor();
		 * 
		 */
		// 2 tabs + enter

		// 20 + enter

		// Konto välj: 10 pilar up + # pilar ned till konto man vill ha + 2 enter

		// Pris: 3 tabs + pris

		// Kapital: 2 shift:tab + kapital

		// Köp knapp: 4 tabs + enter

		// Köp: enter igen
	}

	// --------------------------------------------------------
	// --------------------------------------------------------
	// ScheduledTasksListener:
	@Override
	public void onNewPrice(Stock stock) {

		// Two ways of accessing the object stock
		System.out.println(stockInfo.getStock());
		// or
		// System.out.println(stock);

	}

	@Override
	public void onNewTradingviewWindow(Stock stock) {

		System.out.println("New Stock: " + stockInfo.getStock());
		// or
		// System.out.println("New Stock: " + stock);

	}

	// --------------------------------------------------------
	// NativeKeyListener:
	boolean altPressed = false;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
			altPressed = true;

		// If alt has been pressed but not released + another key pressed
		if (altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B)
			System.out.println("Buy");

		if (altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_V)
			System.out.println("Sell");

		
		
		
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			System.exit(0); // Terminate program
		}

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

		// if alt is released before pressing the other key
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
			altPressed = false;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent ne) {
		// TODO Auto-generated method stub

	}
}
