package com.wltt.issuetree.global.slasckbot.controller;

import com.wltt.issuetree.global.slasckbot.dto.ValidDto;
import com.wltt.issuetree.global.slasckbot.form.SlackUrlEncodeForm;
import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.awt.*;


@RestController
public class SlackbotController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SlackbotService slackbotService;

    public SlackbotController(SlackbotService slackbotService) {
        this.slackbotService = slackbotService;
    }
/*    @PostMapping(value = "/solve", comsumes = MediaType.APPLICATION_FROM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> solve(@RequestBody MultiValueMap<String, Object> data){
        try {
            SlackUrlEncodeForm slackUrlEncodeForm = new SlackUrlEncodeForm(data);
            log.info("command:" + slackUrlEncodeForm.getCommand());
            String responseText = slackbotService.slashCommandResponse(slackUrlEncodeForm.getIssue(),
                    slackUrlEncodeForm.getChannelId(),
                    slackUrlEncodeForm.getQuestioner());
            slackbotService.settReault(responseText);

            slackbotService.setJson();
            return new ResponseEntity<>(slackUrlEncodeForm.getJson(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }

    }*/

/*   @PostMapping("/issue")
    public String validateURL(@RequestBody ValidDto req) {
        return req.getChallenge();
    }*/



}

