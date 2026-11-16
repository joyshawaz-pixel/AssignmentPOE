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
        System.out.print("Enter Password (8 chars, capital, number, special): ");
        String password = sc.nextLine();
        System.out.print("Enter Cell Phone (+27XXXXXXXXX): ");
        String cell = sc.nextLine();

        // Check and print exact messages from rubric
        if (!login.checkUserName(username)) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
        } else {
            System.out.println("Username successfully captured.");
        }

        if (!login.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } else {
            System.out.println("Password successfully captured.");
        }

        if (!login.checkCellPhoneNumber(cell)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        System.out.println(login.registerUser(username, password, cell, firstName, lastName));

        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter Username: ");
        String loginU = sc.nextLine();
        System.out.print("Enter Password: ");
        String loginP = sc.nextLine();

        System.out.println(login.returnLoginStatus(loginU, loginP)); 
    }   
    }
    

