package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.MySavedListsPage;
import lib.pages.android.AndroidMySavedListsPage;
import lib.pages.ios.iOsMySavedListsPage;

public class MySavedListsPageFactory {

    public static MySavedListsPage get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMySavedListsPage(driver);
        } else return new iOsMySavedListsPage(driver);
    }
}
