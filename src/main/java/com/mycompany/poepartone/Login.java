/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

/**
 *
 * @author Admin
 */
public class Login {
    public boolean checkUserName(String username) {
        boolean isValid = false;
        
        if (username .contains("_") && username.length()<=5) { //checks if username has specifics
            isValid = true;
        }
        
        return isValid;
    }
    public boolean checkPasswordComplexity(String password) { //checks if password has specifics
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            // special character check
            if (!Character.isLetterOrDigit(c)) {
    hasSpecialChar = true;
}
        }
        boolean isValid = false;
        //checking all
        if (hasCapital && hasNumber && hasSpecialChar && password.length()>=8){
            isValid = true;
        }
        return isValid;
    }
    public boolean checkCellPhoneNumber(String cellPhoneNumber){
    String pattern = "^\\+27\\d{9}$";
    boolean isValid = cellPhoneNumber.matches(pattern);
    return isValid;
}
    public String registerUser(String username, String password, String cellPhoneNumber) {
    String message = "";
       
    if (!checkUserName(username)) {
        message = "Username is not correctly formatted; please ensure that your username contains an underscore and"
                + " is no more than five characters in length.";
    } else if (!checkPasswordComplexity(password)) {
        message = "Password is not correctly formatted; please ensure that the password contains at least eight characters,"
                + " a capital letter, a number and a special character.";
    } else if (!checkCellPhoneNumber(cellPhoneNumber)){
        message = ("Cell phone number is incorrectly formatted or does not contain an international code.");
       
    } else {
        message = "Username successfully captured. Password successfully captured. Cell phone number successfully added";
    }
    
    return message;
}
    public boolean loginUser(String username, String password, String storedUsername, String storedPassword) {
    boolean isValid = false;
    if (username.equals(storedUsername) && password.equals(storedPassword)) {
        isValid = true;
    }
    return isValid;
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