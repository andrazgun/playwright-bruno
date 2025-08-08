package pages;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import pages.base.BasePage;
import session.SessionKeys;

public class LoginPage extends BasePage {
    public LoginPage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void typeUsername(String username) {
        fillField("Username", username);
    }

    public void typePassword(String password) {
        fillField("Password", password);
    }

    public void clickLoginBtn() {
        getBrowserManager().getPage().onceDialog(dialog -> {
            String alertText = dialog.message();
            scenarioSession.put(SessionKeys.ALERT_TEXT, alertText);
            dialog.accept();
        });
        Locator loginButton = getBrowserManager().getPage().locator("#login-button");
        waitAndClick(loginButton);


    }
}
