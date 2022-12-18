import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppTest {

    private AppiumDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Nerve\\IdeaProjects\\JavaMobileAutomationCourse\\src\\test\\resources\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL ("http://localhost:4723/wd/hub"), capabilities);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void firstAppTest() {
        System.out.println("First test run. Hello, Appium!");
    }
}
