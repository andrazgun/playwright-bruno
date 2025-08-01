package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUs_steps {
        public BrowserManager browserManager;

    public ContactUs_steps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @When("I type a first name")
    public void iTypeAFirstName() {
//        browserManager.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions()
//                .setName("First Name")).fill("Joe");
        browserManager.page.getByPlaceholder("First Name").fill("Joe");
    }
    @And("I type a last name")
    public void iTypeALastName() {
        browserManager.page.getByPlaceholder("Last Name").fill("Doe");
    }
    @When("I enter an email address")
    public void iEnterAnEmailAddress() {
        browserManager.page.getByPlaceholder("Email Address").fill("agtest1@yopmail.com");
    }
    @When("I type a comment")
    public void iTypeAComment() {
        browserManager.page.getByPlaceholder("Comments").fill("Sample text");
    }
    @When("I click on the submit button")
    public void iClickOnTheSubmitButton() {
        browserManager.page.getByText("SUBMIT").click();
    }
    @Then("I should be presented with a successful contact us submission message")
    public void iShouldBePresentedWithASuccessfulContactUsSubmissionMessage() {
        PlaywrightAssertions.assertThat(browserManager.page.getByText("Thank You for your Message!")).isVisible();
//        browserManager.page.pause();
    }
}
