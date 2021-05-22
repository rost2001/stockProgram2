package stocks.selenium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBot {

    static {
	System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver\\chromedriver.exe");
    }

    // options f�r att g�ra f�nstret osynligt
    public final static String USERAGENT = "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    public final static String HEADLESS = "--headless";
    public final static String DEFAULT_WINDOW_SIZE = "window-size=1050,1000"; // size used when I selected xpaths, may not work if other size


    
    // Options used in current instance, koma ih�g ifall om det beh�vs en omstart
    String[] options;

    // Huvudgrejjen, chromes drivrutin, den som pratar med chrome
    public WebDriver driver = null;


    public int timeout = 10;

    /**
     * Startar ChromeRobot med m�jliga chrome parameters(options). m�jliga options
     * finns att hitta p�:
     * 
     * <a href="https://peter.sh/experiments/chromium-command-line-switches">
     * https://peter.sh/experiments/chromium-command-line-switches</a>
     * 
     * @param chromeParameters
     * @throws IOException
     */
    public void start(PageLoadStrategy type, String... chromeOptions) {
	    
	options = chromeOptions;

	// l�gger till options
	ChromeOptions options = new ChromeOptions();
	for (String option : chromeOptions)
	    options.addArguments(option);
	
	options.setPageLoadStrategy(type);

	// Startar drivrutinen och skickar dom parameterna till chrome
	// doc info: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html
	this.driver = new ChromeDriver(options);
    }

    public void get(String url, long ...delay) {
	try {
	    if(!driver.getCurrentUrl().toLowerCase().contains(url.toLowerCase())) {

		driver.get(url);

		if(delay.length == 0)
		    Awaitility.await().atMost(timeout, TimeUnit.SECONDS).until(() -> {
			do {continue;} while (!driver.getCurrentUrl().toLowerCase().contains(url.toLowerCase())); return true;});
		else
		    Thread.sleep(delay[0]);


	    }

	} catch(Exception e) {
	    System.err.println("Invalid URL");
	    e.printStackTrace();
	}
    }



    // Gets elements on the page by xpath, puts them in a list
    // timeout wait change is due to that it messes up sometimes and waits when it shouldn't
    public List<WebElement> findElements(String xpath) throws Exception{
	List<WebElement> el = new ArrayList<WebElement>();

	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	Awaitility.await().atMost(timeout, TimeUnit.SECONDS).until(() -> {
	    do {continue;} while (driver.findElements(By.xpath(xpath)).size() == 0);return true;});
	el = driver.findElements(By.xpath(xpath));
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	return el;
    }

    public boolean elementExist(String xpath) {
	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	boolean exists = driver.findElements(By.xpath(xpath)).size() > 0;
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	return exists;
    }


    //  https://www.w3schools.com/tags/ref_attributes.asp
    public void modifyElement(WebElement element, String functionCall, String attr, String value) {
	
	JavascriptExecutor js = (JavascriptExecutor) this.driver;
	js.executeScript("arguments[0]."+functionCall+"('"+attr+"', '"+value+"')", element);
    }

    public void removeElement(WebElement element) {
	
	JavascriptExecutor js = (JavascriptExecutor) this.driver;
	js.executeScript("arguments[0].remove()", element);
    }

    public void createElement(WebElement parentElement, String elementType) {

	JavascriptExecutor js = (JavascriptExecutor) this.driver;
	js.executeScript("newElement = document.createElement(\""+elementType+"\");"
		+ "arguments[0].appendChild(newElement);", parentElement);
    }

    // Gets the text from an element and splits it into words
    public static List<String> getWords(WebElement el){
	String text = el.getAttribute("innerText"); // text
	String[] w = text.split(" |\t|\n"); // words as String[]
	List<String> words = Arrays.asList(w); // words as List
	return words;
    }


    // Closes the bot, important todo before program terminates
    // otherwise the windows process "Chromedriver.exe" still runs
    public void close() {
	driver.quit();
    }

    public void setSize(int x, int y) {
	driver.manage().window().setSize(new Dimension(x,y));
    }

    public void setTimeout(int timeout) {
	this.timeout = timeout;
    }

    public void maximizeWindow() {
	this.driver.manage().window().maximize();
    }

}