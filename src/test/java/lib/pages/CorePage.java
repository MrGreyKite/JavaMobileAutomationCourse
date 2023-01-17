package lib.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class CorePage {

    protected AppiumDriver driver;

    public CorePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String stringLocator, String errorMessage, long timeout) {
        By by = this.getLocatorByString(stringLocator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String stringLocator, String errorMessage) {
        return waitForElementPresent(stringLocator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String stringLocator, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(stringLocator, errorMessage, timeout);
        e.click();
        return e;
    }

    public WebElement waitForElementAndSendKeys(String stringLocator, String query, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(stringLocator, errorMessage, timeout);
        e.sendKeys(query);
        return e;
    }

    public Boolean waitForElementNotPresent(String stringLocator, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        By by = this.getLocatorByString(stringLocator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String stringLocator, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(stringLocator, errorMessage, timeout);
        e.clear();
        return e;
    }

    public String waitForElementAndGetAttribute(String stringLocator, String attribute, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(stringLocator, errorMessage, timeout);
        return e.getAttribute(attribute);
    }

    public void swipeUp(int timeForSwipe) {
        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.9);
        int endY = (int) (size.height * 0.2);
        action.press(point(x, startY)).waitAction(waitOptions(Duration.ofSeconds(timeForSwipe))).
                moveTo(point(x, endY)).release().perform();

    }

    public void swipeToTheLeft(String stringLocator, String errorMessage, int durationOfSwipe) {
        WebElement el = waitForElementPresent(stringLocator, errorMessage, 10);
        int leftX = el.getLocation().getX();
        int rightX = leftX + el.getSize().width;
        int upperY = el.getLocation().getY();
        int lowerY = upperY + el.getSize().height;
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        action.press(point(leftX,middleY)).waitAction(waitOptions(Duration.ofSeconds(durationOfSwipe))).moveTo(point(rightX, middleY)).release().perform();
    }

    public void swipeUntilElementIsFound(String stringLocator, int maxSwipes) {
        int alreadySwiped = 0;
        while(getAmountOfElements(stringLocator) == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(stringLocator, "Cannot find element " + stringLocator + " while swiping");
                return;
            }
            swipeUp(1);
            ++alreadySwiped;
        }
    }

    public int getAmountOfElements(String stringLocator){
        By locator = this.getLocatorByString(stringLocator);
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }
    public void assertElementNotPresent(String stringLocator) {
        if (getAmountOfElements(stringLocator) > 0) {
            throw new AssertionError("Element(s) with locator '" + stringLocator + "' should be not present");
        }
    }


    //Урок 3, ДЗ-1
    public void assertElementHasText(String stringLocator, String expectedText, String errorMessage) {
        WebElement e = waitForElementPresent(stringLocator, errorMessage);
        Assertions.assertEquals(expectedText, e.getText(), errorMessage);
    }

    public void assertElementHasText(WebElement element, String expectedText, String errorMessage) {
        Assertions.assertEquals(expectedText, element.getText(), errorMessage);
    }

    //Урок 4, ДЗ-2
    public void assertElementPresent(String stringLocator) {
        if (getAmountOfElements(stringLocator) == 0) {
            throw new AssertionError("Element(s) with locator '" + stringLocator + "' should be on the screen");
        }

    }

    By getLocatorByString(String locatorWithType) { //id:someLocator
        String[] dividedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = dividedLocator[0];
        String locator = dividedLocator[1];
        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else throw new IllegalArgumentException("Cannot get type of locator");

    }




}
