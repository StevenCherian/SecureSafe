package com.example.cmsc413project;
//imports
import androidx.annotation.NonNull;

public class LoginCredentials {
    int id;
    String appName;
    String email;
    String password;

    //constructor for all variables
    public LoginCredentials(int id, String appName, String email, String password) {
        this.id = id;
        this.appName = appName;
        this.email = email;
        this.password = encrypt(password);      //password is encrypted before storing to ensure more security
    }

    /*
        this method encrypts the password for the account when the add credentials activity happens
        this method also re-encrypts the password if the user edits the password later on
        the password is never stored un-encrypted
     */

    public static String encrypt(String password){
        String encryptedPassword = "";
        try {
            char[] result = new char[password.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = (char) (password.charAt(i) + 13);   //encryption shifts stored password characters by 13
            }
            encryptedPassword = new String(result);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return (encryptedPassword);     //store encrypted password
    }

    /*
        this method decrypts the password for the account when the view/edit credentials activity happens
        the password is never stored un-encrypted, rather it is decrypted from the stored encrypted version whenever the view/edit button is pressed
     */
    public static String decrypt(String password){
        String decryptedPassword = "";
        try {
            char[] result = new char[password.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = (char) (password.charAt(i) - 13);       //reversing encrypt method encryption
            }
            decryptedPassword = new String(result);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return decryptedPassword;       //only show user plaintext password when requested
    }

    @NonNull
    @Override
    public String toString() {
        return appName;
    }

    @Override
    public boolean equals(Object c){
        return c.toString().equals(this.toString());
    }
}
