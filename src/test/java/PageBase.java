import org.openqa.selenium.WebDriver;

class PageBase {

  protected WebDriver driver;

  public PageBase(WebDriver driver) {
    this.driver = driver;
  }
}
