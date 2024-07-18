package com.surveyapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.surveyapplication.domain.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SurveyResultResponseDTO {
    private Long surveyId;
    private String surveyDescription;
    private Long userId;
    private List<QuestionDTO> questions;

    private SurveyResultResponseDTO(Survey survey, Long userId, List<QuestionDTO> questionDTOs) {
        this.surveyId = survey.getId();
        this.surveyDescription = survey.getDescription();
        this.userId = userId;
        this.questions = questionDTOs;
    }

    public static SurveyResultResponseDTO from(Survey survey, Long userId, List<Question> questions, List<Choice> choices, List<SurveyAnswer> surveyAnswers) {
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(question -> QuestionDTO.from(question, choices, surveyAnswers))
                .collect(Collectors.toList());

        return new SurveyResultResponseDTO(survey, userId, questionDTOs);
    }


    @Data
    public static class QuestionDTO {
        private Long id;
        private String description;
        private String type;
        private List<ChoiceOptionDTO> choices;

        private QuestionDTO(Question question, List<ChoiceOptionDTO> choices) {
            this.id = question.getId();
            this.description = question.getDescription();
            this.type = question.getType().name();
            this.choices = choices;
        }

        private static QuestionDTO from(Question question, List<Choice> choices, List<SurveyAnswer> surveyAnswers) {
            if (question.getType() == QuestionType.SUBJECTIVE) {
                List<ChoiceOptionDTO> choiceOptionDTOs = surveyAnswers.stream()
                        .filter(answer -> answer.getQuestionId().equals(question.getId()))
                        .map(answer -> new ChoiceOptionDTO(null, answer.getAnswer(), null))
                        .collect(Collectors.toList());
                return new QuestionDTO(question, choiceOptionDTOs);
            }

            List<ChoiceOptionDTO> choiceOptionDTOs = choices.stream()
                    .filter(choice -> choice.getQuestionId().equals(question.getId()))
                    .map(choice -> ChoiceOptionDTO.from(choice, surveyAnswers))
                    .collect(Collectors.toList());
            return new QuestionDTO(question, choiceOptionDTOs);
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class ChoiceOptionDTO {
            private Long id;
            private String text;
            private Boolean checked;

            private ChoiceOptionDTO(Long id, String text, Boolean checked) {
                this.id = id;
                this.text = text;
                this.checked = checked;
            }

            private static ChoiceOptionDTO from(Choice choice, List<SurveyAnswer> surveyAnswers) {
                boolean checked = surveyAnswers.stream()
                        .anyMatch(answer -> answer.getAnswer().equals(choice.getId().toString()));

                return new ChoiceOptionDTO(choice.getId(), choice.getText(), checked);
            }
        }
    }
}
