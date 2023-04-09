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

import java.time.Duration;

public class CoreTest {

    protected AppiumDriver driver;

    private DesiredCapabilities capabilities;

    @BeforeEach
    public void setUp() throws Exception {

        driver = Platform.getInstance().getDriverTypeFromPlatform();

        //Урок 4, ДЗ-3
        this.rotateScreenToPortrait();

        if (Platform.getInstance().isAndroid()) {
            WebElement skipButton = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
            skipButton.click();
        }
        if (Platform.getInstance().isIOS()) {
            WelcomeScreenPage welcomeScreen = new WelcomeScreenPage(driver);
            welcomeScreen.clickOnSkipButton();
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



}
