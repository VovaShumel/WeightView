package com.livejournal.lofe.weightview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Environment.getExternalStorageDirectory;

class DB {
    private static final String DB_PATH = getExternalStorageDirectory().getAbsolutePath() + "/";
    private static String DB_NAME = "Weights.db";
    private static final int DB_VERSION = 1;
    private static final String WEIGHTS_TABLE = "WEIGHTS";

    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_WEIGHT = "WEIGHT";
    public static final String COLUMN_DATE = "DATE";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    private String[] columns = null;
    private String[] selectionArgs = null;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_PATH + DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        mDB.setForeignKeyConstraintsEnabled(true);
    }

    long addWeigth(int weight, long ms) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_DATE, ms);
        return mDB.insert(WEIGHTS_TABLE, null, cv);
    }

    public void close() {
        if (mDBHelper!=null)
            mDBHelper.close();
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
