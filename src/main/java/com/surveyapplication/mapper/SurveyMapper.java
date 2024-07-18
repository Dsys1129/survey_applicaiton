package com.surveyapplication.mapper;

import com.surveyapplication.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SurveyMapper {
    void saveSurvey(Survey survey);

    void saveChoice(Choice choice);

    void saveQuestion(Question question);

    void saveSurveyResponse(SurveyResponse surveyResponse);

    void saveSurveyAnswer(SurveyAnswer surveyAnswer);

    Survey findSurveyById(Long surveyId);

    List<Question> findQuestionsBySurveyId(Long surveyId);

    List<Choice> findChoicesByQuestionId(Long questionId);

    List<Choice> findChoicesByQuestionIds(List<Long> questionIds);

    List<Long> findSurveys();

    Optional<SurveyResponse> findSurveyResponseBySurveyIdAndUserId(Long surveyId, Long userId);

    List<SurveyAnswer> findSurveyAnswersBySurveyResponseId(Long surveyResponseId);

    List<SurveyResponse> findSurveysResults();

    List<ChoiceStatisticsVO> findChoiceAndChoicePercentageBy(Long surveyId);

    void updateSurveyById(Long id, String description);

    void deleteChoiceByQuestionIds(List<Long> questionIds);

    void deleteQuestionsBySurveyId(Long surveyId);

    boolean existSurveyResponseBySurveyId(Long surveyId);
}
