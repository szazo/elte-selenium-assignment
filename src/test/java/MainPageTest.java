import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;

import java.util.Arrays;
import java.util.List;

public class MainPageTest extends CaseBase {

  @Test
  public void mainPageShouldContainOnlyHamstersCourse() {
    // when
    MainPage mainPage = MainPage.navigateToMainPage(this.rootUrl(), this.driver);

    // then
    List<String> courseNames = mainPage.getAvailableCourseNames();
    Assert.assertEquals(Arrays.asList("Hamsters"), courseNames);
  }
}
