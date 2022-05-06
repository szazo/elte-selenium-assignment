import java.util.List;
import java.util.Arrays;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

public class LoginTest extends CaseBase {

  @Test
  public void succeededLoginShouldNavigateToDashboard() {
    // given, when
    DashboardPage dashboard = this.login();

    // then
    String loggedInUserFullname = dashboard.getLoggedInUserFullname();
    Assert.assertEquals("Zoltán Szarvas", loggedInUserFullname);
  }

  @Test
  public void logoutShouldGoBackToMainPage() {
    // given
    DashboardPage dashboard = this.login();

    // when
    MainPage mainPage = dashboard.logout();

    // then
    assertEquals("You are not logged in. (Log in)", mainPage.getLoginText());
    assertEquals("Skillgo Moodle", mainPage.getTitle());
  }

  private DashboardPage login() {
    GivenLoggedIn givenLoggedIn = new GivenLoggedIn(this.driver);
    return givenLoggedIn.login();
  }
}
