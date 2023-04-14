package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = "http://localhost:4723/wd/hub";

    private static Platform instance;

    private Platform(){}

    public static Platform getInstance(){
        if (instance == null) {
            instance = new Platform();
        } return instance;
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }
    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW() {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    public RemoteWebDriver getDriverTypeFromPlatform() throws Exception {
        if (this.isAndroid()) {
            return new AndroidDriver(new URL(APPIUM_URL), this.getDesiredCapabilitiesForAndroid());
        } else if (this.isIOS()) {
            return new IOSDriver(new URL(APPIUM_URL), this.getDesiredCapabilitiesForIOS());
        } else if(this.isMW()) {
            return new ChromeDriver(this.getOptionsForMWC());
        }
        else throw new Exception("Not known platform: " + this.getPlatform());
    }

    public String getPlatform() {
        return System.getenv().getOrDefault("PLATFORM", "mobile_web");
    }

    private boolean isPlatform(String myPlatform) {
        String platform = this.getPlatform();
        return platform.equals(myPlatform);
    }

    private DesiredCapabilities getDesiredCapabilitiesForAndroid() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "PixelOreo8");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("rotatable", true);
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Nerve\\IdeaProjects\\JavaMobileAutomationCourse\\src\\test\\resources\\apks\\org.wikipedia.apk");

        return capabilities;
    }
    private DesiredCapabilities getDesiredCapabilitiesForIOS() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8");
        capabilities.setCapability("platformVersion", "13.5");
        capabilities.setCapability("app", "/Users/a123/IdeaProjects/JavaMobileAutomationCourse/src/test/resources/apks/Wikipedia.app");
        capabilities.setCapability("udid", "DD592AB3-D894-4921-9BF4-79E6BCCCDB74");
        capabilities.setCapability("wdaStartupRetries", "5");
        capabilities.setCapability("iosInstallPause","6000" );
        capabilities.setCapability("wdaStartupRetryInterval", "30000");

        return capabilities;
    }

    private ChromeOptions getOptionsForMWC() {

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 393);
        deviceMetrics.put("height", 768);
        deviceMetrics.put("pixelRatio", 3.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent",
                "Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Mobile Safari/537.36");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("--remote-allow-origins=*");

        return chromeOptions;
    }
}
