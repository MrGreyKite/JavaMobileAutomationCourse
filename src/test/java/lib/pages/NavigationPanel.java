package lib.pages;

import io.appium.java_client.AppiumDriver;
import lib.pages.factories.MySavedListsPageFactory;
import lib.pages.factories.NavigationPanelFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract public class NavigationPanel extends CorePage {
    public NavigationPanel(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String OPEN_MENU, TAB_LISTS, LOGIN;

    public MySavedListsPage selectSavedListsTab() {
        WebElement saved = this.waitForElementPresent(TAB_LISTS, "Not found Saved tab", 5);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(saved)).click();
        return MySavedListsPageFactory.get(driver);
    }

    public void openMenu() {
        this.waitForElementAndClick(OPEN_MENU, "Cannot open menu", 5);
    }

    public AuthorizationPage clickLogIn() {
        WebElement login = this.waitForElementPresent(LOGIN, "Cannot find login button", 15);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(login)).click();
        return new AuthorizationPage(driver);
    }
}
