/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentpoe;

/**
 *
 * @author Student
 */
public class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasCapital && hasNumber && hasSpecial;
    }
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("^\\+27[0-9]{9}$");
    }
    public String registerUser(String username, String password, String cellNumber, String firstName, String lastName) {
        this.firstName = firstName; this.lastName = lastName;
        if (!checkUserName(username)) return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        if (!checkPasswordComplexity(password)) return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber(cellNumber)) return "Cell phone number incorrectly formatted or does not contain international code.";
        this.registeredUsername = username; this.registeredPassword = password;
        return "User registered successfully.";
    }
    public boolean loginUser(String username, String password) {
        return username.equals(registeredUsername) && password.equals(registeredPassword);
    }
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        else return "Username or password incorrect, please try again.";
    }
}
     