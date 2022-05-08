import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pages.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationTest extends CaseBase {

    TempMailClient tempMailClient;

    @Before
    public void setup() {
        this.tempMailClient = new TempMailClient(tempMailHost(), tempMailApiKey());
    }

    @Ignore
    @Test
    public void shouldRegisterUsingConfirmation() throws Exception {
        // given
        String emailAddress = tempMailClient.generateEmailAddress();

        RegistrationPage.UserDetails details = new RegistrationPage.UserDetails() {{
            username = emailAddress;
            password = randomString("Pw+", 5);
            email = emailAddress;
            firstname = randomString("First", 5);
            lastname = randomString("Last", 5);
        }};
        RegistrationPage registrationPage = this.navigateToRegistrationPage();

        // when register
        RegistrationResultPage resultPage = registrationPage.register(details);

        String confirmText = resultPage.getConfirmText();
        Assert.assertTrue(confirmText.contains(emailAddress));

        // when waiting for email and read confirm url
        String confirmUrl = this.waitForEmailAndExtractConfirmUrl(emailAddress);

        // confirm
        RegistrationConfirmedPage confirmedPage = confirm(resultPage, confirmUrl);
        Assert.assertTrue(confirmedPage.getConfirmText().contains("Your registration has been confirmed"));
        DashboardPage dashboardPage = confirmedPage.clickContinueButton();

        // then
        Assert.assertEquals(details.firstname + " " + details.lastname, dashboardPage.getLoggedInUserFullName());
    }

    private RegistrationPage navigateToRegistrationPage() {
        MainPage mainPage = MainPage.navigateToMainPage(this.rootUrl(), this.driver);
        LoginPage loginPage = mainPage.gotoLogin();
        RegistrationPage registrationPage = loginPage.gotoRegistration();

        return registrationPage;
    }

    private String waitForEmailAndExtractConfirmUrl(String emailAddress) throws Exception {
        TempMailClient.Email email = tempMailClient.waitForEmailBySubject(emailAddress,
                "Skillgo Moodle: account confirmation",
                5000);

        String confirmUrl = this.extractConfirmUrl(email.text);
        return confirmUrl;
    }

    private RegistrationConfirmedPage confirm(RegistrationResultPage resultPage, String confirmUrl) {
        MainPage mainPageAfterRegister = resultPage.clickContinueButton();
        RegistrationConfirmedPage confirmedPage = mainPageAfterRegister.confirmUrl(confirmUrl);
        return confirmedPage;
    }

    private String extractConfirmUrl(String message) {
        Pattern pattern = Pattern.compile("^\\s*(https.*)\\s*$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(message);

        if (!matcher.find()) {
            throw new RuntimeException("URL could not be extracted from message: " + message);
        }

        String confirmUrl = matcher.group(0).trim();
        return confirmUrl;
    }
}
