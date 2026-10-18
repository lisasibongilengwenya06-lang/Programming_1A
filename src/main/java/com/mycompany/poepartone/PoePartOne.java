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
        
        boolean isValid = true;
        // 1. Only continue if isValid is true
       if (isValid) {
           // 2. Print Welcome message
            System.out.println("Welcome to QuickChat.");
            // 3. Ask how many messages the user wants to enter at the start
            System.out.print("How many messages would you like to enter? ");
            int totalMessagesToEnter = scan.nextInt();
            scan.nextLine(); // consume newline

            int menuChoice = 0;

            // 4. Declare boolean allEntered = false above the while loop to prevent re-entering full batches
            boolean allEntered = false;

            // Helper Message object to perform checks, generate IDs, and retrieve totals without rebuilding every iteration
            Message helper = new Message();

            // 4. Loop a numeric menu until user selects 'quit' (3)
            while (menuChoice != 3) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Select an option: ");
                
                menuChoice = scan.nextInt();
                scan.nextLine(); // consume newline

                if (menuChoice == 1) {
                    // 4. Check if messages have already been entered for this run
                    if (allEntered) {
                        System.out.println("Limit reached. All messages for this session have already been entered.");
                    } else {
                        // 5. Loop starts at 0, goes up to totalMessagesToEnter, displaying (i + 1) in the heading
                        for (int i = 0; i < totalMessagesToEnter; i++) {
                            System.out.println("\n--- Entering Message " + (i + 1) + " of " + totalMessagesToEnter + " ---");

                            // Collect Recipient
                            System.out.print("Enter recipient cell number: ");
                            String recipient = scan.nextLine();

                            String cellCheck = helper.checkRecipientCell(recipient);
                            System.out.println(cellCheck);

                            if (!cellCheck.contains("successfully captured")) {
                                System.out.println("Please try entering this message again.");
                                i--; // repeat iteration for invalid input
                                continue;
                            }

                            // Collect Message Text
                            System.out.print("Enter message text (max 250 characters): ");
                            String messageText = scan.nextLine();

                            String lengthCheck = helper.checkMessageLength(messageText);
                            System.out.println(lengthCheck);

                            if (!lengthCheck.contains("ready to send")) {
                                System.out.println("Please try entering this message again.");
                                i--; // repeat iteration for invalid input
                                continue;
                            }

                            // Generate ID and Hash
                            String generatedID = helper.generateMessageID();
                            System.out.println("Message ID generated: " + generatedID);

                            // Create the final Message object (the constructor automatically builds the hash)
                            Message activeMessage = new Message(recipient, messageText, generatedID, i);

                            // Ask send / disregard / store choice
                            System.out.println("Choose an action:");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message to send later");
                            System.out.print("Enter choice (1-3): ");
                            int actionChoice = scan.nextInt();
                            scan.nextLine();

                            String actionResult = activeMessage.sentMessage(actionChoice);
                            System.out.println(actionResult);

                            // 8. Handle option 3 by invoking storeMessage() if selected
                            if (actionChoice == 3) {
                                activeMessage.storeMessage();
                            }

                            // If sent successfully, display full details in order: Message ID, Message Hash, Recipient, Message
                            if (actionChoice == 1) {
                                System.out.println("\n====== Sent Message Details ======");
                                System.out.println("Message ID   : " + activeMessage.getMessageID());
                                System.out.println("Message Hash : " + activeMessage.getMessageHash());
                                System.out.println("Recipient    : " + activeMessage.getRecipient());
                                System.out.println("Message      : " + activeMessage.getMessageText());
                                System.out.println("==================================");
                            }
                        }
                        // 4. Set allEntered to true so the user cannot run the batch loop again
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

            // 6. Display the total number of messages sent after the while loop using helper object
            System.out.println("\nTotal messages sent this session: " + helper.returnTotalMessages());
        }
    }
}