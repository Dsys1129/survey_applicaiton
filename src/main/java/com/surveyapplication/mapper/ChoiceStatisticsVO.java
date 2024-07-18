package com.surveyapplication.mapper;

import lombok.Getter;

@Getter
public class ChoiceStatisticsVO {

    private Long questionId;
    private String description;
    private String questionType;
    private Long choiceId;
    private String choiceText;
    private double choicePercentage;

    public ChoiceStatisticsVO(Long questionId, String description, String questionType, Long choiceId, String choiceText, double choicePercentage) {
        this.questionId = questionId;
        this.description = description;
        this.questionType = questionType;
        this.choiceId = choiceId;
        this.choiceText = choiceText;
        this.choicePercentage = choicePercentage;
    }
}
