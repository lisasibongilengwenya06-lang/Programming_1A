/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

/**
 *
 * @author Admin
 */
public class Message {
    // fields: messageID, messageNumber, recipient, messageText, messageHash
    // static or shared: list of sent messages, total sent counter

    public boolean checkMessageID(String messageID) {
        // TODO: not more than 10 characters
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell(String recipient) {
        // TODO: reuse your Part 1 regex; return the success or failure message
        Registration reg = new Registration();
        boolean isValid = reg.checkCellPhoneNumber(recipient);

        if (isValid) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        // TODO: first 2 chars of ID + ":" + message number + ":" + FIRSTWORDLASTWORD (caps)
        String subID = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";

        String[] words = (messageText != null) ? messageText.trim().split("\\s+") : new String[]{};
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z0-9]", "") : "";
        String lastWord = words.length > 1 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "") : firstWord;

        return subID + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
        // TODO: 1 = send, 2 = disregard, 3 = store
        switch (choice) {
            case 1:
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
        // TODO: all messages sent this run
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public int returnTotalMessages() {
        // TODO
        return 0;
    }

    public void storeMessage() {
        // TODO: JSON (research needed)
        try {
            // Code to write message details into a JSON file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
