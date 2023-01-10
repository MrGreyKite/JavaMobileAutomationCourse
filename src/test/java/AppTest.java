
import lib.CoreTest;
import lib.pages.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class AppTest extends CoreTest {

    private CorePage corePage = new CorePage(driver);

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


    //Урок 3, ДЗ-2
    @Test
    void testDoSearchAndClose(){
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");
        WebElement resultsParent = corePage.waitForElementPresent(By.id("org.wikipedia:id/search_results_list"), "Not present");
        List<WebElement> results = resultsParent.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assertions.assertFalse(results.isEmpty());
        corePage.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='Clear query']"), "Not found close button", 5);

        Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOf(resultsParent)));
    }

    //Урок 3, ДЗ-3
    @Test
    void testDoSearchAndFindResults() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");

        WebElement resultsParent = corePage.waitForElementPresent(By.id("org.wikipedia:id/search_results_list"), "Not present");
        List<WebElement> results = resultsParent.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        results.forEach(result -> Assertions.assertTrue((result.getText()).contains("Java")));
    }

    //Урок 4, ДЗ-1
    @Test
    void testOnSaveTwoArticlesAndDeleteOne() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Java");
        String firstTitle = corePage.waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Element not found", 10);
        String secondTitle = corePage.waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[2]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Element not found", 10);
        corePage.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '" + firstTitle + "')]"), "No element", 1);
        corePage.waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        corePage.waitForElementAndClick(By.id("org.wikipedia:id/snackbar_action"), "Not present Add to list text", 5);
        String nameOfTheList = "List for new articles";
        corePage.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                nameOfTheList, "Not present text input", 5);
        corePage.waitForElementAndClick(By.id("android:id/button1"), "Not found OK button", 1);
        corePage.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "!", 5);
        corePage.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '" + secondTitle + "')]"),
                "No element", 5);
        corePage.waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        corePage.waitForElementAndClick(By.id("org.wikipedia:id/snackbar_action"), "Not present Add to list text", 5);
        corePage.waitForElementAndClick(By.xpath("//*[contains(@text, '" + nameOfTheList + "')]"),
                "Not found the list with name " + nameOfTheList, 5);
        corePage.waitForElementAndClick(By.xpath("//*[@text=\"VIEW LIST\"]"),
                "not present snackBar", 5);
        corePage.swipeToTheLeft(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + firstTitle + "']/.."),
                "Not found element to swipe by text " + firstTitle, 5);
        corePage.assertElementNotPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + firstTitle + "']"));
        corePage.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + secondTitle + "']"),
                "Not found article in list with title " + secondTitle, 5);
        corePage.assertElementHasText(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::android.widget.TextView"),
                secondTitle, "Not have title");
    }


    //Урок 4, ДЗ-2
    @Test
    void testArticleHasTitle() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchSomethingOnInput("Appium");
        corePage.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot click on first article", 5);
        corePage.assertElementPresent(By.xpath("//*[@resource-id='pcs']//android.widget.TextView[1]"));
    }

}
