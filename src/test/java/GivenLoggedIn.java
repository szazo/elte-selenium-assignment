import org.openqa.selenium.WebDriver;

class GivenLoggedIn {

  private WebDriver driver;
  private String rootUrl;
  private String username;
  private String password;

  public GivenLoggedIn(String rootUrl, String username, String password, WebDriver driver) {
    this.rootUrl = rootUrl;
    this.username = username;
    this.password = password;
    this.driver = driver;
  }

  public DashboardPage login() {
    MainPage mainPage = MainPage.navigateToMainPage(this.rootUrl, this.driver);
    LoginPage loginPage = mainPage.gotoLogin();

    DashboardPage dashboard = loginPage.loginAs(this.username, this.password);

    return dashboard;
  }
}
