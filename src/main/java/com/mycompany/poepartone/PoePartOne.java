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

            if (isValid) {
                runQuickChat(scan);
            }
        }
    }

    private static void runQuickChat(Scanner scan) {
        System.out.println("Welcome to QuickChat.");
        System.out.print("How many messages would you like to enter? ");
        int totalMessagesToEnter = scan.nextInt();
        scan.nextLine();

        int menuChoice = 0;
        boolean allEntered = false;
        Message helper = new Message();

        while (menuChoice != 3) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Select an option: ");
            menuChoice = scan.nextInt();
            scan.nextLine();

            if (menuChoice == 1) {
                if (allEntered) {
                    System.out.println("Limit reached. All messages for this session have already been entered.");
                } else {
                    for (int i = 0; i < totalMessagesToEnter; i++) {
                        System.out.println("\n--- Entering Message " + (i + 1) + " of " + totalMessagesToEnter + " ---");

                        System.out.print("Enter recipient cell number: ");
                        String recipient = scan.nextLine();
                        String cellCheck = helper.checkRecipientCell(recipient);
                        System.out.println(cellCheck);
                        if (!cellCheck.contains("successfully captured")) {
                            System.out.println("Please try entering this message again.");
                            i--;
                            continue;
                        }

                        System.out.print("Enter message text (max 250 characters): ");
                        String messageText = scan.nextLine();
                        String lengthCheck = helper.checkMessageLength(messageText);
                        System.out.println(lengthCheck);
                        if (!lengthCheck.contains("ready to send")) {
                            System.out.println("Please try entering this message again.");
                            i--;
                            continue;
                        }

                        String generatedID = helper.generateMessageID();
                        System.out.println("Message ID generated: " + generatedID);

                        Message activeMessage = new Message(recipient, messageText, generatedID, i);

                        System.out.println("Choose an action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Enter choice (1-3): ");
                        int actionChoice = scan.nextInt();
                        scan.nextLine();

                        System.out.println(activeMessage.sentMessage(actionChoice));

                        if (actionChoice == 3) {
                            activeMessage.storeMessage();
                        }

                        if (actionChoice == 1) {
                            System.out.println("\n====== Sent Message Details ======");
                            System.out.println("Message ID   : " + activeMessage.getMessageID());
                            System.out.println("Message Hash : " + activeMessage.getMessageHash());
                            System.out.println("Recipient    : " + activeMessage.getRecipient());
                            System.out.println("Message      : " + activeMessage.getMessageText());
                            System.out.println("==================================");
                        }
                    }
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
}
