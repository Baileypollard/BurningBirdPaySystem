package com.example.app.databaseassignment.util;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.BasicAuthenticator;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Replicator;
import com.couchbase.lite.ReplicatorChange;
import com.couchbase.lite.ReplicatorChangeListener;
import com.couchbase.lite.ReplicatorConfiguration;
import com.couchbase.lite.URLEndpoint;
import com.example.app.databaseassignment.callback.LoginAttemptedCallback;

import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseManager
{
    private static DatabaseManager instance;
    private static Database database;
    private static Replicator replicator;

    private DatabaseManager(Context context, String employee_id)
    {
        DatabaseConfiguration config = new DatabaseConfiguration(context);
        try
        {
            database = new Database("employee-info-" + employee_id, config);
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }
    }

    public static void beginDatabaseReplication(String username, String password, final LoginAttemptedCallback callback)
    {
        URI url = null;
        try
        {
            url = new URI("ws://35.236.5.211:4984/comp3753");
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        ReplicatorConfiguration config = new ReplicatorConfiguration(database, new URLEndpoint(url));
        config.setReplicatorType(ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL);
        config.setContinuous(true);
        config.setAuthenticator(new BasicAuthenticator(username, password));

        replicator = new Replicator(config);
        replicator.addChangeListener(new ReplicatorChangeListener()
        {
            LoginAttemptedCallback loginCallback = callback;
            @Override
            public void changed(ReplicatorChange change)
            {
                final int UNAUTHORIZED_CODE = 10401;

                if (change.getReplicator().getStatus().getActivityLevel().equals(Replicator.ActivityLevel.IDLE))
                {
                    if (loginCallback != null)
                    {
                        loginCallback.onLoginSuccess();
                        loginCallback = null;
                    }
                    Log.e("Replication Comp Log", "Scheduler Completed");
                }
                if (change.getReplicator().getStatus().getActivityLevel().equals(Replicator.ActivityLevel.STOPPED)
                        || change.getReplicator().getStatus().getActivityLevel().equals(Replicator.ActivityLevel.OFFLINE)) {

                    Log.e("Replication Comp Log", "Scheduler Stopped");

                    if (change.getReplicator().getStatus().getError() != null
                            && change.getReplicator().getStatus().getError().getCode() == UNAUTHORIZED_CODE)
                    {
                        if (loginCallback != null)
                        {
                            loginCallback.onLoginFailed();
                            replicator.stop();

                            try
                            {
                                database.close();
                                instance = null;
                            }
                            catch (CouchbaseLiteException e)
                            {

                            }
                            loginCallback = null;
                        }
                    }
                }
            }
        });
        replicator.start();
    }

    public static void closeDatabase() throws CouchbaseLiteException
    {
        replicator.stop();
        database.close();
        instance = null;
    }

    public static DatabaseManager getSharedInstance(Context context, String employeeId)
    {
        if (instance == null)
        {
            instance = new DatabaseManager(context, employeeId);
        }
        return instance;
    }

    public static Database getDatabase()
    {
        return database;
    }

}
