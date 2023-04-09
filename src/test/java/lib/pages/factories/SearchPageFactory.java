package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.SearchPage;
import lib.pages.android.AndroidSearchPage;
import lib.pages.ios.iOsSearchPage;

public class SearchPageFactory {

    public static SearchPage get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidSearchPage(driver);
        } else return new iOsSearchPage(driver);
    }
}
