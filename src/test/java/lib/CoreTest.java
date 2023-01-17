package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsRotation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTest {

    protected AppiumDriver driver;
    private static final String appiumUrl = "http://localhost:4723/wd/hub";
    protected String platform = System.getenv().getOrDefault("PLATFORM", "ios");
    private DesiredCapabilities capabilities;

    @BeforeEach
    void setUp() throws Exception {
        capabilities = this.getCapsByEnvPlatform();
        driver = this.getDriverTypeFromEnv();

        //Урок 4, ДЗ-3
        this.rotateScreenToPortrait();

        if (platform.equals("android")) {
        WebElement skipButton = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
        skipButton.click();
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

    private DesiredCapabilities getCapsByEnvPlatform() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals("android")) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "PixelOreo8");
            capabilities.setCapability("platformVersion", "8.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("rotatable", true);
            capabilities.setCapability("orientation", "PORTRAIT");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "C:\\Users\\Nerve\\IdeaProjects\\JavaMobileAutomationCourse\\src\\test\\resources\\apks\\org.wikipedia.apk");
        } else if (platform.equals("ios")) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone 8");
            capabilities.setCapability("platformVersion", "13.5");
            capabilities.setCapability("app", "/Users/a123/IdeaProjects/JavaMobileAutomationCourse/src/test/resources/apks/Wikipedia.app");
            capabilities.setCapability("udid", "DD592AB3-D894-4921-9BF4-79E6BCCCDB74");
            capabilities.setCapability("wdaStartupRetries", "5");
            capabilities.setCapability("iosInstallPause","6000" );
            capabilities.setCapability("wdaStartupRetryInterval", "30000");
        } else throw new Exception("Not known platform");

        return capabilities;
    }

    private AppiumDriver getDriverTypeFromEnv() throws Exception {

        if (platform.equals("android")) {
            return new AndroidDriver(new URL(appiumUrl), capabilities);
        } else if (platform.equals("ios")) {
            return new IOSDriver(new URL(appiumUrl), capabilities);
        } else throw new Exception("Not known platform");
    }

}
