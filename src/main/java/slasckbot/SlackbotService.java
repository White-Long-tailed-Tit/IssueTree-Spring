package slasckbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackbotService {
    @Value(value = "${slack.token}")
    String slackToken;
    @Bean
    public void sendSlackMessage(String message){

    }
}
