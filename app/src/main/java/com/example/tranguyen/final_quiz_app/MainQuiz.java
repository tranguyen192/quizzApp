package com.example.tranguyen.final_quiz_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;
import android.widget.CheckBox;
import android.graphics.drawable.Drawable;

import com.example.tranguyen.final_quiz_app.data.QuestionsDbHelper;

import java.util.ArrayList;

public class MainQuiz extends AppCompatActivity {

    TextView score, mQuestion, mCategory;
    RadioGroup answerRadioGroup;
    RadioButton radioA1, radioA2, radioA3, radioA4;
    CheckBox checkA1, checkA2, checkA3, checkA4;
    ImageView view;

    Button button, submitButton;

    private int mScore = 0;
    private int mQuestionNumber = -1;
    private int multipleOrSingle, cnt = 0;
    private Boolean isOpenQuestion = false;
    protected  ArrayList<DBQuestions> question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        // Get all questions by selected category
        final String category = getIntent().getStringExtra("category");
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(this);
        question = dbHelper.getQuestionByCategory(category);

        // back button
        backHome();

        // show all questions
        showQuestions(category);

        // Set category
        mCategory = (TextView) findViewById(R.id.category);
        mCategory.setText(category);

        // Set score
        score = (TextView)findViewById(R.id.score);
        score.setText("Score: " + mScore);

        // Set question
        updateQuestion();
    }

    private void gameOver() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainQuiz.this);
        alertDialogBuilder
                .setMessage("Game over! Your score: " + mScore + " points.")
                .setCancelable(false)
                .setPositiveButton("NEW GAME",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton("EXIT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showQuestions(final String category) {
        button = (Button) findViewById(R.id.showQuestions);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainQuiz.this, AllQuestions.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
    }

    private void backHome() {
        button = (Button) findViewById(R.id.backhome);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainQuiz.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateQuestion() {
        mQuestionNumber++;

        if (mQuestionNumber < question.size()) {
            view = (ImageView) findViewById(R.id.image);
            mQuestion = (TextView) findViewById(R.id.Question);
            mQuestion.setText(question.get(mQuestionNumber).question);


            if (!question.get(mQuestionNumber).image.isEmpty()) {
                int resID = getResources().getIdentifier(question.get(mQuestionNumber).image, "drawable", this.getPackageName());
                view.setImageResource(resID);
            } else {
                view.setImageDrawable(null);
            }

            // open question
            if (question.get(mQuestionNumber).areCorrect1.contains("open") &&
                    question.get(mQuestionNumber).areCorrect2.contains("open") &&
                    question.get(mQuestionNumber).areCorrect3.contains("open") &&
                    question.get(mQuestionNumber).areCorrect4.contains("open")) {
                isOpenQuestion = true;
            }

            if (question.get(mQuestionNumber).areCorrect1.contains("1")) {
                multipleOrSingle++;
            }
            if (question.get(mQuestionNumber).areCorrect2.contains("1")) {
                multipleOrSingle++;
            }
            if (question.get(mQuestionNumber).areCorrect3.contains("1")) {
                multipleOrSingle++;
            }
            if (question.get(mQuestionNumber).areCorrect4.contains("1")) {
                multipleOrSingle++;
            }

            if (isOpenQuestion) {
                final EditText editText = findViewById(R.id.answerOpenQuestion);
                editText.setVisibility(View.VISIBLE);

                submitButton = (Button)findViewById(R.id.submit);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString().trim().length() != 0) {
                            mScore++;
                            score.setText("Score: " + mScore);
                            editText.setVisibility(View.INVISIBLE);
                            editText.setText(null);

                            updateQuestion();

                        } else {
                            gameOver();
                        }
                    }
                });
                isOpenQuestion = false;
            }

            if (multipleOrSingle == 1) {
                mQuestion.setText(question.get(mQuestionNumber).question);

                answerRadioGroup = (RadioGroup) findViewById(R.id.singleQuestionRadio);

                answerRadioGroup.setVisibility(View.VISIBLE);

                radioA1 = (RadioButton)findViewById(R.id.singleQuestion1);
                radioA2 = (RadioButton)findViewById(R.id.singleQuestion2);
                radioA3 = (RadioButton)findViewById(R.id.singleQuestion3);
                radioA4 = (RadioButton)findViewById(R.id.singleQuestion4);

                radioA1.setText(question.get(mQuestionNumber).answers1);
                radioA2.setText(question.get(mQuestionNumber).answers2);
                radioA3.setText(question.get(mQuestionNumber).answers3);
                radioA4.setText(question.get(mQuestionNumber).answers4);

                radioA1.setVisibility(View.VISIBLE);
                radioA2.setVisibility(View.VISIBLE);
                radioA3.setVisibility(View.VISIBLE);
                radioA4.setVisibility(View.VISIBLE);

                radioA1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect1.contains("1")) {
                            mScore++;
                            score.setText("Score: " + mScore);
                            answerRadioGroup.setVisibility(View.GONE);

                            radioA1.setVisibility(View.GONE);
                            radioA2.setVisibility(View.GONE);
                            radioA3.setVisibility(View.GONE);
                            radioA4.setVisibility(View.GONE);

                            radioA1.setChecked(false);
                            radioA2.setChecked(false);
                            radioA3.setChecked(false);
                            radioA4.setChecked(false);

                            multipleOrSingle = 0;

                            updateQuestion();
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                radioA2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect2.contains("1")) {
                            mScore++;
                            score.setText("Score: " + mScore);
                            answerRadioGroup.setVisibility(View.GONE);

                            radioA1.setVisibility(View.GONE);
                            radioA2.setVisibility(View.GONE);
                            radioA3.setVisibility(View.GONE);
                            radioA4.setVisibility(View.GONE);

                            radioA1.setChecked(false);
                            radioA2.setChecked(false);
                            radioA3.setChecked(false);
                            radioA4.setChecked(false);

                            multipleOrSingle = 0;

                            updateQuestion();
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                radioA3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect3.contains("1")) {
                            mScore++;
                            score.setText("Score: " + mScore);
                            answerRadioGroup.setVisibility(View.GONE);

                            radioA1.setVisibility(View.GONE);
                            radioA2.setVisibility(View.GONE);
                            radioA3.setVisibility(View.GONE);
                            radioA4.setVisibility(View.GONE);

                            radioA1.setChecked(false);
                            radioA2.setChecked(false);
                            radioA3.setChecked(false);
                            radioA4.setChecked(false);

                            multipleOrSingle = 0;

                            updateQuestion();
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                radioA4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect4.contains("1")) {
                            mScore++;
                            score.setText("Score: " + mScore);
                            answerRadioGroup.setVisibility(View.GONE);

                            radioA1.setVisibility(View.GONE);
                            radioA2.setVisibility(View.GONE);
                            radioA3.setVisibility(View.GONE);
                            radioA4.setVisibility(View.GONE);

                            radioA1.setChecked(false);
                            radioA2.setChecked(false);
                            radioA3.setChecked(false);
                            radioA4.setChecked(false);

                            multipleOrSingle = 0;

                            updateQuestion();
                        }
                        else {
                            gameOver();
                        }
                    }
                });
            }

            if (multipleOrSingle > 1) {
                checkA1 = (CheckBox)findViewById(R.id.multipleQuestion1);
                checkA2 = (CheckBox)findViewById(R.id.multipleQuestion2);
                checkA3 = (CheckBox)findViewById(R.id.multipleQuestion3);
                checkA4 = (CheckBox)findViewById(R.id.multipleQuestion4);

                checkA1.setVisibility(View.VISIBLE);
                checkA2.setVisibility(View.VISIBLE);
                checkA3.setVisibility(View.VISIBLE);
                checkA4.setVisibility(View.VISIBLE);

                checkA1.setText(question.get(mQuestionNumber).answers1);
                checkA2.setText(question.get(mQuestionNumber).answers2);
                checkA3.setText(question.get(mQuestionNumber).answers3);
                checkA4.setText(question.get(mQuestionNumber).answers4);

                checkA1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect1.contains("1")) {
                            cnt++;
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                checkA2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect2.contains("1")) {
                            cnt++;
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                checkA3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect3.contains("1")) {
                            cnt++;
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                checkA4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (question.get(mQuestionNumber).areCorrect4.contains("1")) {
                            cnt++;
                        }
                        else {
                            gameOver();
                        }
                    }
                });

                submitButton = (Button)findViewById(R.id.submit);
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cnt == multipleOrSingle) {
                            mScore++;
                            score.setText("Score: " + mScore);

                            checkA1.setVisibility(View.GONE);
                            checkA2.setVisibility(View.GONE);
                            checkA3.setVisibility(View.GONE);
                            checkA4.setVisibility(View.GONE);

                            checkA1.setChecked(false);
                            checkA2.setChecked(false);
                            checkA3.setChecked(false);
                            checkA4.setChecked(false);

                            cnt = 0;
                            multipleOrSingle = 0;

                            updateQuestion();
                        } else {
                            gameOver();
                        }
                    }
                });
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainQuiz.this);
            alertDialogBuilder
                    .setMessage("Congratulation! You have completed the category! Your score: " + mScore + " points.")
                    .setCancelable(false)
                    .setPositiveButton("NEW GAME",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            })
                    .setNegativeButton("EXIT",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
