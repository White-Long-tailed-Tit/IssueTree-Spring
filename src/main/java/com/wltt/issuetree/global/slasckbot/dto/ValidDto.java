package com.wltt.issuetree.global.slasckbot.dto;

import lombok.Getter;

@Getter
public class ValidDto {
    private String token;
    private String challenge;
    private String type;

    public ValidDto(String type, String token, String challenge) {
        this.type = type;
        this.token = token;
        this.challenge = challenge;
    }
}