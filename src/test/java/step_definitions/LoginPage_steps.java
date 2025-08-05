package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginPage_steps {
    public BrowserManager browserManager;
    private String alertText;

    public LoginPage_steps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @When("I type a user name {string} and a password {string}")
    public void iTypeAUserNameUsernameAndAPasswordPassword(String username, String password) {
        browserManager.page.getByPlaceholder("Username").fill(username);
        browserManager.page.getByPlaceholder("Password").fill(password);
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        browserManager.page.onceDialog(dialog -> {
            alertText = dialog.message();
            dialog.accept();
        });
        Locator loginButton = browserManager.page.locator("#login-button");
        loginButton.hover();
        loginButton.click(new Locator.ClickOptions().setForce(true));
//        browserManager.page.getByText("Login").click();
    }

    @Then("I should be presented with an alert box which contains text {string}")
    public void iShouldBePresentedWithAnAlertBoxWhichContainsTextExpectedAlertText(String expectedAlertText) {
        Assert.assertEquals(alertText, expectedAlertText, "Text doesn't match");
    }
}
