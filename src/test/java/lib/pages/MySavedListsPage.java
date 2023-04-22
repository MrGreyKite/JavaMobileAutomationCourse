package lib.pages;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
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

    @Step("Open list '{listTitle}'")
    public void openListByName(String listTitle) {
        String xpath = getListXpathFromTemplate(LIST_BY_NAME_TEMPLATE, listTitle);
        this.waitForElementAndClick(xpath,
                "Not found list by name " + listTitle, 5);
        screenshot(this.takeScreenshot("after_opening_list"));
    }

    @Step("Wait for the article with name '{articleTitle}' to appear in list")
    public void waitForArticleInListByTitle(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementPresent(xpath, "Cannot find an article in list by title " + articleTitle);
    }

    @Step("Open article with title '{articleTitle}'")
    public ArticlePage openArticleFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementAndClick(xpath,
                "Not found saved article with title " + articleTitle, 15);
        screenshot(this.takeScreenshot("after_opening_article"));
        return ArticlePageFactory.get(driver);
    }

    @Step("Delete article '{articleTitle}' from the list by swipe")
    public void swipeArticleToDeleteFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + "/..";
        this.swipeToTheLeft(xpath,
                "Not found element to swipe by title " + articleTitle, 5);
        if(Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(DELETE_BUTTON, "Not found delete icon", 10);
        }
        screenshot(this.takeScreenshot("after_deletion"));
    }

    //ДЗ-17, вспомогательный метод
    @Step("Delete article '{articleTitle}' from the list by click on icon")
    public void clickToRemoveFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + DELETE_BUTTON;
        this.waitForElementAndClick(xpath, "Cannot click on element to remove article from list", 15);
        screenshot(this.takeScreenshot("after_deletion"));
    }

    @Step("Verify absence of deleted article '{articleTitle}'")
    public void assertThatArticleIsDeletedFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementNotPresent(xpath,
                "Article with title " + articleTitle + " was not deleted", 10);
    }
}
