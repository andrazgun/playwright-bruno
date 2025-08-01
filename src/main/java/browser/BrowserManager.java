package browser;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class BrowserManager {
    public static final Logger logger = LoggerFactory.getLogger(BrowserManager.class);
    public Playwright playwright; //provides a method to launch a browser instance
    public Page page; //Represents a single web page within a context (i.e. Represents a single tab or page in the browser)
    public BrowserContext browserContext; //way to operate multiple independent browser sessions.
    public Browser browser; //represents the browser instance

    public void setUp() {
        logger.info("Setting up Playwright initiated");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = browserContext.newPage();

        logger.info("Setting up Playwright completed");
    }

    public void tearDown() {
        logger.info("Tearing down Playwright initiated");

        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();

        logger.info("Tearing down Playwright completed");
    }
}
