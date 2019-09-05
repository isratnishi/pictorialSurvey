package com.opus_bd.pictorialsurvey.Model;

import java.io.Serializable;

public class Survey implements Serializable {
    private String surveyCondition;
    private String surveyName;
    private String description;
    private String password;
    private Question question;
    private String  key;

    public String getSurveyCondition() {
        return surveyCondition;
    }

    public void setSurveyCondition(String surveyCondition) {
        this.surveyCondition = surveyCondition;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Survey() {
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
