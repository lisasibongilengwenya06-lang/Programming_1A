/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents one chat message and keeps the shared arrays of sent, disregarded and stored messages.
 *
 * @author Admin
 */
public class Message {

    // Details of a single message
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Message objects used for the Part 2 report and the JSON file
    private static ArrayList<Message> sentMessagesList = new ArrayList<>();
    private static ArrayList<Message> storedMessagesList = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // Final PoE arrays: the message text of each category
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();

    // Parallel arrays: the same index in each list belongs to the same message
    private static ArrayList<String> messageIDs = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> recipients = new ArrayList<>();
    private static ArrayList<String> senders = new ArrayList<>();
    private static ArrayList<String> messageTexts = new ArrayList<>();
    private static ArrayList<String> flags = new ArrayList<>();   // "Sent", "Stored" or "Disregard"

    // The logged-in user who is sending the messages
    private static String sender = "";

    /** Empty constructor so the check and utility methods can be used without a full message. */
    public Message() { }

    /** Creates a message and builds its hash from the ID, message number and text. */
    public Message(String recipient, String messageText, String messageID, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    /** Sets the logged-in user's name so it can be shown as the sender. */
    public static void setSender(String senderName) { sender = senderName; }

    public static ArrayList<String> getSentMessages() { return sentMessages; }
    public static ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<String> getStoredMessages() { return storedMessages; }
    public static ArrayList<String> getMessageIDs() { return messageIDs; }
    public static ArrayList<String> getMessageHashes() { return messageHashes; }

    /** Empties every array and counter (used by the unit tests so each test starts fresh). */
    public static void clearAllMessages() {
        sentMessagesList.clear();
        storedMessagesList.clear();
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageIDs.clear();
        messageHashes.clear();
        recipients.clear();
        senders.clear();
        messageTexts.clear();
        flags.clear();
        totalMessagesSent = 0;
        sender = "";
    }

    /** Checks that the message ID is not more than ten characters. */
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    /** Reuses the Part 1 cell number check and returns the matching message. */
    public String checkRecipientCell(String recipient) {
        Registration reg = new Registration();
        if (reg.checkCellPhoneNumber(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    /** Checks that the message is not more than 250 characters. */
    public String checkMessageLength(String messageText) {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText != null ? messageText.length() - 250 : 0;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    /** Generates a random ten-digit ID (kept as text so leading zeros stay) that has not been used yet. */
    public String generateMessageID() {
        Random rand = new Random();
        String id;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(rand.nextInt(10));
            }
            id = sb.toString();
        } while (isIDUsed(id));
        return id;
    }

    /** Returns true if the ID already belongs to a sent or stored message. */
    private boolean isIDUsed(String id) {
        for (Message m : sentMessagesList) {
            if (m.messageID.equals(id)) {
                return true;
            }
        }
        for (Message m : storedMessagesList) {
            if (m.messageID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /** Builds the hash: first two ID characters, message number, then first and last word in capitals. */
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String subID = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";

        String[] words = (messageText != null) ? messageText.trim().split("\\s+") : new String[]{};
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z0-9]", "") : "";
        String lastWord = words.length > 1 ? words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "") : firstWord;

        return subID + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    /** Handles the user's choice: 1 = send, 2 = disregard, 3 = store, and fills the matching arrays. */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessagesList.add(this);
                totalMessagesSent++;
                sentMessages.add(messageText);
                recordMessage("Sent");
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(messageText);
                recordMessage("Disregard");
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(messageText);
                recordMessage("Stored");
                return "Message successfully stored.";
            default:
                return "Invalid choice selected.";
        }
    }

    /** Adds this message's details to the parallel arrays. */
    private void recordMessage(String flag) {
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
        recipients.add(recipient);
        senders.add(sender);
        messageTexts.add(messageText);
        flags.add(flag);
    }

    /** Returns the details of every message sent while the program is running. */
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

    /** Returns the total number of messages sent. */
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Adds this message to the stored list and writes all stored messages to storedMessages.json.
     * The JSON layout follows the format described on json.org.
     * Reference: JSON.org (n.d.) Introducing JSON. Available at: https://www.json.org (Accessed: 19 October 2026).
     */
    public void storeMessage() {
        storedMessagesList.add(this);
        try (FileWriter writer = new FileWriter("storedMessages.json")) {
            writer.write("[\n");
            for (int i = 0; i < storedMessagesList.size(); i++) {
                Message m = storedMessagesList.get(i);
                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + escapeJson(m.messageID) + "\",\n");
                writer.write("    \"messageHash\": \"" + escapeJson(m.messageHash) + "\",\n");
                writer.write("    \"recipient\": \"" + escapeJson(m.recipient) + "\",\n");
                writer.write("    \"message\": \"" + escapeJson(m.messageText) + "\"\n");
                writer.write("  }" + (i < storedMessagesList.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Escapes backslashes and quotes so the text is valid inside a JSON string. */
    private static String escapeJson(String text) {
        return text == null ? "" : text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
