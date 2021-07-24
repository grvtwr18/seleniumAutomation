package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalHooks {

    protected static String browserType;
    protected static WebDriver driver;
    protected static final String APP_URL = "appUrl";
    protected static final String BROWSER_VERSION = "browserVersion";
    protected static final String BROWSER_NAME = "browserName";
    private static final String RUN_ON_GRID = "runOnGrid";

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {

        String appUrl = context.getCurrentXmlTest().getParameter(APP_URL);
        System.setProperty("appUrl", appUrl);

        String browserType = context.getCurrentXmlTest().getParameter(BROWSER_NAME);
        System.setProperty("rp.enable", browserType);
    }

    /**
     * To set the browsertype value
     * @param context -- test NG Context
     */

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        browserType = context.getCurrentXmlTest().getParameter(BROWSER_NAME);
    }


    /**
     * To set the test case info
     *
     * @param context - testNG context
     * @param method  - method
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, Method method) {
        String testCaseKey = method.getName();
        System.out.println("Executing tests"+testCaseKey);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method m) throws IOException {
        WebDriverFactory.closeDriver(driver);
    }
}
