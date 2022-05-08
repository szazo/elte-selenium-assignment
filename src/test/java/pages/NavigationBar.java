package pages;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class NavigationBar extends ComponentBase {

  private final By rootLocator = By.xpath("//nav[contains(@class, 'navbar')]");
  private final By actionMenuToggleLocator =
    new ByChained(rootLocator,
                By.xpath("//div[@class='usermenu']//a[contains(@class, 'dropdown-toggle')]"));
  private final By userTextLocator = new ByChained(actionMenuToggleLocator,
                                             By.xpath("//span[contains(@class, 'usertext')]"));

  private final By dropdownMenuLocator =
    new ByChained(rootLocator,
                  By.xpath("//div[contains(@class, 'dropdown-menu')]"));

  private final By logoutMenuItemLocator =
    new ByChained(dropdownMenuLocator,
                  By.xpath("a[@data-title='logout,moodle']"));

  private final By profileMenuItemLocator =
    new ByChained(dropdownMenuLocator,
                  By.xpath("a[@data-title='profile,moodle']"));
  
  protected NavigationBar(WebDriver driver) {
    super(driver);
  }

  public String getLoggedInUserFullname() {
    WebElement userText = this.waitAndReturnElement(this.userTextLocator);
    return userText.getText();
  }

  public void toggleUserMenuAndLogout() {
    this.toggleUserMenu();

    this.waitAndReturnElement(this.logoutMenuItemLocator).click();
  }

  public void toggleUserMenuAndShowProfile() {
    this.toggleUserMenu();

    this.waitAndReturnElement(this.profileMenuItemLocator).click();
  }

  private void toggleUserMenu() {
    WebElement userMenuToggle = this.waitAndReturnElement(this.userTextLocator);
    userMenuToggle.click();
  }

  // public List<String> getAvailableCourseNames() {

  //   List<WebElement> elements = this.waitAndReturnElements(availableCourseLinksLocator);

  //   ArrayList<String> courseNames = new ArrayList<>();
  //   for (WebElement element : elements) {
  //     courseNames.add(element.getText());
  //   }

  //   return courseNames;
  // }

  // private void navigateToMainPage() {
  //   this.driver.get("https://skillgo.io/moodle");

  //   this.checkTitle("Skillgo Moodle");
  // }

  // public LoginPage gotoLogin() {
  //   this.waitAndReturnElement(this.loginLinkLocator).click();

  //   return new LoginPage(this.driver);
  // }
}
