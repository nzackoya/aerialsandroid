package com.aerials.db;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.aerials.domain.Category;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.aerials.domain.Wave;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "aerials.db";

    private static final int DATABASE_VERSION = 8;

    private static volatile DatabaseHelper instance;

    private static Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Wave.class);
            TableUtils.createTable(connectionSource, Category.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
        Category category = new Category();
        category.setName("RSS");
        try {
            DatabaseHelper.getHelper().getDao(Category.class).create(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Wave wave1 = new Wave();
        wave1.setUrl("http://www.vesti.ru/vesti.rss", context);
        wave1.setTitle("Vesti");
        wave1.setCategory(category);

        Wave wave2 = new Wave();
        wave2.setUrl("http://www.kommersant.ru/RSS/main.xml", context);
        wave2.setTitle("Kommersant");
        wave2.setCategory(category);

        try {
            DatabaseHelper.getHelper().getDao(Wave.class).create(wave1);
            DatabaseHelper.getHelper().getDao(Wave.class).create(wave2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Wave.class, true);
            context.deleteDatabase(DATABASE_NAME);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public static void init(Context context){
        if(instance == null)
            instance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void release(){
        if(instance != null)
            OpenHelperManager.releaseHelper();
    }

    public static DatabaseHelper getHelper(){
        return instance;
    }

    @Override
    public void close() {
        super.close();
    }
}
