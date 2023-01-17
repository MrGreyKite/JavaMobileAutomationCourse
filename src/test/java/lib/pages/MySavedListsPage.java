package lib.pages;

import io.appium.java_client.AppiumDriver;

public class MySavedListsPage extends CorePage {
    public MySavedListsPage(AppiumDriver driver) {
        super(driver);
    }

    private static final String LIST_BY_NAME_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{TITLE}']";
    private static final String ARTICLE_IN_THE_LIST = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";

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
                "Not found saved article with title " + articleTitle, 5);
        return new ArticlePage(driver);
    }

    public void swipeArticleToDeleteFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + "/..";
        this.swipeToTheLeft(xpath,
                "Not found element to swipe by title " + articleTitle, 5);
    }

    public void assertThatArticleIsDeletedFromList(String articleTitle) {
        this.waitForElementNotPresent("xpath:[@text='" + articleTitle + "']",
                "Cannot delete article with title " + articleTitle, 10);
    }
}
