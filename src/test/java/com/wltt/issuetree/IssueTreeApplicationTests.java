package com.wltt.issuetree;

import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class IssueTreeApplicationTests {

	@Autowired
	private SlackbotService slackbotService;

	@Test
	void sendMessageTest(){

	}

	@Test
	void contextLoads() {
	}

}
