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



}

