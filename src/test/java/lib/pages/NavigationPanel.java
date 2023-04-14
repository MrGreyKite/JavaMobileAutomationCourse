package lib.pages;

import io.appium.java_client.AppiumDriver;
import lib.pages.factories.MySavedListsPageFactory;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationPanel extends CorePage {
    public NavigationPanel(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String TAB_LISTS;

    public MySavedListsPage selectSavedListsTab() {
        this.waitForElementAndClick(TAB_LISTS, "Not found Saved tab", 5);
        return MySavedListsPageFactory.get(driver);
    }
}
