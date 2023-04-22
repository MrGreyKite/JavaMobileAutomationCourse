package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.remote.SupportsRotation;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lib.pages.WelcomeScreenPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.stream.FileImageOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class CoreTest {

    protected RemoteWebDriver driver;

    private DesiredCapabilities capabilities;

    @BeforeEach
    @Step("Run driver and start session")
    public void setUp() throws Exception {

        driver = Platform.getInstance().getDriverTypeFromPlatform();
        this.createAllurePropertyFile();

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
    @Step("Close driver and end session")
    public void tearDown() {
        driver.quit();
    }

    @Step("Rotate screen to landscape mode")
    protected void rotateScreenToLandscape() {
        ((SupportsRotation)driver).rotate(ScreenOrientation.LANDSCAPE);
    }

    @Step("Rotate screen to portrait mode")
    protected void rotateScreenToPortrait() {
        ((SupportsRotation)driver).rotate(ScreenOrientation.PORTRAIT);
    }

    @Step("Send app to background")
    @Description("(only for Android and iOS)")
    protected void getAppToBackground(int secs) {
        ((InteractsWithApps)driver).runAppInBackground(Duration.ofSeconds(secs));
    }

    @Step("Open main page of Mobile Wiki")
    protected void openMobileWiki() {
        if(Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org/");
        }
    }

    protected void createAllurePropertyFile() {
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("environment", Platform.getInstance().getPlatform());
            props.store(fos, "See also: https://docs.qameta.io/allure/#_environment");
        } catch (IOException e) {
            System.out.println("IO problem when writing allure properties file");
            e.printStackTrace();
        }
    }



}
