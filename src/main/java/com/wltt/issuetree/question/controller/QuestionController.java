package com.wltt.issuetree.question.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wltt.issuetree.question.model.EventJson;
//import com.wltt.issuetree.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
   // private final QuestionService questionService;

    @PostMapping(value = "/issue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eventHandle(@RequestBody Map<String, Object> data) {
        try{
            EventJson eventJson = new EventJson(data);
            log.info("event type:"+eventJson.getEventType());
            /*if (eventJson.getEventType().equals("message")) {
                String responseText = questionService.appMentionResponse(eventJson.getText());
                eventJson.setResultText(responseText);
            }*/
            eventJson.setJson();
            System.out.println(eventJson.getJson());
            return new ResponseEntity<>(eventJson.getJson(), HttpStatus.OK);
            } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    }

