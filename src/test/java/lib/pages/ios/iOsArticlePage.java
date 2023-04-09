package lib.pages.ios;

import io.appium.java_client.AppiumDriver;
import lib.pages.ArticlePage;

public class iOsArticlePage extends ArticlePage {

    static {
        ARTICLE_TITLE = "id:Appium";
        ARTICLE_TITLE_TEMPLATE = "xpath://XCUIElementTypeOther[@name='{SUBSTRING}'][2]";
        ARTICLE_FOOTER = "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
        SAVE_BUTTON = "id:Save for later";
        SNACKBAR_ACTION_TEMPLATE = "xpath://XCUIElementTypeStaticText[@name=\"Add “{SUBSTRING}” to a reading list?\"]/..";
        CONFIRM_NEW_LIST_CREATION = "xpath://XCUIElementTypeButton[@name=\"Create a new list\"]";
        ARROW_BACK_BUTTON = "xpath://XCUIElementTypeButton[@name='Back']";
        MY_LIST_NAME_INPUT = "xpath://XCUIElementTypeStaticText[@name=\"Reading list name\"]/following-sibling::*";
        MY_LIST_OK_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Create reading list\"]";
    }

    public iOsArticlePage(AppiumDriver driver) {
        super(driver);
    }
}
