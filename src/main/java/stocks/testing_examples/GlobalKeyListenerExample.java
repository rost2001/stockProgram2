package stocks.testing_examples;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class GlobalKeyListenerExample implements NativeKeyListener, NativeMouseListener {
	
	// info: https://github.com/kwhat/jnativehook
	
	
	boolean ctrl = false;	// for control + other key checks. If control has been pressed but not released yet
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) 
			ctrl = true;
		
		
		if (ctrl == true && e.getKeyCode() == NativeKeyEvent.VC_B) 
			System.out.println("Buy");
		
		if (ctrl == true && e.getKeyCode() == NativeKeyEvent.VC_S) 
			System.out.println("Sell");
		
		
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		
		
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
	
	public void nativeKeyReleased(NativeKeyEvent e) {
		
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) 
			ctrl = false;
		
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	
	
	public void nativeKeyTyped(NativeKeyEvent e) {
		System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	
	
	public static void main(String[] args) {
		
		// info: https://stackoverflow.com/questions/30560212/how-to-remove-the-logging-data-from-jnativehook-library
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		
		
		
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
		GlobalScreen.addNativeMouseListener(new GlobalKeyListenerExample());
	}



	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		System.out.println("Mouse Clicked: " + e.getButton());
		
	}



	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println("Mouse Pressed: " + (e.getButton()));
		
	}



	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		System.out.println("Mouse Released: " + (e.getButton()));
		
	}
}