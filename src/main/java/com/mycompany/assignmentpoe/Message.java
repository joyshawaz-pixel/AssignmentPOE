/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentpoe;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author Student
 */
public class Message { 
    private int messageNum;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;
    
    // No arrays: we use String concatenation to store history
    private static int totalMessagesSent = 0;
    private static String allMessagesHistory = "";

    public Message(int messageNum, String recipient, String messageText) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.messageText = messageText;
        long randomID = (long)(Math.random() * 9000000000L) + 1000000000L;
        this.messageID = String.valueOf(randomID);
        this.messageHash = createMessageHash();
    }
    
    // For testing with fixed ID like 00:0:HITONIGHT
    public Message(int messageNum, String recipient, String messageText, String fixedID) {
        this.messageNum = messageNum;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = fixedID;
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Rubric says String: checkRecipientCell() - returns exact messages
    public String checkRecipientCell() {
        if (recipient == null) return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        if (recipient.length() != 12) return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        if (!recipient.startsWith("+27")) return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }
    
    // No arrays - no split()
    public String createMessageHash() {
        String trimmed = messageText.trim();
        if (trimmed.endsWith("?") || trimmed.endsWith(".") || trimmed.endsWith("!")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        int firstSpace = trimmed.indexOf(' ');
        String firstWord;
        if (firstSpace == -1) firstWord = trimmed;
        else firstWord = trimmed.substring(0, firstSpace);
        
        int lastSpace = trimmed.lastIndexOf(' ');
        String lastWord;
        if (lastSpace == -1) lastWord = trimmed;
        else lastWord = trimmed.substring(lastSpace + 1);
        
        // Clean punctuation from words
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        
        String prefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (prefix + ":" + messageNum + ":" + firstWord + lastWord).toUpperCase();
    }
    
    // Check message length
    public String checkMessageLength() {
        if (messageText.length() <= 250) return "Message ready to send.";
        else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
        }
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            allMessagesHistory = allMessagesHistory + "\n" + printMessages();
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return storeMessage();
        }
        return "Invalid choice.";
    }

    public String printMessages() {
        return "MessageID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + messageText;
    }
    
    // Returns all messages sent while program running - no arrays
    public String printAllMessages() {
        if (allMessagesHistory.isEmpty()) return "No messages sent.";
        return allMessagesHistory;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    // JSON storage - research requirement
    public String storeMessage() {
        try {
            String safeMessage = messageText.replace("\"", "'");
            String json = "{\"messageID\":\"" + messageID + "\",\"messageHash\":\"" + messageHash + "\",\"recipient\":\"" + recipient + "\",\"message\":\"" + safeMessage + "\"}";
            FileWriter fw = new FileWriter("storedMessages.json", true);
            fw.write(json + System.lineSeparator());
            fw.close();
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Failed to store message.";
        }
    }
    
    // Getters for testing
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}

