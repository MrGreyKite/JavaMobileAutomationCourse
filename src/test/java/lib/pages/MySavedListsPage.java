package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MySavedListsPage extends CorePage {
    public MySavedListsPage(AppiumDriver driver) {
        super(driver);
    }

    private static final String LIST_BY_NAME_TEMPLATE = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{TITLE}']";
    private static final String ARTICLE_IN_THE_LIST = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";

    private static String getListXpathFromTemplate(String forReplacement, String substring) {
        return forReplacement.replace("{TITLE}", substring);
    }

    public void openListByName(String listTitle) {
        String xpath = getListXpathFromTemplate(LIST_BY_NAME_TEMPLATE, listTitle);
        this.waitForElementAndClick(By.xpath(xpath),
                "Not found list by name " + listTitle, 5);
    }

    public void waitForArticleInListByTitle(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementPresent(By.xpath(xpath), "Cannot find an article in list by title " + articleTitle);
    }

    public ArticlePage openArticleFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle);
        this.waitForElementAndClick(By.xpath(xpath),
                "Not found saved article with title " + articleTitle, 5);
        return new ArticlePage(driver);
    }

    public void swipeArticleToDeleteFromList(String articleTitle) {
        String xpath = getListXpathFromTemplate(ARTICLE_IN_THE_LIST, articleTitle) + "/..";
        this.swipeToTheLeft(By.xpath(xpath),
                "Not found element to swipe by title " + articleTitle, 5);
    }

    public void assertThatArticleIsDeletedFromList(String articleTitle) {
        this.waitForElementNotPresent(By.xpath("[@text='" + articleTitle + "']"),
                "Cannot delete article with title " + articleTitle, 10);
    }
}
