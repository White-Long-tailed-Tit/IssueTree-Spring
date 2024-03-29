package com.wltt.issuetree.user.service;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.user.domain.User;
import com.wltt.issuetree.user.domain.repository.UserRepository;
import com.wltt.issuetree.user.parser.UserParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SlackbotService slackbotService;

    @Transactional
    public void addUser(final SlackUrlEncodedForm slackUrlEncodedForm) {

        System.out.println(slackUrlEncodedForm.getUserId());
        final Optional<User> optionalUser = userRepository.findBySlackId(slackUrlEncodedForm.getUserId());

        User user;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
            user.setGithubId(UserParser.parseGithubId(slackUrlEncodedForm));
            chatModifyMessage(slackUrlEncodedForm);
        } else {
            user = UserParser.parseToUser(slackUrlEncodedForm);
            chatRegistrationMessage(slackUrlEncodedForm);
        }
        userRepository.save(user);
    }

    private void chatRegistrationMessage(final SlackUrlEncodedForm slackUrlEncodedForm) {
        final String header = "정상적으로 깃허브 아이디가 등록되었습니다.";
        String content
                =
                "*깃허브 아이디:*\n" +
                        ">" + slackUrlEncodedForm.getText() + "\n";

        slackbotService.chatMessage(
                content,
                header,
                slackUrlEncodedForm.getUserId()
        );
    }

    private void chatModifyMessage(final SlackUrlEncodedForm slackUrlEncodedForm) {
        final String header = "정상적으로 깃허브 아이디가 수정되었습니다.";
        String content
                =
                "*깃허브 아이디:*\n" +
                        ">" + slackUrlEncodedForm.getText() + "\n";

        slackbotService.chatMessage(
                content,
                header,
                slackUrlEncodedForm.getUserId()
        );
    }
}
