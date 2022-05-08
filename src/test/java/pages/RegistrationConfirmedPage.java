package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationConfirmedPage extends PageBase {

    private final By confirmedTextBy = By.xpath("//div[@role='main']");
    private final By continueButtonBy = By.xpath("//div[@role='main']//form/button[@type='submit']");

    public RegistrationConfirmedPage(WebDriver driver) {
        super(driver);

        this.checkTitle("Your registration has been confirmed");
    }

    public String getConfirmText() {
        return waitAndReturnElement(this.confirmedTextBy).getText();
    }

    public DashboardPage clickContinueButton() {

        waitAndReturnElement(this.continueButtonBy).click();
        return new DashboardPage(this.driver);
    }
}
