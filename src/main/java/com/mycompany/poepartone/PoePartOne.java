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
        Scanner input = new Scanner(System.in);
        Account register = new Account();
        
        System.out.println("Enter your name"); //user enter name
        String name = input.nextLine();
        register.name = name;
        
        System.out.println("Enter your surname"); //user enter surname
        String surname = input.nextLine();
        register.surname = surname;
        
        System.out.println("Enter your username"); //user enter username
        String username = input.nextLine();
        register.username = username;
        
        System.out.println("Enter a password"); //user enter password
        String password = input.nextLine();
        register.password = password;
        
        System.out.println("Enter your cell phone number"); // user enter cell no. 
        String cellPhoneNumber = input.nextLine();
        register.cellPhoneNumber = cellPhoneNumber;
       
        //info outputted
        System.out.println("Name and surname: " + name + " " + surname);
        System.out.println("Username: " + register.username);
        System.out.println("Password: " + register.password);
        System.out.println("Cell phone number: " + register.cellPhoneNumber);
        
        
    
        //checking if it meets requirements
        Login login = new Login();
        System.out.println(login.checkUserName("kyl_1"));
        System.out.println(login.checkUserName("kyle!!!!!!"));
        System.out.println(login.checkPasswordComplexity("Ch&&sec@ke99!")); // should print true
        System.out.println(login.checkPasswordComplexity("password"));      // should print false
        System.out.println(login.checkCellPhoneNumber("+27838968976")); // should print true
        System.out.println(login.checkCellPhoneNumber("08966553"));     // should print false
        System.out.println(login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976")); // should print success message
        System.out.println(login.registerUser("kyle!!!!!!", "password", "08966553"));      // should print username failure message
        System.out.println(login.registerUser("kyl_1", "password", "08966553"));           // should print password failure message
        System.out.println(login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553")); // should print cell number failure message
        System.out.println(login.loginUser("kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!")); // should print true — matches
        System.out.println(login.loginUser("kyl_1", "wrongpass", "kyl_1", "Ch&&sec@ke99!"));      // should print false — password mismatch
        System.out.println(login.returnLoginStatus(true, "Lisa", "Ngwenya"));   // should print welcome message
System.out.println(login.returnLoginStatus(false, "Lisa", "Ngwenya"));  // should print failure message
    }
}
