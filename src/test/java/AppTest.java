import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class AppTest {

    private AppiumDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("rotatable", true);
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Nerve\\IdeaProjects\\JavaMobileAutomationCourse\\src\\test\\resources\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL ("http://localhost:4723/wd/hub"), capabilities);
        WebElement skipButton = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
        skipButton.click();


    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void firstAppTest() {
        WebElement elementToInitSearch = driver.findElement(By.xpath("//*[contains(@text, 'Search Wikipedia')]"));
        elementToInitSearch.click();
        WebElement elementToEnterSearchQuery = this.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Not found query input");
        elementToEnterSearchQuery.sendKeys("Java");
        waitForElementPresent(By.xpath("//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "Not found object with needed text",
                10);
    }

    @Test
    void doSearchAttemptAndClose() {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Not found search input", 5);
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"), "App", "Not found query input", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"), "Not found close button", 5);
        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"), "Close button is still active", 10);
    }

    @Test
    void testCompareArticleTitle() {
        searchSomethingOnInput("Java");
        String articleTitle = "Java (programming language)";
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']"),
                "Not found needed article", 10);
        assertElementHasText(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*[@class='android.widget.TextView']"),
                articleTitle, "Not expected title");

    }

    @Test
    void swipeArticle() {
        searchSomethingOnInput("Appium");
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Not found needed article", 10);
        waitForElementPresent(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*[@class='android.widget.TextView']"),
                "Not found title on page");
        swipeUp(5);
    }
    @Test
    void testSwipeArticleToTheEnd() {
        searchSomethingOnInput("Java");
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Not found needed article", 10);
        waitForElementPresent(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*[@class='android.widget.TextView']"),
                "Not found title on page");
        swipeUntilElementIsFound(By.xpath("//android.view.View[@content-desc=\"View article in browser\"]/android.widget.TextView")
                , 50);
    }

    @Test
    void testSaveFirstArticle() {
        searchSomethingOnInput("Appium");
        String articleTitle = "Appium";
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']"),
                "Not found needed article", 10);
        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Not found back arrow in article", 5);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton"),
                "Not found back arrow in search results", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/nav_tab_reading_lists"), "Not found Saved tab", 5);
        waitForElementAndClick(By.xpath("//*[@text='Default list for your saved articles']"),
                "Not found link on list", 5);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']"),
                "Not found saved article", 5);
    }

    @Test
    void testSaveArticleAndDelete() {
        searchSomethingOnInput("Appium");
        String articleTitle = "Appium";
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']"),
                "Not found needed article", 10);
        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Not found back arrow in article", 5);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton"),
                "Not found back arrow in search results", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/nav_tab_reading_lists"), "Not found Saved tab", 5);
        waitForElementAndClick(By.xpath("//*[@text='Default list for your saved articles']"),
                "Not found link on list", 5);
        swipeToTheLeft(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']/.."),
                "Not found element to swipe");
        waitForElementNotPresent(By.xpath("[@text='\" + articleTitle + \"']"), "Cannot delete article", 5);
    }

    @Test
    void testEmptySearch() {
        String randomSearchString = RandomStringUtils.randomAlphabetic(10);
        searchSomethingOnInput(randomSearchString);
        String emptySearchResultsLabel = "//*[@text='No results']";
        String resultsLocator = "org.wikipedia:id/page_list_item_title";
        waitForElementPresent(By.xpath(emptySearchResultsLabel),
                "Not empty results by request " + randomSearchString, 10);
        assertElementNotPresent(By.id("org.wikipedia:id/page_list_item_title"));
    }

    @Test
    void testChangeScreenOrientationOnResultsPage() {
        searchSomethingOnInput("Java");
        List<WebElement> results = waitForElementPresent(By.id("org.wikipedia:id/search_results_list"), "Not present").
                findElements(By.id("org.wikipedia:id/page_list_item_title"));
        String firstTitleBeforeRotation = waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "!!!", 10);
        ((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
        String firstTitleAfterRotation = waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Not found", 10);
        Assertions.assertEquals(firstTitleBeforeRotation, firstTitleAfterRotation, "First title changed since rotation");
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.click();
        return e;
    }

    private WebElement waitForElementAndSendKeys(By by, String query, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.sendKeys(query);
        return e;
    }

    private Boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.clear();
        return e;
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        return e.getAttribute(attribute);
    }

    private void swipeUp(int timeForSwipe) {
        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.9);
        int endY = (int) (size.height * 0.2);
        action.press(point(x, startY)).waitAction(waitOptions(Duration.ofSeconds(timeForSwipe))).
                moveTo(point(x, endY)).release().perform();

    }

    private void swipeToTheLeft(By locator, String errorMessage) {
        WebElement el = waitForElementPresent(locator, errorMessage, 10);
        int leftX = el.getLocation().getX();
        int rightX = leftX + el.getSize().width;
        int upperY = el.getLocation().getY();
        int lowerY = upperY + el.getSize().height;
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        action.press(point(leftX,middleY)).waitAction(waitOptions(Duration.ofSeconds(5))).moveTo(point(rightX, middleY)).release().perform();
    }

    private void swipeUntilElementIsFound(By by, int maxSwipes) {
        int alreadySwiped = 0;
        while(getAmountOfElements(by) == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element " + by.toString() + " while swiping");
                return;
            }
            swipeUp(1);
            ++alreadySwiped;
        }
    }

    private int getAmountOfElements(By locator){
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }
    private void assertElementNotPresent(By locator) {
        if (getAmountOfElements(locator) > 0) {
            throw new AssertionError("Element with locator '" + locator.toString() + "' should be not present");
        }
    }


    //Урок 3, ДЗ-1
    private void assertElementHasText(By locator, String expectedText, String errorMessage) {
        WebElement e = waitForElementPresent(locator, errorMessage);
        Assertions.assertEquals(expectedText, e.getText(), errorMessage);
    }

    @Test
    void testSearchInputHasRightText() {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Not found search input", 5);
        assertElementHasText(By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "Element doesn't have expected text");
    }

    //Урок 3, ДЗ-2

    private void searchSomethingOnInput(String query) {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Not found search input", 5);
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"), query, "Not found query input", 5);
    }

    @Test
    void testDoSearchAndClose(){
        searchSomethingOnInput("Appium");
        WebElement resultsParent = waitForElementPresent(By.id("org.wikipedia:id/search_results_list"), "Not present");
        List<WebElement> results = resultsParent.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assertions.assertFalse(results.isEmpty());
        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='Clear query']"), "Not found close button", 5);

        Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOf(resultsParent)));
    }

    //Урок 3, ДЗ-3
    @Test
    void testDoSearchAndFindResults() {
        searchSomethingOnInput("Java");
        WebElement resultsParent = waitForElementPresent(By.id("org.wikipedia:id/search_results_list"), "Not present");
        List<WebElement> results = resultsParent.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        results.forEach(result -> Assertions.assertTrue((result.getText()).contains("Java")));
    }

    //Урок 4, ДЗ-1
    @Test
    void testOnSaveTwoArticlesAndDeleteOne() {
        searchSomethingOnInput("Java");
        String firstTitle = waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Element not found", 10);
        String secondTitle = waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[2]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text", "Element not found", 10);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '" + firstTitle + "')]"), "No element", 1);
        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/snackbar_action"), "Not present Add to list text", 5);
        String nameOfTheList = "List for new articles";
        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                nameOfTheList, "Not present text input", 5);
        waitForElementAndClick(By.id("android:id/button1"), "Not found OK button", 1);
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "!", 5);
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '" + secondTitle + "')]"),
                "No element", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Not found Save button", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/snackbar_action"), "Not present Add to list text", 5);
        waitForElementAndClick(By.xpath("//*[contains(@text, '" + nameOfTheList + "')]"),
                "Not found the list with name " + nameOfTheList, 5);
        waitForElementAndClick(By.xpath("//*[@text=\"VIEW LIST\"]"),
                "not present snackBar", 5);
        swipeToTheLeft(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + firstTitle + "']/.."),
                "Not found element to swipe by text " + firstTitle);
        assertElementNotPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + firstTitle + "']"));
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + secondTitle + "']"),
                "Not found article in list with title " + secondTitle, 5);
        assertElementHasText(By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::android.widget.TextView"),
                secondTitle, "Not have title");
    }

    //Урок 4, ДЗ-2
    public void  assertElementPresent(By locator, String errorMessage) {
        if (getAmountOfElements(locator) == 0) {
            throw new AssertionError("Element with locator '" + locator.toString() + "' should be on the screen");
        }
    }

    @Test
    void testArticleHasTitle() {
        searchSomethingOnInput("Appium");
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup[1]/*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot click on first article", 5);
        assertElementPresent(By.xpath("//*[@resource-id='pcs']//android.widget.TextView[1]"),
                "Page doesn't have a title element");
    }

}
