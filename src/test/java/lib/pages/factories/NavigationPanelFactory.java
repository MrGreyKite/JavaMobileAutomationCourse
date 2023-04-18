package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.NavigationPanel;
import lib.pages.android.AndroidNavigationPanel;
import lib.pages.ios.iOsNavigationPanel;
import lib.pages.mobileWeb.MobileWebNavigation;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationPanelFactory {
    public static NavigationPanel get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationPanel((AppiumDriver) driver);
        } else if(Platform.getInstance().isIOS()) {
            return new iOsNavigationPanel((AppiumDriver) driver);
        } else return new MobileWebNavigation(driver);
    }
    }

