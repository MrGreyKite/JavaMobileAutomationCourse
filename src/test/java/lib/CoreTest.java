package lib;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTest {

    protected AndroidDriver driver;
    private static final String appiumUrl = "http://localhost:4723/wd/hub";

    @BeforeEach
    void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("rotatable", true);
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "");

        driver = new AndroidDriver(new URL(appiumUrl), capabilities);

        //Урок 4, ДЗ-3
        driver.rotate(ScreenOrientation.PORTRAIT);

        WebElement skipButton = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
        skipButton.click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    protected void rotateScreenToLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void rotateScreenToPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void getAppToBackground(int secs) {

        driver.runAppInBackground(Duration.ofSeconds(secs));
    }


}
