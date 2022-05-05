import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

  private WebDriver driver;
  
  @Before
  public void setup() {
    WebDriverManager.chromedriver().setup();

    driver = new ChromeDriver();
    driver.manage().window().maximize();
  }

  @Test
  public void testMe() {
    MainPage mainPage = new MainPage(this.driver);
    
  }

  @After
  public void close() {
    if (driver != null) {
      driver.quit();
    }
  }
}
