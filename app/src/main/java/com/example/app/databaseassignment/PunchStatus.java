package com.example.app.databaseassignment;

public enum PunchStatus
{
    PUNCHED_IN("PUNCHED_IN", "PunchInDate"),
    PUNCHED_OUT("PUNCHED_OUT", "PunchOutDate");

    private String value;
    private String docTimeField;

    PunchStatus(String value, String documentTimeField)
    {
        this.docTimeField = documentTimeField;
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public String getDocTimeField()
    {
        return docTimeField;
    }
}
