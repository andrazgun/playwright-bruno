package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import context.PersonContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class ContactUs_steps {
    public BrowserManager browserManager;
    private final Faker faker = new Faker();
    private final PersonContext personContext;

    public ContactUs_steps(BrowserManager browserManager, PersonContext personContext) {
        this.browserManager = browserManager;
        this.personContext = personContext;
    }

    @When("I type a first name")
    public void iTypeAFirstName() {
        browserManager.getPage().getByPlaceholder("First Name").fill("Joe");
    }

    @And("I type a last name")
    public void iTypeALastName() {
        browserManager.getPage().getByPlaceholder("Last Name").fill("Doe");
    }

    @When("I enter an email address")
    public void iEnterAnEmailAddress() {
        browserManager.getPage().getByPlaceholder("Email Address").fill("agtest1@yopmail.com");
    }

    @When("I type a comment")
    public void iTypeAComment() {
        browserManager.getPage().getByPlaceholder("Comments").fill("Hello World!");
    }

    @When("I click on the submit button")
    public void iClickOnTheSubmitButton() {
        // custom wait
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions().setTimeout(1000); //1 sec
        //wait for the button to load
        browserManager.getPage().waitForSelector("input[value='SUBMIT']", options);
        // once loaded, click on button
        browserManager.getPage().click("input[value='SUBMIT']");
//        browserManager.page.getByText("SUBMIT").click();
    }

    @And("I type a specific first name {string}")
    public void iTypeASpecificFirstName(String firstName) {
        browserManager.getPage().getByPlaceholder("First Name").fill(firstName);
    }

    @And("I type a specific last name {string}")
    public void iTypeASpecificLastName(String lastName) {
        browserManager.getPage().getByPlaceholder("Last Name").fill(lastName);
    }

    @And("I enter a specific email address {string}")
    public void iEnterASpecificEmailAddress(String emailAddress) {
        browserManager.getPage().getByPlaceholder("Email Address").fill(emailAddress);
    }

    @And("I type specific text {string} and number {int} within the comment input field")
    public void iTypeSpecificTextAndNumberWithinTheCommentInputField(String word, int number) {
        browserManager.getPage().getByPlaceholder("Comments").fill(word + " " + number);
    }

    @And("I type a random first name")
    public void iTypeARandomFirstName() {
        String randomFirstName = faker.name().firstName();
        personContext.setRandomFirstName(randomFirstName); //Store in personContext
        browserManager.getPage().getByPlaceholder("First Name").fill(randomFirstName);
    }

    @And("I type a random last name")
    public void iTypeARandomLastName() {
        String randomLastName = faker.name().lastName();
        personContext.setRandomLastName(randomLastName); //Store in personContext
        browserManager.getPage().getByPlaceholder("Last Name").fill(randomLastName);
    }

    @And("I enter a random email address")
    public void iEnterARandomEmailAddress() {
        String randomEmailAddress = faker.internet().emailAddress();
        personContext.setRandomEmailAddress(randomEmailAddress); //Store in personContext
        browserManager.getPage().getByPlaceholder("Email Address").fill(randomEmailAddress);
    }

    @And("I type a random comment")
    public void iTypeARandomComment() {
        browserManager.getPage().getByPlaceholder("Comments").fill("Hi, my details: "
        + "\n" + personContext.getRandomFirstName() + " " + personContext.getRandomLastName()
        + " " + personContext.getRandomEmailAddress());
    }

    @Then("I should be presented with a successful contact us submission message")
    public void iShouldBePresentedWithASuccessfulContactUsSubmissionMessage() {
//        assertThat(browserManager.page.getByText("Thank You for your Message!")).isVisible();
        Locator locator = browserManager.getPage().locator("div#contact_reply > h1");
        assertThat(locator).isVisible();
        assertThat(locator).hasText("Thank You for your Message!");
//        browserManager.page.pause();
    }

    @Then("I should be presented with a unsuccessful contact us submission message")
    public void iShouldBePresentedWithAUnsuccessfulContactUsSubmissionMessage() {
        //wait for element
        browserManager.getPage().waitForSelector("body");
        Locator bodyElement = browserManager.getPage().locator("body");
        String bodyText = bodyElement.textContent();
        Pattern pattern = Pattern.compile("Error: (all fields are required|Invalid email address)");
        Matcher matcher = pattern.matcher(bodyText);
        assertTrue(matcher.find(), "Text does not match text: '" + bodyText + "'");
    }

    @And("I type a first name {word} and a last name {word}")
    public void iTypeAFirstNameFirstNameAndALastNameLastName(String firstName, String lastName) {
        browserManager.getPage().getByPlaceholder("First Name").fill(firstName);
        browserManager.getPage().getByPlaceholder("Last Name").fill(lastName);
    }

    @And("I type a email address {string} and a comment {string}")
    public void iTypeAEmailAddressEmailAddressAndACommentComment(String emailAddress, String comment) {
        browserManager.getPage().getByPlaceholder("Email Address").fill(emailAddress);
        browserManager.getPage().getByPlaceholder("Comments").fill(comment);
    }

    @Then("I should be presented with header text {string}")
    public void iShouldBePresentedWithHeaderTextMessage(String message) {
        //Wait for the target element
        //h1 | body
        browserManager.getPage().waitForSelector("//h1 | //body"); //dynamic selector h1 OR body
        //Get all elements inner text
        List<String> texts = browserManager.getPage().locator("//h1 | //body").allInnerTexts();
        //Variable to store the found text
        String foundText = "";
        //Check if any of the text include the expected message
        boolean found = false;
        for (String text : texts) {
            if (text.contains(message)) {
                foundText = text;
                found = true;
                break;
            } else {
                foundText = text;
            }
        }
        assertTrue(found, "Expected message: " + foundText + ", to be equal to: " + message);
    }
}