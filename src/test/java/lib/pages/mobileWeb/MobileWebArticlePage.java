package lib.pages.mobileWeb;

import lib.pages.ArticlePage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebArticlePage extends ArticlePage {
    public MobileWebArticlePage(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_TITLE = "css:span.mw-page-title-main";
        ARTICLE_FOOTER = "id:footer-info";
        SAVE_BUTTON = "xpath://*[@id='ca-watch' and text()='Watch']";
        DELETE_FROM_SAVED_BUTTON = "css:[title='Unwatch']";
        LOG_IN_BUTTON = "xpath://a[text()='Log in']";
        MY_LIST_NAME_INPUT = ""; //No custom lists on Mobile Web
        MY_LIST_OK_BUTTON = ""; //No custom lists on Mobile Web
    }
}
