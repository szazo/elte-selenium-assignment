import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

public class CaseBase {

  protected WebDriver driver;
  
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
}
