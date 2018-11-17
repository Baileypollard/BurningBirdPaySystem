package com.example.app.databaseassignment.model;

public class DateWorked
{
    private String dateWorked;
    private Double hoursWorked;
    private Double wageWorked;


    public DateWorked(String dateWorked, Double hoursWorked, Double wageWorked)
    {
        this.dateWorked = dateWorked;
        this.hoursWorked = hoursWorked;
        this.wageWorked = wageWorked;
    }

    public String getDateWorked()
    {
        return dateWorked;
    }

    public Double getHoursWorked()
    {
        return hoursWorked;
    }

    public Double getWageWorked()
    {
        return wageWorked;
    }
}
