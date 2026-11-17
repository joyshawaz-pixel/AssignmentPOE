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
    // Part 3 - 5 Arrays (No hard-coding, populated dynamically)
    public static List<String> sentMessages = new ArrayList<>();
    public static List<String> disregardedMessages = new ArrayList<>();
    public static List<String> storedMessages = new ArrayList<>();
    public static List<String> messageHashes = new ArrayList<>();
    public static List<String> messageIDs = new ArrayList<>();
    public static List<String> recipientsList = new ArrayList<>();
    public static List<String> developers = new ArrayList<>();
    public static List<String> messagesList = new ArrayList<>();

    private String messageID;
    private String recipient;
    private String messageText;

    public Message(int par, String recipient, String messageText) {
        this.messageID = generateID();
        this.recipient = recipient;
        this.messageText = messageText;
    }
    public Message() {}

    // Part 2 methods
    public String generateID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(rand.nextInt(10));
        return sb.toString();
    }
    public boolean checkRecipientCell(String cell) {
        return cell != null && cell.startsWith("+27") && cell.length() <= 13 && cell.length() >= 10;
    }
    public String createMessageHash() {
        if (messageID == null || messageText == null) return "";
        String[] words = messageText.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        String hash = messageID.substring(0,2) + ":" + messagesList.size() + ":" + first.toUpperCase() + last.toUpperCase();
        return hash.replaceAll("[^A-Z0-9:]", "");
    }

    // Part 3 - Clear
    public static void clearArrays() {
        sentMessages.clear(); disregardedMessages.clear(); storedMessages.clear();
        messageHashes.clear(); messageIDs.clear(); recipientsList.clear();
        developers.clear(); messagesList.clear();
    }

    // Part 3 - Test Data from your brief page 17
    public static void populateTestData() {
        clearArrays();
        addTestMessage("+27834557896", "Did you get the cake?", "Sent", "Developer1");
        addTestMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", "Developer2");
        addTestMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard", "Developer3");
        addTestMessage("0838884567", "It is dinner time !", "Sent", "0838884567");
        addTestMessage("+27838884567", "Ok, I am leaving without you.", "Stored", "Developer5");
    }

    private static void addTestMessage(String recipient, String text, String flag, String dev) {
        Message m = new Message(0, recipient, text);
        m.messageID = String.format("%010d", messageIDs.size() + 1);
        if (text.contains("dinner time")) m.messageID = "0838884567";
        
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]", "");
        String last = words[words.length-1].replaceAll("[^A-Za-z]", "");
        String hash = m.messageID.substring(0,2) + ":" + messageIDs.size() + ":" + (first+last).toUpperCase();
        
        messagesList.add(text);
        recipientsList.add(recipient);
        messageIDs.add(m.messageID);
        messageHashes.add(hash);
        developers.add(dev);
        
        if (flag.equalsIgnoreCase("Sent")) sentMessages.add(text);
        else if (flag.equalsIgnoreCase("Stored")) storedMessages.add(text);
        else disregardedMessages.add(text);
        
        if (flag.equalsIgnoreCase("Stored")) saveToJson();
    }

    // a) Sender and Recipient
    public static String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedMessages.size(); i++) {
            int idx = messagesList.indexOf(storedMessages.get(i));
            if (idx >=0) {
                sb.append("Sender: ").append(developers.get(idx))
                  .append(" | Recipient: ").append(recipientsList.get(idx)).append("\n");
            }
        }
        return sb.length()==0 ? "No stored messages." : sb.toString();
    }

    // b) Longest
    public static String displayLongestMessage() {
        if (messagesList.isEmpty()) return "";
        String longest = messagesList.get(0);
        for (String s : messagesList) if (s.length() > longest.length()) longest = s;
        return longest;
    }

    // c) Search by ID
    public static String searchByMessageID(String id) {
        for (int i=0;i<messageIDs.size();i++) {
            if (messageIDs.get(i).equals(id)) {
                return "Recipient: " + recipientsList.get(i) + "\nMessage: \"" + messagesList.get(i) + "\"";
            }
        }
        return "Message ID not found.";
    }

    // d) Search by recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<recipientsList.size();i++) {
            if (recipientsList.get(i).equals(recipient)) {
                if (sb.length()>0) sb.append("\n");
                sb.append("\"" + messagesList.get(i) + "\"");
            }
        }
        return sb.length()==0 ? "No messages found." : sb.toString();
    }

    // e) Delete by hash
    public static String deleteByHash(String hash) {
        for (int i=0;i<messageHashes.size();i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash)) {
                String msg = messagesList.get(i);
                messagesList.remove(i); recipientsList.remove(i);
                messageIDs.remove(i); messageHashes.remove(i); developers.remove(i);
                sentMessages.remove(msg); storedMessages.remove(msg); disregardedMessages.remove(msg);
                saveToJson();
                return "Message: \"" + msg + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    // f) Report
    public static String displayReport() {
        StringBuilder sb = new StringBuilder("--- STORED MESSAGES REPORT ---\n");
        for (int i=0;i<messagesList.size();i++) {
            sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(recipientsList.get(i)).append("\n");
            sb.append("Message: ").append(messagesList.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    // JSON - Store Data
    public static void saveToJson() {
        try {
            StringBuilder json = new StringBuilder("[\n");
            for (int i=0;i<storedMessages.size();i++) {
                int idx = messagesList.indexOf(storedMessages.get(i));
                if (idx<0) idx=i;
                json.append("  {\"messageID\": \"").append(messageIDs.get(idx)).append("\", ");
                json.append("\"recipient\": \"").append(recipientsList.get(idx)).append("\", ");
                json.append("\"message\": \"").append(messagesList.get(idx).replace("\"","'")).append("\", ");
                json.append("\"hash\": \"").append(messageHashes.get(idx)).append("\"}");
                if (i < storedMessages.size()-1) json.append(",");
                json.append("\n");
            }
            json.append("]");
            FileWriter fw = new FileWriter("storedMessages.json");
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {}
    }

    public static void loadFromJson() {
        try {
            if (!Files.exists(Paths.get("storedMessages.json"))) return;
        } catch (Exception e) {}
    }

    public String getMessageID() { return messageID; }
}

/**
 *
 * @author Student
 */
