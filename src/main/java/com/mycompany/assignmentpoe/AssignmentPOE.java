/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignmentpoe;
import java.util.Scanner;

/**
 *
 * @author Student
 */

public class AssignmentPOE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter Username (must contain _ and <=5 chars): ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Cell Phone (+27XXXXXXXXX): ");
        String cell = sc.nextLine();

        System.out.println(login.registerUser(username, password, cell, firstName, lastName));

        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter Username: ");
        String loginU = sc.nextLine();
        System.out.print("Enter Password: ");
        String loginP = sc.nextLine();
        System.out.println(login.returnLoginStatus(loginU, loginP));
        if (!login.loginUser(loginU, loginP)) return;

        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages you wish to enter? ");
        int totalToEnter = Integer.parseInt(sc.nextLine());
        int entered = 0;

        Message dummy = new Message(0, "+27000000000", "dummy");

        while (true) {
            System.out.println("\nChoose: 1) Send Messages 2) Show recently sent messages 3) Quit");
            System.out.print("Choice: ");
            String menu = sc.nextLine();

            if (menu.equals("1")) {
                if (entered >= totalToEnter) {
                    System.out.println("You have reached the limit of " + totalToEnter + " messages.");
                    continue;
                }
                System.out.print("Enter Recipient (+27...): ");
                String recipient = sc.nextLine();
                System.out.print("Enter Message (<=250 chars): ");
                String text = sc.nextLine();

                Message msg = new Message(entered, recipient, text);
                
                System.out.println(msg.checkRecipientCell());
                System.out.println(msg.checkMessageLength());
                
                if (!msg.checkRecipientCell().equals("Cell phone number successfully captured.")) continue;
                if (!msg.checkMessageLength().equals("Message ready to send.")) continue;

                System.out.println("Choose: 1) Send Message 2) Disregard Message 3) Store Message");
                int sendChoice = Integer.parseInt(sc.nextLine());
                System.out.println(msg.SentMessage(sendChoice));
                
                if (sendChoice == 1) {
                    System.out.println("\n" + msg.printMessages());
                    entered++;
                }
            } else if (menu.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (menu.equals("3")) {
                System.out.println("Total messages sent: " + dummy.returnTotalMessages());
                System.out.println("Exiting...");
                break;
            }
        }
    }
}