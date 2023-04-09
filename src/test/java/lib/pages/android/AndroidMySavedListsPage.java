package lib.pages.android;

import io.appium.java_client.AppiumDriver;
import lib.pages.MySavedListsPage;

public class AndroidMySavedListsPage extends MySavedListsPage {

    static {
        LIST_BY_NAME_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{TITLE}']";
        ARTICLE_IN_THE_LIST = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";
    }

    public AndroidMySavedListsPage(AppiumDriver driver) {
        super(driver);
    }
}
