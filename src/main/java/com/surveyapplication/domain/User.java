package com.surveyapplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class User {
    private Long id;
    private String userId;
    private String password;
    private UserType userType;

    private User(String userId, String password, UserType userType) {
        this.userId = userId;
        this.password = password;
        this.userType = userType;
    }

    public static User createRegularMember(String userId, String password) {
        return new User(userId, password, UserType.USER);
    }
}
