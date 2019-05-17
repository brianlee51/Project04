package sg.edu.rp.c346.project04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    //Table Part Type (Stores info like bricks, slopes, wheel etc)
    private static final String TABLE_PART_TYPE = "part_type";
    private static final String COLUMN_PART_TYPE_ID = "_id";
    private static final String COLUMN_PART_NAME = "partName";

    //Table Stud_area (Stores info like 1x2, 2x2)
    private static final String TABLE_STUD_AREA = "stud_area";
    private static final String COLUMN_STUD_ID = "stud_id";
    private static final String COLUMN_STUD_NAME = "stud_name";

    //Table color_list
    private static final String TABLE_COLOR = "color_list";
    private static final String COLUMN_COLOR_ID = "color_id";
    private static final String COLUMN_COLOR_NAME = "color_name";

    //Table main part list
    private static final String TABLE_PART_LIST = "part_list";
    private static final String COLUMN_PART_LIST_ID = "part_list_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    private static final String COLUMN_STORAGE_LOCATION = "storage_location";
    private static final String COLUMN_FK_PART_ID= "part_id";
    private static final String COLUMN_FK_COLOR_ID= "color_id";
    private static final String COLUMN_FK_STUD_ID= "stud_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Note
        String createTableColorSql = "CREATE TABLE " + TABLE_COLOR +  "("
                + COLUMN_COLOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COLOR_NAME + " TEXT )";

        String createTableStudSql = "CREATE TABLE " + TABLE_STUD_AREA +  "("
                + COLUMN_STUD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUD_NAME + " TEXT )";

        String createTablePartTypeSql = "CREATE TABLE " + TABLE_PART_TYPE +  "("
                + COLUMN_PART_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PART_NAME + " TEXT)";

        String createTablePartListSql = "CREATE TABLE " + TABLE_PART_LIST +  "("
                + COLUMN_PART_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT, "
                + COLUMN_ITEM_QUANTITY + " INTEGER,"
                + COLUMN_STORAGE_LOCATION + " TEXT,"
                + COLUMN_FK_PART_ID + " INTEGER,"
                + COLUMN_FK_COLOR_ID + " INTEGER,"
                + COLUMN_FK_STUD_ID + " INTEGER,"
                + "FOREIGN KEY (part_id) REFERENCES " + TABLE_PART_TYPE + "(_id),"
                + "FOREIGN KEY (color_id) REFERENCES " + TABLE_COLOR + "(color_id), "
                + "FOREIGN KEY (stud_id) REFERENCES " + TABLE_PART_TYPE + "(stud_id))";

        db.execSQL(createTablePartListSql);
        db.execSQL(createTableStudSql);
        db.execSQL(createTableColorSql);
        db.execSQL(createTablePartTypeSql);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PART_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PART_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUD_AREA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR);
        onCreate(db);
    }

    public void insertColor(String color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR_NAME, color);
        db.insert(TABLE_COLOR, null, values);
        db.close();
    }

    public boolean dbContainColor(String color) {
        String selectQuery = "SELECT " + COLUMN_COLOR_NAME
                + " FROM " + TABLE_COLOR
                + " WHERE " + COLUMN_COLOR_NAME + " == '" + color + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        boolean isInDB = false;
        cursor.close();
        db.close();
        if (count > 0) {
            isInDB = true;
        }

        return isInDB;
    }

    public ArrayList<String> getColor() {
        ArrayList<String> colors = new ArrayList<String>();
        String selectQuery = "SELECT " + COLUMN_COLOR_NAME
                + " FROM " + TABLE_COLOR
                + " ORDER BY " + COLUMN_COLOR_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                colors.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return colors;
    }
}
