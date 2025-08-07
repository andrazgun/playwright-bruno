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

    // A ThreadLocal is like a personal locker for each thread, so they don't share data with other threads.
    // Think of threads as individual workers in a factory, each with their own toolbox (ThreadLocal).
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>(); //used to create an instance of the Chromium, Firefox browser etc.
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>(); //represents the browser instance.
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>(); //is the isolated browser session.
    private static final ThreadLocal<Page> page = new ThreadLocal<>(); //is the single tab or window in the browser.

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

    public Page getPage() {
        return page.get();
    }

    public void setPage(Page newPage) {
        page.set(newPage);
    }

    public BrowserContext getContext() {
        return context.get();
    }

    public byte[] takeScreenshot() {
        if (getPage() != null) {
            return getPage().screenshot();
        }
        logger.warn("Page is null; screenshot not taken.");
        return null;
    }

    public void setUp() {
        logger.info("Setting up Playwright initiated");

        if (playwright.get() == null) {
            playwright.set(Playwright.create());
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        String browserType = properties.getProperty("browser", "chromium").toLowerCase();

        logger.info("Thread [{}] initializing browser: {}", Thread.currentThread().getId(), browserType);

        switch (browserType) {
            case "firefox":
                browser.set(playwright.get().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chromium":
            default:
                logger.warn("Unsupported browser type: {}. Defaulting to chromium", browserType);
                browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
        }

        context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
        page.set(context.get().newPage());
        logger.info("Setting up Playwright completed");
    }

    public void tearDown() {
        logger.info("Tearing down Playwright initiated");

        closeAndRemove(page);
        closeAndRemove(context);
        closeAndRemove(browser);
        closeAndRemove(playwright);

        logger.info("Tearing down Playwright completed");
    }

    private <T extends AutoCloseable> void closeAndRemove(ThreadLocal<T> threadLocal) {
        T resource = threadLocal.get();
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.warn("Failed to close resource: {}", resource.getClass().getSimpleName(), e);
            } finally {
                threadLocal.remove();
            }
        }
    }
}
