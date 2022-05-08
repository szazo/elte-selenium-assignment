import io.github.cdimascio.dotenv.Dotenv;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

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

  protected String rootUrl() {
    return System.getProperty("TEST_ROOT_URL");
  }

  private String username() {
    return System.getProperty("TEST_USERNAME");
  }

  private String password() {
    return System.getProperty("TEST_PASSWORD");
  }
}
