package stocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeBot {

    static {
	System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver\\chromedriver.exe");
    }

    // options för att göra fönstret osynligt
    public final static String useragent = "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    public final static String headless = "--headless";
    public final static String defaultWindowSize = "window-size=1050,1000";

    // Options used in current instance, koma ihåg ifall om det behövs en omstart
    String[] options;

    // Huvudgrejjen, chromes drivrutin, den som pratar med chrome
    public WebDriver driver = null;

    
    
    /**
     * Startar ChromeRobot med möjliga chrome parameters(options). möjliga options
     * finns att hitta på:
     * 
     * <a href="https://peter.sh/experiments/chromium-command-line-switches">
     * https://peter.sh/experiments/chromium-command-line-switches</a>
     * 
     * @param chromeParameters
     * @throws IOException
     */
    public void start(String... chromeOptions) {

	options = chromeOptions;

	// lägger till options
	ChromeOptions options = new ChromeOptions();
	for (String option : chromeOptions)
	    options.addArguments(option);

	// Startar drivrutinen och skickar dom parameterna till chrome
	// doc info: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html
	this.driver = new ChromeDriver(options);
    }

    public void loginToAvanza(String personnummer)  {
	try { 
	    driver.get("https://www.avanza.se");
	    // godkänna cookies knapp
	    List<WebElement> el = new ArrayList<WebElement>();
	    el = findElements("//button[contains(@class,'ng-tns-c44-3 aza-button cta-primary')]");
	    el.get(0).sendKeys(Keys.ENTER);

	    // login in knapp
	    el = findElements("//button[contains(@class,'aza-button aza-text-button aza-text-primary ng-star-inserted')]");
	    el.get(0).sendKeys(Keys.ENTER);

	    // personnummer fält
	    el = findElements( "/html/body/aza-app/aza-right-overlay-area/aside/ng-component/aza-login-overlay/aza-right-overlay-template/main/div/aza-login/div/aza-toggle-switch-view/div/aza-bank-id/form/div[1]/div/input");
	    el.get(0).sendKeys(personnummer);

	    // mobilt bankid knapp
	    el = findElements( "/html/body/aza-app/aza-right-overlay-area/aside/ng-component/aza-login-overlay/aza-right-overlay-template/main/div/aza-login/div/aza-toggle-switch-view/div/aza-bank-id/form/div[1]/div/button[1]");
	    el.get(0).sendKeys(Keys.ENTER);

	    // Kollar sidan efter inloggning
	    new WebDriverWait(driver, 200).until(ExpectedConditions.urlToBe("https://www.avanza.se/hem/senaste.html"));
	} catch (Exception e) { // ifall om det misslyckas
	    driver.quit(); // stops the current instance
	    this.start(options); // restarts it
	    return;
	}
    }

   
    // Gets elements on the page by xpath, puts them in a list
    // timeout wait change is due to that it messes up sometimes and waits when it shouldn't
    public List<WebElement> findElements(String xpath){
	    List<WebElement> el = new ArrayList<WebElement>();
	try { 

	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> {
		do {continue;} while (driver.findElements(By.xpath(xpath)).size() == 0);return true;});
	    el = driver.findElements(By.xpath(xpath));
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);


	}catch(Exception e) {
	    return el;
	}
	return el;
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

}