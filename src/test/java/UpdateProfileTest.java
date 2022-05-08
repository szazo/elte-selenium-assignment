import org.junit.Test;
import pages.DashboardPage;
import pages.EditProfilePage;
import pages.ProfilePage;

import static org.junit.Assert.assertEquals;

public class UpdateProfileTest extends CaseBase {

    @Test
    public void updateProfileShouldUpdateDetails() {
        // given
        DashboardPage dashboard = this.login();
        ProfilePage profilePage = dashboard.showProfile();
        EditProfilePage editProfilePage = profilePage.editProfile();

        EditProfilePage.UpdateProfileDetails details = new EditProfilePage.UpdateProfileDetails() {{
            firstname = randomString("First", 5);
            lastname = randomString("Last", 5);
            city = randomString("City", 5);
            description = randomString("Description", 5);
        }};

        // when
        ProfilePage profilePageAfterEdit = editProfilePage.updateProfile(details);

        // then
        ProfilePage.ProfileDetails detailsAfterEdit = profilePageAfterEdit.getProfileDetails();
        assertEquals(details.firstname + " " + details.lastname, detailsAfterEdit.fullName);
        assertEquals(details.description, detailsAfterEdit.description);
        assertEquals(details.city, detailsAfterEdit.city);
        assertEquals(details.outSelectedCountry, detailsAfterEdit.country);
    }
}
