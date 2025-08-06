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
        browserManager.getPage().navigate("https://webdriveruniversity.com/");
    }
    @When("I click on the contact us button")
    public void iClickOnTheContactUsButton() {
        browserManager.setPage(browserManager.getContext().waitForPage(() ->
                browserManager.getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions()
                .setName("CONTACT US Contact Us Form")).click()));
        browserManager.getPage().bringToFront(); //point to new opened tab
    }

    @When("I click on the login portal button")
    public void iClickOnTheLoginPortalButton() {
        browserManager.setPage(browserManager.getContext().waitForPage(() ->
                browserManager.getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions()
                        .setName("LOGIN PORTAL Login Portal")).click()));
        browserManager.getPage().bringToFront(); //point to new opened tab
    }
}
