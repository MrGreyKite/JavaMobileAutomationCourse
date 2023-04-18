package lib.pages.android;

import io.appium.java_client.AppiumDriver;
import lib.pages.ArticlePage;

public class AndroidArticlePage extends ArticlePage {

    static {
        ARTICLE_DESCRIPTION = "xpath://*[@resource-id='pcs-edit-section-title-description']";
        ARTICLE_TITLE = ARTICLE_DESCRIPTION + "/preceding-sibling::*[@class='android.widget.TextView']";
        ARTICLE_FOOTER = "xpath://android.view.View[@content-desc=\"View article in browser\"]/android.widget.TextView";
        SAVE_BUTTON = "id:org.wikipedia:id/page_save";
        SNACKBAR_ACTION = "id:org.wikipedia:id/snackbar_action";
        ARROW_BACK_BUTTON = "xpath://android.widget.ImageButton[@content-desc=\"Navigate up\"]";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "id:android:id/button1";
    }

    public AndroidArticlePage(AppiumDriver driver) {
        super(driver);
    }
}
