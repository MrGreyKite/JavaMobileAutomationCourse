package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPage extends CorePage {
    public SearchPage(AppiumDriver driver) {
        super(driver);
    }

    private static final String SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container";
    private static final String QUERY_INPUT_ELEMENT = "org.wikipedia:id/search_src_text";
    private static final String RESULTS_LIST = "org.wikipedia:id/search_results_list";
    private static final String SOME_RESULT_BY_DESCRIPTION = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']";
    private static final String SOME_RESULT_BY_TITLE = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
    private static final String ANY_RESULT_TITLE = "org.wikipedia:id/page_list_item_title";
    private static final String SEARCH_CLOSE_BUTTON = "org.wikipedia:id/search_close_btn";
    private static final String ARROW_BACK_BUTTON = "//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton";
    private static final String EMPTY_SEARCH_RESULTS_LABEL = "//*[@text='No results']";

    public void initSearch() {
        this.waitForElementPresent(By.id(SEARCH_INIT_ELEMENT), "Not found init search input", 5);
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Cannot click by init search input", 2);
    }

    public void typeSearchQuery(String queryText) {
        this.waitForElementPresent(By.id(QUERY_INPUT_ELEMENT), "Not found a query input", 5);
        this.waitForElementAndSendKeys(By.id(QUERY_INPUT_ELEMENT), queryText, "Cannot send keys to query input", 5);
    }

    public void assertThatQueryInputHasARightPlaceholder() {
        assertElementHasText(By.id(QUERY_INPUT_ELEMENT),
                "Search Wikipedia",
                "Query input doesn't have expected placeholder text");
    }

    //Урок 3, ДЗ-2
    public void searchSomethingOnInput(String query) {
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Not found search input", 5);
        this.waitForElementAndSendKeys(By.id(QUERY_INPUT_ELEMENT), query, "Not found query input", 5);
    }

    public void clickOnCloseSearchButton() {
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BUTTON), "Not found close button", 5);
        this.waitForElementNotPresent(By.id(SEARCH_CLOSE_BUTTON), "Close button is still active", 10);
    }

    private static String getResultsSearchElement(String forReplacement, String substring) {
        return forReplacement.replace("{SUBSTRING}", substring);
    }

    public void waitForSearchResultByDescription(String description) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_DESCRIPTION, description);
        this.waitForElementPresent(By.xpath(xpath),
                "Cannot find result by description " + description, 5);
    }

    public void waitForSearchResultByTitle(String title) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, title);
        this.waitForElementPresent(By.xpath(xpath),
                "Cannot find result by title " + title, 5);
    }

    public ArticlePage clickOnSearchResultByTitle(String articleTitle) {
        String xpath = getResultsSearchElement(SOME_RESULT_BY_TITLE, articleTitle);
        this.waitForElementAndClick(By.xpath(xpath),"Cannot click on needed article by title " + articleTitle, 10);
        return new ArticlePage(driver);
    }

    public ArticlePage clickOnSearchResultByNumber(int numberOfArticleInResults) {
        this.waitForElementAndClick
                (By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup["
                                + numberOfArticleInResults
                                + "]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                        "Cannot find an article in results", 15);
        return new ArticlePage(driver);
    }

    public void goBackOnMainPageFromSearch() {
        this.waitForElementAndClick(By.xpath(ARROW_BACK_BUTTON),
                "Not found back arrow in search results", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(By.id(RESULTS_LIST), "No results in search");
        return this.getAmountOfElements(By.id(ANY_RESULT_TITLE));
    }

    public void assertThatSomeArticlesAreFoundOnSearch() {
        this.waitForElementPresent(By.id(RESULTS_LIST), "No results in search");
        this.assertElementPresent(By.id(ANY_RESULT_TITLE));
    }

    public void waitForEmptySearchResultByQuery(String searchString) {
        this.waitForElementPresent(By.xpath(EMPTY_SEARCH_RESULTS_LABEL),
                "Not empty results by request " + searchString, 10);
    }

    public void assertThatAreNotFoundAnyArticles() {
        this.assertElementNotPresent(By.id(ANY_RESULT_TITLE));
    }

    public String getTitleOfSomeArticleInResults(int numberOfArticle) {
        return this.waitForElementAndGetAttribute
                (By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup["
                                + numberOfArticle
                                + "]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Cannot find an article in results", 15);
    }
}
