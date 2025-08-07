package pages;

import browser.BrowserManager;
import pages.base.BasePage;

public class ContactUsPage extends BasePage {
    public ContactUsPage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void typeFirstName(String firstName) {
        fillField("First Name", firstName);
    }

    public void typeLastName(String lastName) {
        fillField("Last Name", lastName);
    }

    public void typeEmailAddress(String emailAddress) {
        fillField("Email Address", emailAddress);
    }

    public void typeComment(String comment) {
        fillField("Comments", comment);
    }

    public void clickSubmitBtn() {
        waitAndClickBySelector("input[value='SUBMIT']");
    }
}
