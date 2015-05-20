package history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.outcube.cashsavior.MainActivity;

public class HistoryDatabase extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HistoryLog";

    // History table name
    private static final String TABLE_TRANSECTION = "Transections";

    // History Table Columns names
    private static final String KEY_TYPEID = "typeid";
    private static final String KEY_SUBID = "subid";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTE = "note";
    private static final String KEY_STATUS = "updateStatus";

    public HistoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_TRANSECTION + "("
                + KEY_TYPEID + " INTEGER," + KEY_SUBID + " INTEGER," + KEY_AMOUNT + " INTEGER,"
                + KEY_DATE + " TEXT," + KEY_NOTE + " TEXT," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSECTION);
        // Create tables again
        onCreate(db);
    }

    public void addHistory(HistoryLog historyLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPEID, historyLog.getTypeid());
        values.put(KEY_SUBID, historyLog.getSubid());
        values.put(KEY_AMOUNT, historyLog.getAmount());
        values.put(KEY_DATE, historyLog.getDate());
        values.put(KEY_NOTE, historyLog.getNote());
        values.put(KEY_STATUS, "no");
        // Inserting Row
        db.insert(TABLE_TRANSECTION, null, values);
        db.close(); // Closing database connection
    }

    public void addHistory(HistoryLog historyLog, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPEID, historyLog.getTypeid());
        values.put(KEY_SUBID, historyLog.getSubid());
        values.put(KEY_AMOUNT, historyLog.getAmount());
        values.put(KEY_DATE, historyLog.getDate());
        values.put(KEY_NOTE, historyLog.getNote());
        values.put(KEY_STATUS, status);
        // Inserting Row
        db.insert(TABLE_TRANSECTION, null, values);
        db.close(); // Closing database connection
    }

    // Getting All history
    public List<HistoryLog> getAllHistory() {
        List<HistoryLog> historyList = new ArrayList<HistoryLog>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSECTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryLog historyLog = new HistoryLog();
                historyLog.setTypeid(Integer.parseInt(cursor.getString(0)));
                historyLog.setSubid(Integer.parseInt(cursor.getString(1)));
                historyLog.setAmount(Integer.parseInt(cursor.getString(2)));
                historyLog.setDate(cursor.getString(3));
                historyLog.setNote(cursor.getString(4));
                // Adding history to list
                historyList.add(historyLog);
            } while (cursor.moveToNext());
        }
        // return contact list
        return historyList;
    }

    // Getting history Count
    public int getHistoryCount() {
        String historyQuery = "SELECT  * FROM " + TABLE_TRANSECTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(historyQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSECTION, null, null);
        db.close();
    }

    public int dbSyncCount() {
        int count = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSECTION + " where " + KEY_STATUS + " = 'no'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    public void updateSyncStatus(HistoryLog history, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update " + TABLE_TRANSECTION + " set " + KEY_STATUS + " = '"+ status + "' where "+
                KEY_TYPEID + " = " + history.getTypeid() + " AND " + KEY_SUBID + " = " + history.getSubid() + " AND " +
                KEY_AMOUNT + " = " + history.getAmount() + " AND " + KEY_DATE + " = " + "'" + history.getDate() + "' AND " +
                KEY_NOTE + " = " + "'" + history.getNote() + "'";
        database.execSQL(updateQuery);
        database.close();
    }

    public ArrayList<HistoryLog> getUnsyncHistory(){
        ArrayList<HistoryLog> historyLogs = new ArrayList<HistoryLog>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSECTION + " where " + KEY_STATUS + " = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryLog historyLog = new HistoryLog();
                historyLog.setTypeid(Integer.parseInt(cursor.getString(0)));
                historyLog.setSubid(Integer.parseInt(cursor.getString(1)));
                historyLog.setAmount(Integer.parseInt(cursor.getString(2)));
                historyLog.setDate(cursor.getString(3));
                historyLog.setNote(cursor.getString(4));
                historyLogs.add(historyLog);
            } while (cursor.moveToNext());
        }
        database.close();
        return historyLogs;
    }

    public String composeJSONfromSQLite(){
        ArrayList<HistoryLog> historyLogs = new ArrayList<HistoryLog>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSECTION + " where " + KEY_STATUS + " = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryLog historyLog = new HistoryLog();
                historyLog.setTypeid(Integer.parseInt(cursor.getString(0)));
                historyLog.setSubid(Integer.parseInt(cursor.getString(1)));
                historyLog.setAmount(Integer.parseInt(cursor.getString(2)));
                historyLog.setDate(cursor.getString(3));
                historyLog.setNote(cursor.getString(4));
                historyLog.setUserId(MainActivity.userId);
                historyLogs.add(historyLog);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(historyLogs);
    }

}
