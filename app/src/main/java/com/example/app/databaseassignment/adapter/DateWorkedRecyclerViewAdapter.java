package com.example.app.databaseassignment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.databaseassignment.activity.R;
import com.example.app.databaseassignment.model.DateWorked;
import com.example.app.databaseassignment.viewholder.CellDateWorkedViewHolder;

import java.util.ArrayList;

public class DateWorkedRecyclerViewAdapter extends RecyclerView.Adapter<CellDateWorkedViewHolder>
{
    private ArrayList<DateWorked> datesWorkedList;
    private Context context;

    public DateWorkedRecyclerViewAdapter(Context context)
    {
        this.context = context;
        this.datesWorkedList = new ArrayList<>();
    }

    public void setDatesWorked(ArrayList<DateWorked> datesWorked)
    {
        this.datesWorkedList = datesWorked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CellDateWorkedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_date_worked, viewGroup, false);
        return new CellDateWorkedViewHolder(view, datesWorkedList.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull CellDateWorkedViewHolder cellDateWorkedViewHolder, int i)
    {
        DateWorked dateWorked = datesWorkedList.get(i);
        cellDateWorkedViewHolder.setDetails(dateWorked);
    }

    @Override
    public int getItemCount()
    {
        return datesWorkedList.size();
    }
}
