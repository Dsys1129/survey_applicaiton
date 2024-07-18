package com.surveyapplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Question, Survey
// N : 1
@NoArgsConstructor
@Getter
public class Question {
    private Long id;
    private Long surveyId;
    private String description;
    private QuestionType type;
    private List<Choice> choices = new ArrayList<>();

    public Question(Long surveyId, String description, String type) {
        this.surveyId = surveyId;
        this.description = description;
        this.type = QuestionType.valueOf(type);
    }


}
