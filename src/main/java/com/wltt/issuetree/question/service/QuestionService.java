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
import java.util.Optional;
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

    private String extractSolves(List<Message> messages) throws SlackApiException, IOException {
        int size = messages.size();
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 1; i<size; i++){
            Message message = messages.get(i);
            String text = message.getText();
            String userName = findUserName(message.getUser());
            resultBuilder.append(userName).append(" : ").append(text).append("\n");
        }
        String result = resultBuilder.toString();
        return result;
    }

    private String summarizeMessages(String messages) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.put("model", "gpt-4");
        ArrayNode messagesArrayNode = requestBodyNode.putArray("messages");

        ObjectNode ObjectNode1 = objectMapper.createObjectNode();
        ObjectNode1.put("role", "system");
        ObjectNode1.put("content", "You are a helpful assistant.");
        messagesArrayNode.add(ObjectNode1);

        ObjectNode ObjectNode2 = objectMapper.createObjectNode();
        ObjectNode2.put("role", "user");
        ObjectNode2.put("content", messages + "를 요약해줘. 대신 링크는 그대로 두어줘. 코드도 그대로 두어줘. 말투는 공식문서처럼 한국어로 해줘. 의견을 제기한 이름도 넣어줘.");
        messagesArrayNode.add(ObjectNode2);


        try {
            String requestBody = objectMapper.writeValueAsString(requestBodyNode);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return extractSummaryFromResponse(responseEntity.getBody());
            } else {
                return "Messgaes를 요약하는 데에 실패하였습니다. Status code : " + responseEntity.getStatusCodeValue();
            }
        } catch (JsonProcessingException e) {
            return "Json Parsing에 실패하였습니다. " + e.getMessage();
        }
    }

    private String extractSummaryFromResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode choicesNode = rootNode.get("choices");

            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode assistantChoice = choicesNode.get(0);  // Assuming the assistant's reply is the first choice
                JsonNode contentNode = assistantChoice.get("message").get("content");

                if (contentNode != null) {
                    return contentNode.asText();
                }
            }
            return "응답에서 요약을 찾을 수 없습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "JSON 응답을 구문 분석하는 중 오류가 발생했습니다.";
        }
    }



    public boolean isTsExist(String ts) {
        Optional<Question> optionalQuestion = questionRepository.findByTs(ts);
        return optionalQuestion.isPresent();
    }



}

