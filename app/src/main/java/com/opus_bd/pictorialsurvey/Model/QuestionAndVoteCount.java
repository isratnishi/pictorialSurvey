package com.opus_bd.pictorialsurvey.Model;

public class QuestionAndVoteCount {
    String QuestionBody;
    String OptionID;
    String optionOneValue;
    String optionOneCount;
    String optionTwoValue;
    String optionTwoCount;
    String optionThreeValue;
    String optionThreeCount;
    String optionFourValue;
    String optionFourCount;
    int firstoption;
    int secondoption;
    int thirdoption;
    int forthoption;
    String type;

    public QuestionAndVoteCount(String questionBody, String optionID, String optionOneValue, String optionOneCount, String optionTwoValue, String optionTwoCount, String optionThreeValue, String optionThreeCount, String optionFourValue, String optionFourCount, int firstoption, int secondoption, int thirdoption, int forthoption, String type) {
        QuestionBody = questionBody;
        OptionID = optionID;
        this.optionOneValue = optionOneValue;
        this.optionOneCount = optionOneCount;
        this.optionTwoValue = optionTwoValue;
        this.optionTwoCount = optionTwoCount;
        this.optionThreeValue = optionThreeValue;
        this.optionThreeCount = optionThreeCount;
        this.optionFourValue = optionFourValue;
        this.optionFourCount = optionFourCount;
        this.firstoption = firstoption;
        this.secondoption = secondoption;
        this.thirdoption = thirdoption;
        this.forthoption = forthoption;
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

    public String getOptionThreeValue() {
        return optionThreeValue;
    }

    public void setOptionThreeValue(String optionThreeValue) {
        this.optionThreeValue = optionThreeValue;
    }

    public String getOptionThreeCount() {
        return optionThreeCount;
    }

    public void setOptionThreeCount(String optionThreeCount) {
        this.optionThreeCount = optionThreeCount;
    }

    public String getOptionFourValue() {
        return optionFourValue;
    }

    public void setOptionFourValue(String optionFourValue) {
        this.optionFourValue = optionFourValue;
    }

    public String getOptionFourCount() {
        return optionFourCount;
    }

    public void setOptionFourCount(String optionFourCount) {
        this.optionFourCount = optionFourCount;
    }

    public int getThirdoption() {
        return thirdoption;
    }

    public void setThirdoption(int thirdoption) {
        this.thirdoption = thirdoption;
    }

    public int getForthoption() {
        return forthoption;
    }

    public void setForthoption(int forthoption) {
        this.forthoption = forthoption;
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
