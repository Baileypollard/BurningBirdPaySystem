package com.example.app.databaseassignment.callback;

public interface LoginAttemptedCallback
{
    void onLoginFailed();

    void onLoginSuccess(String id);
}
