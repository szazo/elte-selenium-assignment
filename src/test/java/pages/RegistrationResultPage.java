package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationResultPage extends PageBase {

    private final By confirmTextBy = By.xpath("//div[@role='main']//div[@id='notice']");
    private final By continueButtonBy = By.xpath("//div[@class='continuebutton']//form/button[@type='submit']");

    public RegistrationResultPage(WebDriver driver, String emailAddress) {
        super(driver);

        this.checkTitle("Confirm your account");
    }

    public String getConfirmText() {

        return waitAndReturnElement(this.confirmTextBy).getText();

    }

    public MainPage clickContinueButton() {

        waitAndReturnElement(this.continueButtonBy).click();
        return new MainPage(this.driver);

    }
}
