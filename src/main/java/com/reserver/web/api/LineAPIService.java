package com.reserver.web.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.function.Function;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reserver.web.http.Client;
import com.reserver.web.response.AccessToken;
import com.reserver.web.response.IdToken;
import com.reserver.web.response.Verify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import retrofit2.Call;


@Component
public class LineAPIService {

    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    @Value("${linecorp.platform.channel.channelId:1639961986}")
    private String channelId;
    @Value("${linecorp.platform.channel.channelSecret:549323d68c7ddd7ec19afc2d8b6f6a89}")
    private String channelSecret;
    @Value("${linecorp.platform.channel.callbackUrl:https://5c847941.ngrok.io/auth}")
    private String callbackUrl;

    public AccessToken accessToken(String code) {
        return getClient(t -> t.accessToken(
                GRANT_TYPE_AUTHORIZATION_CODE,
                channelId,
                channelSecret,
                callbackUrl,
                code));
    }

    public AccessToken refreshToken(final AccessToken accessToken) {
        return getClient(t -> t.refreshToken(
                GRANT_TYPE_REFRESH_TOKEN,
                accessToken.refresh_token,
                channelId,
                channelSecret));
    }

    public Verify verify(final AccessToken accessToken) {
        return getClient(t -> t.verify(
                accessToken.access_token));
    }

    public void revoke(final AccessToken accessToken) {
        getClient(t -> t.revoke(
                accessToken.access_token,
                channelId,
                channelSecret));
    }

    public IdToken idToken(String id_token) {
        try {
            DecodedJWT jwt = JWT.decode(id_token);
            return new IdToken(
                    jwt.getClaim("iss").asString(),
                    jwt.getClaim("sub").asString(),
                    jwt.getClaim("aud").asString(),
                    jwt.getClaim("ext").asLong(),
                    jwt.getClaim("iat").asLong(),
                    jwt.getClaim("nonce").asString(),
                    jwt.getClaim("name").asString(),
                    jwt.getClaim("picture").asString());
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLineWebLoginUrl(String state, String nonce, List<String> scopes) {
        final String encodedCallbackUrl;
        final String scope = String.join("%20", scopes);

        try {
            encodedCallbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "https://access.line.me/oauth2/v2.1/authorize?response_type=code"
                + "&client_id=" + channelId
                + "&redirect_uri=" + encodedCallbackUrl
                + "&state=" + state
                + "&scope=" + scope
                + "&nonce=" + nonce;
    }

    public boolean verifyIdToken(String id_token, String nonce) {
        try {
            JWT.require(
                    Algorithm.HMAC256(channelSecret))
                    .withIssuer("https://access.line.me")
                    .withAudience(channelId)
                    .withClaim("nonce", nonce)
                    .build()
                    .verify(id_token);
            return true;
 //       } catch (UnsupportedEncodingException e) {
            //UTF-8 encoding not supported
 //          return false;
        } catch (JWTVerificationException e) {
            //Invalid signature/claims
            return false;
        }
    }

    private <R> R getClient(final Function<LineAPI, Call<R>> function) {
        return Client.getClient("https://api.line.me/", LineAPI.class, function);
    }

}