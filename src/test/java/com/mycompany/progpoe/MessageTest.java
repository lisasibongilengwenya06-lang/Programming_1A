/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoe;

import com.mycompany.progpoe.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MessageTest {
    Message helper = new Message();

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", helper.checkMessageLength("a".repeat(250)));
    }

    @Test
    public void testMessageLengthFailure() {
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.",
                helper.checkMessageLength("a".repeat(251)));
    }

    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.", helper.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                helper.checkRecipientCell("08575975889"));
    }

    @Test
    public void testHashTestCase1() {
        assertEquals("00:0:HITONIGHT",
                helper.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testHashLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};
        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i], helper.createMessageHash("0012345678", i, messages[i]));
        }
    }

    @Test
    public void testMessageIDCreated() {
        String id = helper.generateMessageID();
        assertEquals(10, id.length());
        assertTrue(helper.checkMessageID(id));
    }

    @Test
    public void testMessageIDTooLong() {
        assertFalse(helper.checkMessageID("12345678901"));
    }

    @Test
    public void testMessageSent() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678", 0);
        int before = helper.returnTotalMessages();
        assertEquals("Message successfully sent.", m.sentMessage(1));
        assertEquals(before + 1, helper.returnTotalMessages());
    }

    @Test
    public void testMessageDisregard() {
        Message m = new Message("08575975889", "Hi Keegan, did you receive the payment?", "0012345678", 1);
        assertEquals("Press 0 to delete the message.", m.sentMessage(2));
    }

    @Test
    public void testMessageStore() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678", 0);
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }
    
     @Test
    public void testPrintMessagesShowsSentMessage() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678", 0);
        m.sentMessage(1);
        String output = helper.printMessages();
        assertTrue(output.contains("00:0:HITONIGHT"));
        assertTrue(output.contains("+27718693002"));
    }

    @Test
    public void testInvalidChoice() {
        assertEquals("Invalid choice selected.", new Message().sentMessage(9));
    }
    
        @Test
    public void testArraysPopulated() {
        Message.clearAllMessages();
        new Message("+27834557896", "Did you get the cake?", "1111111111", 0).sentMessage(1);
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "2222222222", 1).sentMessage(3);
        new Message("+27834484567", "Yohoooo, I am at your gate.", "3333333333", 2).sentMessage(2);
        new Message("+27830000004", "It is dinner time!", "0838884567", 3).sentMessage(1);
        new Message("+27838884567", "Ok, I am leaving without you.", "5555555555", 4).sentMessage(3);

        assertEquals(List.of("Did you get the cake?", "It is dinner time!"), Message.getSentMessages());
        assertEquals(List.of("Yohoooo, I am at your gate."), Message.getDisregardedMessages());
        assertEquals(2, Message.getStoredMessages().size());
        assertEquals(5, Message.getMessageHashes().size());
        assertEquals(5, Message.getMessageIDs().size());
    }
    
        @Test
    public void testStoredMessagesReadFromJson() throws Exception {
        Message.clearAllMessages();
        java.io.File temp = java.io.File.createTempFile("stored", ".json");
        Message.setStorageFile(temp.getAbsolutePath());
        Message.setSender("Lisa Ngwenya");

        Message first = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "2222222222", 1);
        first.sentMessage(3);
        first.storeMessage();
        Message second = new Message("+27838884567", "Ok, I am leaving without you.", "5555555555", 4);
        second.sentMessage(3);
        second.storeMessage();

        Message.loadStoredMessages();   // reads the JSON file back into the arrays

        assertEquals(2, Message.getStoredMessages().size());
        assertEquals("Ok, I am leaving without you.", Message.getStoredMessages().get(1));
        assertTrue(new Message().storedMessagesReport().contains("Lisa Ngwenya"));

        Message.clearAllMessages();
        temp.delete();
    }
    
        @org.junit.jupiter.api.AfterEach
    public void cleanUp() {
        Message.clearAllMessages();
    }

    /** Fills the arrays with the brief's five test messages (the developer's entries). */
    private void loadBriefTestData() throws Exception {
        Message.clearAllMessages();
        java.io.File temp = java.io.File.createTempFile("stored", ".json");
        temp.deleteOnExit();
        Message.setStorageFile(temp.getAbsolutePath());
        Message.setSender("Lisa Ngwenya");
        new Message("+27834557896", "Did you get the cake?", "1111111111", 0).sentMessage(1);
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "2222222222", 1).sentMessage(3);
        new Message("+27834484567", "Yohoooo, I am at your gate.", "3333333333", 2).sentMessage(2);
        new Message("+27830000004", "It is dinner time!", "0838884567", 3).sentMessage(1);
        new Message("+27838884567", "Ok, I am leaving without you.", "5555555555", 4).sentMessage(3);
    }

    @Test
    public void testLongestMessage() throws Exception {
        loadBriefTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                new Message().longestStoredMessage());
    }

    @Test
    public void testSearchByMessageID() throws Exception {
        loadBriefTestData();
        assertEquals("It is dinner time!", new Message().searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchByRecipient() throws Exception {
        loadBriefTestData();
        assertEquals(List.of("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."),
                new Message().searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteByHash() throws Exception {
        loadBriefTestData();
        String hash = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.",
                "2222222222", 1).getMessageHash();
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                new Message().deleteByHash(hash));
        assertEquals(1, Message.getStoredMessages().size());
    }

    @Test
    public void testDisplayReport() throws Exception {
        loadBriefTestData();
        String report = new Message().printMessages();
        assertTrue(report.contains("11:0:DIDCAKE"));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("08:3:ITTIME"));
    }
}
