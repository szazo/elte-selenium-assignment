import org.junit.Test;
import pages.StaticPage;

import static org.junit.Assert.assertEquals;

public class StaticPageTest extends CaseBase {

    @Test
    public void shouldAccessStaticPagesAfterLogin() {
        // given
        this.login();

        StaticPageCase[] cases = new StaticPageCase[]{
                new StaticPageCase("/", "Dashboard"),
                new StaticPageCase("/message/index.php", "Messages"),
                new StaticPageCase("/user/preferences.php", "Preferences")
        };

        // when
        for (StaticPageCase testCase : cases) {
            StaticPage page = new StaticPage(this.rootUrl() + testCase.url, driver);
            String title = page.getTitle();

            assertEquals(testCase.expectedTitle, title);
        }
    }
}

class StaticPageCase {
    public StaticPageCase(String url, String expectedTitle) {
        this.url = url;
        this.expectedTitle = expectedTitle;
    }

    public final String url;
    public final String expectedTitle;
}
