package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import lib.CoreTest;
import lib.Platform;
import lib.pages.WelcomeScreenPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WelcomeTest extends CoreTest {

    @Override
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriverTypeFromPlatform();
        this.rotateScreenToPortrait();
    }

    @Test
    @Features({@Feature("Onboarding")})
    @DisplayName("Test for all welcome screens")
    @Description("Pass through all onboarding in right order")
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
    @Features({@Feature("Onboarding")})
    @DisplayName("Test for ability to skip onboarding")
    @Description("Skipping onboarding is a real case too")
    void testSkipWelcome() {
        WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
        welcomeScreen.waitForLearnAboutWikiScreen();
        welcomeScreen.clickOnSkipButton();
        welcomeScreen.assertThatSearchInputIsVisible();
    }

}
