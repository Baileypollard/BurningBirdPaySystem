package com.example.app.databaseassignment.contract;

import com.example.app.databaseassignment.model.Employee;

public interface ViewEmployeeInfoContract
{
    interface view
    {
        void displayEmployeeInformation(Employee e);
    }

    interface presenter
    {
        void fetchEmployeeInformation(String id);
    }
}
