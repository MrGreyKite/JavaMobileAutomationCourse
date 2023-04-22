package lib.pages;

import io.qameta.allure.Step;
import lib.Platform;
import lib.pages.factories.ArticlePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.regex.Pattern;

abstract public class SearchPage extends CorePage {
    public SearchPage(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String
            SEARCH_INIT_ELEMENT,
            QUERY_INPUT_ELEMENT,
            RESULTS_LIST,
            SOME_RESULT_BY_DESCRIPTION,
            SOME_RESULT_BY_TITLE,
            ANY_RESULT,
            SEARCH_CLOSE_BUTTON,
            SEARCH_CLEAR_BUTTON,
            ARROW_BACK_BUTTON,
            EMPTY_SEARCH_RESULTS_LABEL,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION;

    @Step("Initialize search")
    public void initSearch() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Not found init search input", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot click by init search input", 2);
        screenshot(this.takeScreenshot("after_init_search"));
    }

    @Step("Type a query in the search field")
    public void typeSearchQuery(String queryText) {
        this.waitForElementPresent(QUERY_INPUT_ELEMENT, "Not found a query input", 5);
        this.waitForElementAndSendKeys(QUERY_INPUT_ELEMENT, queryText, "Cannot send keys to query input", 5);
        screenshot(this.takeScreenshot("after_search"));
    }

    @Step("Verify the text in the search field's placeholder")
    public void assertThatQueryInputHasARightPlaceholder() {
        //для iOS - нужно искать значение атрибута методом assertElementHasAttribute - атрибут label или value
        if(Platform.getInstance().isAndroid()) {
            assertElementHasText(QUERY_INPUT_ELEMENT,
                    "Search Wikipedia",
                    "Query input doesn't have expected placeholder text");
        } else if (Platform.getInstance().isIOS()) {
            this.assertElementHasAttribute(QUERY_INPUT_ELEMENT, "value",
                    "Search Wikipedia",
                    "Query input doesn't have expected placeholder text");
        } else {
            this.assertElementHasAttribute(QUERY_INPUT_ELEMENT, "placeholder",
                    "Search Wikipedia",
                    "Query input doesn't have expected placeholder text");
        }
    }

    //Урок 3, ДЗ-2
    @Step("Search for the query '{query}'")
    public void searchSomethingOnInput(String query) {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Not found search input", 5);
        this.waitForElementAndSendKeys(QUERY_INPUT_ELEMENT, query, "Not found query input", 10);
        screenshot(this.takeScreenshot("after_search"));
    }

    @Step("Close search")
    public void clickOnCloseSearchButton() {
        //для iOS - иная логика закрытия поиска: поиск отменяется нажатием на Cancel, а очищается по крестику
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Not found close button", 5);
            this.waitForElementNotPresent(SEARCH_CLOSE_BUTTON, "Close button is still active", 10);
        } else {
            this.waitForElementAndClick(SEARCH_CLEAR_BUTTON, "Not found clear button", 5);
            this.waitForElementNotPresent(SEARCH_CLEAR_BUTTON, "Clear button is still active", 10);
        }
        screenshot(this.takeScreenshot("after_close_search"));
    }

    private static String getResultsSearchElement(String forReplacement, String substring) {
        return forReplacement.replace("{SUBSTRING}", substring);
    }

    private static String getResultsSearchElement(String forReplacement, String substring1, String substring2) {
        return forReplacement.replace("{SUBSTRING1}", substring1).replace("{SUBSTRING2}", substring2);
    }

    @Step("Detect element with title '{0}' and description '{1}'")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String xpath = getResultsSearchElement(SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION, title, description);
        this.waitForElementPresent(xpath,
                "Cannot find result by description " + description + " and title " + title, 15);
    }

    @Step("Detect element with description '{0}'")
    public void waitForSearchResultByDescription(String description) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_DESCRIPTION, description);
        this.waitForElementPresent(xpath,
                "Cannot find result by description " + description, 5);
    }

    @Step("Detect element with title '{0}'")
    public void waitForSearchResultByTitle(String title) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, title);
        this.waitForElementPresent(xpath,
                "Cannot find result by title " + title, 5);
    }

    @Step("Open article with title '{0}' from the search results")
    public ArticlePage clickOnSearchResultByTitle(String articleTitle) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, articleTitle);
        this.waitForElementAndClick(xpath,"Cannot click on needed article by title " + articleTitle, 10);
        screenshot(this.takeScreenshot("after_open_article"));
        return ArticlePageFactory.get(driver);
    }

    @Step("Open article from the search results")
    public ArticlePage clickOnSearchResultByNumber(int numberOfArticleInResults) {
        //для поиска по номеру в iOS воспользоваться иной логикой - List<WebElement> и соответственно по номеру в листе искать - минус единица
        if(Platform.getInstance().isAndroid()){
            this.waitForElementAndClick
                    ("xpath://*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup["
                                    + numberOfArticleInResults
                                    + "]/*[@resource-id='org.wikipedia:id/page_list_item_title']",
                            "Cannot find an article in results", 15);
        } else {
            List<WebElement> results = this.getAllSearchResults();
            results.get(numberOfArticleInResults-1).click();
        }
        screenshot(this.takeScreenshot("after_open_article"));
        return ArticlePageFactory.get(driver);

    }

    @Step("Return to main page")
    public void goBackOnMainPageFromSearch() {
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(ARROW_BACK_BUTTON,
                    "Not found back arrow in search results", 5);
        } else if(Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Not found iOS close button", 10);
        }

    }

    @Step("Get amount of articles found")
    public int getAmountOfFoundArticles() {
        //для поиска по номеру в iOS воспользоваться иной логикой - List<WebElement>, уже есть getAllSearchResults()
        this.waitForElementPresent(RESULTS_LIST, "No results in search");
        //return this.getAmountOfElements(ANY_RESULT_TITLE);
        return this.getAllSearchResults().size();
    }


    @Step("Verify that search found some articles")
    public void assertThatSomeArticlesAreFoundOnSearch() {
        this.waitForElementPresent(RESULTS_LIST, "No results in search");
        this.assertElementPresent(ANY_RESULT);
        screenshot(this.takeScreenshot("results"));
    }

    @Step("Waiting for results of incorrect search")
    public void waitForEmptySearchResultByQuery(String searchString) {
        this.waitForElementPresent(EMPTY_SEARCH_RESULTS_LABEL,
                "Not empty results by request " + searchString, 10);
    }

    @Step("Verify that none articles are found")
    public void assertThatAreNotFoundAnyArticles() {
        this.assertElementNotPresent(ANY_RESULT);
        screenshot(this.takeScreenshot("results"));
    }

    @Step("Get title of some found article")
    public String getTitleOfSomeArticleInResults(int numberOfArticle) {
        if(Platform.getInstance().isAndroid()) {
            return this.waitForElementAndGetAttribute
                    ("xpath://*[@resource-id='" + RESULTS_LIST.split(Pattern.quote(":"), 2)[1] + "']/android.view.ViewGroup["
                                    + numberOfArticle
                                    + "]/*[@resource-id='" + ANY_RESULT.split(Pattern.quote(":"), 2)[1] + "']",
                            "text", "Cannot find an article in results", 15);
        } else {
            List<WebElement> results = this.getAllSearchResults();
            if (Platform.getInstance().isIOS()) {
                return results.get(numberOfArticle-1).getAttribute("name");}
            else return results.get(numberOfArticle-1).getAttribute("data-title");
        }
    }

    @Step("Clear search input")
    public void clearQueryInput() {
        this.waitForElementAndClear(QUERY_INPUT_ELEMENT, "Not found a query input", 5);
        screenshot(this.takeScreenshot("after_clear"));
    }

    @Step("Get all search results as list")
    public List<WebElement> getAllSearchResults() {
        WebElement resultsList = this.waitForElementPresent(RESULTS_LIST, "Not present result list");
        By by = this.getLocatorByString(ANY_RESULT);
        screenshot(this.takeScreenshot("results_list"));
        return resultsList.findElements(by);
    }
}
