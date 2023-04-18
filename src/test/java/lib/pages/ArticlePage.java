package lib.pages;

import lib.Platform;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


abstract public class ArticlePage extends CorePage {
    public ArticlePage(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String
        ARTICLE_DESCRIPTION,
        ARTICLE_TITLE,
        ARTICLE_TITLE_TEMPLATE,
        ARTICLE_FOOTER,
        SAVE_BUTTON,
        DELETE_FROM_SAVED_BUTTON,
        LOG_IN_BUTTON,
        SNACKBAR_ACTION_TEMPLATE,
        SNACKBAR_ACTION,
        CONFIRM_NEW_LIST_CREATION,
        ARROW_BACK_BUTTON,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON;


    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE, "Cannot find article title", 10);
    }

    //перегрузка метода для получения названия статей в iOS
    public WebElement waitForTitleElement(String title) {
        return this.waitForElementPresent(getArticleFullTitleElement(title), "Cannot find a title", 10);
    }

    public void assertThatArticleHasARightTitle(WebElement element, String expectedTitle) {
        this.assertElementHasText(element, expectedTitle, "Not expected title: wanted " + expectedTitle);
    }

    public String getArticleTitle() {
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            return this.waitForElementAndGetAttribute(ARTICLE_TITLE, "text", "Cannot find an article title", 10);
        } else {
        return this.waitForElementAndGetAttribute(ARTICLE_TITLE, "name", "Cannot find an article title", 10);
    }
    }

    private static String getSnackbarAction(String title){
        return SNACKBAR_ACTION_TEMPLATE.replace("{SUBSTRING}", title);
    }

    private static String getArticleFullTitleElement(String title) {
        return ARTICLE_TITLE_TEMPLATE.replace("{SUBSTRING}", title);
    }

    public void checkThatArticleHasATitleElement(){
        this.waitForTitleElement();
        this.assertElementPresent(ARTICLE_TITLE);
    }

    public void swipeArticleToTheEnd(int maxSwipes) {
        if (Platform.getInstance().isMW()) {
            this.scrollMobileWebPageUntilElementIsFound(ARTICLE_FOOTER, maxSwipes);
        } else this.swipeUntilElementIsFound(ARTICLE_FOOTER
                , maxSwipes);
    }

    public void saveArticleToDefaultList(){
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfAdded();
            this.waitForElementAndClick(SAVE_BUTTON, "Not found Save button", 15);
        } else {
        this.waitForElementAndClick(SAVE_BUTTON, "Not found Save button", 15); }
    }

    public void goBackByArrowButton() {
        this.waitForElementAndClick(ARROW_BACK_BUTTON,
                "Not found back arrow in article", 5);
    }

    public void saveArticle(String title){
        this.waitForElementAndClick(SAVE_BUTTON, "Not found Save button", 5);
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(SNACKBAR_ACTION, "Not present Add to list text", 5);
        } else {
            this.waitForElementAndClick(getSnackbarAction(getArticleFullTitleElement(title)), "Not present action", 5);
        }
    }

    public void saveArticleToCustomListFirstTime(String nameOfList, String title) {
        this.saveArticle(title);
        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                nameOfList, "Not present text input", 5);
        this.waitForElementAndClick(MY_LIST_OK_BUTTON, "Not found OK button", 1);
    }

    public void saveArticleToExistingCustomList(String nameOfList, String title) {
        this.saveArticle(title);
        this.waitForElementAndClick("xpath://*[contains(@text, '" + nameOfList + "')]",
                "Not found the list with name " + nameOfList, 5);
    }

    public void removeArticleFromSavedIfAdded() {
        boolean alreadySaved = this.isElementPresent(DELETE_FROM_SAVED_BUTTON);
        if (alreadySaved) {
            this.waitForElementAndClick(DELETE_FROM_SAVED_BUTTON, "Cannot click on Unsave button", 15);
            this.waitForElementPresent(SAVE_BUTTON, "Cannot detect Save button", 15);
        }
    }

    public AuthorizationPage clickLoginButtonInView() {
       WebElement lb = this.waitForElementPresent(LOG_IN_BUTTON, "Login button not clickable", 10);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(lb)).click();
        return new AuthorizationPage(driver);
    }

}
