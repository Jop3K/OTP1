package models;

public final class CurrentUser extends User {
	private static User user;
	
	public CurrentUser(User user) {
		CurrentUser.setUser(user);
	}
	
	public void updateCurrentUser(User user) {
		CurrentUser.setUser(user);
		
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		CurrentUser.user = user;
	}
	
	
}
