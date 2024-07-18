package com.surveyapplication.config;

import com.surveyapplication.domain.SurveyStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class SurveyStatusHandler extends BaseTypeHandler<SurveyStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SurveyStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public SurveyStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String name = rs.getString(columnName);
        return name == null ? null : SurveyStatus.valueOf(name);
    }

    @Override
    public SurveyStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String name = rs.getString(columnIndex);
        return name == null ? null : SurveyStatus.valueOf(name);
    }

    @Override
    public SurveyStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String name = cs.getString(columnIndex);
        return name == null ? null : SurveyStatus.valueOf(name);
    }
}
