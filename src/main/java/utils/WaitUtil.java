package utils;

import com.google.common.base.Function;
import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class WaitUtil {

    private static final int lighteningMaxRetryCount = 10;

    /**
     * Fluent wait.
     *
     * @param driver  the driver
     * @param locator the locator
     * @return the web element
     */
    public static WebElement fluentWait(WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(120))
                .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return foo;
    }

    ;

    /**
     * Wait for element.
     *
     * @param driver    the driver
     * @param condition the condition
     * @param timeout   the timeout
     */
    public static void waitForElement(WebDriver driver, ExpectedCondition<WebElement> condition, Integer timeout) {
        timeout = timeout != null ? timeout : 10;
        // WebDriverWait wait = new WebDriverWait(driver, timeout);
        FluentWait<WebDriver> wait = new WebDriverWait(driver, timeout).ignoring(NoSuchElementException.class);
        wait.until(condition);
    }

    /**
     * Wait for Element to be enabled.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the boolean
     */
    public static Boolean waitForIsEnabled(WebDriver driver, By locator, Integer... timeout) {
        try {
            waitForElement(driver, ExpectedConditions.elementToBeClickable(locator),
                    (timeout.length > 0 ? timeout[0] : null));
            return true;
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
    }

    public static Boolean waitForIsEnabled(WebDriver driver, WebElement element, Integer... timeout) {
        try {
            waitForElement(driver, ExpectedConditions.elementToBeClickable(element),
                    (timeout.length > 0 ? timeout[0] : null));
            return true;
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
    }


    /**
     * Nap.
     *
     * @param seconds the seconds
     */
    public static void napStatic(int seconds) {
        try {
            Thread.sleep(seconds * (long) 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waitForPageToLoad(WebDriver driver) {
        new WebDriverWait(driver, 60).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public static boolean waitForTheElementVisible(WebDriver driver, int timeUnits, WebElement webElement) {

        try {
            FluentWait<WebDriver> wait = new WebDriverWait(driver, timeUnits)
                    .ignoring(NoSuchElementException.class);
            WebElement result = wait.until(ExpectedConditions.visibilityOf(webElement));
            return (result != null);
        } catch (Exception e) {
            return false;
        }
    }

}
