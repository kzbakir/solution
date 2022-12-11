package com.example.solution.service.impl;

import com.example.solution.model.json.TokenJson;
import com.example.solution.property.AdmitadProperties;
import com.example.solution.service.AdmitadHttpClient;
import com.example.solution.service.TokenService;
import com.example.solution.service.XMLService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.solution.model.constant.AdmitadConstants.*;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties({AdmitadProperties.class})
class AdmitadHttpClientImpl implements AdmitadHttpClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final AdmitadProperties admitadProperties;
    private final TokenService tokenService;

    @Override
    public JsonNode parsePrograms() {
        try {
            var accessToken = new AtomicReference<>(Strings.EMPTY);
            var url = admitadProperties.getApiUrl() + "/advcampaigns/website/2090016/";
            var httpGet = new HttpGet(url);
            var token = tokenService.getToken(admitadProperties.getClientId());
            if (token.isEmpty()) {
                var savedToken = tokenService.save(getToken());
                accessToken.set(savedToken.getAccessToken());
            }
            token.ifPresent(x -> {
                if (new Date().after(x.getExpiresDate())) {
                    var savedToken = tokenService.save(refreshToken(x.getRefreshToken()));
                    accessToken.set(savedToken.getAccessToken());
                }
                accessToken.set(x.getAccessToken());
            });
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            var response = httpClient.execute(httpGet);
            return objectMapper.readTree(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TokenJson getToken() {
        var token = new TokenJson();
        try {
            var clientId = admitadProperties.getClientId();
            var clientSecret = admitadProperties.getClientSecret();
            var url = admitadProperties.getApiUrl() + "/token/";
            var httpPost = new HttpPost(url);
            var params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("grant_type", GRANT_TYPE_FOR_ACCESS_TOKEN));
            params.add(new BasicNameValuePair("client_id", clientId));
            params.add(new BasicNameValuePair("scope", SCOPE));
            var encodedData = "TXQ0NEJCQ01Ld1lENUJFUExpWTlGRmRhdWwyMXhuOkFESFlJaUJvb2RvUUhIWDVsZVZudTAzbklDa0RDUg==";
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedData);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            var response = httpClient.execute(httpPost);
            token = objectMapper.readValue(EntityUtils.toString(response.getEntity(), "UTF-8"), TokenJson.class);
            token.setClientId(clientId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    private TokenJson refreshToken(String refreshToken) {
        var token = new TokenJson();
        try {
            var url = admitadProperties.getApiUrl() + "/token/";
            var httpPost = new HttpPost(url);
            var params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("grant_type", GRANT_TYPE_FOR_REFRESH_TOKEN));
            params.add(new BasicNameValuePair("client_id", admitadProperties.getClientId()));
            params.add(new BasicNameValuePair("client_secret", admitadProperties.getClientSecret()));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            params.add(new BasicNameValuePair("scope", SCOPE));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            var response = httpClient.execute(httpPost);
            token = objectMapper.readValue(EntityUtils.toString(response.getEntity(), "UTF-8"), TokenJson.class);
            token.setClientId(admitadProperties.getClientId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    private String encode(String clientId, String clientSecret) {
        var bytes = String.format("%s:%s", clientId, clientSecret).getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
