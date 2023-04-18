package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.MySavedListsPage;
import lib.pages.android.AndroidMySavedListsPage;
import lib.pages.ios.iOsMySavedListsPage;
import lib.pages.mobileWeb.MobileWebMySavedListsPage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MySavedListsPageFactory {

    public static MySavedListsPage get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMySavedListsPage((AppiumDriver) driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOsMySavedListsPage((AppiumDriver) driver);
        } else return new MobileWebMySavedListsPage(driver);
    }
}
