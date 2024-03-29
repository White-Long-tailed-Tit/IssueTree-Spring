package com.wltt.issuetree;

import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;

@SpringBootApplication
@EnableElasticsearchAuditing
public class IssueTreeApplication {


	public static void main(String[] args) {
		SpringApplication.run(IssueTreeApplication.class, args);
	}

}
