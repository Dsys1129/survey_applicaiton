package com.surveyapplication.service;

import com.surveyapplication.domain.*;
import com.surveyapplication.dto.request.SurveyCreateRequestDTO;
import com.surveyapplication.dto.request.SurveySubmitRequestDTO;
import com.surveyapplication.dto.response.SurveyDetailResponseDTO;
import com.surveyapplication.dto.response.SurveyResultResponseDTO;
import com.surveyapplication.dto.response.SurveyStatisticsResponseDTO;
import com.surveyapplication.mapper.ChoiceStatisticsVO;
import com.surveyapplication.mapper.SurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyService {

    private final SurveyMapper surveyMapper;

    @Transactional
    public Map<String, Long> createSurvey(SurveyCreateRequestDTO requestDTO, User user) {
        LocalDateTime now = LocalDateTime.now();
        Survey survey = new Survey(requestDTO.getDescription(), user.getId(), now, SurveyStatus.IN_PROGRESS);
        surveyMapper.saveSurvey(survey);
        log.info("surveyId = {}", survey.getId());

        saveQuestionsAndChoices(survey.getId(), requestDTO);

        return Collections.singletonMap("surveyId", survey.getId());
    }

    // N + 1
//    @Transactional(readOnly = true)
//    public Map<String, SurveyDetailResponseDTO> getSurveyDetail(Long id) {
//        // 설문 조회
//        Survey surveyById = surveyMapper.findSurveyById(id);
//        // 문항 조회
//        List<Question> questions = surveyMapper.findQuestionsBySurveyId(surveyById.getId());
//
//        List<SurveyDetailResponseDTO.QuestionDTO> questionDTOs = questions.stream().map(question -> {
//            // 문항 마다 선택지 조회(N + 1)
//            List<Choice> choicesByQuestionId = surveyMapper.findChoicesByQuestionId(question.getId());
//            List<SurveyDetailResponseDTO.QuestionDTO.ChoiceOptionDTO> choiceOptionDTOs = choicesByQuestionId.stream()
//                    .map(choice -> new SurveyDetailResponseDTO.QuestionDTO.ChoiceOptionDTO(choice.getId(), choice.getText()))
//                    .collect(Collectors.toList());
//            return new SurveyDetailResponseDTO.QuestionDTO(question.getId(), question.getDescription(), question.getType().name(), choiceOptionDTOs);
//        }).collect(Collectors.toList());
//
//        SurveyDetailResponseDTO surveyDTO = new SurveyDetailResponseDTO(
//                surveyById.getId(),
//                surveyById.getDescription(),
//                surveyById.getUserId(),
//                surveyById.getStatus().name(),
//                surveyById.getCreatedAt(),
//                questionDTOs
//        );
//
//        return Collections.singletonMap("data", surveyDTO);
//    }

    @Transactional(readOnly = true)
    public Map<String, SurveyDetailResponseDTO> getSurveyDetail(Long id) {
        Survey survey = surveyMapper.findSurveyById(id);
        List<Question> questions = surveyMapper.findQuestionsBySurveyId(survey.getId());
        List<Long> questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        List<Choice> choicesByQuestionIds = surveyMapper.findChoicesByQuestionIds(questionIds);

        SurveyDetailResponseDTO surveyDetailResponseDTO = SurveyDetailResponseDTO.from(survey, questions, choicesByQuestionIds);
        return Collections.singletonMap("data", surveyDetailResponseDTO);
    }

    @Transactional
    public Map<String, Long> submitSurvey(Long id, SurveySubmitRequestDTO requestDTO, User user) {
        SurveyResponse surveyResponse = new SurveyResponse(id, user.getId());
        // surveyResponse 저장
        surveyMapper.saveSurveyResponse(surveyResponse);

        for (SurveySubmitRequestDTO.QuestionRequestDTO question : requestDTO.getQuestions()) {
            for (String answer : question.getAnswer()) {
                SurveyAnswer surveyAnswer = new SurveyAnswer(surveyResponse.getId(), question.getQuestionId(), answer);
                // 응답 저장
                surveyMapper.saveSurveyAnswer(surveyAnswer);
            }
        }
        return Collections.singletonMap("data", surveyResponse.getSurveyId());
    }

    @Transactional(readOnly = true)
    public Map<String, List<Long>> getSurveyList() {
        List<Long> surveys = surveyMapper.findSurveys();

        return Collections.singletonMap("data", surveys);
    }

    @Transactional
    public Map<String, SurveyResultResponseDTO> getSurveyResult(Long surveyId, Long userId) {
        // SurveyResponse 조회
        SurveyResponse surveyResponse = surveyMapper.findSurveyResponseBySurveyIdAndUserId(surveyId, userId)
                .orElseThrow(() -> new IllegalArgumentException("설문조사 응답이 없습니다."));

        // Survey Detail
        // Survey 조회
        Survey survey = surveyMapper.findSurveyById(surveyResponse.getSurveyId());
        // Questions 조회
        List<Question> questions = surveyMapper.findQuestionsBySurveyId(surveyResponse.getSurveyId());
        // choice 조회
        List<Long> questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        List<Choice> choicesByQuestionIds = surveyMapper.findChoicesByQuestionIds(questionIds);

        // answer 조회
        List<SurveyAnswer> surveyAnswers = surveyMapper.findSurveyAnswersBySurveyResponseId(surveyResponse.getId());

        SurveyResultResponseDTO surveyResultResponseDTO = SurveyResultResponseDTO
                .from(survey, userId, questions, choicesByQuestionIds, surveyAnswers);

        return Collections.singletonMap("data", surveyResultResponseDTO);
    }

    @Transactional(readOnly = true)
    public Map<String, List<SurveyResponse>> getSurveyResultList() {
        List<SurveyResponse> surveysResults = surveyMapper.findSurveysResults();
        return Collections.singletonMap("data", surveysResults);
    }

    @Transactional(readOnly = true)
    public Map<String, SurveyStatisticsResponseDTO> getSurveyStatisticsResult(Long surveyId) {
        Survey survey = surveyMapper.findSurveyById(surveyId);
        List<ChoiceStatisticsVO> choiceStatisticsVOs = surveyMapper.findChoiceAndChoicePercentageBy(surveyId);

        SurveyStatisticsResponseDTO result = SurveyStatisticsResponseDTO.from(survey, choiceStatisticsVOs);

        return Collections.singletonMap("data", result);
    }

    @Transactional
    public Map<String, Long> modifySurvey(Long surveyId, User user, SurveyCreateRequestDTO requestDTO) {
        boolean hasSurveyResponse = surveyMapper.existSurveyResponseBySurveyId(surveyId);
        if (hasSurveyResponse) {
            throw new IllegalArgumentException("설문을 수정할 수 없습니다.");
        }
        Survey survey = surveyMapper.findSurveyById(surveyId);

        if (!survey.getId().equals(user.getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        surveyMapper.updateSurveyById(surveyId, requestDTO.getDescription());

        List<Question> questions = surveyMapper.findQuestionsBySurveyId(surveyId);
        List<Long> questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        surveyMapper.deleteChoiceByQuestionIds(questionIds);
        surveyMapper.deleteQuestionsBySurveyId(surveyId);

        saveQuestionsAndChoices(surveyId, requestDTO);

        return Collections.singletonMap("surveyId", surveyId);
    }

    private void saveQuestionsAndChoices(Long surveyId, SurveyCreateRequestDTO requestDTO) {
        requestDTO.getQuestions().forEach(questionDTO -> {
            Question question = new Question(surveyId, questionDTO.getText(), questionDTO.getType());
            surveyMapper.saveQuestion(question);

            questionDTO.getChoices().forEach(choiceDTO -> {
                Choice choice = new Choice(choiceDTO.getText(), question.getId());
                surveyMapper.saveChoice(choice);
            });
        });
    }
}
