package tests;

import lib.CoreTest;
import lib.Platform;
import lib.pages.*;
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
        if (Platform.getInstance().isMW()) {
            AuthorizationPage authPage = articlePage.clickLoginButtonInView();
            authPage.authorize("testrb","rb26rb26");
        }
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            articlePage.goBackByArrowButton();
            searchPage.goBackOnMainPageFromSearch();
        }

        NavigationPanel navPanel = NavigationPanelFactory.get(driver);
        if (Platform.getInstance().isMW()) {
            navPanel.openMenu();
        }
        MySavedListsPage savedLists = navPanel.selectSavedListsTab();

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            savedLists.openListByName("Saved");
        }

        ArticlePage articlePageFromList = savedLists.openArticleFromList(articleTitle);
        articlePageFromList.assertThatArticleHasARightTitle(articlePageFromList.waitForTitleElement(), articleTitle);

    }

    @Test
    void testSaveArticleAndDelete() {
        NavigationPanel navPanel = NavigationPanelFactory.get(driver);

        if (Platform.getInstance().isMW()){
            navPanel.openMenu();
            AuthorizationPage authPage = navPanel.clickLogIn();
            authPage.authorize("testrb","rb26rb26");
        }
        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput("Appium");
        String articleTitle = "Appium";

        ArticlePage articlePage = searchPage.clickOnSearchResultByTitle(articleTitle);
        articlePage.saveArticleToDefaultList();

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            articlePage.goBackByArrowButton();
            searchPage.goBackOnMainPageFromSearch();
        }

        if (Platform.getInstance().isMW()) {
            navPanel.openMenu();
        }

        MySavedListsPage savedLists = navPanel.selectSavedListsTab();

        if (Platform.getInstance().isAndroid()) {
            savedLists.openListByName("Saved");
        }

        savedLists.waitForArticleInListByTitle(articleTitle);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            savedLists.swipeArticleToDeleteFromList(articleTitle);
        }

        if (Platform.getInstance().isMW()) {
            savedLists.clickToRemoveFromList(articleTitle);
            driver.navigate().refresh();
        }

        savedLists.assertThatArticleIsDeletedFromList(articleTitle);
    }

    //Урок 4, ДЗ-1
    @Test
    void testOnSaveTwoArticlesAndDeleteOne() {
        String searchQuery;
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            searchQuery = "Java";
        } else {searchQuery = "Appium";}
        String nameOfTheList = "List for new articles";
        String firstPageTitle;
        String secondPageTitle;

        SearchPage searchPage = SearchPageFactory.get(driver);
        searchPage.searchSomethingOnInput(searchQuery);
        firstPageTitle = searchPage.getTitleOfSomeArticleInResults(1);

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            secondPageTitle = searchPage.getTitleOfSomeArticleInResults(2);
            ArticlePage firstArticle = searchPage.clickOnSearchResultByTitle(firstPageTitle);

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

            if(Platform.getInstance().isIOS()) {
                secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(secondPageTitle),
                        secondPageTitle);
            } else {
                secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(),
                        secondPageTitle);
            }

        //ДЗ-17
        } else if (Platform.getInstance().isMW()) {
            ArticlePage firstArticle = searchPage.clickOnSearchResultByTitle(firstPageTitle);
            firstArticle.saveArticleToDefaultList();

            AuthorizationPage authPage = firstArticle.clickLoginButtonInView();
            authPage.authorize("testrb","rb26rb26");

            searchPage.searchSomethingOnInput(searchQuery);
            secondPageTitle = searchPage.getTitleOfSomeArticleInResults(2);
            System.out.println(secondPageTitle);
            ArticlePage secondArticle = searchPage.clickOnSearchResultByTitle(secondPageTitle);
            secondArticle.saveArticleToDefaultList();

            NavigationPanel navPanel = NavigationPanelFactory.get(driver);
            navPanel.openMenu();

            MySavedListsPage savedLists = navPanel.selectSavedListsTab();

            savedLists.clickToRemoveFromList(firstPageTitle);

            driver.navigate().to(driver.getCurrentUrl());
            driver.navigate().refresh();

            savedLists.assertThatArticleIsDeletedFromList(firstPageTitle);
            ArticlePage secondArticleFromList = savedLists.openArticleFromList(secondPageTitle);

            secondArticleFromList.assertThatArticleHasARightTitle(secondArticleFromList.waitForTitleElement(),
                    secondPageTitle);
        }


    }
}
