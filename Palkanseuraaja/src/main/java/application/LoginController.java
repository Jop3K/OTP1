package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class LoginController {
	private ViewChanger viewChanger;
	private UserDAO dao;
	private LoginView loginView;
	private LoginModel loginModel;

	public LoginController (LoginView loginView, LoginModel loginModel, ViewChanger viewChanger) {

		this.viewChanger = viewChanger;
		this.dao = new UserDAO();
		this.loginModel = loginModel;
		this.loginView = loginView;
		//Lisätään listenerit loginViewin buttoneille
		loginView.addRegisterButtonEventListener(new RegisterButtonListener());
		loginView.addLoginButtonEventListener(new LoginButtonListener());
	}
	//Kuuntelee register-buttonia
	class RegisterButtonListener implements EventHandler {

		@Override
		//Ohjaa registerViewiin
		public void handle(Event event) {

			RegisterView registerView = new RegisterView();
			RegisterModel registerModel = new RegisterModel();
			RegisterController registerViewController = new RegisterController(registerView,registerModel, viewChanger);
			viewChanger.viewBuilder(registerView.getView());
			//sulkee tietokantayhteyden
			dao.closeConnection();
		}

	}
	//Kuuntelee login-buttonia
	class LoginButtonListener implements EventHandler {

		@Override
		public void handle(Event arg0) {
			//Varmistaa, että kumpikaan kentistä ei ole tyhjä
			if(loginModel.loginFieldValidation(loginView.getUsernameField(), loginView.getPasswordField())) {
				
				//Yritetään sisäänkirjaumista ottamalla yhteys tietokantaan
				if(dao.login(loginView.getUsernameField(), loginView.getPasswordField())) {
					//Kirjautuminen onnistui ja luodaan ilmoitus siitä. 
					loginView.showAlert(dao.getAlert());
					
					//Luodaan sessionUser, joka pitää tallessaan uuden istunnon käyttäjätiedot.
									
					SessionUser sessionUser = new SessionUser();
					sessionUser = dao.getSessionUser();
					
					/*Tähän sitten luodaan ohjaus itse ohjelmaan sekä passataan jollain tavalla 
					 * sessionUser eteenpäin sinne
					 */
					viewChanger.switchStage("mainView", viewChanger);
					dao.closeConnection();
				}
				
				else {
						//Kirjautuminen epäonnistui. Ilmoitetaan siitä
						loginView.showAlert(dao.getAlert());
				}	
			}
			else {
				//Jompikumpi tai molemmat kentät ovat tyhjät. Ilmoitetaan siitä
				loginView.showAlert(loginModel.getAlert());
			}
		}
		
	}


}

