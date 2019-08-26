package com.opus_bd.pictorialsurvey.Model;

public class QuestionAndVoteCount {
    String QuestionBody;
    String OptionID;
    String optionOneValue;
    String optionOneCount;
    String optionTwoValue;
    String optionTwoCount;
    int firstoption;
    int secondoption;
    String type;

    public QuestionAndVoteCount(String questionBody, String optionID, String optionOneValue, String optionOneCount, String optionTwoValue, String optionTwoCount, int firstoption, int secondoption, String type) {
        QuestionBody = questionBody;
        OptionID = optionID;
        this.optionOneValue = optionOneValue;
        this.optionOneCount = optionOneCount;
        this.optionTwoValue = optionTwoValue;
        this.optionTwoCount = optionTwoCount;
        this.firstoption = firstoption;
        this.secondoption = secondoption;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFirstoption() {
        return firstoption;
    }

    public void setFirstoption(int firstoption) {
        this.firstoption = firstoption;
    }

    public int getSecondoption() {
        return secondoption;
    }

    public void setSecondoption(int secondoption) {
        this.secondoption = secondoption;
    }

    public String getOptionOneValue() {
        return optionOneValue;
    }

    public void setOptionOneValue(String optionOneValue) {
        this.optionOneValue = optionOneValue;
    }

    public String getOptionOneCount() {
        return optionOneCount;
    }

    public void setOptionOneCount(String optionOneCount) {
        this.optionOneCount = optionOneCount;
    }

    public String getOptionTwoValue() {
        return optionTwoValue;
    }

    public void setOptionTwoValue(String optionTwoValue) {
        this.optionTwoValue = optionTwoValue;
    }

    public String getOptionTwoCount() {
        return optionTwoCount;
    }

    public void setOptionTwoCount(String optionTwoCount) {
        this.optionTwoCount = optionTwoCount;
    }

    public String getQuestionBody() {
        return QuestionBody;
    }

    public void setQuestionBody(String questionBody) {
        QuestionBody = questionBody;
    }

    public String getOptionID() {
        return OptionID;
    }

    public void setOptionID(String optionID) {
        OptionID = optionID;
    }
}
