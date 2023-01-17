package lib.pages;

import io.appium.java_client.AppiumDriver;

public class NavigationPanel extends CorePage {
    public NavigationPanel(AppiumDriver driver) {
        super(driver);
    }

    private static final String TAB_LISTS = "id:org.wikipedia:id/nav_tab_reading_lists";

    public MySavedListsPage selectSavedListsTab() {
        this.waitForElementAndClick(TAB_LISTS, "Not found Saved tab", 5);
        return new MySavedListsPage(driver);
    }
}
