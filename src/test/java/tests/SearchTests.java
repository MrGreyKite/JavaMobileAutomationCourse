package tests;

import io.qameta.allure.*;
import lib.CoreTest;
import lib.pages.SearchPage;
import lib.pages.factories.SearchPageFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lib.pages.CorePage.screenshot;

@Epic("Tests for search")
@DisplayName("Search Tests")
public class SearchTests extends CoreTest {

    @Test
    @Features({@Feature("Search")})
    @DisplayName("Test for having search results with some description")
    @Severity(SeverityLevel.NORMAL)
    void testFirstSearch() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.initSearch();
        searchPage.typeSearchQuery("Java");
        searchPage.waitForSearchResultByDescription("Object-oriented programming language");
    }

    @Test
    @Features({@Feature("Search")})
    @DisplayName("Test for the correct text in search input field")
    @Severity(SeverityLevel.TRIVIAL)
    void testQueryInputHasRightText() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.initSearch();
        searchPage.assertThatQueryInputHasARightPlaceholder();
    }

    @Test
    @Features({@Feature("Search"), @Feature("Navigation")})
    @DisplayName("Test for quiting a search")
    @Description("Sometimes user may want to abort an attempt to search, maybe not intended")
    @Severity(SeverityLevel.MINOR)
    void testAbortSearchAttempt() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("App");
        searchPage.clickOnCloseSearchButton();
    }

    @Test
    @Features({@Feature("Search")})
    @DisplayName("Positive test for search results")
    @Description("This test asserts that user found at least some results when searching existing things")
    @Severity(SeverityLevel.BLOCKER)
    void testProductiveSearch() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Linkin Park");
        searchPage.assertThatSomeArticlesAreFoundOnSearch();
    }

    @Test
    @Features({@Feature("Search")})
    @DisplayName("Test for empty results")
    @Description("Test asserts that if there is non-existent search query, there is right text and nothing found")
    @Severity(SeverityLevel.NORMAL)
    void testEmptySearch() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        String randomSearchString = RandomStringUtils.randomAlphabetic(10);
        searchPage.searchSomethingOnInput(randomSearchString);
        searchPage.waitForEmptySearchResultByQuery(randomSearchString);
        searchPage.assertThatAreNotFoundAnyArticles();
    }

    //Урок 3, ДЗ-2
    @Test
    @Features({@Feature("Search")})
    @DisplayName("Test for clearing input field after some search attempt")
    @Severity(SeverityLevel.MINOR)
    void testDoSearchAndClearInput() throws InterruptedException {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");
        Assertions.assertNotEquals(0, searchPage.getAmountOfFoundArticles());
        searchPage.clearQueryInput();
        Thread.sleep(5000);
        searchPage.assertThatAreNotFoundAnyArticles();//in mobile Web test fails
        screenshot(searchPage.takeScreenshot("results_titles"));

    }

    //Урок 3, ДЗ-3
    @Test
    @Features({@Feature("Search")})
    @DisplayName("All results must contain a search query")
    @Severity(SeverityLevel.CRITICAL)
    void testDoSearchAndFindResults() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        List<WebElement> searchResults = searchPage.getAllSearchResults();
        searchResults.forEach(result -> Assertions.assertTrue((result.getText()).contains("Java")));
        screenshot(searchPage.takeScreenshot("search_results"));
        //тест падает на mobileWeb, потому что один из ответов содержит "JaVa" в другом регистре
    }

    //Урок 5, ДЗ-2
    @Test
    @Features({@Feature("Search")})
    @DisplayName("Test for search results having a specific titles and descriptions")
    @Description("It can be extended to more than three results check")
    @Severity(SeverityLevel.NORMAL)
    void testOnFirstThreeResultsOfSearch() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");

        Map<String, String> articlesOnDisplay = new HashMap<>();
        articlesOnDisplay.put("Java", "Island in Indonesia");
        articlesOnDisplay.put("JavaScript", "High-level programming language");
        articlesOnDisplay.put("Java (programming language)", "Object-oriented programming language");

        for (Map.Entry<String, String> article : articlesOnDisplay.entrySet()) {
            searchPage.waitForElementByTitleAndDescription(article.getKey(), article.getValue());
        }
        screenshot(searchPage.takeScreenshot("results_titles"));

    }
}
