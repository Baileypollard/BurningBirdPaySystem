package com.example.app.databaseassignment.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.app.databaseassignment.activity.R;
import com.example.app.databaseassignment.model.DateWorked;

import java.text.NumberFormat;

public class CellDateWorkedViewHolder extends RecyclerView.ViewHolder
{
    private TextView dateWorked;
    private TextView hoursWorked;
    private TextView wageWorked;
    private TextView totalEarned;

    private DateWorked dateWorkedObj;

    public CellDateWorkedViewHolder(@NonNull View itemView, DateWorked dateWorked)
    {
        super(itemView);

        this.dateWorkedObj = dateWorked;

        this.dateWorked = (TextView) itemView.findViewById(R.id.dateWorked_text);
        this.hoursWorked = (TextView) itemView.findViewById(R.id.hoursWorked_text);
        this.wageWorked = (TextView) itemView.findViewById(R.id.wageWorked_text);
        this.totalEarned = (TextView) itemView.findViewById(R.id.totalEarned_text);

        setDetails(dateWorkedObj);
    }

    public void setDetails(DateWorked dateWorkedObj)
    {
        dateWorked.setText(dateWorkedObj.getDateWorked());
        hoursWorked.setText(dateWorkedObj.getHoursWorked().toString());
        wageWorked.setText(dateWorkedObj.getWageWorked().toString());
        Double totalEarnedDouble = dateWorkedObj.getHoursWorked() * dateWorkedObj.getWageWorked();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(totalEarnedDouble);
        totalEarned.setText(moneyString);
    }


}
