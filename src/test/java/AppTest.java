import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class AppTest {

    private static AppiumDriver driver;

    @BeforeAll
    static void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Nerve\\IdeaProjects\\JavaMobileAutomationCourse\\src\\test\\resources\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL ("http://localhost:4723/wd/hub"), capabilities);

    }

    @BeforeEach
    void skipOnboarding() {
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
        WebElement elementToEnterSearchQuery = waitForElementPresentByXPath("//*[@resource-id='org.wikipedia:id/search_src_text']",
                "Not found query input");
        elementToEnterSearchQuery.sendKeys("Java");
        waitForElementPresentByXPath("//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']",
                "Not found object with needed text",
                10);
    }

    private WebElement waitForElementPresentByXPath(String xpath, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresentByXPath(String xpath, String errorMessage) {
        return waitForElementPresentByXPath(xpath, errorMessage, 5);
    }



    //Урок 3, ДЗ-1
    private void assertElementHasText(By locator, String expectedText, String errorMessage) {

    }

    @Test
    void searchInputHasRightText() {

    }



}
