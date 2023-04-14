package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.NavigationPanel;
import lib.pages.android.AndroidNavigationPanel;
import lib.pages.ios.iOsNavigationPanel;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationPanelFactory {
    public static NavigationPanel get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationPanel((AppiumDriver) driver);
        } else return new iOsNavigationPanel((AppiumDriver) driver);
    }
    }

