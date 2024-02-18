package com.wltt.issuetree.question.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.slack.api.methods.SlackApiException;
import com.wltt.issuetree.question.domain.Question;
import com.wltt.issuetree.question.model.EventJson;
//import com.wltt.issuetree.question.service.QuestionService;
import com.wltt.issuetree.question.service.QuestionService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final QuestionService questionService;

    @PostMapping(value = "/issue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eventHandle(@RequestBody Map<String, Object> data) {
        try {
            EventJson eventJson = new EventJson(data);
            eventJson.setJson();
            String ts = eventJson.getTs();
            if (eventJson.getSubType()!=null && eventJson.getSubType().equals("message_changed")) {
                if (questionService.isTsExist(ts)) {
                    log.info("이미 처리된 이벤트입니다. 중복 이벤트를 건너뛰고 있습니다.", ts);
                    return ResponseEntity.ok().body("중복된 이벤트입니다. 이미 처리되었습니다.");
                }
                questionService.appMentionResponse(ts, eventJson.getChannel());
            }

            return new ResponseEntity<>(eventJson.getJson(), HttpStatus.OK);

        } catch (SlackApiException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }

