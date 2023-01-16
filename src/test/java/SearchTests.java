import lib.CoreTest;
import lib.pages.SearchPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTests extends CoreTest {

    @Test
    void testFirstSearch() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.initSearch();
        searchPage.typeSearchQuery("Java");
        searchPage.waitForSearchResultByDescription("Object-oriented programming language");
    }

    @Test
    void testQueryInputHasRightText() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.initSearch();
        searchPage.assertThatQueryInputHasARightPlaceholder();
    }

    @Test
    void testAbortSearchAttempt() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("App");
        searchPage.clickOnCloseSearchButton();
    }

    @Test
    void testProductiveSearch() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Linkin Park");
        searchPage.assertThatSomeArticlesAreFoundOnSearch();
    }

    @Test
    void testEmptySearch() {
        SearchPage searchPage = new SearchPage(driver);
        String randomSearchString = RandomStringUtils.randomAlphabetic(10);
        searchPage.searchSomethingOnInput(randomSearchString);
        searchPage.waitForEmptySearchResultByQuery(randomSearchString);
        searchPage.assertThatAreNotFoundAnyArticles();
    }

    //Урок 3, ДЗ-2
    @Test
    void testDoSearchAndClearInput(){
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");
        Assertions.assertNotEquals(0, searchPage.getAmountOfFoundArticles());
        searchPage.clearQueryInput();
        searchPage.assertThatAreNotFoundAnyArticles();
    }

    //Урок 3, ДЗ-3
    @Test
    void testDoSearchAndFindResults() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        List<WebElement> searchResults = searchPage.getAllSearchResults();
        searchResults.forEach(result -> Assertions.assertTrue((result.getText()).contains("Java")));
    }

    //Урок 5, ДЗ-2
    @Test
    void testOnFirstThreeResultsOfSearch() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");

        Map<String, String> articlesOnDisplay = new HashMap<>();
        articlesOnDisplay.put("Java", "Island in Indonesia");
        articlesOnDisplay.put("JavaScript", "High-level programming language");
        articlesOnDisplay.put("Java (programming language)", "Object-oriented programming language");

        for (Map.Entry<String, String> article : articlesOnDisplay.entrySet()) {
            searchPage.waitForElementByTitleAndDescription(article.getKey(), article.getValue());
        }

    }
}
