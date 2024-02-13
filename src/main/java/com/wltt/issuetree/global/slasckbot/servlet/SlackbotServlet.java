package com.wltt.issuetree.global.slasckbot.servlet;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.servlet.annotation.WebServlet;

@WebServlet("/solve")
public class SlackbotServlet extends SlackAppServlet {
    public SlackbotServlet(@Qualifier("issueTree") App app){ super(app);}
    
}
