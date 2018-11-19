package com.example.app.databaseassignment.presenter;

import android.util.Log;

import com.couchbase.lite.*;

import com.example.app.databaseassignment.contract.ViewEmployeeInfoContract;
import com.example.app.databaseassignment.model.*;
import com.example.app.databaseassignment.util.DatabaseManager;

import java.util.ArrayList;

public class ViewEmployeeInfoPresenter implements ViewEmployeeInfoContract.presenter
{
    private ViewEmployeeInfoContract.view view;

    public ViewEmployeeInfoPresenter(ViewEmployeeInfoContract.view view)
    {
        this.view = view;
    }

    @Override
    public void fetchEmployeeInformation(String id)
    {
        final Database database = DatabaseManager.getDatabase();

        Query query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("EmployeeId").equalTo(Expression.string(id)));

        query.addChangeListener(new QueryChangeListener()
        {
            @Override
            public void changed(QueryChange change)
            {
                ResultSet rows = change.getResults();

                Dictionary dictionary = rows.allResults().get(0).getDictionary(0);

                String employeeId = dictionary.getString("EmployeeId");
                String firstName = dictionary.getString("FirstName");
                String middleName = dictionary.getString("MiddleName");
                String lastName = dictionary.getString("LastName");

                Array array = dictionary.getArray("HoursWorked");
                ArrayList<DateWorked> dateWorkedList = new ArrayList<>();
                for (int i = 0; i < array.count(); i++)
                {
                    Double hoursWorked = array.getDictionary(i).getDouble("HoursWorked");
                    Double wageWorked = array.getDictionary(i).getDouble("WageWorked");
                    String date = array.getDictionary(i).getString("Date");

                    dateWorkedList.add(new DateWorked(date, hoursWorked, wageWorked));
                }

                Employee employee = new Employee(employeeId, firstName, middleName, lastName, dateWorkedList);
                view.displayEmployeeInformation(employee);
            }
        });

        try
        {
            query.execute();
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }

    }
}
