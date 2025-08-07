package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.HomePage;

public class Homepage_steps {
    private final HomePage homePage;

    public Homepage_steps(HomePage homePage) {
        this.homePage = homePage;
    }

    @Given("I navigate to the webdriveruniversity homepage")
    public void iNavigateToTheWebdriverUniversityHomepage() {
        homePage.navigateToHomePage();
    }

    @When("I click on the contact us button")
    public void iClickOnTheContactUsButton() {
        homePage.clickContactUsBtn();
    }

    @When("I click on the login portal button")
    public void iClickOnTheLoginPortalButton() {
        homePage.clickLoginBtn();
    }
}
