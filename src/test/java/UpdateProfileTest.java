import java.util.List;
import java.util.Arrays;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

public class UpdateProfileTest extends CaseBase {

  @Test
  public void changeProfileShouldUpdateDetails() {
    // given
    DashboardPage dashboard = this.login();
    ProfilePage profilePage = dashboard.showProfile();
    profilePage.editProfile();

    // when
    try { Thread.sleep(2000); } catch (Exception ex) {}

    // then
    // String loggedInUserFullname = dashboard.getLoggedInUserFullname();
    // Assert.assertEquals("Zoltán Szarvas", loggedInUserFullname);
  }

  // @Test
  // public void logoutShouldGoBackToMainPage() {
  //   // given
  //   DashboardPage dashboard = this.login();

  //   // when
  //   MainPage mainPage = dashboard.logout();

  //   // then
  //   assertEquals("You are not logged in. (Log in)", mainPage.getLoginText());
  //   assertEquals("Skillgo Moodle", mainPage.getTitle());
  // }

  private DashboardPage login() {
    GivenLoggedIn givenLoggedIn = new GivenLoggedIn(this.driver);
    return givenLoggedIn.login();
  }
}
