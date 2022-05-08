import java.util.List;
import java.util.Arrays;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.DashboardPage;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

public class LoginTest extends CaseBase {

  @Test
  public void loginShouldNavigateToDashboardWhenSucceeded() {
    // given, when
    DashboardPage dashboard = this.login();

    // then
    String loggedInUserFullName = dashboard.getLoggedInUserFullname();
    Assert.assertNotEquals("", loggedInUserFullName);
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
}
