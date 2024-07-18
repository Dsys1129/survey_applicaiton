package com.surveyapplication.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveySubmitRequestDTO {
    private List<QuestionRequestDTO> questions = new ArrayList<>();

    @Getter
    public static class QuestionRequestDTO {
        private Long questionId;
        private List<String> answer = new ArrayList<>();
    }
}
