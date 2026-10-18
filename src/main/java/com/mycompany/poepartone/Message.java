/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class Message {
    // Instance fields for one message[cite: 10]
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Shared static storage[cite: 10]
    private static ArrayList<Message> sentMessagesList = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // Default no-argument constructor
    // Allows calling validation and utility methods before creating a full object instance
    public Message() { }

    // Parameterized constructor taking recipient, messageText, messageID, and messageNumber[cite: 11]
    public Message(String recipient, String messageText, String messageID, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    // Getters[cite: 11]
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    // --- Validation Methods ---
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell(String recipient) {
        Registration reg = new Registration();
        boolean isValid = reg.checkCellPhoneNumber(recipient);
        
        if (isValid) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength(String messageText) {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText != null ? messageText.length() - 250 : 0;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // --- Utility Methods ---
    public String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String subID = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";
        
        String[] words = (messageText != null) ? messageText.trim().split("\\s+") : new String[]{};
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "") : "";
        String lastWord = words.length > 1 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "") : firstWord;
        
        return subID + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessagesList.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice selected.";
        }
    }

    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessagesList) {
            sb.append("Message ID   : ").append(m.getMessageID()).append("\n")
              .append("Message Hash : ").append(m.getMessageHash()).append("\n")
              .append("Recipient    : ").append(m.getRecipient()).append("\n")
              .append("Message      : ").append(m.getMessageText()).append("\n\n");
        }
        return sb.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        try {
            // JSON file storage implementation research placeholder[cite: 3, 4]
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
