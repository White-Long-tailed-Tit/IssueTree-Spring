package com.wltt.issuetree.question.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EventJson {

    private Map<String, String> event = null;
    private String text = null;
    private String user = null;
    private String eventType = "N";
    private String channel = null;
    private String ts = null;
    private String json = null;
    private Map<String, String> result = new HashMap<>();

    public EventJson(Map<String, Object> data){
        String type = String.valueOf(data.get("type"));
        if (type.equals("url_verification")){
            result.put("challenge", String.valueOf(data.get("challenge")));
        } else {
            Object item = data.get("event");
            if (item != null && type.equals("event_callback")){
                this.event = (Map<String, String>)item;
                this.eventType = event.get("type");
                this.text = event.get("text");
                this.user = event.get("user");
                this.channel = event.get("channel");
                this.ts = event.get("ts");
                result.put("channel", channel);
                result.put("user", user);
                result.put("ts", ts);
                result.put("text", text);
            }
        }
    }

    public void setResultText(String responseText){
        result.put("text", responseText);
    }

    public void setJson() throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        this.json = objectMapper.writeValueAsString(result);
    }
}
