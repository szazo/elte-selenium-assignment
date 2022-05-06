import java.util.List;
import java.util.Arrays;
import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

public class MainPageTest extends CaseBase {

  @Test
  public void mainPageShouldContainOnlyHamstersCourse() {
    // when
    MainPage mainPage = MainPage.navigateToMainPage(this.driver);

    // then
    List<String> courseNames = mainPage.getAvailableCourseNames();
    Assert.assertEquals(Arrays.asList("Hamsters"), courseNames);
  }
}
