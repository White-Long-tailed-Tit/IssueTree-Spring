package com.wltt.issuetree.user.controller;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void addUser(
            @RequestBody final MultiValueMap<String, Object> data
    ) {
        userService.addUser(new SlackUrlEncodedForm(data));
    }
}
