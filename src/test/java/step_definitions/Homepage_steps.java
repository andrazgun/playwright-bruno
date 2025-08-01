package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class Homepage_steps {
    public BrowserManager browserManager;

    public Homepage_steps(BrowserManager browserManager) {
     this.browserManager = browserManager;
    }

    @Given("I navigate to the webdriveruniversity homepage")
    public void iNavigateToTheWebdriverUniversityHomepage() {
        browserManager.page.navigate("https://webdriveruniversity.com/");
    }
    @When("I click on the contact us button")
    public void iClickOnTheContactUsButton() {
        browserManager.page = browserManager.browserContext.waitForPage(() ->
                browserManager.page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions()
                .setName("CONTACT US Contact Us Form")).click());
        browserManager.page.bringToFront(); //point to new opened tab
    }
}
