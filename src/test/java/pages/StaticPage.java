package pages;

import org.openqa.selenium.WebDriver;

public class StaticPage extends PageBase {

    public StaticPage(String relativeUrl, WebDriver driver) {
        super(driver);

        this.driver.get(relativeUrl);
    }

    public String getTitle() {
        return this.driver.getTitle();
    }
}
