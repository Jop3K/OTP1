package models;

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

}
