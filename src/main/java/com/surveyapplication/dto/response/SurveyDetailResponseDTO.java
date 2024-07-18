package com.surveyapplication.dto.response;

import com.surveyapplication.domain.Choice;
import com.surveyapplication.domain.Question;
import com.surveyapplication.domain.Survey;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SurveyDetailResponseDTO {
    private Long id;
    private String description;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private List<QuestionDTO> questions;

    private SurveyDetailResponseDTO(Survey survey, List<QuestionDTO> questions) {
        this.id = survey.getId();
        this.description = survey.getDescription();
        this.userId = survey.getUserId();
        this.status = survey.getStatus().name();
        this.createdAt = survey.getCreatedAt();
        this.questions = questions;
    }

    public static SurveyDetailResponseDTO from(Survey survey, List<Question> questions, List<Choice> choices) {
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(question -> QuestionDTO.from(question, choices))
                .collect(Collectors.toList());

        return new SurveyDetailResponseDTO(survey, questionDTOs);
    }
}
