package application;
/*
 * Luokan idea on säilyttää dataa uuden istunnon aloittavasta käyttäjästä.
 * Tänne voidaan vielä lisäillä halumiamme attribuutteja.
 */
public class SessionUser extends User {
	private int UserID;

	public SessionUser() {

	}
	public SessionUser(User user, int userID) {
		super(user);
		this.UserID = userID;

	}

}
