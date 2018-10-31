package com.ds.assignment1_2.bll;

import com.ds.assignment1_2.utils.Notification;
import com.ds.assignment1_2.dao.UserDAO;
import com.ds.assignment1_2.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserBLLImplementation implements UserBLL {

    private UserDAO userDAO;

    public UserBLLImplementation(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Notification<User> login(String username, String password) {
        Notification<User> notification = new Notification<>();

        User user = userDAO.getUser(username);
        if(user != null){
            if(BCrypt.checkpw(password, user.getPassword())){
                notification.setResult(user);
            } else {
                notification.addError("Invalid password.");
            }
        } else {
            notification.addError("No such user.");
        }

        return notification;
    }
}
