package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CurrentWorkProfile extends WorkProfile {

    private static WorkProfile workProfile;

    public CurrentWorkProfile(WorkProfile w) {
        CurrentWorkProfile.setWorkProfile(w);
    }

    public static WorkProfile getWorkProfile() {
        return workProfile;
    }

    public static void setWorkProfile(WorkProfile w) {
        CurrentWorkProfile.workProfile = w;
    }

    public static List<ExtraPay> getProfilesExtraPays() {

        /*
            Moved here because before, all extrapays were loaded twice. First when loading workprofiles and again when doing getProfilesExtraPays database call.
            Now extrapays are retrieved from the already existing workprofiles instead.

         */

        Set<ExtraPay> extraPays = workProfile.getExtraPays();

        return new ArrayList<>(extraPays);
    }

}
