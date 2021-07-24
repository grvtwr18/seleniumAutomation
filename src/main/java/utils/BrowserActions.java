package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserActions {

    private static final int TIMEOUT = Integer.parseInt(System.getProperty("maxPageLoadWait"));
    private static final String WINDOW_DOT_FOCUS = "window.focus();";
    private static final String SCRIPT_GET_ELEMENT_BORDER="arguments[0].style.border='3px solid blue'";
    private static final String ARGUMENTS_ZERO_CLICK = "arguments[0].click();";
    private static final String ARGUMENTS_ZERO_SCROLLINTOVIEW_AS_TRUE = "arguments[0].scrollIntoView(true);";

    /**
     * Go to.
     *
     * @param url the url
     */
    public static void goTo(WebDriver driver, String url) {
        System.out.println("Launching Url: " + url);
        driver.get(url);
    }

    /*
	@param driver - the web driver
	@param element - the webelement
	 Method to Highlight the WebElement
	 */
    public static void highlightWebElement(WebDriver driver,WebElement element){
        WaitUtil.waitForPageToLoad(driver);
        //WaitUtil.waitForTheElementVisible(driver,MAX_ELEMENT_WAIT*2,element,true);
        ((JavascriptExecutor) driver).executeScript(SCRIPT_GET_ELEMENT_BORDER, element);
    }

    /**
     * Click.
     *
     * @param driver      the driver
     * @param element     the element
     * @param elementName the element name
     */
    public static void click(WebDriver driver, WebElement element, String elementName ) {

        ((JavascriptExecutor) driver).executeScript(WINDOW_DOT_FOCUS);

        try {
            WaitUtil.waitForIsEnabled(driver, element, TIMEOUT);

            Thread.sleep(100);
            ((JavascriptExecutor) driver).executeScript(WINDOW_DOT_FOCUS);
            scrollElementIntoView(driver, element);
            highlightWebElement(driver,element);
            element.click();
        } catch (Exception e) {
            if (e.getMessage().contains("not clickable")) {
                System.out.println("Retry- Clicking on element: " + elementName);
                highlightWebElement(driver,element);
                ((JavascriptExecutor) driver).executeScript(ARGUMENTS_ZERO_CLICK, element);
            }
        }
    }

    /**
     * Scroll element into view.
     *
     * @param driver  the driver
     * @param locator the locator
     */
    public static void scrollElementIntoView(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript(ARGUMENTS_ZERO_SCROLLINTOVIEW_AS_TRUE, driver.findElement(locator));
    }

    public static void scrollElementIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(ARGUMENTS_ZERO_SCROLLINTOVIEW_AS_TRUE, element);
    }
}
