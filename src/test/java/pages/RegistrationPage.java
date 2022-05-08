import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class UserDetails {
  public String username;
  public String password;
  public String email;
  public String firstname;
  public String lastname;
}

class RegistrationPage extends PageBase {

  private final By registrationFormBy = By.xpath("//div[@role='main']//form");
  private final By usernameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='username']"));
  private By passwordBy = new ByChained(registrationFormBy, By.xpath("//input[@name='password']"));
  private By emailBy = new ByChained(registrationFormBy, By.xpath("//input[@name='email']"));
  private By email2By = new ByChained(registrationFormBy, By.xpath("//input[@name='email2']"));
  private By firstnameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='firstname']"));
  private By lastnameBy = new ByChained(registrationFormBy, By.xpath("//input[@name='lastname']"));

  private By registerButtonBy = new ByChained(registrationFormBy, By.xpath("//input[@name='submitbutton']"));

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

  private void enterText(By locator, String text) {
    waitAndReturnElement(locator).sendKeys(text);
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
}
