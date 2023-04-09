package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.ArticlePage;
import lib.pages.android.AndroidArticlePage;
import lib.pages.ios.iOsArticlePage;

public class ArticlePageFactory {
    public static ArticlePage get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidArticlePage(driver);
        } else return new iOsArticlePage(driver);
    }
}
