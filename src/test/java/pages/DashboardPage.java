import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class DashboardPage extends PageBase {

  public DashboardPage(WebDriver driver) {
    super(driver);

    this.checkTitle("Dashboard");
  }

  public String getLoggedInUserFullname() {
    return this.navigationBar().getLoggedInUserFullname();
  }

  public MainPage logout() {
    this.navigationBar().toggleUserMenuAndLogout();

    return new MainPage(driver);
  }

  public ProfilePage showProfile() {
    this.navigationBar().toggleUserMenuAndShowProfile();

    return new ProfilePage(driver);
  }

  private NavigationBar navigationBar() {
    return new NavigationBar(this.driver);
  }
}
