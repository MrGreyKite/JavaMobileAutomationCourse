package lib.pages.mobileWeb;

import lib.pages.NavigationPanel;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebNavigation extends NavigationPanel {
    public MobileWebNavigation(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        TAB_LISTS = "css:span.mw-ui-icon-minerva-watchlist";
        OPEN_MENU = "id:mw-mf-main-menu-button";
        LOGIN = "css:span.mw-ui-icon-minerva-logIn";
    }
}
