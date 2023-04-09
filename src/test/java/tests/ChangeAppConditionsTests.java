package tests;

import lib.CoreTest;
import lib.pages.ArticlePage;
import lib.pages.SearchPage;
import lib.pages.factories.SearchPageFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangeAppConditionsTests extends CoreTest {
    @Test
    void testChangeScreenOrientationOnArticlePage() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        ArticlePage articlePage = searchPage.clickOnSearchResultByNumber(1);
        String titleBeforeRotation = articlePage.getArticleTitle();
        this.rotateScreenToLandscape();
        articlePage.assertThatArticleHasARightTitle(articlePage.waitForTitleElement(), titleBeforeRotation);
        String titleAfterRotation = articlePage.getArticleTitle();
        this.rotateScreenToPortrait();
        articlePage.assertThatArticleHasARightTitle(articlePage.waitForTitleElement(), titleAfterRotation);
    }

    @Test
    void testChangeScreenOrientationOnResultsPage() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        String firstPageTitleBeforeRotation = searchPage.getTitleOfSomeArticleInResults(1);
        this.rotateScreenToLandscape();
        String firstPageTitleAfterRotation = searchPage.getTitleOfSomeArticleInResults(1);

        Assertions.assertEquals(firstPageTitleBeforeRotation, firstPageTitleAfterRotation, "First title changed since rotation!");
    }

    @Test
    void testReturnAppFromBackground() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        searchPage.waitForSearchResultByTitle("JavaScript");
        this.getAppToBackground(5);
        searchPage.clickOnSearchResultByTitle("JavaScript");
    }
}
