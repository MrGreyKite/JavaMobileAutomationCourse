package lib.pages.android;

import io.appium.java_client.AppiumDriver;
import lib.pages.SearchPage;

public class AndroidSearchPage extends SearchPage {
    public AndroidSearchPage(AppiumDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container";
        QUERY_INPUT_ELEMENT = "id:org.wikipedia:id/search_src_text";
        RESULTS_LIST = "id:org.wikipedia:id/search_results_list";
        SOME_RESULT_BY_DESCRIPTION = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']";
        SOME_RESULT_BY_TITLE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
        ANY_RESULT = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_CLOSE_BUTTON = "id:org.wikipedia:id/search_close_btn";
        ARROW_BACK_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton";
        EMPTY_SEARCH_RESULTS_LABEL = "//*[@text='No results']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING1}']/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING2}']";

    }
}
