package com.example.app.databaseassignment.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.databaseassignment.PunchStatus;
import com.example.app.databaseassignment.activity.R;
import com.example.app.databaseassignment.contract.EnterEmployeeInfoContract;
import com.example.app.databaseassignment.presenter.EnterEmployeeInfoPresenter;
import com.example.app.databaseassignment.util.DateUtil;
import com.example.app.databaseassignment.util.SharedPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterEmployeeDetailsFragment extends Fragment implements EnterEmployeeInfoContract.view
{
    private EnterEmployeeInfoContract.presenter presenter;

    private Button submitHoursButton;
    private Button punchInButton;
    private Button punchOutButton;

    private Calendar myCalendar;
    private EditText dateEditText;
    private EditText hoursWorkedEditText;
    private EditText wageWorkedEditText;

    private TextView dateAddedSuccessTextView;
    private TextView punchTextView;
    private TextView employeeIdTextView;

    private SharedPreference sharedPreference;
    private String employeeId;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        sharedPreference = new SharedPreference(getContext());
        employeeId = sharedPreference.getEmployeeId();

        presenter = new EnterEmployeeInfoPresenter(this, employeeId);

        submitHoursButton = view.findViewById(R.id.submitHours_button);
        punchInButton = view.findViewById(R.id.punchIn_button);
        punchOutButton = view.findViewById(R.id.punchOut_button);

        punchTextView = view.findViewById(R.id.punchedInOn_text);
        employeeIdTextView = view.findViewById(R.id.employeeId_text);

        dateEditText = view.findViewById(R.id.dateWorked_edit);

        hoursWorkedEditText = view.findViewById(R.id.hoursWorked_edit);
        wageWorkedEditText = view.findViewById(R.id.wageWorked_edit);

        dateAddedSuccessTextView = view.findViewById(R.id.successDateAdded_text);

        employeeIdTextView.setText(employeeId);

        submitHoursButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dateEditText.getText().length() != 0 && hoursWorkedEditText.getText().length() != 0
                        && wageWorkedEditText.getText().length() != 0)
                {
                    presenter.addDateWorked(dateEditText.getText().toString()
                            , Double.parseDouble(hoursWorkedEditText.getText().toString())
                            , Double.parseDouble(wageWorkedEditText.getText().toString()));
                }
            }
        });

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        punchInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.punch(PunchStatus.PUNCHED_IN);
            }
        });

        punchOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.punch(PunchStatus.PUNCHED_OUT);
            }
        });

        presenter.getPunchStatus();
    }

    private void updateLabel()
    {
        dateEditText.setText(DateUtil.saveFormat.format(myCalendar.getTime()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_enter_employee_info, container, false);
    }

    @Override
    public void updatePunchedIn(String punchDate)
    {
        punchOutButton.setEnabled(true);
        punchInButton.setEnabled(false);
        punchTextView.setVisibility(View.VISIBLE);
        punchTextView.setText(getString(R.string.punchedInString, punchDate));
    }

    @Override
    public void updatePunchedOut()
    {
        punchOutButton.setEnabled(false);
        punchInButton.setEnabled(true);
        punchTextView.setVisibility(View.GONE);
    }

    @Override
    public void clearDetails(String date)
    {
        dateEditText.setText("");
        hoursWorkedEditText.setText("");
        wageWorkedEditText.setText("");

        dateAddedSuccessTextView.setText(getContext().getString(R.string.success_date_added, date));
        dateAddedSuccessTextView.setVisibility(View.VISIBLE);
    }
}
