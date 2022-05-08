package pages;

import org.openqa.selenium.WebDriver;

public class DashboardPage extends PageBase {

    public DashboardPage(WebDriver driver) {
        super(driver);

        this.checkTitle("Dashboard");
    }

    public String getLoggedInUserFullName() {
        return this.navigationBar().getLoggedInUserFullname();
    }

    public MainPage logout() {
        this.navigationBar().toggleUserMenuAndLogout();

        return new MainPage(driver);
    }

    public ProfilePage showProfile() {
        this.navigationBar().toggleUserMenuAndShowProfile();

        return new ProfilePage(driver);
    }

    private NavigationBar navigationBar() {
        return new NavigationBar(this.driver);
    }
}
