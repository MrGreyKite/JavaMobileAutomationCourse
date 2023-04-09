package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.NavigationPanel;
import lib.pages.android.AndroidNavigationPanel;
import lib.pages.ios.iOsNavigationPanel;

public class NavigationPanelFactory {
    public static NavigationPanel get(AppiumDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationPanel(driver);
        } else return new iOsNavigationPanel(driver);
    }
    }

