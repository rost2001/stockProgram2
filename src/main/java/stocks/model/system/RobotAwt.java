package stocks.model.system;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

// Extra functions for AWT Robot klassen, and to help with limiting duplicating code;
public class RobotAwt extends java.awt.Robot {

	public RobotAwt() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}



	public void mouseMove(int x, int y, int delay) throws InterruptedException {

		mouseMove(x, y);
		Thread.sleep(delay);
	}

	public void keyPress(int keyEvent, int delay) throws InterruptedException {

		keyPress(keyEvent);
		keyRelease(keyEvent);
		Thread.sleep(delay);
	}

	public void typeText(String text, int delay) throws InterruptedException {

		for (int i = 0; i < text.length(); i++) {

			keyPress(text.charAt(i));
			keyRelease(text.charAt(i));
		}
		Thread.sleep(delay);
	}

	public void mouseClick(int mouseEvent, int delay) throws InterruptedException {

		mousePress(mouseEvent);
		mouseRelease(mouseEvent);
		Thread.sleep(delay);
	}

	public BufferedImage takeScreenshot(int x, int y, int w, int h) throws InterruptedException, AWTException {

		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = createScreenCapture(screenRect);

		if (x == -1)
			return capture;
		else
			return capture.getSubimage(x, y, w, h);
	}

	// If not wanting a sub image
	public BufferedImage takeScreenshot() throws InterruptedException, AWTException {
		return takeScreenshot(-1, -1, -1, -1);
	}

}
