package stocks.model.utils;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

public class ULogging {

    // For yahoo requests
    public static void disableSlf4j() {
	// Disable slf4j Logging
	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

    }

    public static void disableKeyLogging() {

	// info: https://stackoverflow.com/questions/30560212/how-to-remove-the-logging-data-from-jnativehook-library
	// Disables Top4j logging
	// Clear previous logging configurations.
	LogManager.getLogManager().reset();

	// Get the logger and turns it off
	Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	logger.setLevel(Level.OFF);
    }
}
