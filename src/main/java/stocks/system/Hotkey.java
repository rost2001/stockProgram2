package stocks.system;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/* Hotkey class, handles single hotkey and multiple hotkeys.
 * To use: Provide a NativeKeyEvent.* and a method for functional interface to call.
 * */
public class Hotkey implements NativeKeyListener{

    int[] keys;
    OnHotkey function;
    boolean[] pressedKeys;

    public interface OnHotkey {
	void run();
    }
    public Hotkey(OnHotkey function, int ...keys) {
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
	
	for(int i = 0; i < keys.length-1; i++){

	    if(ev.getKeyCode() == keys[i]) {
		pressedKeys[i] = true;
	    }
	}
	

	for(int i = 0; i < pressedKeys.length-1; i++){

	    if(pressedKeys[i] == false) {
		return;
	    }
	}
	
	if(ev.getKeyCode() == keys[keys.length-1])
	    function.run();

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent ev) {
	for(int i = 0; i < keys.length-1; i++){

	    if(ev.getKeyCode() == keys[i]) {
		pressedKeys[i] = false;
	    }
	}

    }

}
