import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class DashboardPage extends PageBase {

  public DashboardPage(WebDriver driver) {
    super(driver);

    if (!driver.getTitle().equals("Skillgo Moodle: Log in to the site")) {
        throw new IllegalStateException("This is not the main page, current page is: " + driver.getCurrentUrl());
    }
  }
}
