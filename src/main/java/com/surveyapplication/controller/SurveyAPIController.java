package com.surveyapplication.controller;

import com.surveyapplication.auth.LoginUser;
import com.surveyapplication.domain.SurveyResponse;
import com.surveyapplication.domain.User;
import com.surveyapplication.dto.request.SurveyCreateRequestDTO;
import com.surveyapplication.dto.request.SurveySubmitRequestDTO;
import com.surveyapplication.dto.response.SurveyDetailResponseDTO;
import com.surveyapplication.dto.response.SurveyResultResponseDTO;
import com.surveyapplication.dto.response.SurveyStatisticsResponseDTO;
import com.surveyapplication.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SurveyAPIController {

    private final SurveyService surveyService;

    @PostMapping("/surveys")
    public ResponseEntity<?> createSurvey(@RequestBody SurveyCreateRequestDTO requestDTO, @LoginUser User user) {
        Map<String, Long> response = surveyService.createSurvey(requestDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/surveys/{id}")
    public ResponseEntity<?> submitSurvey(@PathVariable Long id, @RequestBody SurveySubmitRequestDTO requestDTO, @LoginUser User user) {
        surveyService.submitSurvey(id, requestDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/surveys")
    public ResponseEntity<?> getSurveyList() {
        Map<String, List<Long>> response = surveyService.getSurveyList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/surveys/{id}")
    public ResponseEntity<?> getSurveyDetail(@PathVariable Long id) {
        Map<String, SurveyDetailResponseDTO> response = surveyService.getSurveyDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/surveys/{surveyId}/result")
    public ResponseEntity<?> getSurveyResult(@PathVariable Long surveyId, @RequestParam Long userId) {
        Map<String, SurveyResultResponseDTO> response = surveyService.getSurveyResult(surveyId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/surveys/results")
    public ResponseEntity<?> getSurveyResultList() {
        Map<String, List<SurveyResponse>> response = surveyService.getSurveyResultList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/statistics/{surveyId}")
    public ResponseEntity<?> getSurveyStatisticsInfo(@PathVariable Long surveyId) {
        Map<String, SurveyStatisticsResponseDTO> response = surveyService.getSurveyStatisticsResult(surveyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/surveys/{id}")
    public ResponseEntity<?> modifySurvey(@PathVariable Long id, @LoginUser User user, @RequestBody SurveyCreateRequestDTO requestDTO) {
        surveyService.modifySurvey(id, user, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
