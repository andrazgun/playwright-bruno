package step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import session.SessionKeys;

import static pages.base.BasePage.scenarioSession;

public class LoginPage_steps {
    private final LoginPage loginPage;

    public LoginPage_steps(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @When("I type a user name {string}")
    public void iTypeAUserName(String username) {
        loginPage.typeUsername(username);
    }

    @When("I type a password {string}")
    public void iTypeAPassword(String password) {
        loginPage.typePassword(password);
    }

    @When("I type a user name {string} and a password {string}")
    public void iTypeAUserNameUsernameAndAPasswordPassword(String username, String password) {
        loginPage.typeUsername(username);
        loginPage.typePassword(password);
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        loginPage.clickLoginBtn();
    }

    @Then("I should be presented with an alert box which contains text {string}")
    public void iShouldBePresentedWithAnAlertBoxWhichContainsTextExpectedAlertText(String expectedAlertText) {
        String alertText = scenarioSession.get(SessionKeys.ALERT_TEXT, String.class);
        Assert.assertEquals(alertText, expectedAlertText, "Text doesn't match");
    }
}
