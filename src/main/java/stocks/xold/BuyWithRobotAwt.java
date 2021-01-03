package stocks.xold;

public class BuyWithRobotAwt{
    
}
/*
 * import java.awt.AWTException; import java.awt.MouseInfo; import
 * java.awt.Point; import java.awt.event.InputEvent; import
 * java.awt.event.KeyEvent; import java.awt.image.BufferedImage; import
 * java.io.IOException; import java.io.PrintWriter; import
 * java.util.concurrent.TimeUnit;
 * 
 * import stocks.Account; import stocks.Order; import stocks.Stock; import
 * stocks.image.processing.ocr.MainOcrImage; import stocks.system.RobotAwt;
 * import stocks.system.WindowsNative;
 * 
 * import org.awaitility.Awaitility;
 * 
 * import com.sun.jna.platform.win32.WinDef.HWND;
 * 
 * 
 * 
 * public class BuyWithRobotAwt extends Order {
 * 
 * Checks/error handling to implement: +If not enough money in account +If order
 * doesnt go through +To remove order +If it does something too fast and get
 * stuck or get stuck for any other reason +If there is a swedish stock with
 * same symbol +If it doesnt find the stock in avanza +If it doesnt find the
 * stock on yahoo +If Alerts on avanza about some problem
 * 
 * 
 * 
 * public BuyWithRobotAwt(Stock stock, int shares, double price, Account
 * account) { this.stock = stock; this.shares = shares; this.price = price;
 * this.account = account;
 * 
 * this.capital = 0; }
 * 
 * 
 * public BuyWithRobotAwt(Stock stock, double capital, double price, Account
 * account) { this.stock = stock; this.capital = capital; this.price = price;
 * this.account = account;
 * 
 * this.shares = 0; }
 * 
 * 
 * @Override public void place() { account number not implemented, needs to be
 * checked when choosing account(ocr) try {
 * 
 * Process p = Runtime.getRuntime().exec("cmd"); PrintWriter stdin = new
 * PrintWriter(p.getOutputStream()); stdin.println("start chrome " +
 * "--app=\"data:text/html,<html><body><script>window.moveTo(0,0);" +
 * "window.resizeTo(800,1000);" +
 * "window.location='https://www.avanza.se/ab/sok/inline?query=" + stock.symbol
 * + "';" + "</script></body></html>\""); stdin.close(); p.waitFor();
 * 
 * // --------------------------------------------------------
 * 
 * 
 * RobotAwt bot = new RobotAwt(); // For some reason it is needed a click when
 * repeating buy and sell Point mouseInfo =
 * MouseInfo.getPointerInfo().getLocation(); Thread.sleep(400);
 * bot.mouseMove(100, 10); bot.mouseClick(InputEvent.getMaskForButton(1),10);
 * 
 * // Moves mouse back to where it was before bot.mouseMove(mouseInfo.x,
 * mouseInfo.y);
 * 
 * // Increase to increase compatibility with lag/slow computer int
 * timeBetweenKeys = 10;
 * 
 * // Get windows title and wait for window to load until it is correct one
 * Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> { do { continue;
 * 
 * } while
 * (!WindowsNative.getActiveWindowTitle(WindowsNative.getActiveWindow()).split(
 * "\\?")[0] .contains("https://www.avanza.se/ab/sok/inline"));
 * 
 * return true; });
 * 
 * 
 * // 2 tabs + enter bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * 
 * 
 * 
 * Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> { do { continue;
 * 
 * } while
 * (WindowsNative.getActiveWindowTitle(WindowsNative.getActiveWindow()).split(
 * "\\|").length != 3 ||
 * !WindowsNative.getActiveWindowTitle(WindowsNative.getActiveWindow()).split(
 * "\\|")[2].contains(" Avanza"));
 * 
 * return true; }); // System.out.println(windowTitle.split("\\|")[2]); //
 * System.out.println(windowTitle);
 * 
 * // 20 tabs + enter for (int i = 0; i < 20; i++) {
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys); }
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * 
 * // Konto välj: 10 pilar up + # pilar ned till konto man vill ha + 2 enter for
 * (int i = 0; i < 10; i++) { bot.keyPress(KeyEvent.VK_UP, 10); }
 * 
 * 
 * for (int i = 0; i < account - 1; i++) { bot.keyPress(KeyEvent.VK_DOWN,
 * timeBetweenKeys); } bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * 
 * // Pris: 3 tabs + pris bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys); bot.keyPress(KeyEvent.VK_TAB,
 * timeBetweenKeys); bot.typeText(String.valueOf(price), 100);
 * 
 * 
 * if (capital > 0) { // Kapital: 2 shift:tab + kapital
 * bot.keyPress(KeyEvent.VK_SHIFT); bot.keyPress(KeyEvent.VK_TAB);
 * bot.keyRelease(KeyEvent.VK_SHIFT); bot.keyRelease(KeyEvent.VK_TAB);
 * Thread.sleep(100); bot.keyPress(KeyEvent.VK_SHIFT);
 * bot.keyPress(KeyEvent.VK_TAB); bot.keyRelease(KeyEvent.VK_SHIFT);
 * bot.keyRelease(KeyEvent.VK_TAB); Thread.sleep(100);
 * bot.typeText(String.valueOf(capital), timeBetweenKeys);
 * 
 * } else { // use shares
 * System.err.println("Buying with shares is not implemented yet"); }
 * 
 * // Köp knapp: 4 tabs + enter bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys); bot.keyPress(KeyEvent.VK_TAB,
 * timeBetweenKeys); bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * 
 * // Köp: Screenshot + tab + enter igen Thread.sleep(500); // Increase if it
 * does not work BufferedImage screenshot = bot.takeScreenshot(160,200,500,650);
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * // Close window afterwards HWND activeWindow =
 * WindowsNative.getActiveWindow(); Thread.sleep(200);
 * WindowsNative.closeWindow(activeWindow);
 * 
 * 
 * 
 * 
 * // Updates info from the ending popup String ocr =
 * MainOcrImage.ocr(screenshot); int numberOfShares =
 * Integer.parseInt(ocr.split("Antal ")[1].split("st")[0].replace(" ",
 * "").replace("\n", "")); double buyTotal =
 * Double.parseDouble(ocr.split("SEK")[2].split("belopp")[1].replace(",",
 * ".").replace(" ", "").replace("\n", ""));
 * 
 * 
 * fee = buyTotal * 0.005; capital = buyTotal; shares = numberOfShares;
 * 
 * } catch (IOException | InterruptedException | AWTException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); }
 * 
 * 
 * }
 * 
 * 
 * }
 */