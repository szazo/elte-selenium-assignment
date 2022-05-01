import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

  private WebDriver driver;
  
  @Before
  public void setup() {
    WebDriverManager.chromedriver().setup();

    driver = new ChromeDriver();
    driver.manage().window().maximize();

    
  }
}
