package stocks.testing_examples;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import stocks.ChromeBot;

public class ListenerMainClass implements WebDriverEventListener, NativeKeyListener{

    // https://medium.com/quick-code/understanding-webdriver-events-e58124575edd

    
    
    

    public static void main(String[] args) throws Exception {

	
	
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

	GlobalScreen.addNativeKeyListener(new ListenerMainClass());
	
	
	ChromeBot bot = new ChromeBot();
	bot.start(ChromeBot.useragent, ChromeBot.defaultWindowSize);
	
	EventFiringWebDriver eventFiringDriver = new EventFiringWebDriver(bot.driver);
	ListenerMainClass eventListener = new ListenerMainClass();


	
	    eventFiringDriver.register(eventListener);
	    eventFiringDriver.get("http://www.google.com");
	    eventFiringDriver.get("http://www.facebook.com");
	    eventFiringDriver.navigate().back();
	    
	    

    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
	// TODO Auto-generated method stub

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
	// TODO Auto-generated method stub

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
	System.out.println("Before");
	
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
	System.out.println("After");
	
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
	// TODO Auto-generated method stub
	
    }
}