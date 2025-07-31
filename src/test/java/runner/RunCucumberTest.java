package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "src/test/java/step_definitions"
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
