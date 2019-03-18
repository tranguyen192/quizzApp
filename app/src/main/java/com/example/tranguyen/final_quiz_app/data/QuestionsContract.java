package com.example.tranguyen.final_quiz_app.data;

import android.provider.BaseColumns;

public final class QuestionsContract {

    private QuestionsContract() {}

    public static final class Questions implements BaseColumns {

        /** Name of database table for questions */
        public final static String TABLE_NAME = "questions";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_CATEGORY = "category";

        public final static String COLUMN_QUESTION = "question";

        public final static String COLUMN_IMGNAME = "imgName";

        public final static String COLUMN_ANSWERS1 = "answers1";
        public final static String COLUMN_ANSWERS2 = "answers2";
        public final static String COLUMN_ANSWERS3 = "answers3";
        public final static String COLUMN_ANSWERS4 = "answers4";

        public final static String COLUMN_ARECORRECT1 = "areCorrect1";
        public final static String COLUMN_ARECORRECT2 = "areCorrect2";
        public final static String COLUMN_ARECORRECT3 = "areCorrect3";
        public final static String COLUMN_ARECORRECT4 = "areCorrect4";


        public Questions(int id, String category, String question, String imgName, String answers1, String answers2, String answers3, String answers4, String areCorrect1, String areCorrect2, String areCorrect3, String areCorrect4) {
        }

        public static String getTableName() {
            return TABLE_NAME;
        }

        public static String getId() {
            return _ID;
        }

        public static String getColumnCategory() {
            return COLUMN_CATEGORY;
        }

        public static String getColumnQuestion() {
            return COLUMN_QUESTION;
        }

        public static String getColumnImgname() {
            return COLUMN_IMGNAME;
        }

        public static String getColumnAnswers1() {
            return COLUMN_ANSWERS1;
        }

        public static String getColumnAnswers2() {
            return COLUMN_ANSWERS2;
        }

        public static String getColumnAnswers3() {
            return COLUMN_ANSWERS3;
        }

        public static String getColumnAnswers4() {
            return COLUMN_ANSWERS4;
        }

        public static String getColumnArecorrect1() {
            return COLUMN_ARECORRECT1;
        }

        public static String getColumnArecorrect2() {
            return COLUMN_ARECORRECT2;
        }

        public static String getColumnArecorrect3() {
            return COLUMN_ARECORRECT3;
        }

        public static String getColumnArecorrect4() {
            return COLUMN_ARECORRECT4;
        }
    }

}


