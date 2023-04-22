package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lib.CoreTest;
import lib.pages.ArticlePage;
import lib.pages.SearchPage;
import lib.pages.factories.SearchPageFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("only Android/iOS apps")
@Epic("Side influence on app")
public class ChangeAppConditionsTests extends CoreTest {
    @Test
    @DisplayName("Test for change screen orientation - 1")
    @Description("Asserting that change orientation does not affect an article page")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("Test for change screen orientation - 2")
    @Description("Asserting that change orientation does not affect results page")
    @Severity(SeverityLevel.CRITICAL)
    void testChangeScreenOrientationOnResultsPage() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        String firstPageTitleBeforeRotation = searchPage.getTitleOfSomeArticleInResults(1);
        this.rotateScreenToLandscape();
        String firstPageTitleAfterRotation = searchPage.getTitleOfSomeArticleInResults(1);

        Assertions.assertEquals(firstPageTitleBeforeRotation, firstPageTitleAfterRotation, "First title changed since rotation!");
    }

    @Test
    @DisplayName("Test for putting the app in the background")
    @Severity(SeverityLevel.MINOR)
    void testReturnAppFromBackground() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");
        searchPage.waitForSearchResultByTitle("JavaScript");
        this.getAppToBackground(5);
        searchPage.clickOnSearchResultByTitle("JavaScript");
    }
}
