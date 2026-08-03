/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poepartone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Admin
 */
public class LoginTest {
    
    public LoginTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
    

    /**
     * Test of checkUserName method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        String username = "kyl_1";
        Login instance = new Login();
        boolean expResult = true;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of checkUserName method, of class Login when invalid.
     */
    @org.junit.jupiter.api.Test
    public void testCheckUserName_invalid() {
        System.out.println("checkUserName");
        String username = "kyle!!!!!!";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        String password = "Ch&&sec@ke99!";
        Login instance = new Login();
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of checkPasswordComplexity method, of class Login when invalid.
     */
    @org.junit.jupiter.api.Test
    public void testCheckPasswordComplexity_invalid() {
        System.out.println("checkPasswordComplexity");
        String password = "password";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }
    
    

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String cellPhoneNumber = "+27838968976";
        Login instance = new Login();
        boolean expResult = true;
        boolean result = instance.checkCellPhoneNumber(cellPhoneNumber);
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of checkCellPhoneNumber method, of class Login when invalid.
     */
    @org.junit.jupiter.api.Test
    public void testCheckCellPhoneNumber_invalid() {
        System.out.println("checkCellPhoneNumber");
        String cellPhoneNumber = "08966553";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber(cellPhoneNumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String cellPhoneNumber = "+27838968976";
        Login instance = new Login();
        String expResult = "Username successfully captured. Password successfully captured. Cell phone number successfully added";
        String result = instance.registerUser(username, password, cellPhoneNumber);
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test1 of registerUser method, of class Login when invalid.
     */
    @org.junit.jupiter.api.Test
    public void testRegisterUser_invalidUsername() {
        System.out.println("registerUser");
        String username = "kyle!!!!!!";
        String password = "Ch&&sec@ke99!";
        String cellPhoneNumber = "+27838968976";
        Login instance = new Login();
        String expResult = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String result = instance.registerUser(username, password, cellPhoneNumber);
        assertEquals(expResult, result);
    }
    
    /**
     * Test2 of registerUser method, of class Login when invalid.
     */
     @org.junit.jupiter.api.Test
     public void testRegisterUser_invalidPassword() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "password";
        String cellPhoneNumber = "+27838968976";
        Login instance = new Login();
        String expResult = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.";
        String result = instance.registerUser(username, password, cellPhoneNumber);
        assertEquals(expResult, result);
}
     
     /**
     * Test3 of registerUser method, of class Login when invalid.
     */
     @org.junit.jupiter.api.Test
     public void testRegisterUser_invalidCellPhoneNumber() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String cellPhoneNumber = "08966553";
        Login instance = new Login();
        String expResult = "Cell phone number is incorrectly formatted or does not contain an international code.";
        String result = instance.registerUser(username, password, cellPhoneNumber);
        assertEquals(expResult, result);
}

    /**
     * Test of loginUser method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testLoginUser_valid() {
    System.out.println("loginUser");
    String username = "kyl_1";
    String password = "Ch&&sec@ke99!";
    String storedUsername = "kyl_1";
    String storedPassword = "Ch&&sec@ke99!";
    Login instance = new Login();
    boolean expResult = true;
    boolean result = instance.loginUser(username, password, storedUsername, storedPassword);
    assertEquals(expResult, result);
}

    @org.junit.jupiter.api.Test
    public void testLoginUser_invalid() {
    System.out.println("loginUser");
    String username = "kyl_1";
    String password = "wrongPassword";
    String storedUsername = "kyl_1";
    String storedPassword = "Ch&&sec@ke99!";
    Login instance = new Login();
    boolean expResult = false;
    boolean result = instance.loginUser(username, password, storedUsername, storedPassword);
    assertEquals(expResult, result);
}
    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @org.junit.jupiter.api.Test
public void testReturnLoginStatus_valid() {
    System.out.println("returnLoginStatus");
    boolean isValid = true;
    String name = "Lisa";
    String surname = "Ngwenya";
    Login instance = new Login();
    String expResult = "Welcome Lisa, Ngwenya it is great to see you again.";
    String result = instance.returnLoginStatus(isValid, name, surname);
    assertEquals(expResult, result);
}

/**
     * Test of returnLoginStatus method, of class Login when invalid.
     */
    @org.junit.jupiter.api.Test
public void testReturnLoginStatus_invalid() {
    System.out.println("returnLoginStatus");
    boolean isValid = false;
    String name = "Lisa";
    String surname = "Ngwenya";
    Login instance = new Login();
    String expResult = "Username or password incorrect, please try again.";
    String result = instance.returnLoginStatus(isValid, name, surname);
    assertEquals(expResult, result);
}
    
}
