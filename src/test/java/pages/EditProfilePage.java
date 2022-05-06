import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

class EditProfilePage extends PageBase {

  private By formLocator = By.xpath("//div[@role='main']/form[@class='mform']");

  //  private By 
  
  private By userProfileLocator = By.xpath("//div[@class='userprofile']");
  private By editProfileLinkLocator = new ByChained(userProfileLocator,
                                                    By.xpath("//li[@class='editprofile']//a"));


  public EditProfilePage(WebDriver driver) {
    super(driver);
  }

  public void editProfile() {
    this.waitAndReturnElement(this.editProfileLinkLocator).click();

    //    return new LoginPage(this.driver);
  }
}
