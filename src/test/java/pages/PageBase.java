package pages;

import java.util.List;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class PageBase extends ComponentBase {

  public PageBase(WebDriver driver) {
    super(driver);
  }

  protected void checkTitle(String expected) {
    if (!driver.getTitle().equals(expected)) {
      throw new IllegalStateException("This is not the page for " + this.getClass().getName() +
                                      ", current page is: " + driver.getCurrentUrl() + "; title: " + driver.getTitle());
    }
  }
}
