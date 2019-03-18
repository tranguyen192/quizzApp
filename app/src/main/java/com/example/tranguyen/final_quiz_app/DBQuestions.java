package com.example.tranguyen.final_quiz_app;

public class DBQuestions {

    int ID;

    String category;
    String question;
    String image;

    String answers1;
    String answers2;
    String answers3;
    String answers4;

    String areCorrect1;
    String areCorrect2;
    String areCorrect3;
    String areCorrect4;


    public DBQuestions(int ID, String category, String question, String image,
                     String answers1, String answers2, String answers3, String answers4,
                     String areCorrect1, String areCorrect2, String areCorrect3, String areCorrect4) {
        this.ID = ID;
        this.category = category;
        this.question = question;
        this.image = image;
        this.answers1 = answers1;
        this.answers2 = answers2;
        this.answers3 = answers3;
        this.answers4 = answers4;
        this.areCorrect1 = areCorrect1;
        this.areCorrect2 = areCorrect2;
        this.areCorrect3 = areCorrect3;
        this.areCorrect4 = areCorrect4;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswers1() {
        return answers1;
    }

    public void setAnswers1(String answers1) {
        this.answers1 = answers1;
    }

    public String getAnswers2() {
        return answers2;
    }

    public void setAnswers2(String answers2) {
        this.answers2 = answers2;
    }

    public String getAnswers3() {
        return answers3;
    }

    public void setAnswers3(String answers3) {
        this.answers3 = answers3;
    }

    public String getAnswers4() {
        return answers4;
    }

    public void setAnswers4(String answers4) {
        this.answers4 = answers4;
    }

    public String getAreCorrect1() {
        return areCorrect1;
    }

    public void setAreCorrect1(String areCorrect1) {
        this.areCorrect1 = areCorrect1;
    }

    public String getAreCorrect2() {
        return areCorrect2;
    }

    public void setAreCorrect2(String areCorrect2) {
        this.areCorrect2 = areCorrect2;
    }

    public String getAreCorrect3() {
        return areCorrect3;
    }

    public void setAreCorrect3(String areCorrect3) {
        this.areCorrect3 = areCorrect3;
    }

    public String getAreCorrect4() {
        return areCorrect4;
    }

    public void setAreCorrect4(String areCorrect4) {
        this.areCorrect4 = areCorrect4;
    }
}