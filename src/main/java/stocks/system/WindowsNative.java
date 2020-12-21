package stocks.system;



import static stocks.system.WindowsNative.User32DLL.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;


public class WindowsNative {
	
	private static final int MAX_TITLE_LENGTH = 1024;


		// https://stackoverflow.com/questions/6391439/getting-active-window-information-in-java
		// https://stackoverflow.com/questions/18900736/what-are-native-methods-in-java-and-where-should-they-be-used

		// https://stackoverflow.com/questions/58026937/how-do-i-read-the-contents-from-an-open-windows-console-command-prompt-using-j
		// https://stackoverflow.com/questions/56542792/sending-keystrokes-to-hidden-window-via-jna

	public static String getActiveWindowTitle(HWND window) {
		
		char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        GetWindowTextW(window, buffer, MAX_TITLE_LENGTH);
        
		return String.valueOf(buffer);
	}
	
	
	public static HWND getActiveWindow() {

		return GetForegroundWindow();
	}
	
	
	public static void closeWindow(HWND window) {
        // info: https://stackoverflow.com/questions/955248/is-it-possible-to-end-a-process-nicely-in-a-java-application
        int WM_CLOSE = 0x10;
        User32.INSTANCE.PostMessage(window, WM_CLOSE, new WinDef.WPARAM(), new WinDef.LPARAM());
	}
	
	
	/*
	static class Psapi {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }
    */
    

    static class User32DLL {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native HWND GetForegroundWindow();
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    }

}
