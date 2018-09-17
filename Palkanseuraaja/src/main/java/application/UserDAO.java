package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class UserDAO extends DatabaseConnection {

    private PreparedStatement preparedStatement = null;
    private String sql;
    private Alert alert;
    private SessionUser sessionUser;

    public UserDAO() {

        super();

    }

    public boolean login(String username, String password) {
        User user = null;
        ResultSet resultSet = null;

        try {
            sql = "SELECT * FROM users WHERE username=? && password=?";
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("gmail")
                );
                //tekee uuden sessionUserin, jonka LoginController pystyy hakemaan
                this.sessionUser = new SessionUser(user, resultSet.getInt("userID"));

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kirjautuminen epäonnistui.");
                alert.setHeaderText("Tarkista käyttäjätunnus ja salasana");
                this.alert = alert;
                return false;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

            return false;
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();

            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kirjautuminen onnistui!");
        alert.setHeaderText("Tervetuloa!");
        this.alert = alert;
        return true;

    }
    //Metodi palauttaa truen, jos käyttäjän lisäys onnistuu.

    public boolean addUserDB(User user) {
        //Katsoo, löytyykö käyttäjänimi tietokannasta
        if (checkForDuplicateUser(user)) {
            // Luodaan uusi alert, koska käyttäjätunnus on varattu
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText("Käyttäjätunnus on varattu!");
            this.alert = alert;
            return false;
        }

        sql = "INSERT INTO users (username, firstname, lastname, password, gmail) VALUES (?,?,?,?,?)";

        try {

            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getGmail());

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Käyttäjän luominen epäonnistui");
            return false;
        }
        //Luodaan alert, jolla ilmoitetaan uuden käyttäjän luomisen onnistumisesta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rekisteröinti");
        alert.setHeaderText("Uuden käyttäjän luominen onnistui!");
        this.alert = alert;
        return true;
    }
    // palauttaa truen jos käyttäjänimi löytyy tietokannasta

    public boolean checkForDuplicateUser(User user) {

        ResultSet resultSet = null;
        try {
            sql = "SELECT * FROM users WHERE username=?";
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //if user is in DB

                return true;
            } else {
                return false;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return true;
        }
    }

    public Alert getAlert() {
        return this.alert;
    }

    public SessionUser getSessionUser() {
        return sessionUser;
    }

}
