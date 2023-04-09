package lib.pages;

import io.appium.java_client.AppiumDriver;

public class WelcomeScreenPage extends CorePage {
    public WelcomeScreenPage(AppiumDriver driver) {
        super(driver);
    }

    private static final String NEXT_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Next']/..";
    private static final String SKIP_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Skip']/..";
    private static final String GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name='Get started']";
    private static final String FIRST_SCREEN = "id:Learn more about Wikipedia";
    private static final String SECOND_SCREEN = "xpath://XCUIElementTypeStaticText[@name='New ways to explore']";
    private static final String THIRD_SCREEN = "id:Search in over 300 languages";
    private static final String FOURTH_SCREEN = "id:Help make the app better";

    public void clickOnButtonNext() {
        this.waitForElementPresent(NEXT_BUTTON,
                "Cannot find button 'Next'", 30);
        this.waitForElementAndClick(NEXT_BUTTON,
                "Cannot find button 'Next'", 30);
    }

    public void waitForLearnAboutWikiScreen() {
        this.waitForElementPresent(FIRST_SCREEN,
                "Not found element with id " + FIRST_SCREEN, 30);
    }

    public void waitForNewWaysToExploreScreen() {
        this.waitForElementPresent(SECOND_SCREEN, "Not found element with id " + SECOND_SCREEN, 30);
    }

    public void waitForSearchIn300LanguagesScreen() {
        this.waitForElementPresent(THIRD_SCREEN, "Not found element with id " + THIRD_SCREEN, 30);
    }

    public void waitForHelpMakeAppBetterScreen() {
        this.waitForElementPresent(FOURTH_SCREEN, "Not found element with id " + FOURTH_SCREEN, 30);
    }

    public void clickOnStartButton() {
        this.waitForElementPresent(GET_STARTED_BUTTON,
                "Cannot find button 'Next'", 30);
        this.waitForElementAndClick(GET_STARTED_BUTTON,
                "Cannot find button 'Next'", 30);
    }

    public void clickOnSkipButton() {
        this.waitForElementAndClick(SKIP_BUTTON,
                "Cannot find button 'Skip'", 30);
    }

    public void assertThatSearchInputIsVisible() {
        waitForElementPresent("xpath://XCUIElementTypeSearchField[@name=\"Search Wikipedia\"]",
                "Search Input is not visible", 30);
        assertElementPresent("xpath://XCUIElementTypeSearchField[@name=\"Search Wikipedia\"]");
    }


}
