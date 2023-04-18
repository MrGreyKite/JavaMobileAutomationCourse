package lib.pages.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.ArticlePage;
import lib.pages.android.AndroidArticlePage;
import lib.pages.ios.iOsArticlePage;
import lib.pages.mobileWeb.MobileWebArticlePage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePageFactory {
    public static ArticlePage get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidArticlePage((AppiumDriver) driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOsArticlePage((AppiumDriver) driver);
        } else return new MobileWebArticlePage(driver);
    }
}
