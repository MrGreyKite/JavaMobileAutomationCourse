package lib.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class CorePage {

    protected RemoteWebDriver driver;

    public CorePage(RemoteWebDriver driver) {
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

    public void scrollMobileWebPageUp() {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            System.out.println("Not apply to " + Platform.getInstance().getPlatform());
        } else {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("window.scrollBy(0,250)");
        }
    }

    public void scrollMobileWebPageUntilElementIsFound(String stringLocator, int maxSwipes) {
        int alreadySwiped = 0;

        while (getAmountOfElements(stringLocator) == 0) {
            scrollMobileWebPageUp();
            ++alreadySwiped;

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(stringLocator, "Cannot find element " + stringLocator + " while scrolling");
                return;
            }
        }

    }

    public void swipeUp(int timeForSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((PerformsTouchActions) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int startY = (int) (size.height * 0.9);
            int endY = (int) (size.height * 0.2);
            action.press(point(x, startY)).waitAction(waitOptions(Duration.ofSeconds(timeForSwipe))).
                    moveTo(point(x, endY)).release().perform();
        }
        else System.out.println("Method do nothing for the platform " + Platform.getInstance().getPlatform());

    }

    public void swipeToTheLeft(String stringLocator, String errorMessage, int durationOfSwipe) {
        if (driver instanceof AppiumDriver) {
            WebElement el = waitForElementPresent(stringLocator, errorMessage, 10);
            int leftX = el.getLocation().getX();
            int rightX = leftX + el.getSize().getWidth();
            int upperY = el.getLocation().getY();
            int lowerY = upperY + el.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;

            TouchAction action = new TouchAction((PerformsTouchActions) driver);
            action.press(point((int) (rightX * 0.9), middleY));
            action.waitAction(waitOptions(Duration.ofSeconds(durationOfSwipe)));

            if (Platform.getInstance().isAndroid()) {
                action.moveTo(point((int) (leftX * 0.1), middleY));
            } else {
                int offset_x = (-1 * el.getSize().getWidth());
                action.moveTo(point(offset_x, 0));
            }

            action.release().perform();
        } else System.out.println("Method do nothing for the platform " + Platform.getInstance().getPlatform());
    }

    public void swipeUntilElementIsFound(String stringLocator, int maxSwipes) {
        if (driver instanceof AppiumDriver) {
            int alreadySwiped = 0;
            while (getAmountOfElements(stringLocator) == 0) {
                if (alreadySwiped > maxSwipes) {
                    waitForElementPresent(stringLocator, "Cannot find element " + stringLocator + " while swiping");
                    return;
                }
                swipeUp(1);
                ++alreadySwiped;
            }
        } else System.out.println("Method do nothing for the platform " + Platform.getInstance().getPlatform());
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

    public void assertElementHasAttribute(String stringLocator, String attributeType, String attributeValue, String errorMessage){
        WebElement e = waitForElementPresent(stringLocator, errorMessage);
        Assertions.assertEquals(attributeValue, e.getAttribute(attributeType), errorMessage);
    }

    //Урок 4, ДЗ-2
    public void assertElementPresent(String stringLocator) {
        if (getAmountOfElements(stringLocator) == 0) {
            throw new AssertionError("Element(s) with locator '" + stringLocator + "' should be on the screen");
        }
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    By getLocatorByString(String locatorWithType) { //id:someLocator
        String[] dividedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = dividedLocator[0];
        String locator = dividedLocator[1];
        switch (byType) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            case "css":
                return By.cssSelector(locator);
            default:
                throw new IllegalArgumentException("Cannot get type of locator");
        }

    }




}
