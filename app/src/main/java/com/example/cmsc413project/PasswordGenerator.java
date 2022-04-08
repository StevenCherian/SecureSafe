package com.example.cmsc413project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*._-+";
    private boolean azChecked;
    private boolean AZChecked;
    private boolean digitsChecked;
    private boolean specialsChecked;

    public PasswordGenerator (boolean azChecked, boolean AZChecked, boolean numbersChecked, boolean specialsChecked) {
        this.azChecked = azChecked;
        this.AZChecked = AZChecked;
        this.digitsChecked = numbersChecked;
        this.specialsChecked = specialsChecked;
    }

    public String generatePassword (int length) {

        StringBuilder password = new StringBuilder(length);
        Random rand = new Random();

        List<String> charCategories = new ArrayList<>(4);
        if (!(azChecked || AZChecked || digitsChecked || specialsChecked))
            charCategories.add(" ");

        if (azChecked) { charCategories.add(LOWER); }
        if (AZChecked) { charCategories.add(UPPER); }
        if (digitsChecked) { charCategories.add(DIGITS); }
        if (specialsChecked) { charCategories.add(SPECIALS); }

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(rand.nextInt(charCategories.size()));
            int position = rand.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }

        return new String(password);
    }
}
