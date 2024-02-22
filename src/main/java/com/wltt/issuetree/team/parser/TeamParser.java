package com.wltt.issuetree.team.parser;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.team.domain.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamParser {
    public static Team parseToTeam(SlackUrlEncodedForm request) {
        final String text = request.getText();
        final String channelId = parseChannelId(text);
        final List<String> packageList = parsePackageList(text);

        return Team.of()
                .channelId(channelId)
                .packageList(packageList)
                .build();
    }

    public static String parseChannelId(String text) {
        int startIndex = text.indexOf("<#") + 2;
        int endIndex = text.indexOf("|"); // <#id|> : |를 endIndex로 설정함
        return text.substring(startIndex, endIndex).trim();
    }

    public static List<String> parsePackageList(String text) {
        int endIndex = text.indexOf(">");

        String[] packages = text.substring(endIndex + 2).split("\\s+");
        List<String> packageList = new ArrayList<>();

        for (String packageName : packages) {
            packageList.add(packageName.trim());
        }

        return packageList;
    }
}
