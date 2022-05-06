import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class MainPage extends PageBase {

  private By loginLinkBy = By.xpath("//div[@class='usermenu']/span[@class='login']/a");

  public MainPage(WebDriver driver) {
    super(driver);

    this.driver.get("https://skillgo.io/moodle");

    if (!driver.getTitle().equals("Skillgo Moodle")) {
        throw new IllegalStateException("This is not the main page, current page is: " + driver.getCurrentUrl());
    }
  }

  public LoginPage gotoLogin() {
    this.waitAndReturnElement(this.loginLinkBy).click();

    return new LoginPage(this.driver);
  }
}
