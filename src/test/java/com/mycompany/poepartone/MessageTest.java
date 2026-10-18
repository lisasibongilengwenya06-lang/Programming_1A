/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepartone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
