import lib.CoreTest;
import lib.pages.ArticlePage;
import lib.pages.MySavedListsPage;
import lib.pages.NavigationPanel;
import lib.pages.SearchPage;
import org.junit.jupiter.api.Test;

public class ListsTests extends CoreTest {

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
}
