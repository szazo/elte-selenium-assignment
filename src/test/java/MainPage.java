import org.openqa.selenium.WebDriver;

class MainPage extends PageBase {

  public MainPage(WebDriver driver) {
    super(driver);
    this.driver.get("https://skillgo.io/moodle");
  }
}
