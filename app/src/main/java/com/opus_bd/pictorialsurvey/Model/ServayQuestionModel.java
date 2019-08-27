package com.opus_bd.pictorialsurvey.Model;

public class ServayQuestionModel {
    public ServayQuestionModel() {
    }

    private String question;

    private Option1 option1;

    private Option2 option2;

    private Option3 option3;
    private Option4 option4;

    private String optionType;
    private String questionKey;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Option1 getOption1() {
        return option1;
    }

    public void setOption1(Option1 option1) {
        this.option1 = option1;
    }

    public Option2 getOption2() {
        return option2;
    }

    public void setOption2(Option2 option2) {
        this.option2 = option2;
    }
    public Option3 getOption3() {
        return option3;
    }

    public void setOption3(Option3 option3) {
        this.option3 = option3;
    }

    public Option4 getOption4() {
        return option4;
    }

    public void setOption4(Option4 option4) {
        this.option4 = option4;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }
}