package lib.pages.mobileWeb;

import lib.pages.MySavedListsPage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebMySavedListsPage extends MySavedListsPage {
    public MobileWebMySavedListsPage(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        LIST_BY_NAME_TEMPLATE = ""; // no custom lists on MobileWeb
        ARTICLE_IN_THE_LIST = "xpath://*[@class='page-summary with-watchstar' and @title='{TITLE}']";
        DELETE_BUTTON = "//a[contains(@class, 'mw-ui-icon-wikimedia-unStar-progressive')]";
    }
}
