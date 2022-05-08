import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

class EditProfilePage extends PageBase {

  private By formBy = By.xpath("//div[@role='main']/form[@class='mform']");
  private By firstnameBy = new ByChained(formBy, By.xpath("//input[@name='firstname']"));
  private By lastnameBy = new ByChained(formBy, By.xpath("//input[@name='lastname']"));
  private By cityBy = new ByChained(formBy, By.xpath("//input[@name='city']"));
  private By descriptionBy = new ByChained(formBy, By.xpath("//div[@id='id_description_editoreditable']"));
  private By countryBy = new ByChained(formBy, By.xpath("//select[@name='country']"));

  private By updateButtonBy = new ByChained(formBy, By.xpath("//input[@name='submitbutton' and @type='submit']"));

  public EditProfilePage(WebDriver driver) {
    super(driver);

    this.checkTitle("Skillgo Moodle: Edit profile");
  }

  public ProfilePage updateProfile(UpdateProfileDetails details) {
    this.enterText(this.firstnameBy, details.firstname, true);
    this.enterText(this.lastnameBy, details.lastname, true);
    this.enterText(this.cityBy, details.city, true);
    this.enterText(this.descriptionBy, details.description, true);
    String countryName = this.selectRandomCountry();

    // return the randomly selected country's name
    details.outSelectedCountry = countryName;

    // submit
    waitAndReturnElement(this.updateButtonBy).submit();
    return new ProfilePage(this.driver);
  }

  private String selectRandomCountry() {
    Select countrySelect = new Select(waitAndReturnElement(this.countryBy));

    List<WebElement> countryOptions = countrySelect.getOptions();
    int countryIndex = new Random().nextInt(countryOptions.size() - 1) + 1; // we don't select first
    countrySelect.selectByIndex(countryIndex);

    return countryOptions.get(countryIndex).getText();
  }
}

class UpdateProfileDetails {
  public String firstname;
  public String lastname;
  public String city;
  public String description;

  // out parameter with the name of the selected country
  public String outSelectedCountry;
}
