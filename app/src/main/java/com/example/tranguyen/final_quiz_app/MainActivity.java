package com.example.tranguyen.final_quiz_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tranguyen.final_quiz_app.data.QuestionsContract;
import com.example.tranguyen.final_quiz_app.data.QuestionsDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private QuestionsDbHelper mquestionsDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mquestionsDBhelper = new QuestionsDbHelper(this);

        final ArrayList<String> categories = QuestionsDbHelper.getInstance(this).getAllCategories();

        // Show all categories
        final CategoryAdapter listAdapter = new CategoryAdapter(this, categories);

        ListView listView = (ListView) findViewById(R.id.listview_categories);
        listView.setAdapter(listAdapter);

        System.out.print("HELLOOOOO 1");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                String selectedCategory = categories.get(position);

                Intent intent = new Intent(MainActivity.this, MainQuiz.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Insert questions + answers in database
        //insertData();
    }

    private void insertData() {
        // Gets the database in write mode
        SQLiteDatabase db = mquestionsDBhelper.getWritableDatabase();

        final String Q_CATEGORY = "category";
        final String Q_QUESTION = "question";
        final String Q_IMGNAME = "imgName";
        final String Q_ANSWERS = "answers";
        final String Q_ARECORRECT = "areCorrect";

        String[] filesNames = getJSONfilenames();

        for (String name: filesNames) {
            try {
                String result = readDataInJSON("all_Questions/" + name);

                JSONObject obj = new JSONObject(result);
                JSONArray menuItemsJsonArray = obj.getJSONArray("questions");

                for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                    JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                    String category = menuItemObject.getString(Q_CATEGORY);
                    String question = menuItemObject.getString(Q_QUESTION);
                    String imgName = menuItemObject.getString(Q_IMGNAME);

                    ContentValues menuValues = new ContentValues();

                    // ARRAY
                    JSONArray json_answer = menuItemObject.getJSONArray(Q_ANSWERS);
                    JSONArray json_areCorrect = menuItemObject.getJSONArray(Q_ARECORRECT);

                    // if array is empty => open question
                    if (json_answer.length() < 1 && json_areCorrect.length() < 1) {
                        menuValues.put(QuestionsContract.Questions.COLUMN_CATEGORY, category);
                        menuValues.put(QuestionsContract.Questions.COLUMN_QUESTION, question);
                        menuValues.put(QuestionsContract.Questions.COLUMN_IMGNAME, imgName);

                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS1, "");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS2, "");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS3, "");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS4, "");

                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT1, "open");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT2, "open");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT3, "open");
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT4, "open");
                    }
                    // if not, normal questio
                    else {
                        String[] answers = new String[json_answer.length()];
                        for (int j = 0; j < json_answer.length(); j++) {
                            answers[j] = json_answer.getString(j);
                        }

                        Integer f = 0;
                        Integer t = 1;

                        Integer[] areCorrect = new Integer[json_areCorrect.length()];
                        for (int j = 0; j < json_areCorrect.length(); j++) {
                            if (json_areCorrect.getBoolean(j) == true) {
                                areCorrect[j] = t;
                            } else if (json_areCorrect.getBoolean(j) == false) {
                                areCorrect[j] = f;
                            } else {
                                areCorrect[j] = -1;
                            }
                        }

                        menuValues.put(QuestionsContract.Questions.COLUMN_CATEGORY, category);
                        menuValues.put(QuestionsContract.Questions.COLUMN_QUESTION, question);
                        menuValues.put(QuestionsContract.Questions.COLUMN_IMGNAME, imgName);

                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS1, answers[0]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS2, answers[1]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS3, answers[2]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ANSWERS4, answers[3]);

                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT1, areCorrect[0]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT2, areCorrect[1]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT3, areCorrect[2]);
                        menuValues.put(QuestionsContract.Questions.COLUMN_ARECORRECT4, areCorrect[3]);
                    }

                    long newRowId = db.insert(QuestionsContract.Questions.TABLE_NAME, null, menuValues);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected String[] getJSONfilenames() {
        String[] jsonFiles = new String[0];
        try {
            // Get file names
            AssetManager as = getAssets();
            jsonFiles = as.list("all_Questions");
            return jsonFiles;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Main Activity", "WARNING: Something went wrong with parsing the JSON file", ex);
        }

        return jsonFiles;
    }

    private String readDataInJSON(String filename) {
        String json = null;
        try {

            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Main Activity", "WARNING: Something went wrong with parsing the JSON file", ex);
        }
        return json;
    }
}
