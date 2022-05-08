import java.util.List;
import java.util.Arrays;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UpdateProfileTest extends CaseBase {

  @Test
  public void changeProfileShouldUpdateDetails() {
    // given
    DashboardPage dashboard = this.login();
    ProfilePage profilePage = dashboard.showProfile();
    EditProfilePage editProfilePage = profilePage.editProfile();

    // when
    Random rand = new Random();
    UpdateProfileDetails details = new UpdateProfileDetails() {{
      firstname = randomString("First", 5);
      lastname = randomString("Last", 5);
      city = randomString("City", 5);
      description = randomString("Description", 5);
    }};
    
    ProfilePage profilePageAfterEdit = editProfilePage.updateProfile(details);

    System.out.println(details.outSelectedCountry);

    ProfileDetails detailsAfterEdit = profilePageAfterEdit.getProfileDetails();
    System.out.println(detailsAfterEdit);

    assertEquals(details.firstname + " " + details.lastname, detailsAfterEdit.fullName);
    assertEquals(details.description, detailsAfterEdit.description);
    assertEquals(details.city, detailsAfterEdit.city);
    assertEquals(details.outSelectedCountry, detailsAfterEdit.country);
    //    assertEquals(details.city, detailsAfterEdit.)

    try { Thread.sleep(2000); } catch (Exception ex) {}

    // then
    // String loggedInUserFullname = dashboard.getLoggedInUserFullname();
    // Assert.assertEquals("Zoltán Szarvas", loggedInUserFullname);
  }

  private String randomString(String prefix, int randomLength) {
    Random rand = new Random();
    return String.format("%s%0" + randomLength + "d", prefix, rand.nextInt((int) Math.pow(10, randomLength)));
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
}
