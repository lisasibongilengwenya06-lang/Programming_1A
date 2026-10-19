/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.poepartone;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ProgPOE {

    public static void main(String[] args) {
        Login account = new Login();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your name");
        String name = scan.nextLine();
        System.out.println("Enter your surname");
        String surname = scan.nextLine();
        System.out.println("Enter your username");
        String username = scan.nextLine();
        System.out.println("Enter a password");
        String password = scan.nextLine();
        System.out.println("Enter your cell phone number");
        String cellPhoneNumber = scan.nextLine();

        String registerMessage = account.registerUser(username, password, cellPhoneNumber);
        System.out.println(registerMessage);

        if (registerMessage.startsWith("Username successfully captured")) {
            account.name = name;
            account.surname = surname;

            System.out.println("\n===== Login =====");
            System.out.println("Enter your username");
            String loginUsername = scan.nextLine();
            System.out.println("Enter your password");
            String loginPassword = scan.nextLine();

            boolean isValid = account.loginUser(loginUsername, loginPassword, account.username, account.password);
            System.out.println(account.returnLoginStatus(isValid, account.name, account.surname));

            // Messages can only be sent after a successful login
            if (isValid) {
                runQuickChat(scan);
            }
        }
    }

    /** Shows the welcome message, asks how many messages to enter, then runs the menu until the user quits. */
    private static void runQuickChat(Scanner scan) {
        System.out.println("Welcome to QuickChat.");
        int totalMessagesToEnter = readInt(scan, "How many messages would you like to enter? ");

        Message helper = new Message();
        boolean allEntered = false;
        int menuChoice = 0;

        while (menuChoice != 3) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            menuChoice = readInt(scan, "Select an option: ");

            if (menuChoice == 1) {
                if (allEntered) {
                    System.out.println("Limit reached. All messages for this session have already been entered.");
                } else {
                    enterMessages(scan, helper, totalMessagesToEnter);
                    allEntered = true;
                }
            } else if (menuChoice == 2) {
                System.out.println("Coming Soon.");
            } else if (menuChoice == 3) {
                System.out.println("Exiting application. Goodbye!");
            } else {
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        System.out.println("\nTotal messages sent this session: " + helper.returnTotalMessages());
    }

    /** Uses a for loop to collect the set number of messages. The loop counter is the message number. */
    private static void enterMessages(Scanner scan, Message helper, int totalMessagesToEnter) {
        for (int i = 0; i < totalMessagesToEnter; i++) {
            System.out.println("\n--- Entering Message " + (i + 1) + " of " + totalMessagesToEnter + " ---");

            String recipient;
            String cellCheck;
            do {
                System.out.print("Enter recipient cell number: ");
                recipient = scan.nextLine();
                cellCheck = helper.checkRecipientCell(recipient);
                System.out.println(cellCheck);
            } while (!cellCheck.contains("successfully captured"));

            String messageText;
            String lengthCheck;
            do {
                System.out.print("Enter message text (max 250 characters): ");
                messageText = scan.nextLine();
                lengthCheck = helper.checkMessageLength(messageText);
                System.out.println(lengthCheck);
            } while (!lengthCheck.contains("ready to send"));

            String generatedID = helper.generateMessageID();
            System.out.println("Message ID generated: " + generatedID);

            Message activeMessage = new Message(recipient, messageText, generatedID, i);

            System.out.println("Choose an action:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            int actionChoice = readInt(scan, "Enter choice (1-3): ");
            while (actionChoice < 1 || actionChoice > 3) {
                actionChoice = readInt(scan, "Invalid choice. Enter choice (1-3): ");
            }

            System.out.println(activeMessage.sentMessage(actionChoice));

            if (actionChoice == 3) {
                activeMessage.storeMessage();
            } else if (actionChoice == 1) {
                displayMessage(activeMessage);
            }
        }
    }

    /** Prints the message details in the required order: ID, hash, recipient, message. */
    private static void displayMessage(Message message) {
        System.out.println("\n====== Sent Message Details ======");
        System.out.println("Message ID   : " + message.getMessageID());
        System.out.println("Message Hash : " + message.getMessageHash());
        System.out.println("Recipient    : " + message.getRecipient());
        System.out.println("Message      : " + message.getMessageText());
        System.out.println("==================================");
    }

    /** Keeps asking until the user types a whole number, so letters cannot crash the program. */
    private static int readInt(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}