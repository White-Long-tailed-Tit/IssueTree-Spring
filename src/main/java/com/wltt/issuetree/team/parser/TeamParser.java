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

    private static String parseChannelId(String input) {
        int startIndex = input.indexOf("<#") + 2;
        int endIndex = input.indexOf(">");
        return input.substring(startIndex, endIndex).trim();
    }

    private static List<String> parsePackageList(String input) {
        int endIndex = input.indexOf(">");

        String[] packages = input.substring(endIndex + 2).split("\\s+");
        List<String> packageList = new ArrayList<>();

        for (String packageName : packages) {
            packageList.add(packageName.trim());
        }

        return packageList;
    }
}
