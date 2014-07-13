package com.photoboot.db;

/**
 * Created by Нерсес on 05.05.2014.
 */
import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.photoboot.domain.Photo;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "photo.db";

    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Photo.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        Photo photo1 = new Photo("photo 1");
        Photo photo2 = new Photo("photo 2");
        Photo photo3 = new Photo("photo 3");

        try {
            DatabaseHelper.getHelper().getDao(Photo.class).create(photo1);
            DatabaseHelper.getHelper().getDao(Photo.class).create(photo2);
            DatabaseHelper.getHelper().getDao(Photo.class).create(photo3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Photo.class, true);
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
