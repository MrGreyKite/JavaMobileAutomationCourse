package lib.pages;

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

    public void initSearch() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Not found init search input", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot click by init search input", 2);
    }

    public void typeSearchQuery(String queryText) {
        this.waitForElementPresent(QUERY_INPUT_ELEMENT, "Not found a query input", 5);
        this.waitForElementAndSendKeys(QUERY_INPUT_ELEMENT, queryText, "Cannot send keys to query input", 5);
    }

    public void assertThatQueryInputHasARightPlaceholder() {
        //для iOS - нужно искать значение атрибута методом assertElementHasAttribute - атрибут label или value
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            assertElementHasText(QUERY_INPUT_ELEMENT,
                    "Search Wikipedia",
                    "Query input doesn't have expected placeholder text");
        } else {
            this.assertElementHasAttribute(QUERY_INPUT_ELEMENT, "value", "Search Wikipedia",
                    "Query input doesn't have expected placeholder text");
        }
    }

    //Урок 3, ДЗ-2
    public void searchSomethingOnInput(String query) {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Not found search input", 5);
        this.waitForElementAndSendKeys(QUERY_INPUT_ELEMENT, query, "Not found query input", 10);
    }

    public void clickOnCloseSearchButton() {
        //для iOS - иная логика закрытия поиска: поиск отменяется нажатием на Cancel, а очищается по крестику
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Not found close button", 5);
            this.waitForElementNotPresent(SEARCH_CLOSE_BUTTON, "Close button is still active", 10);
        } else {
            this.waitForElementAndClick(SEARCH_CLEAR_BUTTON, "Not found clear button", 5);
            this.waitForElementNotPresent(SEARCH_CLEAR_BUTTON, "Clear button is still active", 10);
        }
    }

    private static String getResultsSearchElement(String forReplacement, String substring) {
        return forReplacement.replace("{SUBSTRING}", substring);
    }

    private static String getResultsSearchElement(String forReplacement, String substring1, String substring2) {
        return forReplacement.replace("{SUBSTRING1}", substring1).replace("{SUBSTRING2}", substring2);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String xpath = getResultsSearchElement(SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION, title, description);
        this.waitForElementPresent(xpath,
                "Cannot find result by description " + description + " and title " + title, 15);
    }

    public void waitForSearchResultByDescription(String description) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_DESCRIPTION, description);
        this.waitForElementPresent(xpath,
                "Cannot find result by description " + description, 5);
    }

    public void waitForSearchResultByTitle(String title) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, title);
        this.waitForElementPresent(xpath,
                "Cannot find result by title " + title, 5);
    }

    public ArticlePage clickOnSearchResultByTitle(String articleTitle) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, articleTitle);
        this.waitForElementAndClick(xpath,"Cannot click on needed article by title " + articleTitle, 10);
        return ArticlePageFactory.get(driver);
    }

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
        return ArticlePageFactory.get(driver);

    }

    public void goBackOnMainPageFromSearch() {
        //для iOS это кнопка Close
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(ARROW_BACK_BUTTON,
                    "Not found back arrow in search results", 5);
        } else {
            this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Not found iOS close button", 10);
        }

    }

    public int getAmountOfFoundArticles() {
        //для поиска по номеру в iOS воспользоваться иной логикой - List<WebElement>, уже есть getAllSearchResults()
        this.waitForElementPresent(RESULTS_LIST, "No results in search");
        //return this.getAmountOfElements(ANY_RESULT_TITLE);
        return this.getAllSearchResults().size();
    }

    public void assertThatSomeArticlesAreFoundOnSearch() {
        this.waitForElementPresent(RESULTS_LIST, "No results in search");
        this.assertElementPresent(ANY_RESULT);
    }

    public void waitForEmptySearchResultByQuery(String searchString) {
        this.waitForElementPresent(EMPTY_SEARCH_RESULTS_LABEL,
                "Not empty results by request " + searchString, 10);
    }

    public void assertThatAreNotFoundAnyArticles() {
        this.assertElementNotPresent(ANY_RESULT);
    }

    public String getTitleOfSomeArticleInResults(int numberOfArticle) {
        //для iOS нужно по-другому - через список всех результатов
        if(Platform.getInstance().isAndroid()) {
            return this.waitForElementAndGetAttribute
                    ("xpath://*[@resource-id='" + RESULTS_LIST.split(Pattern.quote(":"), 2)[1] + "']/android.view.ViewGroup["
                                    + numberOfArticle
                                    + "]/*[@resource-id='" + ANY_RESULT.split(Pattern.quote(":"), 2)[1] + "']",
                            "text", "Cannot find an article in results", 15);
        } else {
            List<WebElement> results = this.getAllSearchResults();
            return results.get(numberOfArticle-1).getAttribute("name");
        }

    }

    public void clearQueryInput() {
        this.waitForElementAndClear(QUERY_INPUT_ELEMENT, "Not found a query input", 5);
    }

    public List<WebElement> getAllSearchResults() {
        this.waitForElementPresent(RESULTS_LIST, "Not present result list");
        WebElement resultsList = driver.findElement(this.getLocatorByString(RESULTS_LIST));
        By by = this.getLocatorByString(ANY_RESULT);
        return resultsList.findElements(by);
    }
}
