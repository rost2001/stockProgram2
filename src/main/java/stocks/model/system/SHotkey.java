package stocks.model.system;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/* Hotkey class, handles single hotkey and multiple hotkeys.
 * To use: Provide a NativeKeyEvent.* and a method for functional interface to call.
 * */
public class SHotkey implements NativeKeyListener{

    int[] keys; // alla keys
    OnHotkey function;
    boolean[] pressedKeys; // samma som keys, fast en boolean för varje,

    public interface OnHotkey {
	void run();
    }
    public SHotkey(OnHotkey function, int ...keys) {
	this.keys = keys;
	this.function = function;
	this.pressedKeys = new boolean[keys.length];

	/* Tries Connecting system libs with org.jnativehook lib.
	 * If already connected, it skips.
	 */
	try {
	    GlobalScreen.registerNativeHook();
	} catch (NativeHookException ex) {
	    System.err.println("There was a problem registering the native hook.");
	    System.err.println(ex.getMessage());
	}

	GlobalScreen.addNativeKeyListener(this); // adding this class to be used when an key event happens
    }
    

    @Override
    public void nativeKeyTyped(NativeKeyEvent ev) {
	// TODO Auto-generated method stub

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent ev) {
	
	/* Om någon av de keys man har valt är nedtryckta,
	 * så registreras dom som ned tryckta,
	 * för att kunna kombinera flera keys som ctrl+alt+del,
	 * det körs en iteration per knapp tryck,
	 * så därför behövs det sparas till nästa iteration, */
	for(int i = 0; i < keys.length-1; i++){

	    if(ev.getKeyCode() == keys[i]) {
		pressedKeys[i] = true;
	    }
	}
	

	/* Om inte alla keys är nedtryckta så ska inget hända,
	 * som ctrl+alt, och inte del, så ska inte det gå igenom, */ 
	for(int i = 0; i < pressedKeys.length-1; i++){

	    if(pressedKeys[i] == false) {
		return;
	    }
	}
	
	/* Om till sist alla är nedtryckta,
	 * och den sista key stämmer också,
	 * så körs metoden/koden man har fört med in hit, */ 
	if(ev.getKeyCode() == keys[keys.length-1])
	    function.run();

    }

    @Override
    /* Ser när man släpper in key,
     * Är den en av de som man tidigare registrerat,
     * så avregistreras den, */
    public void nativeKeyReleased(NativeKeyEvent ev) {
	for(int i = 0; i < keys.length-1; i++){

	    if(ev.getKeyCode() == keys[i]) {
		pressedKeys[i] = false;
	    }
	}

    }

}
