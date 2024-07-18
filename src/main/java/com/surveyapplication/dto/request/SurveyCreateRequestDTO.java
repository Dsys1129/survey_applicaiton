package com.surveyapplication.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveyCreateRequestDTO {

    //Survey
    private String description;
    //Question
    private List<QuestionRequestDTO> questions;


    @Getter
    public static class QuestionRequestDTO {
        private String text;
        private String type;
        private List<ChoiceRequestDTO> choices = new ArrayList<>();
    }

    @Getter
    public static class ChoiceRequestDTO {
        private Integer id;
        private String text;
    }
}
