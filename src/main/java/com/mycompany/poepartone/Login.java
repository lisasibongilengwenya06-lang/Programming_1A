/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

/**
 *
 * @author Admin
 */
public class Login extends Registration {

    public boolean loginUser(String username, String password, String storedUsername, String storedPassword) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    public String returnLoginStatus(boolean isValid, String name, String surname) {
        String message = "";
        if (isValid) {
            message = "Welcome " + name + ", " + surname + " it is great to see you again.";
        } else {
            message = "Username or password incorrect, please try again.";
        }
        return message;
    }
}
