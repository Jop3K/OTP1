package models;

import models.User;

public interface ICurrentUser {

    public void setUser(User user);

    public User getUser();

    public void nullUser();

}
