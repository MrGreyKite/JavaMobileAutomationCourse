package tests;

import lib.CoreTest;
import lib.Platform;
import lib.pages.ArticlePage;
import lib.pages.MySavedListsPage;
import lib.pages.NavigationPanel;
import lib.pages.SearchPage;
import lib.pages.factories.NavigationPanelFactory;
import lib.pages.factories.SearchPageFactory;
import org.junit.jupiter.api.Test;

public class ListsTests extends CoreTest {

    @Test
    void testSaveFirstArticleInDefaultList() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");

        String articleTitle = "Appium";
        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(articleTitle);
        articlePage.saveArticleToDefaultList();
        articlePage.goBackByArrowButton();
        searchPage.goBackOnMainPageFromSearch();

        NavigationPanel navPanel = NavigationPanelFactory.get(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName("Saved");
        ArticlePage articlePageFromList = savedLists.openArticleFromList(articleTitle);
        articlePageFromList.assertThatArticleHasARightTitle(articlePageFromList.waitForTitleElement(), articleTitle);

    }

    @Test
    void testSaveArticleAndDelete() {
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");
        String articleTitle = "Appium";

        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(articleTitle);
        articlePage.saveArticleToDefaultList();
        articlePage.goBackByArrowButton();
        searchPage.goBackOnMainPageFromSearch();

        NavigationPanel navPanel = NavigationPanelFactory.get(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName("Saved");
        savedLists.waitForArticleInListByTitle(articleTitle);
        savedLists.swipeArticleToDeleteFromList(articleTitle);
        savedLists.assertThatArticleIsDeletedFromList(articleTitle);
    }

    //Урок 4, ДЗ-1
    @Test
    void testOnSaveTwoArticlesAndDeleteOne() {
        String searchQuery;
        if(Platform.getInstance().isAndroid()) {
            searchQuery = "Java";
        } else {searchQuery = "Appium";}

        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput(searchQuery);
        String firstPageTitle = searchPage.getTitleOfSomeArticleInResults(1);
        String secondPageTitle = searchPage.getTitleOfSomeArticleInResults(2);
        ArticlePage firstArticle = searchPage.clickOnSearchResultByTitle(firstPageTitle);

        String nameOfTheList = "List for new articles";
        firstArticle.saveArticleToCustomListFirstTime(nameOfTheList, firstPageTitle);
        firstArticle.goBackByArrowButton();

        ArticlePage secondArticle = searchPage.clickOnSearchResultByTitle(secondPageTitle);
        secondArticle.saveArticleToExistingCustomList(nameOfTheList, secondPageTitle);
        secondArticle.goBackByArrowButton();

        searchPage.goBackOnMainPageFromSearch();
        NavigationPanel navPanel = NavigationPanelFactory.get(driver);
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();
        savedLists.openListByName(nameOfTheList);
        savedLists.swipeArticleToDeleteFromList(firstPageTitle);
        savedLists.assertThatArticleIsDeletedFromList(firstPageTitle);
        ArticlePage secondArticleFromList = savedLists.openArticleFromList(secondPageTitle);
        if(Platform.getInstance().isAndroid()) {
            secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(),
                    secondPageTitle);
        } else {
            secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(secondPageTitle),
                    secondPageTitle);
        }

    }
}
