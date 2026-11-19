/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    // Sent message objects, used for the Part 2 report and the sent total
    private static ArrayList<Message> sentMessagesList = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // Final PoE arrays: the message text of each category
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();

    // Parallel arrays for every message: the same index belongs to the same message
    private static ArrayList<String> messageIDs = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> recipients = new ArrayList<>();
    private static ArrayList<String> senders = new ArrayList<>();
    private static ArrayList<String> messageTexts = new ArrayList<>();
    private static ArrayList<String> flags = new ArrayList<>();   // "Sent", "Stored" or "Disregard"

    // The logged-in user who is sending the messages, and the file stored messages are saved in
    private static String sender = "";
    private static String storageFile = "storedMessages.json";

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

    /** Changes the JSON file used for stored messages (the unit tests use a temporary file). */
    public static void setStorageFile(String fileName) { storageFile = fileName; }

    public static ArrayList<String> getSentMessages() { return sentMessages; }
    public static ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<String> getStoredMessages() { return storedMessages; }
    public static ArrayList<String> getMessageIDs() { return messageIDs; }
    public static ArrayList<String> getMessageHashes() { return messageHashes; }

    /** Empties every array and counter so each unit test starts fresh. */
    public static void clearAllMessages() {
        sentMessagesList.clear();
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
        storageFile = "storedMessages.json";
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

    /** Generates a random ten-digit ID (kept as text so leading zeros stay) that is not in the ID array yet. */
    public String generateMessageID() {
        Random rand = new Random();
        String id;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(rand.nextInt(10));
            }
            id = sb.toString();
        } while (messageIDs.contains(id));
        return id;
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
        addRow(messageID, messageHash, sender, recipient, messageText, flag);
    }

    /** Adds one row to every parallel array. */
    private static void addRow(String id, String hash, String from, String to, String text, String flag) {
        messageIDs.add(id);
        messageHashes.add(hash);
        senders.add(from);
        recipients.add(to);
        messageTexts.add(text);
        flags.add(flag);
    }

    /** Removes one row from every parallel array. */
    private static void removeRow(int index) {
        messageIDs.remove(index);
        messageHashes.remove(index);
        senders.remove(index);
        recipients.remove(index);
        messageTexts.remove(index);
        flags.remove(index);
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

    /** Saves every stored message to the JSON file. Call it after sentMessage(3). */
    public void storeMessage() {
        saveStoredMessages();
    }

    /**
     * Writes every stored message to the JSON file.
     * The JSON layout follows the format described on json.org.
     * Reference: JSON.org (n.d.) Introducing JSON. Available at: https://www.json.org (Accessed: 18 November 2026).
     */
    private static void saveStoredMessages() {
        try (FileWriter writer = new FileWriter(storageFile)) {
            writer.write("[\n");
            boolean firstObject = true;
            for (int i = 0; i < flags.size(); i++) {
                if (flags.get(i).equals("Stored")) {
                    if (!firstObject) {
                        writer.write(",\n");
                    }
                    firstObject = false;
                    writer.write("  {\n");
                    writer.write("    \"messageID\": \"" + escapeJson(messageIDs.get(i)) + "\",\n");
                    writer.write("    \"messageHash\": \"" + escapeJson(messageHashes.get(i)) + "\",\n");
                    writer.write("    \"sender\": \"" + escapeJson(senders.get(i)) + "\",\n");
                    writer.write("    \"recipient\": \"" + escapeJson(recipients.get(i)) + "\",\n");
                    writer.write("    \"message\": \"" + escapeJson(messageTexts.get(i)) + "\"\n");
                    writer.write("  }");
                }
            }
            writer.write("\n]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the JSON file back into the stored message arrays, one line at a time.
     * It first removes the stored rows already in memory, so calling it again never creates duplicates.
     * References: JSON.org (n.d.) Introducing JSON. Available at: https://www.json.org (Accessed: 18 November 2026).
     *             Oracle (n.d.) BufferedReader, Java SE documentation. Available at:
     *             https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/BufferedReader.html (Accessed: 18 November 2026).
     */
    public static void loadStoredMessages() {
        removeStoredRows();
        File file = new File(storageFile);
        if (!file.exists()) {
            return;
        }

        String id = "";
        String hash = "";
        String fromUser = "";
        String toUser = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"messageID\"")) {
                    id = readJsonValue(line);
                } else if (line.startsWith("\"messageHash\"")) {
                    hash = readJsonValue(line);
                } else if (line.startsWith("\"sender\"")) {
                    fromUser = readJsonValue(line);
                } else if (line.startsWith("\"recipient\"")) {
                    toUser = readJsonValue(line);
                } else if (line.startsWith("\"message\"")) {
                    // "message" is the last field of each object, so the row is complete here
                    String text = readJsonValue(line);
                    addRow(id, hash, fromUser.isEmpty() ? "Unknown" : fromUser, toUser, text, "Stored");
                    storedMessages.add(text);
                    id = "";
                    hash = "";
                    fromUser = "";
                    toUser = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Removes every stored row from the arrays (the file is read again afterwards). */
    private static void removeStoredRows() {
        for (int i = flags.size() - 1; i >= 0; i--) {
            if (flags.get(i).equals("Stored")) {
                removeRow(i);
            }
        }
        storedMessages.clear();
    }

    /** Returns the text between the quotes after the colon on one JSON line. */
    private static String readJsonValue(String line) {
        int start = line.indexOf(": \"") + 3;
        int end = line.lastIndexOf('"');
        if (start < 3 || end < start) {
            return "";
        }
        return unescapeJson(line.substring(start, end));
    }

    /** Escapes backslashes and quotes so the text is valid inside a JSON string. */
    private static String escapeJson(String text) {
        return text == null ? "" : text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /** Reverses escapeJson when reading the file back in. */
    private static String unescapeJson(String text) {
        return text.replace("\\\"", "\"").replace("\\\\", "\\");
    }

    /** Returns the full details of every stored message. */
    public String storedMessagesReport() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).equals("Stored")) {
                sb.append("Message ID   : ").append(messageIDs.get(i)).append("\n")
                  .append("Message Hash : ").append(messageHashes.get(i)).append("\n")
                  .append("Sender       : ").append(senders.get(i)).append("\n")
                  .append("Recipient    : ").append(recipients.get(i)).append("\n")
                  .append("Message      : ").append(messageTexts.get(i)).append("\n\n");
            }
        }
        if (sb.length() == 0) {
            return "No stored messages.";
        }
        return sb.toString();
    }
    
    /** Returns the sender and recipient of every stored message. */
    public String storedSendersAndRecipients() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).equals("Stored")) {
                sb.append("Sender: ").append(senders.get(i))
                  .append(" | Recipient: ").append(recipients.get(i)).append("\n");
            }
        }
        return sb.length() == 0 ? "No stored messages." : sb.toString();
    }

    /** Returns the longest stored message by comparing the length of each one. */
    public String longestStoredMessage() {
        String longest = "";
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).equals("Stored") && messageTexts.get(i).length() > longest.length()) {
                longest = messageTexts.get(i);
            }
        }
        return longest.isEmpty() ? "No stored messages." : longest;
    }

    /** Searches the message ID array and returns the matching message text. */
    public String searchByMessageID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : messageTexts.get(index);
    }

    /** Searches the message ID array and returns the matching recipient. */
    public String recipientForMessageID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : recipients.get(index);
    }

    /** Returns every sent or stored message for one recipient. */
    public ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            boolean notDisregarded = !flags.get(i).equals("Disregard");
            if (recipients.get(i).equals(recipient) && notDisregarded) {
                found.add(messageTexts.get(i));
            }
        }
        return found;
    }

    /** Deletes a stored message using its hash and updates the JSON file. */
    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash) && flags.get(i).equals("Stored")) {
                String text = messageTexts.get(i);
                removeRow(i);
                storedMessages.remove(text);
                saveStoredMessages();
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "No stored message found with that hash.";
    }
}

