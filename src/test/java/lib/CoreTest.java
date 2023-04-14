package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.remote.SupportsRotation;
import lib.pages.WelcomeScreenPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTest {

    protected RemoteWebDriver driver;

    private DesiredCapabilities capabilities;

    @BeforeEach
    public void setUp() throws Exception {

        driver = Platform.getInstance().getDriverTypeFromPlatform();

        if (Platform.getInstance().isAndroid()) {
            //Урок 4, ДЗ-3
            this.rotateScreenToPortrait();
            WebElement skipButton = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
            skipButton.click();
        }
        if (Platform.getInstance().isIOS()) {
            //Урок 4, ДЗ-3
            this.rotateScreenToPortrait();
            WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
            welcomeScreen.clickOnSkipButton();
        }

        if(Platform.getInstance().isMW()) {
            openMobileWiki();
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    protected void rotateScreenToLandscape() {
        ((SupportsRotation)driver).rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void rotateScreenToPortrait() {
        ((SupportsRotation)driver).rotate(ScreenOrientation.PORTRAIT);
    }

    protected void getAppToBackground(int secs) {
        ((InteractsWithApps)driver).runAppInBackground(Duration.ofSeconds(secs));
    }

    protected void openMobileWiki() {
        if(Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org/");
        }
    }



}
