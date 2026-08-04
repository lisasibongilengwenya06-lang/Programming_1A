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
    if (username.contains("_") && username.length() <= 5) { //checks if username has an underscore and is less than 5 characters
        isValid = true;
    }
    return isValid;
}
    public boolean checkPasswordComplexity(String password) { //Checks password meets complexity rules: 8+ characters, capital letter, number, special character
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
    public boolean checkCellPhoneNumber(String cellPhoneNumber) { //Checks cell number matches SA international format (+27 followed by 9 digits) using regex
    String pattern = "^\\+27\\d{9}$";
    boolean isValid = cellPhoneNumber.matches(pattern);
    return isValid;
}
    
    public String registerUser(String username, String password, String cellPhoneNumber) { //Registers a user by validating username, password, and cell number, returning the appropriate message
    String message = "";
       
    if (!checkUserName(username)) { //message if username invalid
        message = "Username is not correctly formatted; please ensure that your username contains an underscore and"
                + " is no more than five characters in length.";
    } else if (!checkPasswordComplexity(password)) { //message if password invalid
        message = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.";
    } else if (!checkCellPhoneNumber(cellPhoneNumber)){ //message if cell number invalid
        message = ("Cell phone number is incorrectly formatted or does not contain an international code.");
       
    } else { //message when all is well
        message = "Username successfully captured. Password successfully captured. Cell phone number successfully added";
    }
    
    return message;
}
    public boolean loginUser(String username, String password, String storedUsername, String storedPassword) { //Verifies that entered login credentials match the credentials stored at registration
    boolean isValid = false;
    if (username.equals(storedUsername) && password.equals(storedPassword)) {
        isValid = true;
    }
    return isValid;
}
    public String returnLoginStatus(boolean isValid, String name, String surname) { // Returns the welcome message on successful login or a failure message on unsuccessful login
        String message = "";
        
        if (isValid) { //when user returns to app message
            message = "Welcome " + name + ", " + surname + " it is great to see you again.";
        } else {
            message = "Username or password incorrect, please try again.";
        }
        
        return message;
    }
}