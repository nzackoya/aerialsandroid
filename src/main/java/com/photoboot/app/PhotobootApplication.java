package com.photoboot.app;

import android.app.Application;
import com.photoboot.db.DatabaseHelper;
import com.photoboot.db.DatabaseManager;

/**
 * Created by Nerses on 03.05.2014.
 */
public class PhotobootApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseHelper.release();
    }
}

