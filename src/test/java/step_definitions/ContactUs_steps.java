package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import context.PersonContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import pages.ContactUsPage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class ContactUs_steps {
    public BrowserManager browserManager;
    private final Faker faker = new Faker();
    private final PersonContext personContext;
    private final ContactUsPage contactUsPage;

    public ContactUs_steps(BrowserManager browserManager, PersonContext personContext, ContactUsPage contactUsPage) {
        this.browserManager = browserManager;
        this.personContext = personContext;
        this.contactUsPage = contactUsPage;
    }

    @When("I type a first name")
    public void iTypeAFirstName() {
        contactUsPage.typeFirstName("Joe");
    }

    @And("I type a last name")
    public void iTypeALastName() {
        contactUsPage.typeLastName("Doe");
    }

    @When("I enter an email address")
    public void iEnterAnEmailAddress() {
        contactUsPage.typeEmailAddress("agtest1@yopmail.com");
    }

    @When("I type a comment")
    public void iTypeAComment() {
        contactUsPage.typeComment("Hello World!");
    }

    @When("I click on the submit button")
    public void iClickOnTheSubmitButton() {
        contactUsPage.clickSubmitBtn();
    }

    @And("I type a specific first name {string}")
    public void iTypeASpecificFirstName(String firstName) {
        contactUsPage.typeFirstName(firstName);
    }

    @And("I type a specific last name {string}")
    public void iTypeASpecificLastName(String lastName) {
        contactUsPage.typeLastName(lastName);
    }

    @And("I enter a specific email address {string}")
    public void iEnterASpecificEmailAddress(String emailAddress) {
        contactUsPage.typeEmailAddress(emailAddress);
    }

    @And("I type specific text {string} and number {int} within the comment input field")
    public void iTypeSpecificTextAndNumberWithinTheCommentInputField(String word, int number) {
        contactUsPage.typeComment(word + " " + number);
    }

    @And("I type a random first name")
    public void iTypeARandomFirstName() {
        String randomFirstName = faker.name().firstName();
        personContext.setRandomFirstName(randomFirstName); //Store in personContext
        contactUsPage.typeFirstName(randomFirstName);
    }

    @And("I type a random last name")
    public void iTypeARandomLastName() {
        String randomLastName = faker.name().lastName();
        personContext.setRandomLastName(randomLastName); //Store in personContext
        contactUsPage.typeLastName(randomLastName);
    }

    @And("I enter a random email address")
    public void iEnterARandomEmailAddress() {
        String randomEmailAddress = faker.internet().emailAddress();
        personContext.setRandomEmailAddress(randomEmailAddress); //Store in personContext
        contactUsPage.typeEmailAddress(randomEmailAddress);
    }

    @And("I type a random comment")
    public void iTypeARandomComment() {
        contactUsPage.typeComment("Hi, my details: "
                + "\n" + personContext.getRandomFirstName() + " " + personContext.getRandomLastName()
                + " " + personContext.getRandomEmailAddress());
    }

    @And("I type a first name {word} and a last name {word}")
    public void iTypeAFirstNameFirstNameAndALastNameLastName(String firstName, String lastName) {
        contactUsPage.typeFirstName(firstName);
        contactUsPage.typeLastName(lastName);
    }

    @And("I type a email address {string} and a comment {string}")
    public void iTypeAEmailAddressEmailAddressAndACommentComment(String emailAddress, String comment) {
        contactUsPage.typeEmailAddress(emailAddress);
        contactUsPage.typeComment(comment);
    }

    @Then("I should be presented with a successful contact us submission message")
    public void iShouldBePresentedWithASuccessfulContactUsSubmissionMessage() {
//        assertThat(browserManager.page.getByText("Thank You for your Message!")).isVisible();
        Locator locator = browserManager.getPage().locator("div#contact_reply > h1");
        assertThat(locator).isVisible();
        assertThat(locator).hasText("Thank You for your Message!");
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