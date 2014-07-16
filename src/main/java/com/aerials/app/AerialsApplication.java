package com.aerials.app;

import android.app.Application;
import com.aerials.db.DatabaseHelper;

public class AerialsApplication extends Application {
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

