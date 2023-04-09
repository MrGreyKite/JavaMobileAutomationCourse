package lib.pages.android;

import io.appium.java_client.AppiumDriver;
import lib.pages.NavigationPanel;

public class AndroidNavigationPanel extends NavigationPanel {

    static {
        TAB_LISTS = "id:org.wikipedia:id/nav_tab_reading_lists";
    }

    public AndroidNavigationPanel(AppiumDriver driver) {
        super(driver);
    }
}
