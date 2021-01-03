package stocks.xold;

public class SellWithRobotAwt{
    
}
/*
 * import java.awt.AWTException; import java.awt.MouseInfo; import
 * java.awt.Point; import java.awt.event.InputEvent; import
 * java.awt.event.KeyEvent; import java.awt.image.BufferedImage; import
 * java.io.IOException; import java.io.PrintWriter; import
 * java.util.concurrent.TimeUnit;
 * 
 * import org.awaitility.Awaitility;
 * 
 * import com.sun.jna.platform.win32.WinDef.HWND;
 * 
 * import stocks.Order; import stocks.image.processing.ocr.MainOcrImage; import
 * stocks.system.RobotAwt; import stocks.system.WindowsNative;
 * 
 * 
 * public class SellWithRobotAwt extends Order {
 * 
 * 
 * public SellWithRobotAwt(BuyWithRobotAwt buy, double price) { this.stock =
 * buy.stock; this.shares = buy.shares; this.price = price; this.account =
 * buy.account; }
 * 
 * 
 * 
 * 
 * @Override public void place() { try {
 * 
 * Process p = Runtime.getRuntime().exec("cmd"); PrintWriter stdin = new
 * PrintWriter(p.getOutputStream()); stdin.println("start chrome " +
 * "--app=\"data:text/html,<html><body><script>window.moveTo(0,0);" +
 * "window.resizeTo(800,1000);" +
 * "window.location='https://www.avanza.se/ab/sok/inline?query=" + stock.symbol
 * + "';" + "</script></body></html>\""); stdin.close(); p.waitFor();
 * 
 * RobotAwt bot = new RobotAwt(); // For some reason it is needed a click when
 * repeating buy and sell Point mouseInfo =
 * MouseInfo.getPointerInfo().getLocation(); Thread.sleep(400);
 * bot.mouseMove(100, 10); bot.mouseClick(InputEvent.getMaskForButton(1), 10);
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
 * // 3 tabs + enter bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys); bot.keyPress(KeyEvent.VK_TAB,
 * timeBetweenKeys); bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> { do { continue;
 * 
 * } while
 * (WindowsNative.getActiveWindowTitle(WindowsNative.getActiveWindow()).split(
 * "\\|").length != 3 ||
 * !WindowsNative.getActiveWindowTitle(WindowsNative.getActiveWindow()).split(
 * "\\|")[2] .contains(" Avanza"));
 * 
 * return true; });
 * 
 * // 20 tabs + enter for (int i = 0; i < 20; i++) {
 * bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys); }
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * 
 * // Amount of Shares: 1 tab + amount bot.keyPress(KeyEvent.VK_TAB,
 * timeBetweenKeys); bot.typeText(String.valueOf(shares), timeBetweenKeys);
 * 
 * 
 * // price: 1 tab + price bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.typeText(String.valueOf(price), 100);
 * 
 * // Sell button: 2 tabs + enter bot.keyPress(KeyEvent.VK_TAB,
 * timeBetweenKeys); bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * // Sell: Screenshot + tab + enter igen Thread.sleep(500); // Increase if it
 * does not work BufferedImage screenshot = bot.takeScreenshot(160, 200, 500,
 * 650); bot.keyPress(KeyEvent.VK_TAB, timeBetweenKeys);
 * bot.keyPress(KeyEvent.VK_ENTER, timeBetweenKeys);
 * 
 * // Close window afterwards HWND activeWindow =
 * WindowsNative.getActiveWindow(); Thread.sleep(200);
 * WindowsNative.closeWindow(activeWindow);
 * 
 * // Updates info from the ending popup String ocr =
 * MainOcrImage.ocr(screenshot); int sharesTotal =
 * Integer.parseInt(ocr.split("Antal ")[1].split("st")[0].replace(" ",
 * "").replace("\n", "")); double sellTotal = Double.parseDouble(
 * ocr.split("SEK")[2].split("belopp")[1].replace(",", ".").replace(" ",
 * "").replace("\n", ""));
 * 
 * 
 * fee = sellTotal * 0.005; capital = sellTotal; shares = sharesTotal;
 * 
 * 
 * } catch (IOException | InterruptedException | AWTException e) {
 * 
 * // TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * }
 * 
 * 
 * }
 */