package com.opus_bd.pictorialsurvey.Model;

public class VotingModel {
    String serVay_ID;
    String selectedOptionValue;
    String selectedOptionID;
    String OptionOne;
    String OptionTwo;
    String QuestuionID;
    String QuestuionValue;
    String OptionType;

    public VotingModel(String serVay_ID, String selectedOptionValue, String selectedOptionID, String optionOne, String optionTwo, String questuionID, String questuionValue, String optionType) {
        this.serVay_ID = serVay_ID;
        this.selectedOptionValue = selectedOptionValue;
        this.selectedOptionID = selectedOptionID;
        OptionOne = optionOne;
        OptionTwo = optionTwo;
        QuestuionID = questuionID;
        QuestuionValue = questuionValue;
        OptionType = optionType;
    }

    public String getSerVay_ID() {
        return serVay_ID;
    }

    public void setSerVay_ID(String serVay_ID) {
        this.serVay_ID = serVay_ID;
    }

    public String getSelectedOptionValue() {
        return selectedOptionValue;
    }

    public void setSelectedOptionValue(String selectedOptionValue) {
        this.selectedOptionValue = selectedOptionValue;
    }

    public String getSelectedOptionID() {
        return selectedOptionID;
    }

    public void setSelectedOptionID(String selectedOptionID) {
        this.selectedOptionID = selectedOptionID;
    }

    public String getOptionOne() {
        return OptionOne;
    }

    public void setOptionOne(String optionOne) {
        OptionOne = optionOne;
    }

    public String getOptionTwo() {
        return OptionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        OptionTwo = optionTwo;
    }

    public String getQuestuionID() {
        return QuestuionID;
    }

    public void setQuestuionID(String questuionID) {
        QuestuionID = questuionID;
    }

    public String getQuestuionValue() {
        return QuestuionValue;
    }

    public void setQuestuionValue(String questuionValue) {
        QuestuionValue = questuionValue;
    }

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String optionType) {
        OptionType = optionType;
    }
}
