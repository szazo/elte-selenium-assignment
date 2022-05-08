import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.DashboardPage;

import java.util.Random;

public class CaseBase {

  protected WebDriver driver;

  @BeforeClass
  public static void loadConfig() {
    Dotenv.configure().systemProperties().load();
  }

  @Before
  public void setup() {
    WebDriverManager.chromedriver().setup();

    driver = new ChromeDriver();
    driver.manage().window().maximize();
  }

  @After
  public void close() {
    if (driver != null) {
      driver.quit();
    }
  }

  protected DashboardPage login() {
    GivenLoggedIn givenLoggedIn = new GivenLoggedIn(this.rootUrl(),
                                                    this.username(), this.password(),
                                                    this.driver);
    return givenLoggedIn.login();
  }

  protected String randomString(String prefix, int randomLength) {
    Random rand = new Random();
    return String.format("%s%0" + randomLength + "d", prefix, rand.nextInt((int) Math.pow(10, randomLength)));
  }

  protected String rootUrl() {
    return System.getProperty("TEST_ROOT_URL");
  }

  protected String tempMailHost() {
    return System.getProperty("TEST_TEMPMAIL_HOST");
  }

  protected String tempMailApiKey() {
    return System.getProperty("TEST_TEMPMAIL_APIKEY");
  }

  private String username() {
    return System.getProperty("TEST_USERNAME");
  }

  private String password() {
    return System.getProperty("TEST_PASSWORD");
  }
}
