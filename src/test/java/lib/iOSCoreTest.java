package lib;

import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class iOSCoreTest {

    protected IOSDriver driver;
    private static final String appiumUrl = "http://localhost:4723/wd/hub";

    @BeforeEach
    void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8");
        capabilities.setCapability("platformVersion", "13.5");
//        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "/Users/a123/IdeaProjects/JavaMobileAutomationCourse/src/test/resources/apks/Wikipedia.app");
        capabilities.setCapability("udid", "DD592AB3-D894-4921-9BF4-79E6BCCCDB74");
        capabilities.setCapability("wdaStartupRetries", "5");
        capabilities.setCapability("iosInstallPause","6000" );
        capabilities.setCapability("wdaStartupRetryInterval", "20000");

        driver = new IOSDriver(new URL(appiumUrl), capabilities);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
