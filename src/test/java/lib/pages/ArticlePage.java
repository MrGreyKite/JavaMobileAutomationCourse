package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePage extends CorePage {
    public ArticlePage(AppiumDriver driver) {
        super(driver);
    }

    private static final String ARTICLE_DESCRIPTION = "//*[@resource-id='pcs-edit-section-title-description']";
    private static final String ARTICLE_TITLE = ARTICLE_DESCRIPTION + "/preceding-sibling::*[@class='android.widget.TextView']";
    private static final String ARTICLE_FOOTER = "//android.view.View[@content-desc=\"View article in browser\"]/android.widget.TextView";
    private static final String SAVE_BUTTON = "org.wikipedia:id/page_save";
    private static final String SNACKBAR_ACTION = "org.wikipedia:id/snackbar_action";
    private static final String ARROW_BACK_BUTTON = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";
    private static final String MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input";
    private static final String MY_LIST_OK_BUTTON = "android:id/button1";


    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.xpath(ARTICLE_TITLE), "Cannot find article title", 5);
    }

    public void assertThatArticleHasARightTitle(WebElement element, String expectedTitle) {
        this.assertElementHasText(element, expectedTitle, "Not expected title: wanted " + expectedTitle);
    }

    public String getArticleTitle() {
        return this.waitForElementAndGetAttribute(By.xpath(ARTICLE_TITLE), "text", "Cannot find an article title", 5);
    }

    public void checkThatArticleHasATitleElement(){
        this.assertElementPresent(By.xpath(ARTICLE_TITLE));
    }

    public void swipeArticleToTheEnd(int maxSwipes) {
        this.swipeUntilElementIsFound(By.xpath(ARTICLE_FOOTER)
                , maxSwipes);
    }

    public void saveArticleToDefaultList(){
        this.waitForElementAndClick(By.id(SAVE_BUTTON), "Not found Save button", 5);
    }

    public void goBackByArrowButton() {
        this.waitForElementAndClick(By.xpath(ARROW_BACK_BUTTON),
                "Not found back arrow in article", 5);
    }

    public void saveArticleToCustomList(String nameOfList) {
        this.waitForElementAndClick(By.id(SAVE_BUTTON), "Not found Save button", 5);
        this.waitForElementAndClick(By.id(SNACKBAR_ACTION), "Not present Add to list text", 5);
        this.waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT),
                nameOfList, "Not present text input", 5);
        this.waitForElementAndClick(By.id(MY_LIST_OK_BUTTON), "Not found OK button", 1);
    }

}
