package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.slf4j.Logger;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import support.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "step_definitions",
        tags = "@random and not @ignore",
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-report.html"}
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {

    private static final Logger logger = LogUtil.getLogger(RunCucumberTest.class);
    private static final Properties properties = new Properties();

    static {
        Path configPath = Paths.get(System.getProperty("config.path", Paths.get(System.getProperty("user.dir"),
                "src", "main", "resources", "config.properties").toString()));
        try (InputStream input = java.nio.file.Files.newInputStream(configPath)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException("Unable to load configuration", e);
        }
    }

    public static void main(String[] args) {
        //create instance of TestNG
        TestNG testNG = new TestNG();

        //create a new TestNG suite
        XmlSuite suite = new XmlSuite();

        int threadCount = getThreadCount();
        logger.info("Configured thread count: {}", threadCount);

        //set the number of threads for the data provider
        suite.setDataProviderThreadCount(threadCount);

        suite.setThreadCount(threadCount);

        //create a new TestNG test and add it to the suite
        XmlTest test = new XmlTest(suite);
        test.setName("Cucumber tests"); //setting tests name
        test.setXmlClasses(Collections.singletonList(new XmlClass(RunCucumberTest.class))); //add the test class to the test

        //disable default listeners (will disable TestNG reports from being generated)
        testNG.setUseDefaultListeners(false);

        //add the suite to the TestNG instance
        testNG.setXmlSuites(Collections.singletonList(suite));

        //run TestNG with the configured suite
        testNG.run();
    }

    //get thread count from the config file
    private static int getThreadCount() {
        try {
            return Integer.parseInt(properties.getProperty("thread.count", "1"));
        } catch (NumberFormatException e) {
            logger.warn("Invalid thread.count in config.properties. Falling back to 1.");
            return 1;
        }
    }

    //DataProvider method
    // used for parallel execution, allowing multiple tests to run simultaneous
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios(); //provide data for the tests, enabling parallel execution
    }
}
