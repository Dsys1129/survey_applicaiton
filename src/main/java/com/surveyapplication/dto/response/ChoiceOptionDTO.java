package com.surveyapplication.dto.response;

import com.surveyapplication.domain.Choice;
import lombok.Data;

@Data
public class ChoiceOptionDTO {
    private Long id;
    private String text;

    public ChoiceOptionDTO(Choice choice) {
        this.id = choice.getId();
        this.text = choice.getText();
    }
}
