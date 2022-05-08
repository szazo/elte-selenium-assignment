import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.math.BigInteger;

import org.apache.commons.compress.utils.BitInputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;

import javax.xml.datatype.DatatypeConstants;


class TempMailClient {

  private String host;
  private String key;

  public TempMailClient(String host, String key) {
    this.host = host;
    this.key = key;
  }

  public String generateEmailAddress() throws UnirestException {

    HttpResponse<JsonNode> response
            = Unirest.get(this.url("domains/"))
            .headers(this.headers())
            .asJson();

    JSONArray jsonArray = response.getBody().getArray();

    // select random domain
    List<String> domainList = new ArrayList<>();
    for (Object item : jsonArray) {
      domainList.add((String) item);
    }

    Random rand = new Random();
    String domain = domainList.get(rand.nextInt(domainList.size()));
    int index = rand.nextInt(10000);

    // generate mail address
    String emailAddress = String.format("test%05d%s", index, domain);

    return emailAddress;
  }

  public Email waitForEmailBySubject(String emailAddress, String subject, int timeoutMs) throws NoSuchAlgorithmException, UnirestException, TimeoutException {
    String md5Hash = this.md5Hash(emailAddress);

    long start = System.currentTimeMillis();
    while (true) {
      List<Email> emails = this.getEmails(md5Hash);
      for (Email email : emails) {
        if (email.subject.equals(subject)) {
          return email;
        }
      }

      if (System.currentTimeMillis() - start > timeoutMs) {
        throw new TimeoutException(String.format("waitForEmailBySubject condition not met within %s ms", timeoutMs));
      }
    }
  }

  class Email {
    String id;
    String subject;
    String from;
    String text;
    String html;
  }

  private List<Email> getEmails(String emailMd5) throws UnirestException {

    HttpResponse<JsonNode> response
            = Unirest.get(this.url(String.format("mail/id/%s/", emailMd5)))
            .headers(this.headers())
            .asJson();
    JSONArray jsonArray = response.getBody().getArray();

    List<Email> emails = new ArrayList<>();
    for (Object item : jsonArray) {
      JSONObject emailObject = (JSONObject) item;

      if (emailObject.has("error")) {
        break;
      }

      System.out.println("EMAIL: " + emailObject.toString());
      Email email = new Email() {{
        id = emailObject.getString("mail_id");
        subject = emailObject.getString("mail_subject");
        from = emailObject.getString("mail_from");
        text = emailObject.getString("mail_text");
        html = emailObject.getString("mail_html");
      }};
      System.out.println(email.subject);
      emails.add(email);
    }

    return emails;
    //HttpResponse<String> response = Unirest.get("https://privatix-temp-mail-v1.p.rapidapi.com/request/mail/id/5c0bce9a727b8c8f7ee3cd7a03a5491c/")
     //       .header("X-RapidAPI-Host", "privatix-temp-mail-v1.p.rapidapi.com")
      //      .header("X-RapidAPI-Key", "4bc1c48a83mshfdd317857f55020p1678cfjsn531bb38cb008")
       //     .asString();
  }

  private String md5Hash(String input) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    BigInteger bigInteger = new BigInteger(1, md5.digest(input.getBytes()));
    String md5Hash = bigInteger.toString(16);
    while (md5Hash.length() < 32) {
      md5Hash = "0" + md5Hash;
    }

    return md5Hash;
  }

  private Map<String, String> headers() {
    Map<String, String> headers = new HashMap<>();
    headers.put("X-RapidAPI-Host", this.host);
    headers.put("X-RapidAPI-Key", this.key);

    return headers;
  }

  private String url(String suffix) {
    String url = String.format("https://%s/request/%s", this.host, suffix);
    return url;
  }

}

public class RegistrationTest extends CaseBase {

  // @Test
  // public void readEmails() throws Exception {
  //   TempMailClient tempMailClient = new TempMailClient("privatix-temp-mail-v1.p.rapidapi.com", "4bc1c48a83mshfdd317857f55020p1678cfjsn531bb38cb008");

  //   String address = "test02345@mailkept.com";

  //   TempMailClient.Email email = tempMailClient.waitForEmailBySubject(address, "Skillgo Moodle: account confirmation", 5000);
  //   System.out.println(email.text);

  //   Pattern pattern = Pattern.compile("^\\s*(https.*)\\s*$", Pattern.MULTILINE);
  //   Matcher matcher = pattern.matcher(email.text);

  //   Assert.assertTrue(matcher.find());

  //   String confirmUrl = matcher.group(0).trim();
  //   System.out.println("confirmURL" + confirmUrl);
  // }

  @Test
  @Ignore
  public void shouldRegister() throws Exception {
    // given
    TempMailClient tempMailClient = new TempMailClient("privatix-temp-mail-v1.p.rapidapi.com", "4bc1c48a83mshfdd317857f55020p1678cfjsn531bb38cb008");
    String emailAddress = tempMailClient.generateEmailAddress();

    System.out.println("Generated email address:" + emailAddress);

    MainPage mainPage = MainPage.navigateToMainPage(this.rootUrl(), this.driver);
    LoginPage loginPage = mainPage.gotoLogin();
    RegistrationPage registrationPage = loginPage.gotoRegistration();

    String generatedPassword = "Pw1+" + UUID.randomUUID();
    System.out.println("PASSWORD:" + generatedPassword);

    UserDetails details = new UserDetails() {{
      username = emailAddress;
      password = generatedPassword;
      email = emailAddress;
      firstname = "Firstname";
      lastname = "Lastname";
    }};

    RegistrationResultPage resultPage = registrationPage.register(details);

    // then
    String confirmText = resultPage.getConfirmText();
    Assert.assertTrue(confirmText.contains(emailAddress));

    MainPage mainPageAfterRegister = resultPage.clickContinueButton();

    // confirm
    TempMailClient.Email email = tempMailClient.waitForEmailBySubject(emailAddress, "Skillgo Moodle: account confirmation", 5000);
    System.out.println(email.text);

    Pattern pattern = Pattern.compile("^\\s*(https.*)\\s*$", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(email.text);

    Assert.assertTrue(matcher.find());

    String confirmUrl = matcher.group(0).trim();
    RegistrationConfirmedPage confirmedPage = mainPageAfterRegister.confirmUrl(confirmUrl);
    Assert.assertTrue(confirmedPage.getConfirmText().contains("Your registration has been confirmed"));
    DashboardPage dashboardPage = confirmedPage.clickContinueButton();

    // check logged in
    Assert.assertEquals("Firstname Lastname", dashboardPage.getLoggedInUserFullname());
    // when
    Thread.sleep(2000);
  }

//   @Test
//   public void generateEmail() throws UnirestException, NoSuchAlgorithmException {
//     Map<String, String> headers = new HashMap<>();
//     headers.put("X-RapidAPI-Host", "privatix-temp-mail-v1.p.rapidapi.com");
//     headers.put("X-RapidAPI-Key", "4bc1c48a83mshfdd317857f55020p1678cfjsn531bb38cb008");
    
//     String url = "https://privatix-temp-mail-v1.p.rapidapi.com/request/domains/";
    
//     HttpResponse<JsonNode> response
//       = Unirest.get(url)
//       .headers(headers)
//       .asJson();

//     List<String> domainList = new ArrayList<>();
//     JSONArray jsonArray = response.getBody().getArray();
//     for (Object item : jsonArray) {
//       domainList.add((String) item);
//     }

//     Random rand = new Random();
//     String domain = domainList.get(rand.nextInt(domainList.size()));
//     int index = rand.nextInt(10000);

//     String emailAddress = String.format("test%05d@%s", index, domain);

//     // generate md5 hash
//       MessageDigest md5 = MessageDigest.getInstance("MD5");
//       BigInteger bigInteger = new BigInteger(1, md5.digest(emailAddress.getBytes()));
//       String md5Hash = bigInteger.toString(16);
//       while (md5Hash.length() < 32) {
//         md5Hash = "0" + md5Hash;
//       }

//     System.out.println(domainList);
//     System.out.println(index);
//     System.out.println(emailAddress);
//     System.out.println(md5Hash);

// //     JSONArray domainNodes = response.getBody().getArray();
// //     List<String> domainList = new ArrayList<>();
// //     //domainNodes

// // SONArray issueNodes = response.getBody().getArray();
// //         issueNodes.forEach(issueNode -> {
// //             JSONObject issue = (JSONObject) issueNode;
// //             String closedOn = issue.getString("closed_at");
// //             if (since.compareTo(closedOn) < 0) {
// //                 if (!isIssueExcluded(issue)) {
// //                     rval.add(issue);
// //                 } else {
// //                     System.out.println("Skipping issue (excluded): " + issue.getString("title"));
// //                 }
// //             } else {
// //                 System.out.println("Skipping issue (old release): " + issue.getString("title"));
// //             }
// //         });
    
//     System.out.println(response);
    
//   }

//   @Test
//   public void shouldUnirest() throws UnirestException {

//     Map<String, String> headers = new HashMap<>();
//     headers.put("Api-Token", "f018e2f257e17056ed2ae61ac73432ec");

//     Map<String, Object> fields = new HashMap<>();
//     fields.put("name", "new inbox");

//     String url = String.format("https://mailtrap.io/api/v1/companies/%s/inboxes", "42");
//   System.out.println(url);
    
//     String response
//       = Unirest.post(url)
//       .headers(headers)
//       .fields(fields)
//       .asString()
//       .getBody();

//     System.out.println(response);
    
//     // Map<String, String> headers = new HashMap<>();
//     // headers.put("accept", "application/json");
//     // headers.put("Authorization", "Bearer 5a9ce37b3100004f00ab5154");

//     // Map<String, Object> fields = new HashMap<>();
//     // fields.put("name", "Sam Baeldung");
//     // fields.put("id", "PSP123");

//     // HttpResponse<JsonNode> jsonResponse 
//     //   = Unirest.put("http://www.mocky.io/v2/5a9ce7853100002a00ab515e")
//     //   .headers(headers).fields(fields)
//     //   .asJson();
    
    
//     // HttpResponse<JsonNode> jsonResponse =
//     //   Unirest.get("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154")
//     //   .header("accept", "application/json").queryString("apiKey", "123")
//     //   .asJson();

//   }
  
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
