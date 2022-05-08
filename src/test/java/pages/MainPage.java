package pages;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class MainPage extends PageBase {

    private final By userMenuLocator = By.xpath("//div[@class='usermenu']");
    private final By loginLocator = new ByChained(userMenuLocator,
            By.xpath("span[@class='login']"));
    private final By loginLinkLocator = new ByChained(loginLocator, By.xpath("a"));

    private final By availableCourseLinksLocator = By.xpath
            ("//div[contains(@class, 'courses') and contains(@class, 'frontpage-course-list-all')]" +
                    "//h3[@class='coursename']/a");

    public MainPage(WebDriver driver) {
        this(null, driver);
    }

    protected MainPage(String rootUrl, WebDriver driver) {
        super(driver);

        if (rootUrl != null) {
            this.driver.get(rootUrl);
        }
        this.checkTitle("Skillgo Moodle");
    }

    public static MainPage navigateToMainPage(String rootUrl, WebDriver driver) {
        MainPage mainPage = new MainPage(rootUrl, driver);
        return mainPage;
    }

    public RegistrationConfirmedPage confirmUrl(String url) {
        this.driver.get(url);

        return new RegistrationConfirmedPage(this.driver);
    }

    public List<String> getAvailableCourseNames() {

        List<WebElement> elements = this.waitAndReturnElements(availableCourseLinksLocator);

        ArrayList<String> courseNames = new ArrayList<>();
        for (WebElement element : elements) {
            courseNames.add(element.getText());
        }

        return courseNames;
    }

    public LoginPage gotoLogin() {
        this.waitAndReturnElement(this.loginLinkLocator).click();

        return new LoginPage(this.driver);
    }

    public String getLoginText() {
        return this.waitAndReturnElement(this.loginLocator).getText();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }
}
