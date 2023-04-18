package lib.pages;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.pages.factories.ArticlePageFactory;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MySavedListsPage extends CorePage {
    public MySavedListsPage(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String
    LIST_BY_NAME_TEMPLATE, ARTICLE_IN_THE_LIST, DELETE_BUTTON;

    private static String getListXpathFromTemplate(String forReplacement, String substring) {
        return forReplacement.replace("{TITLE}", substring);
    }

    public void openListByName(String listTitle) {
        String xpath = getListXpathFromTemplate(LIST_BY_NAME_TEMPLATE, listTitle);
        this.waitForElementAndClick(xpath,
                "Not found list by name " + listTitle, 5);
    }

    public void waitForArticleInListByTitle(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementPresent(xpath, "Cannot find an article in list by title " + articleTitle);
    }

    public ArticlePage openArticleFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementAndClick(xpath,
                "Not found saved article with title " + articleTitle, 15);
        return ArticlePageFactory.get(driver);
    }

    public void swipeArticleToDeleteFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + "/..";
        this.swipeToTheLeft(xpath,
                "Not found element to swipe by title " + articleTitle, 5);
        if(Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(DELETE_BUTTON, "Not found delete icon", 10);
        }
    }

    //ДЗ-17, вспомогательный метод
    public void clickToRemoveFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + DELETE_BUTTON;
        this.waitForElementAndClick(xpath, "Cannot click on element to remove article from list", 15);
    }

    public void assertThatArticleIsDeletedFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementNotPresent(xpath,
                "Article with title " + articleTitle + " was not deleted", 10);
    }
}
