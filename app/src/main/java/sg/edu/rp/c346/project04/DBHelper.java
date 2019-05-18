package sg.edu.rp.c346.project04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 4;

    //Table Part Type (Stores info like bricks, slopes, wheel etc)
    private static final String TABLE_PART_TYPE = "part_type";
    private static final String COLUMN_PART_TYPE_ID = "_id";
    private static final String COLUMN_PART_NAME = "partName";

    //Table Stud_area (Stores info like 1x2, 2x2)
    private static final String TABLE_STUD_AREA = "stud_area";
    private static final String COLUMN_STUD_ID = "_stud_id";
    private static final String COLUMN_STUD_NAME = "stud_name";

    //Table color_list
    private static final String TABLE_COLOR = "color_list";
    private static final String COLUMN_COLOR_ID = "_color_id";
    private static final String COLUMN_COLOR_NAME = "color_name";

    //Table main part list
    private static final String TABLE_PART_LIST = "part_list";
    private static final String COLUMN_PART_LIST_ID = "part_list_id";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    private static final String COLUMN_STORAGE_LOCATION = "storage_location";
    private static final String COLUMN_FK_PART_ID= "part_id";
    private static final String COLUMN_FK_COLOR_ID= "fk_color_id";
    private static final String COLUMN_FK_STUD_ID= "fk_stud_id";

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
                + COLUMN_ITEM_QUANTITY + " INTEGER,"
                + COLUMN_STORAGE_LOCATION + " TEXT,"
                + COLUMN_FK_PART_ID + " INTEGER,"
                + COLUMN_FK_COLOR_ID + " INTEGER,"
                + COLUMN_FK_STUD_ID + " INTEGER,"
                + "FOREIGN KEY(part_id) REFERENCES " + TABLE_PART_TYPE + "(_id),"
                + "FOREIGN KEY(fk_color_id) REFERENCES " + TABLE_COLOR + "(_color_id), "
                + "FOREIGN KEY(fk_stud_id) REFERENCES " + TABLE_PART_TYPE + "(_stud_id))";

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

    public void insertItem(int quantity, String location, int partID,  int colorID, int studID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_STORAGE_LOCATION, location);
        values.put(COLUMN_FK_PART_ID, partID);
        values.put(COLUMN_FK_COLOR_ID, colorID);
        values.put(COLUMN_FK_STUD_ID, studID);
        db.insert(TABLE_PART_LIST, null, values);
        db.close();
    }

    public void insertColor(String color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR_NAME, color.toUpperCase());
        db.insert(TABLE_COLOR, null, values);
        db.close();
    }

    public void insertPart(String partName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PART_NAME, partName.toUpperCase());
        db.insert(TABLE_PART_TYPE, null, values);
        db.close();
    }

    public void insertArea(String area) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUD_NAME, area.toUpperCase());
        db.insert(TABLE_STUD_AREA, null, values);
        db.close();
    }

    public boolean dbContainColor(String color) {
        String selectQuery = "SELECT " + COLUMN_COLOR_NAME
                + " FROM " + TABLE_COLOR
                + " WHERE " + COLUMN_COLOR_NAME + " == '" + color.toUpperCase() + "'";

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

    public boolean dbContainPartName(String partName) {
        String selectQuery = "SELECT " + COLUMN_PART_NAME
                + " FROM " + TABLE_PART_TYPE
                + " WHERE " + COLUMN_PART_NAME + " == '" + partName.toUpperCase() + "'";

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

    public boolean dbContainsArea(String area) {
        String selectQuery = "SELECT " + COLUMN_STUD_NAME
                + " FROM " + TABLE_STUD_AREA
                + " WHERE " + COLUMN_STUD_NAME + " == '" + area.toUpperCase() + "'";

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

    public int getColorID(String colorName) {
        String selectQuery = "SELECT " + COLUMN_COLOR_ID
                + " FROM " + TABLE_COLOR
                + " WHERE " + COLUMN_COLOR_NAME + " == '" + colorName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }

    public int getPartID(String partName) {
        String selectQuery = "SELECT " + COLUMN_PART_TYPE_ID
                + " FROM " + TABLE_PART_TYPE
                + " WHERE " + COLUMN_PART_NAME + " == '" + partName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }

    public int getStudID(String studName) {
        String selectQuery = "SELECT " + COLUMN_STUD_ID
                + " FROM " + TABLE_STUD_AREA
                + " WHERE " + COLUMN_STUD_NAME + " == '" + studName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }

    public int getTotalUniqueParts() {
        String selectQuery = "SELECT *"
                + " FROM " + TABLE_PART_LIST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getTotalAmount() {
        String selectQuery = "SELECT " + COLUMN_ITEM_QUANTITY
                + " FROM " + TABLE_PART_LIST;

        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int item = cursor.getInt(0);
                total += item;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    public ArrayList<String> getColorName() {
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

    public ArrayList<String> getPartName() {
        ArrayList<String> partName = new ArrayList<String>();
        String selectQuery = "SELECT " + COLUMN_PART_NAME
                + " FROM " + TABLE_PART_TYPE
                + " ORDER BY " + COLUMN_PART_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                partName.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return partName;
    }

    public ArrayList<String> getArea() {
        ArrayList<String> area = new ArrayList<String>();
        String selectQuery = "SELECT " + COLUMN_STUD_NAME
                + " FROM " + TABLE_STUD_AREA
                + " ORDER BY " + COLUMN_STUD_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                area.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return area;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        String selectQuery = "SELECT " + COLUMN_PART_LIST_ID + ", "
                + COLUMN_ITEM_QUANTITY + ", "
                + COLUMN_STORAGE_LOCATION + ", "
                + COLUMN_PART_NAME + ", "
                + COLUMN_COLOR_NAME + ", "
                + COLUMN_STUD_NAME
                + " FROM " + TABLE_PART_LIST + ", " + TABLE_PART_TYPE + ", " +  TABLE_COLOR + ", " + TABLE_STUD_AREA
                + " WHERE " + COLUMN_FK_STUD_ID + " = " + COLUMN_STUD_ID
                + " AND " + COLUMN_FK_PART_ID + " = " + COLUMN_PART_TYPE_ID
                + " AND " + COLUMN_FK_COLOR_ID + " = " + COLUMN_COLOR_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int quantity = cursor.getInt(1);
                String location = cursor.getString(2);
                String partName = cursor.getString(3);
                String colorName = cursor.getString(4);
                String studName = cursor.getString(5);
                Item obj = new Item(id, quantity, location, partName, colorName, studName);
                items.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
}
