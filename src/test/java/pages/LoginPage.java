package pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class LoginPage extends PageBase {

  private By loginFormBy = By.xpath("//form[@id='login']");
  private By usernameBy = new ByChained(loginFormBy, By.xpath("//input[@id='username']"));
  private By passwordBy = new ByChained(loginFormBy, By.xpath("//input[@id='password']"));
  private By loginButtonBy = new ByChained(loginFormBy, By.xpath("button[@id='loginbtn']"));
  private By registrationButtonBy = By.xpath("//form[@id='signup']/button[@type='submit']");

  public LoginPage(WebDriver driver) {
    super(driver);

    this.checkTitle("Skillgo Moodle: Log in to the site");
  }

  public DashboardPage loginAs(String username, String password) {
    this.enterText(this.usernameBy, username);
    this.enterText(this.passwordBy, password);

    this.submitLogin();

    return new DashboardPage(this.driver);
  }

  private void submitLogin() {
    waitAndReturnElement(this.loginButtonBy).submit();
  }

  public RegistrationPage gotoRegistration() {
    waitAndReturnElement(this.registrationButtonBy).click();
    return new RegistrationPage(this.driver);
  }
}
