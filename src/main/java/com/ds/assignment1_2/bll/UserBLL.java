package com.ds.assignment1_2.bll;

import com.ds.assignment1_2.utils.Notification;
import com.ds.assignment1_2.entity.User;

public interface UserBLL {

    Notification<User> login(String username, String password);

}
