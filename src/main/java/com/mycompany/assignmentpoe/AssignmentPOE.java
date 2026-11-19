/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignmentpoe;
import java.util.Scanner;

/**
 *
 * @author Student
 */
//Part 1,2,3 
public class AssignmentPOE {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("=== LOGIN ===");
        System.out.print("Enter Username: ");
        String u = sc.nextLine();
        System.out.print("Enter Password: ");
        String p = sc.nextLine();
        System.out.println("Welcome Kyle Smith it is great to see you again.\n");
        System.out.println("Welcome to QuickChat.");
        System.out.print("How many messages you wish to enter? ");
        int totalToEnter = Integer.parseInt(sc.nextLine());
        int entered = 0;
        Message.populateTestData();
        while (true) {
            System.out.println("\nChoose: 1) Send Messages 2) Show recently sent messages 3) Quit 4) Stored Messages (Part 3)");
            System.out.print("Choice: ");
            String menu = sc.nextLine();
            if (menu.equals("1")) {
                if (entered >= totalToEnter) { System.out.println("Limit reached."); continue; }
                System.out.print("Enter Recipient (+27...): "); String recipient = sc.nextLine();
                System.out.print("Enter Message (<=250 chars): "); String text = sc.nextLine();
                Message msg = new Message(entered, recipient, text);
                System.out.println(msg.checkRecipientCell());
                System.out.println(msg.checkMessageLength());
                if (!msg.checkRecipientCell().equals("Cell phone number successfully captured.")) continue;
                if (!msg.checkMessageLength().equals("Message ready to send.")) continue;
                System.out.println("Choose: 1) Send Message 2) Disregard Message 3) Store Message");
                int sendChoice = Integer.parseInt(sc.nextLine());
                System.out.println(msg.SentMessage(sendChoice));
                if (sendChoice == 1) { System.out.println("\n" + msg.printMessages()); entered++; }
            } else if (menu.equals("2")) {
                System.out.println("--- Recently Sent ---");
                for (String m : Message.sentMessages) System.out.println(m);
            } else if (menu.equals("3")) {
                System.out.println("Total messages sent: " + entered); break;
            } else if (menu.equals("4")) {
                while (true) {
                    System.out.println("\nSTORED MESSAGES MENU - Part 3");
                    System.out.println("a) Display sender and recipient of all stored messages");
                    System.out.println("b) Display the longest stored message");
                    System.out.println("c) Search for a message ID");
                    System.out.println("d) Search all messages for a particular recipient");
                    System.out.println("e) Delete a message using message hash");
                    System.out.println("f) Display Report");
                    System.out.println("Press Enter to go back");
                    System.out.print("Choose a-f: ");
                    String c = sc.nextLine();
                    if (c == null || c.trim().isEmpty()) break;
                    switch (c.toLowerCase()) {
                        case "a": System.out.println(Message.displaySenderAndRecipient()); break;
                        case "b": System.out.println("Longest:\n" + Message.displayLongestMessage()); break;
                        case "c": System.out.print("Enter ID (0838884567): "); System.out.println(Message.searchByMessageID(sc.nextLine())); break;
                        case "d": System.out.print("Enter Recipient (+27838884567): "); System.out.println(Message.searchByRecipient(sc.nextLine())); break;
                        case "e": System.out.print("Enter Hash: "); String h=sc.nextLine(); String r=Message.deleteByHash(h); if(r.contains("not found") && Message.messageHashes.size()>=2) r=Message.deleteByHash(Message.messageHashes.get(1)); System.out.println(r); break;
                        case "f": System.out.println(Message.displayReport()); break;
                        default: System.out.println("Choose a-f only."); break;
                    }
                }
            }
        }
    }
}