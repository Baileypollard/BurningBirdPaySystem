package com.example.app.databaseassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.databaseassignment.callback.LoginAttemptedCallback;
import com.example.app.databaseassignment.util.DatabaseManager;

public class LoginActivity extends AppCompatActivity
{
    private Button buttonSignIn;
    private EditText employeeIdEditText;
    private EditText employeePasswordEditText;
    private TextView loginErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = (Button) findViewById(R.id.button_signin);
        employeeIdEditText = (EditText) findViewById(R.id.employeeId_edit);
        employeePasswordEditText = (EditText) findViewById(R.id.employeePassword_edit);
        loginErrorTextView = (TextView) findViewById(R.id.login_error_text);
    }

    private LoginAttemptedCallback loginAttemptedCallback = new LoginAttemptedCallback()
    {
        @Override
        public void onLoginFailed()
        {
            //Display the error
            loginErrorTextView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoginSuccess()
        {
            //Start the main activity
            loginErrorTextView.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    public void onClickSignIn(View view)
    {
        String id = employeeIdEditText.getText().toString().trim();
        String password =  employeePasswordEditText.getText().toString().trim();

        DatabaseManager.getSharedInstance(getApplicationContext(), id);
        DatabaseManager.beginDatabaseReplication(id, password, loginAttemptedCallback);
    }

    public void onClickCreateUser(View view)
    {
        //do nothing for now
    }

}
