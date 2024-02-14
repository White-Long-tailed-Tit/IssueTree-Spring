package com.wltt.issuetree.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SlackUrlEncodedForm {
    private final String channelName;
    private final String channelId;
    private final String command;
    private final String userId;
    private final String text;

    private String json = null;
    private Map<String, String> result = new HashMap<>();

    public SlackUrlEncodedForm(MultiValueMap<String, Object> data) {
        this.channelName = "#" + String.valueOf(data.getFirst("channel_name"));
        this.channelId = String.valueOf(data.getFirst("channel_id"));
        this.command = String.valueOf(data.getFirst("command"));
        this.userId = String.valueOf(data.getFirst("user_id"));
        this.text = String.valueOf(data.getFirst("text"));
    }

    public void setResult(String responseText) {
        result.put("response_type", "in_channel");
        result.put("text", responseText);
    }

    public void setJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.json = objectMapper.writeValueAsString(result);
    }
}