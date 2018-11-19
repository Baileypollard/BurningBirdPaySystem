package com.example.app.databaseassignment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.databaseassignment.activity.R;
import com.example.app.databaseassignment.adapter.DateWorkedRecyclerViewAdapter;
import com.example.app.databaseassignment.contract.ViewEmployeeInfoContract;
import com.example.app.databaseassignment.model.Employee;
import com.example.app.databaseassignment.presenter.ViewEmployeeInfoPresenter;
import com.example.app.databaseassignment.util.SharedPreference;

public class ViewEmployeeDetailsFragment extends Fragment implements ViewEmployeeInfoContract.view
{
    private ViewEmployeeInfoContract.presenter presenter;
    private TextView firstName;
    private TextView lastName;
    private TextView employeeIdTextView;
    private TextView middleName;

    private RecyclerView datesWorkedRecyclerView;
    private DateWorkedRecyclerViewAdapter adapter;

    private String employeeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_view_employee_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        presenter = new ViewEmployeeInfoPresenter(this);
        SharedPreference sharedPreference = new SharedPreference(getContext());

        firstName = (TextView) view.findViewById(R.id.employeeFirstName_text);
        lastName = (TextView) view.findViewById(R.id.employeeLastName_text);
        middleName = (TextView) view.findViewById(R.id.employeeMiddleName_text);
        employeeIdTextView = (TextView) view.findViewById(R.id.employeeId_text);

        datesWorkedRecyclerView = (RecyclerView) view.findViewById(R.id.dateWorked_recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        datesWorkedRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new DateWorkedRecyclerViewAdapter(getContext());
        datesWorkedRecyclerView.setAdapter(adapter);

        employeeId = sharedPreference.getEmployeeId();
        presenter.fetchEmployeeInformation(employeeId);
    }

    @Override
    public void displayEmployeeInformation(Employee e)
    {
        firstName.setText(e.getFirstName());
        middleName.setText(e.getMiddleName());
        lastName.setText(e.getLastName());
        employeeIdTextView.setText(e.getId());

        adapter.setDatesWorked(e.getDatesWorked());
    }
}
