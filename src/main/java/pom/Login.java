package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BrowserActions;
import utils.WaitUtil;

import java.util.Map;

public class Login {

    @FindBy(xpath = ".//a[text()='Switch to Salesforce Classic']")
    private WebElement lnkSwitchToClassic;

    private WebDriver driver;

    public Login(WebDriver driver, String appUrl) {
        this.driver = driver;
        BrowserActions.goTo(driver, System.getProperty("appUrl"));
        PageFactory.initElements(driver, this);
        WaitUtil.waitForPageToLoad(driver);
        WaitUtil.waitForTheElementVisible(driver,30,lnkSwitchToClassic);
    }
}
