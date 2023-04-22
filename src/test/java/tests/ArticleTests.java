package tests;

import io.qameta.allure.*;
import lib.CoreTest;
import lib.Platform;
import lib.pages.ArticlePage;
import lib.pages.SearchPage;
import lib.pages.factories.SearchPageFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lib.pages.CorePage.screenshot;

@Epic("Tests for articles")
@DisplayName("Article Tests")
public class ArticleTests extends CoreTest {

    @Test
    @Features({@Feature("Search"), @Feature("Article view")})
    @DisplayName("Test for title in search results and in article page are identical")
    @Description("Opening a some article")
    @Severity(SeverityLevel.BLOCKER)
    void testCompareArticleTitle() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Java");

        String title = "Java (programming language)";

        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(title);
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            articlePage.assertThatArticleHasARightTitle(articlePage.waitForTitleElement(), title);
        } else {
            articlePage.assertThatArticleHasARightTitle(articlePage.waitForTitleElement(title), title);
        }
    }

    /* old version of partial swipe, without page-objectifying
    @Test
    void testSwipeArticle() {
        searchPage.searchSomethingOnInput("Appium");
        mainPage.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Not found needed article", 10);
        mainPage.waitForElementPresent(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*[@class='android.widget.TextView']"),
                "Not found title on page");
        mainPage.swipeUp(5);
    }
    */

    @Test
    @Features({@Feature("Article view"), @Feature("Scrolling")})
    @DisplayName("Test for ability to swipe an article")
    @Description("It opens an article and swipes it to the end")
    @Severity(SeverityLevel.MINOR)
    void testSwipeArticleToTheEnd() {
        String searchQuery = "Java";
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput(searchQuery);
        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle("Java (programming language)");
        screenshot(articlePage.takeScreenshot("opened_page_" + searchQuery));
        articlePage.waitForTitleElement();
        articlePage.swipeArticleToTheEnd(30); //not working on Mobile Web as should

    }

    //Урок 4, ДЗ-2
    @Test
    @Features({@Feature("Search"), @Feature("Article view")})
    @DisplayName("Test for existing a title element in the article")
    @Severity(SeverityLevel.NORMAL)
    void testRandomArticleHasTitle() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");
        ArticlePage articlePage = searchPage.clickOnSearchResultByNumber(2);
        articlePage.checkThatArticleHasATitleElement();
    }
}
