package com.wltt.issuetree.question.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EventJson {

    private Map<String, String> event = null;
    private Map<String, String> message = null;
    private String text = null;
    private String user = null;
    private String eventId = null;
    private String eventType = "N";
    private String subType = null;
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
                this.subType = event.get("subtype");
                this.channel = event.get("channel");
                if (subType.equals("message_changed")) {
                    Object msg = event.get("message");
                    this.message = (Map<String, String>)msg;
                    this.ts = message.get("ts");
                    result.put("channel", channel);
                    result.put("ts", ts);
                }
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
