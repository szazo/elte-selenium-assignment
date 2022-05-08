import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;


public class RegistrationTest extends CaseBase {

  @Test
  public void shouldRegister() {
    // given
    MainPage mainPage = MainPage.navigateToMainPage(this.driver);
    LoginPage loginPage = mainPage.gotoLogin();
    RegistrationPage registrationPage = loginPage.gotoRegistration();

    UserDetails details = new UserDetails() {{
      username = "szazo2";
      password = "Alma123+";
      email = "szazo42+3@gmail.com";
      firstname = "Firstname";
      lastname = "Lastname";
    }};

    registrationPage.register(details);

    // when
  }

  @Test
  public void generateEmail() throws UnirestException {
    Map<String, String> headers = new HashMap<>();
    headers.put("X-RapidAPI-Host", "privatix-temp-mail-v1.p.rapidapi.com");
    headers.put("X-RapidAPI-Key", "4bc1c48a83mshfdd317857f55020p1678cfjsn531bb38cb008");
    
    String url = "https://privatix-temp-mail-v1.p.rapidapi.com/request/domains/";
    
    HttpResponse<JsonNode> response
      = Unirest.get(url)
      .headers(headers)
      .asJson();

    JSONArray domainNodes = response.getBody().getArray();
    List<String> domainList = new ArrayList<>();
    domainNodes

SONArray issueNodes = response.getBody().getArray();
        issueNodes.forEach(issueNode -> {
            JSONObject issue = (JSONObject) issueNode;
            String closedOn = issue.getString("closed_at");
            if (since.compareTo(closedOn) < 0) {
                if (!isIssueExcluded(issue)) {
                    rval.add(issue);
                } else {
                    System.out.println("Skipping issue (excluded): " + issue.getString("title"));
                }
            } else {
                System.out.println("Skipping issue (old release): " + issue.getString("title"));
            }
        });
    
    System.out.println(response);
    
  }

  @Test
  public void shouldUnirest() throws UnirestException {

    Map<String, String> headers = new HashMap<>();
    headers.put("Api-Token", "f018e2f257e17056ed2ae61ac73432ec");

    Map<String, Object> fields = new HashMap<>();
    fields.put("name", "new inbox");

    String url = String.format("https://mailtrap.io/api/v1/companies/%s/inboxes", "42");
  System.out.println(url);
    
    String response
      = Unirest.post(url)
      .headers(headers)
      .fields(fields)
      .asString()
      .getBody();

    System.out.println(response);
    
    // Map<String, String> headers = new HashMap<>();
    // headers.put("accept", "application/json");
    // headers.put("Authorization", "Bearer 5a9ce37b3100004f00ab5154");

    // Map<String, Object> fields = new HashMap<>();
    // fields.put("name", "Sam Baeldung");
    // fields.put("id", "PSP123");

    // HttpResponse<JsonNode> jsonResponse 
    //   = Unirest.put("http://www.mocky.io/v2/5a9ce7853100002a00ab515e")
    //   .headers(headers).fields(fields)
    //   .asJson();
    
    
    // HttpResponse<JsonNode> jsonResponse =
    //   Unirest.get("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154")
    //   .header("accept", "application/json").queryString("apiKey", "123")
    //   .asJson();

  }
  
  // @Test
  // public void succeededLoginShouldNavigateToDashboard() {
  //   // given, when
  //   DashboardPage dashboard = this.login();

  //   // then
  //   String loggedInUserFullname = dashboard.getLoggedInUserFullname();
  //   Assert.assertEquals("Zoltán Szarvas", loggedInUserFullname);
  // }

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

  // private DashboardPage login() {
  //   GivenLoggedIn givenLoggedIn = new GivenLoggedIn(this.driver);
  //   return givenLoggedIn.login();
  // }
}
