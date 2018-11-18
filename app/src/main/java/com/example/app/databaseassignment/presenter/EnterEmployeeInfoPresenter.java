package com.example.app.databaseassignment.presenter;

import android.util.Log;

import com.couchbase.lite.*;
import com.couchbase.lite.Document;
import com.example.app.databaseassignment.PunchStatus;
import com.example.app.databaseassignment.contract.EnterEmployeeInfoContract;
import com.example.app.databaseassignment.contract.ViewEmployeeInfoContract;
import com.example.app.databaseassignment.util.DatabaseManager;
import com.example.app.databaseassignment.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EnterEmployeeInfoPresenter implements EnterEmployeeInfoContract.presenter
{
    private EnterEmployeeInfoContract.view view;
    private String employeeId;

    public EnterEmployeeInfoPresenter(EnterEmployeeInfoContract.view view, String employeeId)
    {
        this.view = view;
        this.employeeId = employeeId;
    }

    @Override
    public void getPunchStatus()
    {
        final Database database = DatabaseManager.getDatabase();

        Query query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("EmployeeId").equalTo(Expression.string(employeeId)));
        try
        {
            List<Result> results = query.execute().allResults();
            if (results.size() == 0)
            {
                return;
            }

            Result result = results.get(0);
            Dictionary dictionary = result.getDictionary(0);
            String punchInStatus = dictionary.getString("PunchInStatus");

            if (punchInStatus.equals(PunchStatus.PUNCHED_IN.getValue()))
            {
                Date punchInDate = getPunchDate(PunchStatus.PUNCHED_IN);
                if (punchInDate != null)
                {
                    view.updatePunchedIn(punchInDate.toString());
                }
            }
            else
            {
                view.updatePunchedOut();
            }
        }
        catch (CouchbaseLiteException e)
        {

        }
    }

    @Override
    public void addDateWorked(String date, Double hoursWorked, Double wageWorked)
    {
        Database database = DatabaseManager.getDatabase();

        Document doc = database.getDocument("emp." + employeeId);

        if (doc == null)
        {
            return;
        }

        MutableDocument document = doc.toMutable();

        MutableArray originalArray = document.getArray("HoursWorked").toMutable();

        MutableDictionary dictionary = new MutableDictionary();
        dictionary.setString("Date", DateUtil.parseDateForSaving(date));
        dictionary.setDouble("HoursWorked", hoursWorked);
        dictionary.setDouble("WageWorked", wageWorked);

        originalArray.addDictionary(dictionary);
        document.setArray("HoursWorked", originalArray);

        try
        {
            if (database.save(document, ConcurrencyControl.LAST_WRITE_WINS))
            {
                view.clearDetails(date);
            }
        }
        catch (CouchbaseLiteException e)
        {
            Log.e("Couchbase Error", "Error: " + e);
        }

    }

    @Override
    public void punch(PunchStatus status)
    {
        Date punchDate = new Date();
        Database database = DatabaseManager.getDatabase();

        Document doc = database.getDocument("emp." + employeeId).toMutable();

        if (doc == null)
        {
            return;
        }

        MutableDocument document = doc.toMutable();
        document.setString("PunchInStatus", status.getValue());

        document.setString(status.getDocTimeField(), punchDate.toString());

        try
        {
            if (database.save(document, ConcurrencyControl.LAST_WRITE_WINS))
            {
                if (status.equals(PunchStatus.PUNCHED_IN))
                {
                    view.updatePunchedIn(punchDate.toString());
                }
                else
                {
                    view.updatePunchedOut();
                    addDateWorked(punchDate.toString(),
                            (double) calculateHoursWorked(), 10.50);
                }
            }
        }
        catch (CouchbaseLiteException e)
        {
            Log.e("TAG", "Failed to save document...");
        }

    }

    private Date getPunchDate(PunchStatus status)
    {
        final Database database = DatabaseManager.getDatabase();

        Query query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("EmployeeId").equalTo(Expression.string(employeeId)));
        try
        {
            List<Result> results = query.execute().allResults();
            if (results.size() == 0)
            {
                return null;
            }

            Result result = results.get(0);
            Dictionary dictionary = result.getDictionary(0);
            String stringDate = dictionary.getString(status.getDocTimeField());

            try
            {
                return DateUtil.displayFormat.parse(stringDate);
            }
            catch (ParseException e)
            {
                Log.e("TAG", "Error parsing: " + e);
            }
        }
        catch (CouchbaseLiteException e)
        {
            Log.e("Couchbase", "Error: " + e);
        }
        return null;
    }

    private int calculateHoursWorked()
    {
        Date punchInDate = getPunchDate(PunchStatus.PUNCHED_IN);
        Date punchOutDate = getPunchDate(PunchStatus.PUNCHED_OUT);

        if (punchInDate != null && punchOutDate != null)
        {
            long punchInMillis = punchInDate.getTime();
            long punchOutMillis = punchOutDate.getTime();

            int totalHours = (int) (punchOutMillis - punchInMillis) / (60 * 60 * 1000);

            return totalHours;
        }
        return 0;
    }

}
