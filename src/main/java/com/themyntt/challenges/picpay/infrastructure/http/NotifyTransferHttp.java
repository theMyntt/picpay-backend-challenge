package com.themyntt.challenges.picpay.infrastructure.http;

import com.themyntt.challenges.picpay.domain.contracts.IHttpClientContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotifyTransferHttp implements IHttpClientContract<Boolean> {
    @Autowired
    private RestTemplate httpClient;

    @Override
    public Boolean send() {
        try {
            ResponseEntity<String> response = httpClient.postForEntity("https://util.devi.tools/api/v1/notify", null, String.class);

            return response.getStatusCode() == HttpStatus.NO_CONTENT;
        } catch (HttpServerErrorException e) {
            return false;
        }

    }
}
