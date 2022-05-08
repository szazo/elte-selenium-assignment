import org.junit.Assert;
import org.junit.Test;
import pages.DashboardPage;
import pages.MainPage;

import static org.junit.Assert.assertEquals;

public class LoginTest extends CaseBase {

    @Test
    public void loginShouldNavigateToDashboardWhenSucceeded() {

        // given, when
        DashboardPage dashboard = this.login();

        // then
        String loggedInUserFullName = dashboard.getLoggedInUserFullName();
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
