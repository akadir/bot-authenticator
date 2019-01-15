package com.kadir.twitterbots.entity;

/**
 * @author akadir
 * Date: 15/01/2019
 * Time: 21:53
 */
public enum ApiKeyPropertyName {
    CONSUMER_KEY("consumer-key"), CONSUMER_SECRET("consumer-secret"), ACCESS_TOKEN("access-token"), ACCESS_TOKEN_SECRET("access-token-secret");

    private String value;

    ApiKeyPropertyName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
