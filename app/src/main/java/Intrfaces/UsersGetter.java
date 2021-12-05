package Intrfaces;

import com.example.myapp_1.UserManager;

import java.util.List;

import utils.User;

public interface UsersGetter {
    void gotUsers(List<UserManager> users);
}
