package com.surveyapplication.dto.response;

import com.surveyapplication.domain.Choice;
import com.surveyapplication.domain.Question;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {
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

    public static QuestionDTO from(Question question, List<Choice> choices) {
        List<ChoiceOptionDTO> choiceOptionDTOs = choices.stream()
                .filter(choice -> choice.getQuestionId().equals(question.getId()))
                .map(ChoiceOptionDTO::new)
                .collect(Collectors.toList());

        return new QuestionDTO(question, choiceOptionDTOs);
    }
}
