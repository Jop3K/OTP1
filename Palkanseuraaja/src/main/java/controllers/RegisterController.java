package controllers;

import java.security.NoSuchAlgorithmException;
import javafx.event.Event;
import javafx.event.EventHandler;
import models.RegisterModelRefactored;
import models.User;
import views.RegisterView;

import dataAccessObjects.UserDAO;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the controller for "Register user" -view
 *
 * @author Joni, Artur, Joonas
 *
 */
public class RegisterController {

    private final RegisterView registerView;

    /**
     * The base constructor for the RegisterController class
     *
     * @param registerView
     */
    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.registerView.addCreateButtonEventHandler(new createButtonListener());
        this.registerView.addBackButtonEventHandler(new backButtonListener());
    }

    /**
     * The class for handling the onclick event when clicking the "Back" button
     *
     * @author Joni, Artur, Joonas
     *
     */
    class backButtonListener implements EventHandler {

        @Override
        public void handle(Event event) {
            ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.switchToLoginView();
            //suljetaan tietokantayhteys
        }
    }

    // Luokka kuuntelee create-buttonia ja luo uuden käyttäjän.
    /**
     * This use this method to handle the "Create new user" -button
     *
     * @author Joni, Artur, Joonas
     *
     */
    class createButtonListener implements EventHandler {

        /**
         * 1. Checks if register form is filled correctly 1a. The program alerts
         * if something is not filled correctly 2. Adding the user to the
         * database 2a. We don't add the user to the database if the username is
         * taken
         */
        @Override
        public void handle(Event arg0) {
            /*
			 * 1. Tarkistetaan, että rekisteröinti-formi on täytetty oikein
			 * 1a. Ohjelma ilmoittaa, jos jokin kohta on täytetty väärin
			 * 2. Lisätään käyttäjä tietokantaan
			 * 2a. Ei lisätä käyttäjää tietokantaan, koska käyttäjänimi on varattu
             */

            //Luodaan uusi RegisterModel-olio, joka varastoi tietoonsa registerViewin textfieldien
            RegisterModelRefactored registerModel = new RegisterModelRefactored(registerView.getFname(), registerView.getLname(), registerView.getUname(),
                    registerView.getPword1(), registerView.getPword2());

            //Tarkistetaan formi
            if (registerModel.formValidation()) {
                try {
                    User user = registerModel.buildUser();
                    //luodaan käyttäjä tietokantaan, jos käyttäjänimeä ei ole varattu.
                    if (UserDAO.save(user)) {
                        //ilmoitetaan onnistumisesta
                        registerView.showAlert(UserDAO.getAlert());
                        //Ohjataan takaisin loginiin
                        ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.switchToLoginView();
                    } else {
                        //Ilmoitetaan käyttäjätunnuksen olevan varattu
                        registerView.showAlert(UserDAO.getAlert());
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {//ilmoitetaan formin validaatiosta tapahtuneesta virheestä
                registerView.showAlert(registerModel.getAlert());
            }
        }
    }
}
