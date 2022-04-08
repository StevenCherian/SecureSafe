package com.example.cmsc413project;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditCredentialsActivity extends AppCompatActivity {

    private UserPreferencesManager manager;
    private int credentialsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials);
        manager = new UserPreferencesManager(this);

        //Gets the selected credentials by ID.
        credentialsID = getIntent().getIntExtra("credentialsID", -1);
        LoginCredentials lc = manager.getLoginCredentialsByID(credentialsID);

        //If there is any problems getting the selected credentials, user is notified.
        if(lc.id == -1) {
            Toast t = new Toast(this);
            t.setDuration(Toast.LENGTH_SHORT);
            t.setText("Something went wrong");
            t.show();
            closeNewCredentialsPage();
        }

        //Functionality to close the edit page
        Button closeAddCredentials = findViewById(R.id.closeEditCredentials);
        closeAddCredentials.setOnClickListener(view -> closeNewCredentialsPage());

        //Populates account field with credentials account data of selected credential
        EditText account = findViewById(R.id.editAccountInput);
        account.setText(lc.appName);

        //Populates email field with credentials email data of selected credential
        EditText email = findViewById(R.id.editUsernameEmailInput);
        email.setText(lc.email);

        //Decrypts the password from the login credentials arraylist and populates that into the field
        EditText password = findViewById(R.id.editPasswordInput);
        password.setText(LoginCredentials.decrypt(lc.password));

        //Functionality for the apply changes button.
        Button saveCredentials = findViewById(R.id.editCredentialsButton);
        saveCredentials.setOnClickListener(view -> {
            //Makes sure no fields are empty
            if(TextUtils.isEmpty(account.getText())) { account.setError("Account is required."); }
            else if(TextUtils.isEmpty(email.getText())) { email.setError("Username or email is required."); }
            else if(TextUtils.isEmpty(password.getText().toString().replace(" ", ""))) { password.setError("Password is required."); }
            else {
                //Applies changes with update login credential method call and closes edit page.
                updateLoginCredential(account.getText().toString(), email.getText().toString(), password.getText().toString());
                closeNewCredentialsPage();
            }
        });
    }

    public void updateLoginCredential(String account, String email, String password) {
        if(credentialsID != -1)
            //Passes all credentials data to shared preferences for it to be updated in the shared preference file.
            manager.updateLoginCredentials(credentialsID, account, email, password);

        //If the ID of the credentials being updated is -1, the user is notified that there was an error
        else {
            Toast t = new Toast(this);
            t.setDuration(Toast.LENGTH_SHORT);
            t.setText("Something went wrong");
            t.show();
        }
    }

    private void closeNewCredentialsPage() {
        Intent newMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(newMainActivityPage);
    }
}