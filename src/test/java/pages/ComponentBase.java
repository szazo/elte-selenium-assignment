import java.util.List;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ComponentBase {

  protected WebDriver driver;
  protected WebDriverWait wait;

  public ComponentBase(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 5);
  }

  protected WebElement waitAndReturnElement(By locator) {
    this.waitForElementVisibility(locator);
    return this.driver.findElement(locator);
  }

  protected List<WebElement> waitAndReturnElements(By locator) {
    this.waitForElementVisibility(locator);
    return this.driver.findElements(locator);
  }

  private void waitForElementVisibility(By locator) {
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }
}
