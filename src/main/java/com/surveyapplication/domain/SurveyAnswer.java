package com.surveyapplication.domain;

import lombok.Getter;

@Getter
public class SurveyAnswer {
    private Long id;
    private Long surveyResponseId;
    private Long questionId;
    private String answer;

    public SurveyAnswer(Long surveyResponseId, Long questionId, String answer) {
        this.surveyResponseId = surveyResponseId;
        this.questionId = questionId;
        this.answer = answer;
    }
}
