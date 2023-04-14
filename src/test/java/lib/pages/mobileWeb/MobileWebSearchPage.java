package lib.pages.mobileWeb;

import lib.pages.SearchPage;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebSearchPage extends SearchPage {
    public MobileWebSearchPage(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        QUERY_INPUT_ELEMENT = "css:form>input.search.mw-ui-background-icon-search";
        RESULTS_LIST = "css:ul.page-list";
        SOME_RESULT_BY_DESCRIPTION = "xpath://div[@class='wikidata-description'][contains(text(), '{SUBSTRING}')]";
        SOME_RESULT_BY_TITLE = "css:li>a[data-title='{SUBSTRING}']";
        ANY_RESULT = "css:li>a[data-title]";
        SEARCH_CLOSE_BUTTON = "css:button.cancel";
        SEARCH_CLEAR_BUTTON = "css:button.clear";
        EMPTY_SEARCH_RESULTS_LABEL = "css:p.without-results";
        //ДЗ #18
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                "xpath://*[@title='{SUBSTRING1}' and @class='page-summary']//div[@class='wikidata-description'][contains(text(), '{SUBSTRING2}')]";

    }
}
