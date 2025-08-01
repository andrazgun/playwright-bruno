package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class ContactUs_steps {
        public BrowserManager browserManager;

    public ContactUs_steps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @When("I type a first name")
    public void iTypeAFirstName() {
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
        browserManager.page.getByPlaceholder("Comments").fill("Hello World!");
    }
    @When("I click on the submit button")
    public void iClickOnTheSubmitButton() {
        // custom wait
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions().setTimeout(1000);
        //wait for the button to load
        browserManager.page.waitForSelector("input[value='SUBMIT']", options);
        // once loaded, click on button
        browserManager.page.click("input[value='SUBMIT']");
//        browserManager.page.getByText("SUBMIT").click();
    }
    @Then("I should be presented with a successful contact us submission message")
    public void iShouldBePresentedWithASuccessfulContactUsSubmissionMessage() {
//        assertThat(browserManager.page.getByText("Thank You for your Message!")).isVisible();
        Locator locator = browserManager.page.locator("div#contact_reply > h1");
        assertThat(locator).isVisible();
        assertThat(locator).hasText("Thank You for your Message!");
//        browserManager.page.pause();
    }

    @Then("I should be presented with a unsuccessful contact us submission message")
    public void iShouldBePresentedWithAUnsuccessfulContactUsSubmissionMessage() {
        //wait for element
        browserManager.page.waitForSelector("body");
        Locator bodyElement = browserManager.page.locator("body");
        String bodyText = bodyElement.textContent();
        Pattern pattern = Pattern.compile("Error: (all fields are required|Invalid email address)");
        Matcher matcher = pattern.matcher(bodyText);
        assertTrue(matcher.find(), "Text does not match text: '" + bodyText + "'");

    }
}
