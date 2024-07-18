package com.surveyapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SurveyController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/surveys-create")
    public String renderSurveyCreatePage() {
        return "survey_create";
    }

    @GetMapping("/surveys-detail/{id}")
    public String renderSurveyDetail(@PathVariable Long id) {
        return "survey";
    }

    @GetMapping("/surveys/{surveyId}/result")
    public String renderSurveyResultPage() {
        return "survey_result";
    }

    @GetMapping("/result-list")
    public String renderSurveyResultListPage() {
        return "survey_result_list";
    }

    @GetMapping("/statistics/{surveyId}")
    public String renderSurveyStatisticsPage() {
        return "survey_statistics";
    }

    @GetMapping("/statistics-list")
    public String renderStatisticsListPage() {
        return "survey_statistics_list";
    }

    @GetMapping("/surveys-modify/{surveyId}")
    public String renderSurveyModifyPage() {
        return "survey_modify";
    }
}
