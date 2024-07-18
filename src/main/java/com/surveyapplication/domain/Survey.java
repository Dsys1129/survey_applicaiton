package com.surveyapplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class Survey {

    private Long id;
    private String description;
    private Long userId;
    private SurveyStatus status;
    private LocalDateTime createdAt;
    private List<Question> questions = new ArrayList<>();

    public Survey(String description, Long userId, LocalDateTime createdAt, SurveyStatus status) {
        this.description = description;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
    }
}
