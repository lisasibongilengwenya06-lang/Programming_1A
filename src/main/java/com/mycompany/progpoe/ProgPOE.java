/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.progpoe;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Pop-up box app: registration and login (Part 1), QuickChat messaging (Part 2)
 * and stored messages (Part 3).
 *
 * @author Admin
 */
public class ProgPOE {

    public static void main(String[] args) {
        Login account = new Login();

        String name = ask("Enter your name");
        String surname = ask("Enter your surname");
        String username = ask("Enter your username");
        String password = ask("Enter a password");
        String cellPhoneNumber = ask("Enter your cell phone number");
        if (name == null || surname == null || username == null || password == null || cellPhoneNumber == null) {
            return;   // the user closed a box, so stop
        }

        String registerMessage = account.registerUser(username, password, cellPhoneNumber);
        show(registerMessage);

        if (registerMessage.startsWith("Username successfully captured")) {
            account.name = name;
            account.surname = surname;

            String loginUsername = ask("Login: enter your username");
            String loginPassword = ask("Login: enter your password");
            if (loginUsername == null || loginPassword == null) {
                return;
            }

            boolean isValid = account.loginUser(loginUsername, loginPassword, account.username, account.password);
            show(account.returnLoginStatus(isValid, account.name, account.surname));

            // Messages can only be sent after a successful login
            if (isValid) {
                Message.setSender(account.name + " " + account.surname);
                runQuickChat();
            }
        }
    }

    /** Shows the welcome message, asks how many messages to enter, then runs the menu until the user quits. */
    private static void runQuickChat() {
        show("Welcome to QuickChat.");
        Message.loadStoredMessages();

        int totalMessagesToEnter = askInt("How many messages would you like to enter?");
        if (totalMessagesToEnter == -1) {
            return;
        }

        Message helper = new Message();
        boolean allEntered = false;
        int menuChoice = 0;

        while (menuChoice != 3) {
            menuChoice = askInt("QuickChat Menu\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4) Stored Messages\n\nSelect an option:");
            if (menuChoice == -1) {
                menuChoice = 3;   // closing the box quits
            }

            if (menuChoice == 1) {
                if (allEntered) {
                    show("Limit reached. All messages for this session have already been entered.");
                } else {
                    enterMessages(helper, totalMessagesToEnter);
                    allEntered = true;
                }
            } else if (menuChoice == 2) {
                show("Coming Soon.");
            } else if (menuChoice == 4) {
                storedMessagesMenu(helper);
            } else if (menuChoice == 3) {
                show("Exiting application. Goodbye!");
            } else {
                show("Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }

        show("Total messages sent this session: " + helper.returnTotalMessages());
    }

    /** Uses a for loop to collect the set number of messages. The loop counter is the message number. */
    private static void enterMessages(Message helper, int totalMessagesToEnter) {
        for (int i = 0; i < totalMessagesToEnter; i++) {
            String heading = "Message " + (i + 1) + " of " + totalMessagesToEnter + "\n\n";

            String recipient;
            String cellCheck;
            do {
                recipient = ask(heading + "Enter recipient cell number:");
                if (recipient == null) {
                    return;
                }
                cellCheck = helper.checkRecipientCell(recipient);
                show(cellCheck);
            } while (!cellCheck.contains("successfully captured"));

            String messageText;
            String lengthCheck;
            do {
                messageText = ask(heading + "Enter message text (max 250 characters):");
                if (messageText == null) {
                    return;
                }
                lengthCheck = helper.checkMessageLength(messageText);
                show(lengthCheck);
            } while (!lengthCheck.contains("ready to send"));

            String generatedID = helper.generateMessageID();
            show("Message ID generated: " + generatedID);

            Message activeMessage = new Message(recipient, messageText, generatedID, i);

            int actionChoice = 0;
            while (actionChoice < 1 || actionChoice > 3) {
                actionChoice = askInt("Choose an action:\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                if (actionChoice == -1) {
                    actionChoice = 2;   // closing the box disregards the message
                }
            }

            show(activeMessage.sentMessage(actionChoice));

            if (actionChoice == 3) {
                activeMessage.storeMessage();
            } else if (actionChoice == 1) {
                displayMessage(activeMessage);
            }
        }
    }

    /** Shows the message details in the required order: ID, hash, recipient, message. */
    private static void displayMessage(Message message) {
        show("Sent Message Details\n\n"
                + "Message ID: " + message.getMessageID() + "\n"
                + "Message Hash: " + message.getMessageHash() + "\n"
                + "Recipient: " + message.getRecipient() + "\n"
                + "Message: " + message.getMessageText());
    }

    /** Shows the Stored Messages menu and runs the option the user picks. */
    private static void storedMessagesMenu(Message helper) {
        Message.loadStoredMessages();
        int choice = 0;
        while (choice != 8) {
            choice = askInt("Stored Messages\n"
                    + "1) Display sender and recipient of all stored messages\n"
                    + "2) Display the longest stored message\n"
                    + "3) Search for a message ID\n"
                    + "4) Search messages for a recipient\n"
                    + "5) Delete a message using its hash\n"
                    + "6) Report of all stored messages\n"
                    + "7) Report of all sent messages\n"
                    + "8) Back to main menu\n\nSelect an option:");
            if (choice == -1) {
                choice = 8;   // closing the box goes back
            }

            if (choice == 1) {
                show(helper.storedSendersAndRecipients());
            } else if (choice == 2) {
                show(helper.longestStoredMessage());
            } else if (choice == 3) {
                String id = ask("Enter the message ID:");
                if (id != null) {
                    id = id.trim();
                    show("Recipient: " + helper.recipientForMessageID(id) + "\nMessage: " + helper.searchByMessageID(id));
                }
            } else if (choice == 4) {
                String recipient = ask("Enter the recipient cell number:");
                if (recipient != null) {
                    ArrayList<String> found = helper.searchByRecipient(recipient.trim());
                    show(found.isEmpty() ? "No messages found for that recipient." : String.join("\n", found));
                }
            } else if (choice == 5) {
                String hash = ask("Enter the message hash:");
                if (hash != null) {
                    show(helper.deleteByHash(hash.trim()));
                }
            } else if (choice == 6) {
                show(helper.storedMessagesReport());
            } else if (choice == 7) {
                String report = helper.printMessages();
                show(report.isEmpty() ? "No sent messages." : report);
            } else if (choice != 8) {
                show("Invalid option. Please choose 1 to 8.");
            }
        }
    }

    /** Shows a pop-up message box. */
    private static void show(String message) {
        JOptionPane.showMessageDialog(null, message, "QuickChat", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Asks for text in a pop-up box. Returns null if the user closes the box. */
    private static String ask(String prompt) {
        return JOptionPane.showInputDialog(null, prompt, "QuickChat", JOptionPane.QUESTION_MESSAGE);
    }

    /** Keeps asking until the user types a whole number. Returns -1 if the user closes the box. */
    private static int askInt(String prompt) {
        while (true) {
            String input = ask(prompt);
            if (input == null) {
                return -1;
            }
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                show("Please enter a number.");
            }
        }
    }
}