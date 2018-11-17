package com.example.app.databaseassignment.model;
import java.util.ArrayList;

public class Employee
{
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private ArrayList<DateWorked> datesWorked;

    public Employee(String id, String firstName, String middleName, String lastName, ArrayList<DateWorked> datesWorked)
    {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.datesWorked = datesWorked;
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public ArrayList<DateWorked> getDatesWorked()
    {
        return datesWorked;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

}
