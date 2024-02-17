package com.wltt.issuetree.question.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.model.Message;
import com.wltt.issuetree.question.domain.Question;
import com.wltt.issuetree.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final String botId = "B06HQ37R80Y";
    private final QuestionRepository questionRepository;
    private final MethodsClient methodsClient;
    private final RestTemplate restTemplate;

    @Value("${slack.token}")
    private String slackToken;

    @Value("${openai.api-key}")
    private String openaiApiKey;

    public Question appMentionResponse(String ts, String channelId) throws SlackApiException, IOException {
        Slack slack = Slack.getInstance();

        ConversationsRepliesResponse repliesResponse = slack.methods(slackToken)
                .conversationsReplies(req -> req
                        .channel(channelId)
                        .ts(ts));
        Question question = CreateQuestion(repliesResponse, ts);

        return question;
    }

    private String extractTextFromMessage(Message message, String field) {

        String[] lines = message.getText().split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(field)) {
                return lines[i + 1].trim();
            }
        }
        return null;
    }

    private Question CreateQuestion(ConversationsRepliesResponse repliesResponse, String ts) throws SlackApiException, IOException {
        List<Message> messages = repliesResponse.getMessages();
        Message msg = messages.get(0);
        if (msg != null && msg.getBotId().equals(botId)) {

            String stack = extractTextFromMessage(msg, "[ 스택 ]");
            String version = extractTextFromMessage(msg, "[ 버전 ]");
            String issue = extractTextFromMessage(msg, "[ 내용 ]");
            String questioner = findUserName(extractTextFromMessage(msg, "[ 보낸 사람 ]"));
            String solve = summarizeMessages(extractSolves(messages));



            Question question = Question.builder()
                    .stack(stack)
                    .version(version)
                    .issue(issue)
                    .questioner(questioner)
                    .solve(solve)
                    .ts(ts)
                    .build();

            questionRepository.save(question);
            return question;
        } else {
            if (msg == null)
                throw new RuntimeException("msg가 없습니다.");
            else {
                throw new RuntimeException("해당 워크플로우 메세지가 아닙니다.");
            }
        }
    }

    private String findUserName(String userId) throws SlackApiException, IOException {
        Pattern pattern = Pattern.compile("<@(.*?)>");
        Matcher matcher = pattern.matcher(userId);

        if (matcher.find()) {
            userId = matcher.group(1);
        }
        UsersInfoResponse response = methodsClient.usersInfo(UsersInfoRequest.builder().user(userId).build());
        if (response.isOk()) {
            return response.getUser().getRealName();
        } else {
            System.err.println("사용자 이름 탐색에 실패하였습니다." );
            return null;
        }
    }



}

