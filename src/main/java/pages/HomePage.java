package pages;

import browser.BrowserManager;
import pages.base.BasePage;

public class HomePage extends BasePage {
    public HomePage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void navigateToHomePage() {
        navigate("https://webdriveruniversity.com/");
    }

    public void clickContactUsBtn() {
        getBrowserManager().setPage(getBrowserManager().getContext().waitForPage(() ->
                waitAndClickByRole("link", "CONTACT US Contact Us Form")));

        getBrowserManager().getPage().bringToFront(); //point to new opened tab
    }

    public void clickLoginBtn() {
        getBrowserManager().setPage(getBrowserManager().getContext().waitForPage(() ->
                waitAndClickByRole("link", "LOGIN PORTAL Login Portal")));

        getBrowserManager().getPage().bringToFront(); //point to new opened tab
    }
}
