import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class LoginPage extends PageBase {

  private By loginFormBy = By.xpath("//form[@id='login']");
  private By usernameBy = new ByChained(loginFormBy, By.xpath("//input[@id='username']"));
  private By passwordBy = new ByChained(loginFormBy, By.xpath("//input[@id='password']"));
  private By loginButtonBy = new ByChained(loginFormBy, By.xpath("button[@id='loginbtn']"));

  public LoginPage(WebDriver driver) {
    super(driver);

    if (!driver.getTitle().equals("Skillgo Moodle: Log in to the site")) {
        throw new IllegalStateException("This is not the main page, current page is: " + driver.getCurrentUrl());
    }
  }

  public DashboardPage loginAs(String username, String password) {
    this.enterUsername(username);
    this.enterPassword(password);

    //   waitAndReturnElement(this.loginButtonBy).submit();

    return new DashboardPage(this.driver);
  }

  private void enterUsername(String username) {
    waitAndReturnElement(this.usernameBy).sendKeys(username);
  }

  private void enterPassword(String password) {
    waitAndReturnElement(this.passwordBy).sendKeys(password);    
  }

  // public void gotoLogin() {
  //   this.waitAndReturnElement(this.loginLinkBy).click();
  // }
}
