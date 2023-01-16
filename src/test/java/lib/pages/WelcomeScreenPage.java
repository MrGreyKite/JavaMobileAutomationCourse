package lib.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomeScreenPage extends CorePage {
    public WelcomeScreenPage(AppiumDriver driver) {
        super(driver);
    }

    private static final String NEXT_BUTTON = "//XCUIElementTypeStaticText[@name=\"Next\"]";
    private static final String SKIP_BUTTON = "//XCUIElementTypeStaticText[@name=\"Skip\"]";
    private static final String GET_STARTED_BUTTON = "//XCUIElementTypeButton[@name=\"Get started\"]";
    private static final String FIRST_SCREEN = "";
    private static final String SECOND_SCREEN = "New ways to explore";
    private static final String THIRD_SCREEN = "Search in nearly 300 languages";
    private static final String FOURTH_SCREEN = "Help make the app better";

    public void clickOnButtonNext() {
        this.waitForElementAndClick(By.xpath(NEXT_BUTTON),
                "Cannot find button 'Next'", 15);
    }

    public void waitForNewWaysToExploreScreen() {
        this.waitForElementPresent(By.id(SECOND_SCREEN), "Not found element with id " + SECOND_SCREEN, 15);
    }

    public void waitForSearchIn300LanguagesScreen() {
        this.waitForElementPresent(By.id(THIRD_SCREEN), "Not found element with id " + THIRD_SCREEN, 15);
    }

    public void waitForHelpMakeAppBetterScreen() {
        this.waitForElementPresent(By.id(FOURTH_SCREEN), "Not found element with id " + FOURTH_SCREEN, 15);
    }

    public void clickOnStartButton() {
        this.waitForElementAndClick(By.xpath(GET_STARTED_BUTTON),
                "Cannot find button 'Next'", 15);
    }

    public void clickOnSkipButton() {
        this.waitForElementAndClick(By.xpath(SKIP_BUTTON),
                "Cannot find button 'Next'", 15);
    }

    public void assertThatSearchInputIsVisible() {
        waitForElementPresent(By.xpath("//XCUIElementTypeSearchField[@name=\"Search Wikipedia\"]"),
                "Search Input is not visible", 15);
        assertElementPresent(By.xpath("//XCUIElementTypeSearchField[@name=\"Search Wikipedia\"]"));
    }


}
