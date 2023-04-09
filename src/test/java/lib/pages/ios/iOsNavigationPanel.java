package lib.pages.ios;

import io.appium.java_client.AppiumDriver;
import lib.pages.NavigationPanel;

public class iOsNavigationPanel extends NavigationPanel {

    static {
        TAB_LISTS = "id:Saved";
    }

    public iOsNavigationPanel(AppiumDriver driver) {
        super(driver);
    }
}
