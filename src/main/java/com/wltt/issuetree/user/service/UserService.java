package com.wltt.issuetree.user.service;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.user.domain.User;
import com.wltt.issuetree.user.domain.repository.UserRepository;
import com.wltt.issuetree.user.parser.UserParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SlackbotService slackbotService;

    @Transactional
    public void addUser(SlackUrlEncodedForm slackUrlEncodedForm) {
        final User user = UserParser.parseToUser(slackUrlEncodedForm);
        userRepository.save(user);

        final String header = "정상적으로 깃허브 아이디가 등록되었습니다.";
        String content
                = "*요청자:*\n" +
                ">" + "<@" + slackUrlEncodedForm.getUserId() + ">\n" +
                "*저장된 깃허브 아이디:*\n" +
                ">" + slackUrlEncodedForm.getText() + "\n";

        slackbotService.chatMessage(
                content,
                header,
                slackUrlEncodedForm.getChannelId()
        );
    }
}
