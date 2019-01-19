package com.reserver.web.controller;


import javax.servlet.http.HttpSession;

import com.reserver.web.api.LineAPIService;
import com.reserver.web.response.AccessToken;
import com.reserver.web.response.Verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIController {

    @Autowired
    private LineAPIService lineAPIService;

    @RequestMapping("api/refreshToken")
    public AccessToken refreshToken(HttpSession httpSession) {
        final AccessToken token = getAccessToken(httpSession);
        final AccessToken newAccessToken = lineAPIService.refreshToken(token);
        if (newAccessToken != null) {
            setAccessToken(httpSession, newAccessToken);
        }
        return newAccessToken;
    }

    @RequestMapping("api/verify")
    public Verify verify(HttpSession httpSession) {
        final AccessToken token = getAccessToken(httpSession);
        return lineAPIService.verify(token);
    }

    @RequestMapping("api/revoke")
    public void revoke(HttpSession httpSession) {
        final AccessToken token = getAccessToken(httpSession);
        lineAPIService.revoke(token);
    }

    private AccessToken getAccessToken(HttpSession httpSession) {
        return (AccessToken) httpSession.getAttribute(LoginController.ACCESS_TOKEN);
    }

    private void setAccessToken(HttpSession httpSession, AccessToken accessToken) {
        httpSession.setAttribute(LoginController.ACCESS_TOKEN, accessToken);
    }


}
