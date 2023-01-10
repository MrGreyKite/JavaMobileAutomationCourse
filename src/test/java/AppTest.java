
import lib.CoreTest;
import lib.pages.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import java.util.List;


public class AppTest extends CoreTest {

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
    void testCompareArticleTitle() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");

        String title = "Java (programming language)";

        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(title);
        articlePage.assertThatArticleHasARightTitle(articlePage.waitForTitleElement(), title);
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
    void testSwipeArticleToTheEnd() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle("Java (programming language)");
        articlePage.waitForTitleElement();
        articlePage.swipeArticleToTheEnd(50);
    }

    @Test
    void testSaveFirstArticleInDefaultList() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");

        String articleTitle = "Appium";
        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(articleTitle);
        articlePage.saveArticleToDefaultList();
        articlePage.goBackByArrowButton();
        searchPage.goBackOnMainPageFromSearch();

        NavigationPanel navPanel = new NavigationPanel(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName("Saved");
        ArticlePage articlePageFromList = savedLists.openArticleFromList(articleTitle);
        articlePageFromList.assertThatArticleHasARightTitle(articlePageFromList.waitForTitleElement(), articleTitle);

    }

    @Test
    void testSaveArticleAndDelete() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");
        String articleTitle = "Appium";

        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(articleTitle);
        articlePage.saveArticleToDefaultList();
        articlePage.goBackByArrowButton();
        searchPage.goBackOnMainPageFromSearch();

        NavigationPanel navPanel = new NavigationPanel(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName("Saved");
        savedLists.waitForArticleInListByTitle(articleTitle);
        savedLists.swipeArticleToDeleteFromList(articleTitle);
        savedLists.assertThatArticleIsDeletedFromList(articleTitle);
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

    @Test
    void testChangeScreenOrientationOnArticlePage() {
        SearchPage searchPage = new SearchPage(driver);
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
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        String firstPageTitleBeforeRotation = searchPage.getTitleOfSomeArticleInResults(1);
        this.rotateScreenToLandscape();
        String firstPageTitleAfterRotation = searchPage.getTitleOfSomeArticleInResults(1);

        Assertions.assertEquals(firstPageTitleBeforeRotation, firstPageTitleAfterRotation, "First title changed since rotation!");
    }

    @Test
    void testReturnAppFromBackground() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        searchPage.waitForSearchResultByTitle("JavaScript");
        this.getAppToBackground(5);
        searchPage.clickOnSearchResultByTitle("JavaScript");
    }


    //Рефакторинг предыдущих тестов - ДЗ

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

    //Урок 4, ДЗ-1
    @Test
    void testOnSaveTwoArticlesAndDeleteOne() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        String firstPageTitle = searchPage.getTitleOfSomeArticleInResults(1);
        String secondPageTitle = searchPage.getTitleOfSomeArticleInResults(2);
        ArticlePage firstArticle = searchPage.clickOnSearchResultByTitle(firstPageTitle);

        String nameOfTheList = "List for new articles";
        firstArticle.saveArticleToCustomListFirstTime(nameOfTheList);
        firstArticle.goBackByArrowButton();

        ArticlePage secondArticle = searchPage.clickOnSearchResultByTitle(secondPageTitle);
        secondArticle.saveArticleToExistingCustomList(nameOfTheList);
        secondArticle.goBackByArrowButton();

        searchPage.goBackOnMainPageFromSearch();
        NavigationPanel navPanel = new NavigationPanel(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName(nameOfTheList);
        savedLists.swipeArticleToDeleteFromList(firstPageTitle);
        savedLists.assertThatArticleIsDeletedFromList(firstPageTitle);
        ArticlePage secondArticleFromList = savedLists.openArticleFromList(secondPageTitle);
        secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(), secondPageTitle);
    }


    //Урок 4, ДЗ-2
    @Test
    void testRandomArticleHasTitle() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");
        ArticlePage articlePage = searchPage.clickOnSearchResultByNumber(2);
        articlePage.checkThatArticleHasATitleElement();
    }

}
