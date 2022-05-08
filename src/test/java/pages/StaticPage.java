package pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class StaticPage extends PageBase {

  public StaticPage(String relativeUrl, WebDriver driver) {
    super(driver);

    this.driver.get(relativeUrl);
  }

  public String getTitle() {
    return this.driver.getTitle();
  }
}
