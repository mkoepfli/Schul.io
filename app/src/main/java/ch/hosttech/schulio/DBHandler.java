package ch.hosttech.schulio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ch.hosttech.schulio.model.MarkModal;
import ch.hosttech.schulio.model.SubjectModal;


public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "schulio";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME_SUBJECT = "allSubjects";
    private static final String TABLE_NAME_MARK = "allMarks";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";
    private static final String NAME_SUBJECT = "subject";
    private static final String NAME_COL_MARK = "testName";

    private static final String MARK_COL_MARK = "mark";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating 
        // an sqlite query and we are 
        // setting our column names
        // along with their data types.

        db.execSQL("CREATE TABLE " + TABLE_NAME_SUBJECT + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_MARK + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_SUBJECT + " TEXT, "
                + NAME_COL_MARK + " TEXT, "
                + MARK_COL_MARK + " REAL)");
    }

    // this method is use to add new course to our sqlite database.
    public void addNewSubject(String subjectName) {

        // on below line we are creating a variable for 
        // our sqlite database and calling writable method 
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a 
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values 
        // along with its key and value pair.
        values.put(NAME_COL, subjectName);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME_SUBJECT, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void addNewMark(String subject, String testName, float mark) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_SUBJECT, subject);
        values.put(NAME_COL_MARK, testName);
        values.put(MARK_COL_MARK, mark);

        db.insert(TABLE_NAME_MARK, null, values);

        db.close();
    }

    public ArrayList<SubjectModal> readSubjects() {
        // creating database for reading database.
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorSubject = db.rawQuery("SELECT * FROM " + TABLE_NAME_SUBJECT, null);

        // creating a new array list.
        ArrayList<SubjectModal> subjectModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorSubject.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                subjectModalArrayList.add(new SubjectModal(cursorSubject.getString(1)));
            } while (cursorSubject.moveToNext());
            // moving cursor to next.
        }
        // closing cursor and returning array list.
        cursorSubject.close();
        return subjectModalArrayList;
    }

    public ArrayList<MarkModal> readMarks(String subjectname) {
        // creating database for reading database.
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorMark = db.rawQuery("SELECT * FROM " + TABLE_NAME_MARK + " WHERE TRIM(subject) = '"+subjectname.trim()+"'", null);

        // creating a new array list.
        ArrayList<MarkModal> markModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorMark.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                markModalArrayList.add(new MarkModal(cursorMark.getString(1), cursorMark.getString(2), cursorMark.getFloat(3)));
            } while (cursorMark.moveToNext());
            // moving cursor to next.
        }
        // closing cursor and returning array list.
        cursorMark.close();
        return markModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MARK);
        onCreate(db);
    }
}