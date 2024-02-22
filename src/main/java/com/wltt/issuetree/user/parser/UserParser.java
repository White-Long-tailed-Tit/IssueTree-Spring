package com.wltt.issuetree.user.parser;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.user.domain.User;

public class UserParser {
    public static User parseToUser(final SlackUrlEncodedForm request) {
        final String userId = request.getUserId();
        final String githubId = request.getText();

        return User.of()
                .slackId(userId)
                .githubId(githubId)
                .build();
    }

    public static String parseGithubId(final SlackUrlEncodedForm request) {
        return request.getText();
    }
}
