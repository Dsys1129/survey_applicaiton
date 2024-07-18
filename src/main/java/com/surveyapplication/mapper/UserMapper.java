package com.surveyapplication.mapper;

import com.surveyapplication.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByUserId(String userId);

    void save(User user);

    User findByUserIdAndPassword(String userId, String password);
}
