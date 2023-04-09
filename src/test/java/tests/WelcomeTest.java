package tests;

import lib.CoreTest;
import lib.Platform;
import lib.pages.WelcomeScreenPage;
import org.junit.jupiter.api.Test;

public class WelcomeTest extends CoreTest {

    @Override
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriverTypeFromPlatform();
        this.rotateScreenToPortrait();
    }

    @Test
    void testPassThroughWelcomeScreens() {
        WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
        welcomeScreen.waitForLearnAboutWikiScreen();
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForNewWaysToExploreScreen();
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForSearchIn300LanguagesScreen();
        welcomeScreen.clickOnButtonNext();
        welcomeScreen.waitForHelpMakeAppBetterScreen();
        welcomeScreen.clickOnStartButton();
        welcomeScreen.assertThatSearchInputIsVisible();
    }

    @Test
    void testSkipWelcome() {
        WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
        welcomeScreen.waitForLearnAboutWikiScreen();
        welcomeScreen.clickOnSkipButton();
        welcomeScreen.assertThatSearchInputIsVisible();
    }

}
