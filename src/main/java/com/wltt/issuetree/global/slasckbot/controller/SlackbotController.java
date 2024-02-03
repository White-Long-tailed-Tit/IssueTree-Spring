package com.wltt.issuetree.global.slasckbot.controller;

import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SlackbotController {

    @Autowired
    SlackbotService slackbotService;

}
