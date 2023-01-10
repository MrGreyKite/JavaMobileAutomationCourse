package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationPanel extends CorePage {
    public NavigationPanel(AppiumDriver driver) {
        super(driver);
    }

    private static final String TAB_LISTS = "org.wikipedia:id/nav_tab_reading_lists";

    public MySavedListsPage selectSavedListsTab() {
        this.waitForElementAndClick(By.id(TAB_LISTS), "Not found Saved tab", 5);
        return new MySavedListsPage(driver);
    }
}
