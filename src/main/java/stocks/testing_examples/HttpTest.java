package stocks.testing_examples;


import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import stocks.ChromeBot;


public class HttpTest implements NativeKeyListener{
	/*
	 * https://humanwhocodes.com/blog/2010/05/25/cross-domain-ajax-with-cross-origin-resource-sharing/
	 * https://stackoverflow.com/questions/43951254/can-i-send-message-from-chrome-extension-to-java-server
	 * https://developer.chrome.com/docs/extensions/reference/?utm_source=tool.lu
	 * https://developer.chrome.com/docs/extensions/reference/windows/
	 * 
	 * https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo?hl=sv
	 */
	
	
	
	   private static WebDriver driver;
	   static List<WebElement> el = new ArrayList<WebElement>();
		static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   static {
	       System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver\\chromedriver.exe");
	   }

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
	    
	    
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

		GlobalScreen.addNativeKeyListener(new HttpTest());
		
		
	    /*
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://finance.yahoo.com/quote/TOPS"))
				.build();
		
		
		HttpClient client = HttpClient.newHttpClient();
		/* async version
		
		client.sendAsync(request, BodyHandlers.ofString())
		
	      .thenApply(response -> { System.out.println(response.statusCode()); return response; } )
	      
	      
		      .thenApply(HttpResponse::body)
		      
		      .thenApply(response -> {System.out.println(response); return response;} )
		      
		      .join();

		
		long startTime = System.nanoTime();
		
		HttpResponse<String> response =
			      client.send(request, BodyHandlers.ofString());
		long elapsedTime = System.nanoTime() - startTime;
		
        
			System.out.println(response.statusCode());
			System.out.println(response.body().split("Ov(h) Pend(14%) Pend(44px)--sm1024")[1].split("Span")[1]);
		
			System.out.println("***** " + elapsedTime/1000000 + " ms");
			
			
			
			
			
			URL url = new URL("https://www.nasdaq.com/market-activity/stocks/ocgn");
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			
			*/
			
			
			// info https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html


	    long startTime = System.nanoTime();


	    String useragent = "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
	    String headless = "--headless";
	    start("user-agent=","window-size=1050,1000");


	    long elapsedTime = System.nanoTime() - startTime;	

	    System.out.println("Time to start");
	    System.out.println(elapsedTime/1000000 + " ms");



	     driver.get("https://www.avanza.se");
	     


	     startTime = System.nanoTime();
	     System.out.println("webdriver - Sök på Google".equals(driver.getTitle()));
	     System.out.println(driver.getTitle());

	     String asd=  driver.getWindowHandle();

	     System.out.println(asd);

	     System.out.println();
	     WebElement el2 = null; String str = "";
	     try {
		 el2 = driver.findElement(By.xpath("//a"));

		 str = el2.getText();
	     }catch(Exception e) {
		    driver.quit();

		 e.printStackTrace();
	     }


	     System.out.println(str);



	     elapsedTime = System.nanoTime() - startTime;	
	     System.out.println("Time");
	     System.out.println(elapsedTime/1000000 + " ms");


	     startTime = System.nanoTime();


	     elapsedTime = System.nanoTime() - startTime;	
	     System.out.println("Time");
	     System.out.println(elapsedTime/1000000 + " ms");



	
	}

	public static WebElement findElement(String str) {
	    WebElement el = null;
	    try {
		 el = driver.findElement(By.xpath(str));

		 return el;
		 
	     }catch(Exception e) {
		// stop();

		 e.printStackTrace();
	     }
	    return el;

	}




	/**
	 * Startar ChromeRobot med möjliga chrome parameters(options).
	 * möjliga options finns att hitta på:  
	 * 
	 * <a href="https://peter.sh/experiments/chromium-command-line-switches">
	 * https://peter.sh/experiments/chromium-command-line-switches</a>
	 * @param chromeParameters
	 * @throws IOException
	 */
	public static void start(String ...chromeOptions) throws IOException {

	    // lägger till options
	    ChromeOptions options = new ChromeOptions();
	    for (String option : chromeOptions)
		options.addArguments(option);

	    // Startar drivrutinen och skickar dom parameterna till chrome
	    // doc info https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html
	    driver = new ChromeDriver(options);
	}


	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	    // TODO Auto-generated method stub
	    
	}


	boolean ctrlPressed = false;
	boolean altPressed = false;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

	    if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
		ctrlPressed = true;
	    if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
		altPressed = true;
	    
	    

	    if (ctrlPressed == true && altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_B) {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter line:");
		String str = "";
		try {
		    str = br.readLine();
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}


		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		el = driver.findElements(By.xpath(str));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		if(el.size() != 0) {
		    for(int i = 0; i < el.size(); i++) {
			WebElement element = el.get(i);
			//System.out.print("["+i+"]");
			List<String> words = ChromeBot.getWords(element);

			for(int n = 0; n < words.size(); n++) {
			    System.out.println("["+i+"]" + "["+n+"]" + words.get(n));
			}
		    }

		}else {
		    System.out.println("Could not find any element");
		}
	    }




	    if(ctrlPressed == true && altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_V) {
		driver.get("https://www.avanza.se/ab/sok/inline?query=tops");
	    }

	    if(ctrlPressed == true && altPressed == true && e.getKeyCode() == NativeKeyEvent.VC_A) {
		if(el.size() != 0)
		el.get(0).sendKeys(Keys.ENTER);
	    }
	    

	    
	    

	    if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
		try {
		    driver.quit();
		    GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }

	}






	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

	    if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
		ctrlPressed = false;
	    if (e.getKeyCode() == NativeKeyEvent.VC_ALT)
		altPressed = false;


	}




	
}
