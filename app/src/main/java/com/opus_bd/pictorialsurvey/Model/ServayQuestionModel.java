package com.opus_bd.pictorialsurvey.Model;

public class ServayQuestionModel {
    public ServayQuestionModel() {
    }

    private String question;

private Option1 option1;

private Option2 option2;

private String optionType;

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

public String getOptionType() {
return optionType;
}

public void setOptionType(String optionType) {
this.optionType = optionType;
}

}