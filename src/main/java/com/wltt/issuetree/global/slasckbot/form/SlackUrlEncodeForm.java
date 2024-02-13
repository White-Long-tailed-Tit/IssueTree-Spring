package com.wltt.issuetree.global.slasckbot.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SlackUrlEncodeForm {
    private final String channelId;
    private final String questioner;
    private final String issue;

    private String json = null;
    private Map<String, String> result = new HashMap<>();

    public SlackUrlEncodeForm(MultiValueMap<String, Object> data){
        this.channelId = String.valueOf(data.getFirst("channel_id"));
        this.questioner = String.valueOf(data.getFirst("user_id"));
        this.issue = String.valueOf(data.getFirst("text"));
    }

    public void setResult(String responseText){
        result.put("response_type", "in_channel");
        result.put("text", responseText);
    }

    public void setJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.json = objectMapper.writeValueAsString(result);
    }
}
