package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserCredentials {

    private String username;
    private String password;
    private int accountNumber;
    private int accessNumber;

    // Constructor
    public UserCredentials(String username, String password,int accountNumber, int accessNumber) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.accessNumber = accessNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccessNumber() {
        return accessNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessNumber(int accessNumber) {
        this.accessNumber = accessNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }   

    // Static method to initialize userList
    public static List<UserCredentials> initializeUserList() {
        List<UserCredentials> userList = new ArrayList<>();
        userList.add(new UserCredentials("user1", "password1", 123456, 1));
        userList.add(new UserCredentials("user2", "password2", 234567, 2));
        userList.add(new UserCredentials("user3", "password3", 345678, 3));
        userList.add(new UserCredentials("user4", "password4", 456789, 4));
        return userList;
    }

    // Static method to get user by account number
    public static UserCredentials getUserByName(String username) {
        List<UserCredentials> userList = initializeUserList();
        for (UserCredentials user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static UserCredentials getUserByAccessNumber(int accessNumber) {
        List<UserCredentials> userList = initializeUserList();
        for (UserCredentials user : userList) {
            if (user.getAccessNumber() == accessNumber) {
                return user;
            }
        }
        return null;
    }
}
