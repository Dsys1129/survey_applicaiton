package com.surveyapplication.domain;

import lombok.Getter;

@Getter
public class SurveyResponse {
    private Long id;
    private Long surveyId;
    private Long userId;

    public SurveyResponse(Long surveyId, Long userId) {
        this.surveyId = surveyId;
        this.userId = userId;
    }
}
