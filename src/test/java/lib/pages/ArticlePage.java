package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePage extends CorePage {
    public ArticlePage(AppiumDriver driver) {
        super(driver);
    }

    private static final String ARTICLE_DESCRIPTION = "xpath://*[@resource-id='pcs-edit-section-title-description']";
    private static final String ARTICLE_TITLE = ARTICLE_DESCRIPTION + "/preceding-sibling::*[@class='android.widget.TextView']";
    private static final String ARTICLE_FOOTER = "xpath://android.view.View[@content-desc=\"View article in browser\"]/android.widget.TextView";
    private static final String SAVE_BUTTON = "id:org.wikipedia:id/page_save";
    private static final String SNACKBAR_ACTION = "id:org.wikipedia:id/snackbar_action";
    private static final String ARROW_BACK_BUTTON = "xpath://android.widget.ImageButton[@content-desc=\"Navigate up\"]";
    private static final String MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
    private static final String MY_LIST_OK_BUTTON = "id:android:id/button1";


    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE, "Cannot find article title", 5);
    }

    public void assertThatArticleHasARightTitle(WebElement element, String expectedTitle) {
        this.assertElementHasText(element, expectedTitle, "Not expected title: wanted " + expectedTitle);
    }

    public String getArticleTitle() {
        return this.waitForElementAndGetAttribute(ARTICLE_TITLE, "text", "Cannot find an article title", 5);
    }

    public void checkThatArticleHasATitleElement(){
        this.waitForTitleElement();
        this.assertElementPresent(ARTICLE_TITLE);
    }

    public void swipeArticleToTheEnd(int maxSwipes) {
        this.swipeUntilElementIsFound(ARTICLE_FOOTER
                , maxSwipes);
    }

    public void saveArticleToDefaultList(){
        this.waitForElementAndClick(SAVE_BUTTON, "Not found Save button", 5);
    }

    public void goBackByArrowButton() {
        this.waitForElementAndClick(ARROW_BACK_BUTTON,
                "Not found back arrow in article", 5);
    }

    public void saveArticle(){
        this.waitForElementAndClick(SAVE_BUTTON, "Not found Save button", 5);
        this.waitForElementAndClick(SNACKBAR_ACTION, "Not present Add to list text", 5);
    }

    public void saveArticleToCustomListFirstTime(String nameOfList) {
        this.saveArticle();
        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                nameOfList, "Not present text input", 5);
        this.waitForElementAndClick(MY_LIST_OK_BUTTON, "Not found OK button", 1);
    }

    public void saveArticleToExistingCustomList(String nameOfList) {
        this.saveArticle();
        this.waitForElementAndClick("xpath://*[contains(@text, '" + nameOfList + "')]",
                "Not found the list with name " + nameOfList, 5);
    }

}
