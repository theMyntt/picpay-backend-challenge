package com.themyntt.challenges.picpay.infrastructure.http;

import com.themyntt.challenges.picpay.domain.contracts.IHttpClientContract;
import com.themyntt.challenges.picpay.domain.models.AuthorizeHttpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizeTransferHttp implements IHttpClientContract<AuthorizeHttpResponseModel> {
    @Autowired
    private RestTemplate httpClient;

    @Override
    public AuthorizeHttpResponseModel send() {
        try {
            ResponseEntity<AuthorizeHttpResponseModel> response = httpClient.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizeHttpResponseModel.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAs(AuthorizeHttpResponseModel.class);
        }
    }
}
