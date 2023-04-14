package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.SearchPage;
import lib.pages.android.AndroidSearchPage;
import lib.pages.ios.iOsSearchPage;
import lib.pages.mobileWeb.MobileWebSearchPage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SearchPageFactory {

    public static SearchPage get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidSearchPage((AppiumDriver) driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOsSearchPage((AppiumDriver) driver);
        } else return new MobileWebSearchPage(driver);
    }
    }

