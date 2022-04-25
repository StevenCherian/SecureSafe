package com.example.cmsc413project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorTest {

    //Since passwords created are random, I checked to see if the generated password was the correct length
    @Test
    void correctPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator(true, true,true,true);
        int passwordLength = 8;
        String password = passwordGenerator.generatePassword(passwordLength);
        assertEquals(8, password.length());
    }

    @Test
    void emptyPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator(false, false,false,false);
        int passwordLength = 0;
        String password = passwordGenerator.generatePassword(passwordLength);
        assertEquals(0, password.length());
    }
}
