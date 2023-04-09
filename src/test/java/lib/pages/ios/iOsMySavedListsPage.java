package lib.pages.ios;

import io.appium.java_client.AppiumDriver;
import lib.pages.MySavedListsPage;

public class iOsMySavedListsPage extends MySavedListsPage {

    static {
        LIST_BY_NAME_TEMPLATE = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        ARTICLE_IN_THE_LIST = "xpath://XCUIElementTypeStaticText[contains(@name='{TITLE}')]";
        DELETE_BUTTON = "xpath://XCUIElementTypeButton[@name=\"swipe action delete\"]";
    }
    public iOsMySavedListsPage(AppiumDriver driver) {
        super(driver);
    }
}
