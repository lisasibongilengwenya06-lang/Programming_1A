/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepartone;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class PoePartOne {

    public static void main(String[] args) {
        Login account = new Login();
        Scanner scan = new Scanner(System.in);

        System.out.println("\n===== Registration =====");
        System.out.println("Enter your name");  //user is prompted to enter their name
        String name = scan.nextLine();
        System.out.println("Enter your surname"); //user is prompted to enter their surname/last name
        String surname = scan.nextLine();
        System.out.println("Enter your username");  //user is prompted to a username which has a underscore and is less than 5 characters
        String username = scan.nextLine();
        System.out.println("Enter a password");  //user is prompted to enter a password that has a capital letter, number and a special chracter
        String password = scan.nextLine();
        System.out.println("Enter your cell phone number");  //user is prompted to enter their cell phone number starting with country code +27
        String cellPhoneNumber = scan.nextLine();

        String registerMessage = account.registerUser(username, password, cellPhoneNumber);
        System.out.println(registerMessage);

        if (registerMessage.startsWith("Username successfully captured")) { //when all conditions are met this message will appear
            account.name = name;
            account.surname = surname;

            System.out.println("\n===== Login =====");
            System.out.println("Enter your username"); //user is prompted to re-enter their username
            String loginUsername = scan.nextLine();
            System.out.println("Enter your password"); //user is prompted to re-enter their password
            String loginPassword = scan.nextLine();

            boolean isValid = account.loginUser(loginUsername, loginPassword, account.username, account.password);
            String loginMessage = account.returnLoginStatus(isValid, account.name, account.surname);
            System.out.println(loginMessage);
        }
    }
}
