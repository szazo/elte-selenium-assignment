import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LoginPage;
import pages.MainPage;

class GivenLoggedIn {

    private final WebDriver driver;
    private final String rootUrl;
    private final String username;
    private final String password;

    public GivenLoggedIn(String rootUrl, String username, String password, WebDriver driver) {
        this.rootUrl = rootUrl;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public DashboardPage login() {
        MainPage mainPage = MainPage.navigateToMainPage(this.rootUrl, this.driver);
        LoginPage loginPage = mainPage.gotoLogin();

        return loginPage.loginAs(this.username, this.password);
    }
}
