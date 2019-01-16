package com.reserver.web.controller;

import com.reserver.web.util.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    private static final String NONCE = "nonce";

    @RequestMapping("/gotoauthpage")
    public String goToAuthPage(HttpSession httpSession) {
        System.out.println("gotoAuthPage");
        final String state = CommonUtils.getToken();
        final String nonce = CommonUtils.getToken();

        System.out.println("state=>" + state + "nonce=>" + nonce);

        httpSession.setAttribute(LINE_WEB_LOGIN_STATE, state);
        httpSession.setAttribute(NONCE, nonce);
        // final String url = lineAPIService.

        return "index";

    }

}
