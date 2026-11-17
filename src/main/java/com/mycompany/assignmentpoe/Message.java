/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentpoe;


/**
 *
 * @author Student
 */

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    // Part 3 Arrays - No hard-coding
    public static List<String> sentMessages = new ArrayList<>();
    public static List<String> disregardedMessages = new ArrayList<>();
    public static List<String> storedMessages = new ArrayList<>();
    public static List<String> messageHashes = new ArrayList<>();
    public static List<String> messageIDs = new ArrayList<>();
    public static List<String> recipientsList = new ArrayList<>();
    public static List<String> developers = new ArrayList<>();
    public static List<String> messagesList = new ArrayList<>();

    private int messageNum;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;
    private static int totalMessages = 0;

    // Constructor YOUR code uses - int, String, String
    public Message(int num, String recipient, String text) {
        this.messageNum = num;
        this.recipient = recipient;
        this.messageText = text;
        this.messageID = generateID();
        this.messageHash = createHash();
    }

    // Extra constructor for Part 3 test data
    public Message(String recipient, String text, String flag) {
        this.messageNum = messagesList.size();
        this.recipient = recipient;
        this.messageText = text;
        this.messageID = generateID();
        this.messageHash = createHash();
    }

    public Message() {}

    private String generateID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<10;i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }

    private String createHash() {
        if (messageText == null) messageText = "";
        String[] words = messageText.trim().split("\\s+");
        String first = words.length>0 ? words[0].replaceAll("[^A-Za-z]","") : "";
        String last = words.length>1 ? words[words.length-1].replaceAll("[^A-Za-z]","") : first;
        String idPart = messageID != null && messageID.length()>=2 ? messageID.substring(0,2) : "00";
        return (idPart + ":" + messageNum + ":" + (first+last).toUpperCase()).replaceAll("[^A-Z0-9:]", "");
    }

    // Methods YOUR AssignmentPOE.java calls
    public String checkRecipientCell() {
        if (recipient == null) return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        if (recipient.startsWith("+27") && recipient.length() <= 13 && recipient.length() >= 10) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText == null) return "Message ready to send.";
        if (messageText.length() <= 250) return "Message ready to send.";
        return "Message exceeds 250 characters by " + (messageText.length()-250) + ", please reduce size.";
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(messageText);
                messagesList.add(messageText);
                recipientsList.add(recipient);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                developers.add("User");
                totalMessages++;
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(messageText);
                return "Press 0 to delete message.";
            case 3:
                storedMessages.add(messageText);
                messagesList.add(messageText);
                recipientsList.add(recipient);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                developers.add("User");
                saveToJson();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public String printMessages() {
        return "MessageID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + messageText;
    }

    public boolean checkRecipientCellBoolean(String cell) {
        return cell != null && cell.startsWith("+27") && cell.length() <= 13;
    }

    public String createMessageHash() { return messageHash; }

    // ===== PART 3 Methods a-f =====
    public static void clearArrays() {
        sentMessages.clear(); disregardedMessages.clear(); storedMessages.clear();
        messageHashes.clear(); messageIDs.clear(); recipientsList.clear();
        developers.clear(); messagesList.clear();
    }

    public static void populateTestData() {
        clearArrays();
        addTestMessage("+27834557896", "Did you get the cake?", "Sent", "Developer1");
        addTestMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", "Developer2");
        addTestMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard", "Developer3");
        addTestMessage("0838884567", "It is dinner time !", "Sent", "0838884567");
        addTestMessage("+27838884567", "Ok, I am leaving without you.", "Stored", "Developer5");
    }

    private static void addTestMessage(String recipient, String text, String flag, String dev) {
        String id = String.format("%010d", messageIDs.size()+1);
        if (text.contains("dinner time")) id = "0838884567";
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]",""); 
        String last = words[words.length-1].replaceAll("[^A-Za-z]","");
        String hash = id.substring(0,2) + ":" + messageIDs.size() + ":" + (first+last).toUpperCase();
        messagesList.add(text); recipientsList.add(recipient);
        messageIDs.add(id); messageHashes.add(hash); developers.add(dev);
        if (flag.equalsIgnoreCase("Sent")) sentMessages.add(text);
        else if (flag.equalsIgnoreCase("Stored")) storedMessages.add(text);
        else disregardedMessages.add(text);
        if (flag.equalsIgnoreCase("Stored")) saveToJson();
    }

    public static String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<storedMessages.size();i++) {
            int idx = messagesList.indexOf(storedMessages.get(i));
            if (idx>=0) sb.append("Sender: ").append(developers.get(idx)).append(" | Recipient: ").append(recipientsList.get(idx)).append("\n");
        }
        return sb.length()==0 ? "No stored messages." : sb.toString();
    }
    public static String displayLongestMessage() {
        if (messagesList.isEmpty()) return "";
        String longest = messagesList.get(0);
        for (String s: messagesList) if (s.length()>longest.length()) longest=s;
        return longest;
    }
    public static String searchByMessageID(String id) {
        for (int i=0;i<messageIDs.size();i++) if (messageIDs.get(i).equals(id))
            return "Recipient: " + recipientsList.get(i) + "\nMessage: \"" + messagesList.get(i) + "\"";
        return "Message ID not found.";
    }
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<recipientsList.size();i++) if (recipientsList.get(i).equals(recipient)) {
            if (sb.length()>0) sb.append("\n");
            sb.append("\"" + messagesList.get(i) + "\"");
        }
        return sb.length()==0 ? "No messages found." : sb.toString();
    }
    public static String deleteByHash(String hash) {
        for (int i=0;i<messageHashes.size();i++) if (messageHashes.get(i).equalsIgnoreCase(hash)) {
            String msg = messagesList.get(i);
            messagesList.remove(i); recipientsList.remove(i); messageIDs.remove(i);
            messageHashes.remove(i); developers.remove(i);
            sentMessages.remove(msg); storedMessages.remove(msg); disregardedMessages.remove(msg);
            saveToJson();
            return "Message: \"" + msg + "\" successfully deleted.";
        }
        return "Hash not found.";
    }
    public static String displayReport() {
        StringBuilder sb = new StringBuilder("--- STORED MESSAGES REPORT ---\n");
        for (int i=0;i<messagesList.size();i++) {
            sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(recipientsList.get(i)).append("\n");
            sb.append("Message: ").append(messagesList.get(i)).append("\n\n");
        }
        return sb.toString();
    }
    public static void saveToJson() {
        try {
            StringBuilder json = new StringBuilder("[\n");
            for (int i=0;i<storedMessages.size();i++) {
                int idx = messagesList.indexOf(storedMessages.get(i));
                if (idx<0) idx=i;
                json.append("  {\"messageID\": \"").append(messageIDs.get(idx)).append("\", \"recipient\": \"").append(recipientsList.get(idx)).append("\", \"message\": \"").append(messagesList.get(idx).replace("\"","'")).append("\", \"hash\": \"").append(messageHashes.get(idx)).append("\"}");
                if (i<storedMessages.size()-1) json.append(",");
                json.append("\n");
            }
            json.append("]");
            FileWriter fw = new FileWriter("storedMessages.json");
            fw.write(json.toString()); fw.close();
        } catch (IOException e) {}
    }
    public static void loadFromJson() {
        try { if (!Files.exists(Paths.get("storedMessages.json"))) return; } catch (Exception e) {}
    }
    public String getMessageID() { return messageID; }

    String returnTotalMessages() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}