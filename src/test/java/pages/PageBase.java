package pages;

import org.openqa.selenium.WebDriver;

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
