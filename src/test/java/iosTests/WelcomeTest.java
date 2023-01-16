package iosTests;

import lib.iOSCoreTest;
import lib.pages.WelcomeScreenPage;
import org.junit.jupiter.api.Test;

public class WelcomeTest extends iOSCoreTest {
    @Test
    void testPassThroughWelcomeScreens() {
        WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForNewWaysToExploreScreen();
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForSearchIn300LanguagesScreen();
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForHelpMakeAppBetterScreen();
        welcomeScreen.clickOnStartButton();
        welcomeScreen.assertThatSearchInputIsVisible();
    }
}
