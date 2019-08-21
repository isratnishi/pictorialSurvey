package com.opus_bd.pictorialsurvey.Model;

public class Question {

    private String uid;
    private String question;
    private String answer1;
    private String answer2;
    private String imageanswer1;

    public String getImageanswer1() {
        return imageanswer1;
    }

    public void setImageanswer1(String imageanswer1) {
        this.imageanswer1 = imageanswer1;
    }

    public String getImageanswer2() {
        return imageanswer2;
    }

    public void setImageanswer2(String imageanswer2) {
        this.imageanswer2 = imageanswer2;
    }

    private String imageanswer2;


 /*   public Question(String question, String answer1, String answer2) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public Question(String answer1, String answer2) {
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public Question(String question) {
        this.question = question;
    }*/

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
}
