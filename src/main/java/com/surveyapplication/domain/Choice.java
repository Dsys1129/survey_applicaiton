package com.surveyapplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 객관식 문항 , 질문
// N : 1
@NoArgsConstructor
@Getter
public class Choice {
    private Long id;
    private String text;
    private Long questionId;

    public Choice(String text, Long questionId) {
        this.text = text;
        this.questionId = questionId;
    }
}
