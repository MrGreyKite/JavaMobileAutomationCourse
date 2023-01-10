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

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class CorePage {

    protected AppiumDriver driver;

    public CorePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.click();
        return e;
    }

    public WebElement waitForElementAndSendKeys(By by, String query, String errorMessage, long timeout){
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.sendKeys(query);
        return e;
    }

    public Boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
        e.clear();
        return e;
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeout) {
        WebElement e = waitForElementPresent(by, errorMessage, timeout);
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

    public void swipeToTheLeft(By locator, String errorMessage, int durationOfSwipe) {
        WebElement el = waitForElementPresent(locator, errorMessage, 10);
        int leftX = el.getLocation().getX();
        int rightX = leftX + el.getSize().width;
        int upperY = el.getLocation().getY();
        int lowerY = upperY + el.getSize().height;
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        action.press(point(leftX,middleY)).waitAction(waitOptions(Duration.ofSeconds(durationOfSwipe))).moveTo(point(rightX, middleY)).release().perform();
    }

    public void swipeUntilElementIsFound(By by, int maxSwipes) {
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

    public int getAmountOfElements(By locator){
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }
    public void assertElementNotPresent(By locator) {
        if (getAmountOfElements(locator) > 0) {
            throw new AssertionError("Element(s) with locator '" + locator.toString() + "' should be not present");
        }
    }


    //Урок 3, ДЗ-1
    public void assertElementHasText(By locator, String expectedText, String errorMessage) {
        WebElement e = waitForElementPresent(locator, errorMessage);
        Assertions.assertEquals(expectedText, e.getText(), errorMessage);
    }

    public void assertElementHasText(WebElement element, String expectedText, String errorMessage) {
        Assertions.assertEquals(expectedText, element.getText(), errorMessage);
    }

    //Урок 4, ДЗ-2
    public void assertElementPresent(By locator) {
        if (getAmountOfElements(locator) == 0) {
            throw new AssertionError("Element(s) with locator '" + locator.toString() + "' should be on the screen");
        }

    }




}
