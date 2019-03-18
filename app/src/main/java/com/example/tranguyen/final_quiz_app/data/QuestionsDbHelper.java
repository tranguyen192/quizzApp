package com.example.tranguyen.final_quiz_app.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tranguyen.final_quiz_app.DBQuestions;

import java.util.ArrayList;
import java.util.HashSet;

public class QuestionsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = QuestionsDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;

    private static QuestionsDbHelper instance;

    public static synchronized QuestionsDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionsDbHelper(context);
        }
        return instance;
    }

    public QuestionsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_QUESTIONS_TABLE =  "CREATE TABLE " + QuestionsContract.Questions.TABLE_NAME + " ("
                + QuestionsContract.Questions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuestionsContract.Questions.COLUMN_CATEGORY + " TEXT NOT NULL, "
                + QuestionsContract.Questions.COLUMN_QUESTION + " TEXT UNIQUE NOT NULL, "
                + QuestionsContract.Questions.COLUMN_IMGNAME + " TEXT, "
                + QuestionsContract.Questions.COLUMN_ANSWERS1 + " TEXT, "
                + QuestionsContract.Questions.COLUMN_ANSWERS2 + " TEXT, "
                + QuestionsContract.Questions.COLUMN_ANSWERS3 + " TEXT, "
                + QuestionsContract.Questions.COLUMN_ANSWERS4 + " TEXT, "
                + QuestionsContract.Questions.COLUMN_ARECORRECT1 + " INTEGER, "
                + QuestionsContract.Questions.COLUMN_ARECORRECT2 + " INTEGER, "
                + QuestionsContract.Questions.COLUMN_ARECORRECT3 + " INTEGER, "
                + QuestionsContract.Questions.COLUMN_ARECORRECT4 + " INTEGER " + " );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    // Get categories
    public ArrayList<String> getAllCategories() {
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id, category FROM questions;", null);

        ArrayList<String> categories = new ArrayList<String>();
        HashSet<String> hashSet = new HashSet<>();
        String hashCategory = "";

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                hashCategory = cursor.getString(cursor.getColumnIndex("category"));
                hashSet.add(hashCategory);
                cursor.moveToNext();
            }
        }

        for (String s: hashSet) {
            String category = new String(s);
            categories.add(category);
        }

        cursor.close();
        db.close();

        return categories;
    }

    // Get questions from DB by category
    public ArrayList<DBQuestions> getQuestionByCategory(String category) {
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE category like '" + category + "';", null);

        ArrayList<DBQuestions> questions = new ArrayList<DBQuestions>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DBQuestions q = new DBQuestions(cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("category")),
                        cursor.getString(cursor.getColumnIndex("question")),
                        cursor.getString(cursor.getColumnIndex("imgName")),
                        cursor.getString(cursor.getColumnIndex("answers1")),
                        cursor.getString(cursor.getColumnIndex("answers2")),
                        cursor.getString(cursor.getColumnIndex("answers3")),
                        cursor.getString(cursor.getColumnIndex("answers4")),
                        cursor.getString(cursor.getColumnIndex("areCorrect1")),
                        cursor.getString(cursor.getColumnIndex("areCorrect2")),
                        cursor.getString(cursor.getColumnIndex("areCorrect3")),
                        cursor.getString(cursor.getColumnIndex("areCorrect4")));

                questions.add(q);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return questions;

    }
}