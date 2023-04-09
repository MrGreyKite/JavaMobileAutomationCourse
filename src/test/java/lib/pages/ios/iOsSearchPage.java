package lib.pages.ios;

import io.appium.java_client.AppiumDriver;
import lib.pages.SearchPage;

public class iOsSearchPage extends SearchPage {
    public iOsSearchPage(AppiumDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        QUERY_INPUT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        RESULTS_LIST = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeOther[2]";
        SOME_RESULT_BY_DESCRIPTION = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]/..";
        SOME_RESULT_BY_TITLE = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]/..";
        ANY_RESULT = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeOther[2]" +
                "/XCUIElementTypeStaticText[not(@name='No recent searches yet')]/.."; //сработает при запуске на свежем приложениии
        SEARCH_CLOSE_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        SEARCH_CLEAR_BUTTON = "xpath://XCUIElementTypeButton[@name='Clear text']";
        ARROW_BACK_BUTTON = ""; //нет на iOS, вместо этого функционально кнопка очистки поиска, а кнопка Close выходит на главную
        EMPTY_SEARCH_RESULTS_LABEL = "//*[@name='No results found']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING1}')]/following-sibling::/XCUIElementTypeStaticText[contains(@name,'{SUBSTRING2}')]/..";

    }
}
