package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    static String runOnGrid;
    static InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
    static FirefoxOptions firefoxOptions = new FirefoxOptions();
    static ChromeOptions chromeOptions = new ChromeOptions();
    static SafariOptions safariOptions = new SafariOptions();
    static EdgeOptions edgeOptions = new EdgeOptions();
    static ChromeOptions opt = new ChromeOptions();
    static FirefoxProfile fp = new FirefoxProfile();
    public static ExpectedCondition<Boolean> documentLoad;
    public static ExpectedCondition<Boolean> framesLoad;
    public static ExpectedCondition<Boolean> imagesLoad;
    public static int maxPageLoadWait = 120;

    public static WebDriver get(String browserName) {

        WebDriver driver = null;
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if ("chrome".equalsIgnoreCase(browserName)) {

                ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("profile.default_content_setting_values.notifications", 2);


                chromeOptions.addArguments("--ignore-certifcate-errors");
                chromeOptions.addArguments("test-type");
                chromeOptions.addArguments("--allow-running-insecure-content");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--log-level=3");
                chromeOptions.addArguments("--silent");
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                if (runOnGrid.toLowerCase().contains("true")) {
                    throw new UnsupportedOperationException("The grid options are not yet supported");
                } else {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                }

                if (driver != null) {
                    driver.manage().timeouts().pageLoadTimeout(maxPageLoadWait, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }

    public static void closeDriver(WebDriver driver) {
        if (driver != null) {
            try {
                Thread.sleep(300);
                driver.quit();
            } catch (Exception exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
