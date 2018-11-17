package com.example.app.databaseassignment.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference
{
    private Context context;
    private SharedPreferences sharedPreferences;

    private final String KEY_EMPLOYEE_ID = "KEY_EMPLOYEE_ID";

    public SharedPreference(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
    }

    public void setEmployeeId(String id)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMPLOYEE_ID, id);
        editor.commit();
    }

    public String getEmployeeId()
    {
        return sharedPreferences.getString(KEY_EMPLOYEE_ID, "");
    }
}
