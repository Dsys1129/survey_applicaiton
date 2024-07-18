package com.surveyapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.surveyapplication.domain.Survey;
import com.surveyapplication.mapper.ChoiceStatisticsVO;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class SurveyStatisticsResponseDTO {
    private Long surveyId;
    private String surveyDescription;
    private List<QuestionDTO> questions;

    private SurveyStatisticsResponseDTO(Survey survey, List<QuestionDTO> questionDTOs) {
        this.surveyId = survey.getId();
        this.surveyDescription = survey.getDescription();
        this.questions = questionDTOs;
    }

    public static SurveyStatisticsResponseDTO from(Survey survey, List<ChoiceStatisticsVO> choiceStatistics) {
        Map<Long, List<ChoiceStatisticsVO>> groupedByQuestion = groupByQuestion(choiceStatistics);
        List<QuestionDTO> questionDTOs = convertToQuestionDTOs(groupedByQuestion);
        return new SurveyStatisticsResponseDTO(survey, questionDTOs);
    }

    @Data
    public static class QuestionDTO {
        private Long id;
        private String description;
        private String type;
        private List<ChoiceOptionDTO> choices;

        private QuestionDTO(Long id, String description, String type, List<ChoiceOptionDTO> choices) {
            this.id = id;
            this.description = description;
            this.type = type;
            this.choices = choices;
        }

        private static QuestionDTO from(ChoiceStatisticsVO choiceStatisticsVO, List<ChoiceStatisticsVO> choices) {
            List<ChoiceOptionDTO> choiceOptionDTOs = choices.stream()
                    .map(ChoiceOptionDTO::from)
                    .collect(Collectors.toList());

            return new QuestionDTO(
                    choiceStatisticsVO.getQuestionId(),
                    choiceStatisticsVO.getDescription(),
                    choiceStatisticsVO.getQuestionType(),
                    choiceOptionDTOs
            );
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class ChoiceOptionDTO {
            private Long id;
            private String text;
            private Double percentage;

            private ChoiceOptionDTO(Long id, String text, Double percentage) {
                this.id = id;
                this.text = text;
                this.percentage = percentage;
            }

            public static ChoiceOptionDTO from(ChoiceStatisticsVO choiceStatisticsVO) {
                return new ChoiceOptionDTO(
                        choiceStatisticsVO.getChoiceId(),
                        choiceStatisticsVO.getChoiceText(),
                        choiceStatisticsVO.getChoicePercentage()
                );
            }
        }
    }

    private static Map<Long, List<ChoiceStatisticsVO>> groupByQuestion(List<ChoiceStatisticsVO> choiceStatistics) {
        return choiceStatistics.stream()
                .collect(Collectors.groupingBy(ChoiceStatisticsVO::getQuestionId));
    }

    private static List<QuestionDTO> convertToQuestionDTOs(Map<Long, List<ChoiceStatisticsVO>> groupedByQuestion) {
        return groupedByQuestion.entrySet().stream()
                .map(entry -> {
                    Long questionId = entry.getKey();
                    List<ChoiceStatisticsVO> choices = entry.getValue();
                    return QuestionDTO.from(choices.get(0), choices);
                })
                .collect(Collectors.toList());
    }
}
