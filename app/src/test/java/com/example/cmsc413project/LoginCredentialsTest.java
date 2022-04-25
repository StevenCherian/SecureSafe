package com.example.cmsc413project;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import org.junit.jupiter.api.Test;

class LoginCredentialsTest {

    @Test
    void encrypt() {
        String password = "1234";
        assertEquals(">?@A",LoginCredentials.encrypt(password));
    }

    @Test
    void decrypt() {
        String password = ">?@A";
        assertEquals("1234",LoginCredentials.decrypt(password));
    }

    @Test
    void encryptEmptyString() {
        String password = "";
        assertEquals("",LoginCredentials.encrypt(password));
    }

    @Test
    void decryptEmptyString() {
        String password = "";
        assertEquals("",LoginCredentials.encrypt(password));
    }
}