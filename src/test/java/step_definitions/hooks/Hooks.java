package step_definitions.hooks;

import browser.BrowserManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

public class Hooks {

    private final BrowserManager browserManager;

    public Hooks(BrowserManager browserManager) { //constructor using PicoContainer for DI
        this.browserManager = browserManager;
    }

    //Runs once before all test start (all scenario)
    @BeforeAll
    public static void beforeAll() {
        System.out.println("Executing test suite (beforeAll hook)");
    }

    //Runs once after all test start
    @AfterAll
    public static void afterAll() {
        System.out.println("Finished executing the test suite (afterAll hook)");
    }

    //Runs before each test (each scenario)
    @Before
    public void setUp() {
        browserManager.setUp();
        System.out.println("Executing setUp() (before test hook)");
    }

    //Runs after each test (each scenario)
    @After
    public void tearDown() {
        browserManager.tearDown();
        System.out.println("Executing tearDown() (after test hook)");
    }
}
