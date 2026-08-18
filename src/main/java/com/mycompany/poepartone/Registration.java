/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

/**
 *
 * @author Admin
 */
public class Registration {
    String username;
    String password;
    String cellPhoneNumber;
    String name;
    String surname;
    
    public boolean checkUserName(String username) {
        boolean isValid = false;
        if (username.contains("_") && username.length() <= 5) {
            isValid = true;
        }
        return isValid;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        boolean isValid = false;
        if (hasCapital && hasNumber && hasSpecialChar && password.length() >= 8) {
            isValid = true;
        }
        return isValid;
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String pattern = "^\\+27\\d{9}$";
        return cellPhoneNumber.matches(pattern);
    }

    public String registerUser(String username, String password, String cellPhoneNumber) {
        String message = "";
        if (!checkUserName(username)) {
            message = "Username is not correctly formatted; please ensure that your username contains an underscore and"
                    + " is no more than five characters in length.";
        } else if (!checkPasswordComplexity(password)) {
            message = "Password is not correctly formatted; please ensure that the password contains at least eight characters, "
                    + "a capital letter, a number and a special character.";
        } else if (!checkCellPhoneNumber(cellPhoneNumber)) {
            message = "Cell phone number is incorrectly formatted or does not contain an international code.";
        } else {
            this.username = username;
            this.password = password;
            this.cellPhoneNumber = cellPhoneNumber;
            message = "Username successfully captured. Password successfully captured. Cell phone number successfully added";
        }
        return message;
    }

    
}
