import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class AppTest {

    private static AppiumDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
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

    @AfterAll
    static void endDriver(){
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
    void searchAttemptAndClose() {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Not found search input", 5);
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"), "App", "Not found search input", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"), "Not found close button", 5);
        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"), "Close button is still active", 10);
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


    //Урок 3, ДЗ-1
    private void assertElementHasText(By locator, String expectedText, String errorMessage) {

    }




}
