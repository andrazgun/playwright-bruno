package browser;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import support.LogUtil;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BrowserManager {

    private static final Logger logger = LogUtil.getLogger(BrowserManager.class);
    private final Properties properties;

    public Playwright playwright; //provides a method to launch a browser instance
    public Page page; //Represents a single web page within a context (i.e. Represents a single tab or page in the browser)
    public BrowserContext browserContext; //way to operate multiple independent browser sessions.
    public Browser browser; //represents the browser instance

    public BrowserManager() {
        properties = new Properties();
        //creates a path to a configuration file. If "config.path" isn't set,
        //it defaults to a file located in "src/main/resources/config.properties
        Path configPath = Paths.get(System.getProperty("config.path", Paths.get(System.getProperty("user.dir"),
                "src", "main", "resources", "config.properties").toString()));
        try (InputStream input = java.nio.file.Files.newInputStream(configPath)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException("Unable to load configuration", e);
        }
    }

    public byte[] takeScreenshot() {
        if (page != null) {
            return page.screenshot();
        }
        logger.warn("Page is null; screenshot not taken.");
        return null;
    }

    public void setUp() {
        logger.info("Setting up Playwright initiated");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        playwright = Playwright.create();

        String browserType = properties.getProperty("browser", "chromium").toLowerCase();

        switch (browserType) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chromium":
            default:
                logger.warn("Unsupported browser type: {}. Defaulting to chromium", browserType);
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }

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
