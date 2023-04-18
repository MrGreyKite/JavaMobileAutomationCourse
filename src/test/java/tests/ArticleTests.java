package tests;

import lib.CoreTest;
import lib.Platform;
import lib.pages.ArticlePage;
import lib.pages.SearchPage;
import lib.pages.factories.SearchPageFactory;
import org.junit.jupiter.api.Test;

public class ArticleTests extends CoreTest {

    @Test
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
    void testSwipeArticleToTheEnd() throws InterruptedException {
        String searchQuery = "Java";
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput(searchQuery);
        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle("Java (programming language)");
        articlePage.waitForTitleElement();
        articlePage.swipeArticleToTheEnd(30);

        Thread.sleep(6000);
    }

    //Урок 4, ДЗ-2
    @Test
    void testRandomArticleHasTitle() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");
        ArticlePage articlePage = searchPage.clickOnSearchResultByNumber(2);
        articlePage.checkThatArticleHasATitleElement();
    }
}
