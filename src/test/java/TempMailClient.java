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

  public class Email {
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