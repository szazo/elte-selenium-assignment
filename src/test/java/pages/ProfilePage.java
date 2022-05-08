import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class ProfilePage extends PageBase {

  private By userProfileBy = By.xpath("//div[@class='userprofile']");
  private By editProfileLinkBy = new ByChained(userProfileBy,
                                                    By.xpath("//li[@class='editprofile']//a"));
  private By fullNameBy = By.xpath("//header[@id='page-header']//div[@class='page-header-headings']/h1");
  private By descriptionBy = new ByChained(userProfileBy, By.xpath("//div[@class='description']"));
  private By countryBy = profileTreeValueXPath("Country");
  private By cityBy = profileTreeValueXPath("City/town");

  public ProfilePage(WebDriver driver) {
    super(driver);
  }

  public EditProfilePage editProfile() {
    this.waitAndReturnElement(this.editProfileLinkBy).click();

    return new EditProfilePage(this.driver);
  }

  public ProfileDetails getProfileDetails() {

    return new ProfileDetails() {{
      fullName = getText(fullNameBy);
      description = getText(descriptionBy);
      country = getText(countryBy);
      city = getText(cityBy);
    }};
  }

  private By profileTreeValueXPath(String label) {
    return new ByChained
      (userProfileBy,
       By.xpath
       (String.format("//div[@class='profile_tree']//li[@class='contentnode'] " +
                      "//dt[text()='%s']/following-sibling::dd", label)));
  }
}

class ProfileDetails {
  String fullName;
  String description;
  String country;
  String city;
}
