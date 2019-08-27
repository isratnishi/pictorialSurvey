package com.opus_bd.pictorialsurvey.Model;

public class VotingModel {
    String serVay_ID;
    String selectedOptionValue;
    String selectedOptionID;
    String OptionOne;
    String OptionTwo;
    String OptionThree;
    String OptionFour;
    String QuestuionID;
    String QuestuionValue;
    String OptionType;
    String optionOneID;
    String optionTwoID;
    String optionThreeID;
    String optionFourID;
    String nonSelectedID1;

    public VotingModel(String serVay_ID, String selectedOptionValue, String selectedOptionID, String optionOne, String optionTwo, String optionThree, String optionFour, String questuionID, String questuionValue, String optionType, String optionOneID, String optionTwoID, String optionThreeID, String optionFourID, String nonSelectedID1, String nonSelectedID2, String nonSelectedID3) {
        this.serVay_ID = serVay_ID;
        this.selectedOptionValue = selectedOptionValue;
        this.selectedOptionID = selectedOptionID;
        OptionOne = optionOne;
        OptionTwo = optionTwo;
        OptionThree = optionThree;
        OptionFour = optionFour;
        QuestuionID = questuionID;
        QuestuionValue = questuionValue;
        OptionType = optionType;
        this.optionOneID = optionOneID;
        this.optionTwoID = optionTwoID;
        this.optionThreeID = optionThreeID;
        this.optionFourID = optionFourID;
        this.nonSelectedID1 = nonSelectedID1;
        this.nonSelectedID2 = nonSelectedID2;
        this.nonSelectedID3 = nonSelectedID3;
    }

    public String getNonSelectedID1() {
        return nonSelectedID1;
    }

    public void setNonSelectedID1(String nonSelectedID1) {
        this.nonSelectedID1 = nonSelectedID1;
    }

    public String getNonSelectedID2() {
        return nonSelectedID2;
    }

    public void setNonSelectedID2(String nonSelectedID2) {
        this.nonSelectedID2 = nonSelectedID2;
    }

    public String getNonSelectedID3() {
        return nonSelectedID3;
    }

    public void setNonSelectedID3(String nonSelectedID3) {
        this.nonSelectedID3 = nonSelectedID3;
    }

    String nonSelectedID2;
    String nonSelectedID3;





    public String getOptionOneID() {
        return optionOneID;
    }

    public void setOptionOneID(String optionOneID) {
        this.optionOneID = optionOneID;
    }

    public String getOptionTwoID() {
        return optionTwoID;
    }

    public void setOptionTwoID(String optionTwoID) {
        this.optionTwoID = optionTwoID;
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

    public String getOptionThree() {
        return OptionThree;
    }

    public void setOptionThree(String optionThree) {
        OptionThree = optionThree;
    }

    public String getOptionFour() {
        return OptionFour;
    }

    public void setOptionFour(String optionFour) {
        OptionFour = optionFour;
    }

    public String getOptionThreeID() {
        return optionThreeID;
    }

    public void setOptionThreeID(String optionThreeID) {
        this.optionThreeID = optionThreeID;
    }

    public String getOptionFourID() {
        return optionFourID;
    }

    public void setOptionFourID(String optionFourID) {
        this.optionFourID = optionFourID;
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
