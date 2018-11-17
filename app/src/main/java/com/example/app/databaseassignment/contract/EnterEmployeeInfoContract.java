package com.example.app.databaseassignment.contract;

import com.example.app.databaseassignment.PunchStatus;

public interface EnterEmployeeInfoContract
{
    interface view
    {
        void clearDetails(String Date);

        void updatePunchedIn(String punchDate);

        void updatePunchedOut();
    }

    interface presenter
    {
        void addDateWorked(String id, String date, Double hoursWorked, Double wageWorked);

        void punch(String employeeId, PunchStatus status);

        void getPunchStatus(String employeeId);

    }

}
