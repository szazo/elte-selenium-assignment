import org.openqa.selenium.WebDriver;

class GivenLoggedIn {

  private WebDriver driver;

  public GivenLoggedIn(WebDriver driver) {
    this.driver = driver;
  }

  public DashboardPage login() {
    MainPage mainPage = MainPage.navigateToMainPage(this.driver);
    LoginPage loginPage = mainPage.gotoLogin();

    DashboardPage dashboard = loginPage.loginAs("szazo", "Alma123+");

    return dashboard;
  }
}
