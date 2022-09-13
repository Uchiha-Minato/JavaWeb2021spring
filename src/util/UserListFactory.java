package util;

import domain.User;

import java.util.ArrayList;

public class UserListFactory {
    private ArrayList<User> users = null;

    public UserListFactory(ArrayList<User> users) {
        this.users = users;
    }

    public boolean existed(String userName) {
        for(int i = 0; i < users.size(); i ++) {
            if(userName.equals(users.get(i).getUserName()))
                return true;
        }
        return false;
    }

    public String getTel(String userName) {
        String tel = "";
        for(int i = 0; i < users.size(); i ++) {
            if(userName.equals(users.get(i).getUserName()))
                tel = users.get(i).getTel();
        }
        return tel;
    }

    public String getPassword(String userName) {
        String password = "";
        for (int i = 0; i < users.size(); i++) {
            if(userName.equals(users.get(i).getUserName()))
                password = users.get(i).getPassword();
        }
        return password;
    }
}
