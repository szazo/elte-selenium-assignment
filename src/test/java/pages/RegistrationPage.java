package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

public class RegistrationPage extends PageBase {

    private final By registrationFormBy = By.xpath("//div[@role='main']//form");
    private final By usernameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='username']"));
    private final By passwordBy = new ByChained(registrationFormBy, By.xpath("//input[@name='password']"));
    private final By emailBy = new ByChained(registrationFormBy, By.xpath("//input[@name='email']"));
    private final By email2By = new ByChained(registrationFormBy, By.xpath("//input[@name='email2']"));
    private final By firstnameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='firstname']"));
    private final By lastnameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='lastname']"));

    private final By registerButtonBy = new ByChained(registrationFormBy, By.xpath("//input[@name='submitbutton']"));

    public RegistrationPage(WebDriver driver) {
        super(driver);

        this.checkTitle("New account");
    }

    public RegistrationResultPage register(UserDetails details) {
        this.enterText(this.usernameBy, details.username);
        this.enterText(this.passwordBy, details.password);
        this.enterText(this.emailBy, details.email);
        this.enterText(this.email2By, details.email);
        this.enterText(this.firstnameBy, details.firstname);
        this.enterText(this.lastnameBy, details.lastname);

        this.submitRegister();

        return new RegistrationResultPage(this.driver, details.email);
    }

    private void submitRegister() {
        waitAndReturnElement(this.registerButtonBy).submit();
    }

    // public DashboardPage loginAs(String username, String password) {
    //   this.enterUsername(username);
    //   this.enterPassword(password);

    //   this.submitLogin();

    //   return new DashboardPage(this.driver);
    // }

    // private void enterUsername(String username) {
    //   waitAndReturnElement(this.usernameBy).sendKeys(username);
    // }

    // private void enterPassword(String password) {
    //   waitAndReturnElement(this.passwordBy).sendKeys(password);
    // }

    // private void submitLogin() {
    //   waitAndReturnElement(this.loginButtonBy).submit();
    // }

    public static class UserDetails {
        public String username;
        public String password;
        public String email;
        public String firstname;
        public String lastname;
    }
}
