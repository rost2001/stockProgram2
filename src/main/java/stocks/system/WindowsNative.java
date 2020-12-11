package stocks.system;



import static stocks.system.WindowsNative.User32DLL.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;


public class WindowsNative {
	
	private static final int MAX_TITLE_LENGTH = 1024;
	


		// https://stackoverflow.com/questions/6391439/getting-active-window-information-in-java
		// https://stackoverflow.com/questions/18900736/what-are-native-methods-in-java-and-where-should-they-be-used


  
	public static String getActiveWindowTitle() {
		
		char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
		
		return String.valueOf(buffer);
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
