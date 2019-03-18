package com.example.tranguyen.final_quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.tranguyen.final_quiz_app.data.QuestionsDbHelper;

import java.util.ArrayList;

public class AllQuestions extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        // Get all questions by selected category
        final String category = getIntent().getStringExtra("category");
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(this);
        final ArrayList<DBQuestions> allQuestion = dbHelper.getQuestionByCategory(category);

        ArrayList<String> questions = new ArrayList<>();

        for (int i = 0; i < allQuestion.size(); i++) {
            questions.add(allQuestion.get(i).question);
        }

        // Show all questions
        final CategoryAdapter listAdapter = new CategoryAdapter(this, questions);

        ListView listView = (ListView) findViewById(R.id.listview_questions);
        listView.setAdapter(listAdapter);

        backHome();
    }

    private void backHome() {
        button = (Button) findViewById(R.id.backhome);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AllQuestions.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
