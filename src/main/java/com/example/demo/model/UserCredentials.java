package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserCredentials {

    private String accountNumber;
    private String password;
    private int accessNumber;

    // Constructor
    public UserCredentials(String accountNumber, String password, int accessNumber) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.accessNumber = accessNumber;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getAccessNumber() {
        return accessNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessNumber(int accessNumber) {
        this.accessNumber = accessNumber;
    }

    // Static method to initialize userList
    public static List<UserCredentials> initializeUserList() {
        List<UserCredentials> userList = new ArrayList<>();
        userList.add(new UserCredentials("12345", "password1", 1));
        userList.add(new UserCredentials("54321", "password2", 2));
        userList.add(new UserCredentials("98765", "password3", 3));
        userList.add(new UserCredentials("56789", "password4", 4));
        userList.add(new UserCredentials("24680", "password5", 5));
        return userList;
    }

    // Static method to get user by account number
    public static UserCredentials getUserByAccountNumber(String accountNumber) {
        List<UserCredentials> userList = initializeUserList();
        for (UserCredentials user : userList) {
            if (user.getAccountNumber().equals(accountNumber)) {
                return user;
            }
        }
        return null;
    }
}
