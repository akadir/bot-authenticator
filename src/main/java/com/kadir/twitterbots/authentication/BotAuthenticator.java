package com.kadir.twitterbots.authentication;

import com.kadir.twitterbots.entity.ApiKeyPropertyName;
import com.kadir.twitterbots.entity.AuthenticationKeys;
import com.kadir.twitterbots.exceptions.IllegalApiTokenException;
import com.kadir.twitterbots.exceptions.PropertyNotLoadedException;
import com.kadir.twitterbots.exceptions.TwitterAuthenticationException;
import com.kadir.twitterbots.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author akadir
 * Date: 08/12/2018
 * Time: 13:35
 */
public class BotAuthenticator {
    private static final Logger logger = LoggerFactory.getLogger(BotAuthenticator.class);

    private BotAuthenticator() {
    }

    public static Twitter authenticate(String authFileName, String keyPrefix) {
        Twitter twitter;
        try {
            AuthenticationKeys authenticationKeys = getApiKeys(authFileName, keyPrefix);
            validateTokens(authenticationKeys);
            ConfigurationBuilder cb = buildConfigurationBuilder(authenticationKeys);

            twitter = new TwitterFactory(cb.build()).getInstance();
            User authUser = twitter.showUser(twitter.verifyCredentials().getId());
            logger.info("Bot authenticated successfully. Account information: {} - @{} - {}", authUser.getName(), authUser.getScreenName(), authUser.getId());
        } catch (IOException | TwitterException e) {
            logger.error("An error occured while authenticating twitter account.", e);
            throw new TwitterAuthenticationException(e);
        }

        return twitter;
    }

    private static AuthenticationKeys getApiKeys(String authFileName, String keyPrefix) {
        Properties properties = new Properties();
        AuthenticationKeys authenticationKeys;

        File propertyFile = new File(authFileName);

        try (InputStream input = new FileInputStream(propertyFile)) {
            properties.load(input);
            String consumerKey = properties.getProperty(keyPrefix + ApiKeyPropertyName.CONSUMER_KEY.getValue());
            logger.info("set consumerKey:{}", consumerKey);
            String consumerSecret = properties.getProperty(keyPrefix + ApiKeyPropertyName.CONSUMER_SECRET.getValue());
            logger.info("set consumerSecret:{}", consumerSecret);
            String accessToken = properties.getProperty(keyPrefix + ApiKeyPropertyName.ACCESS_TOKEN.getValue(), "");
            logger.info("set accessToken:{}", accessToken);
            String accessTokenSecret = properties.getProperty(keyPrefix + ApiKeyPropertyName.ACCESS_TOKEN_SECRET.getValue(), "");
            logger.info("set accessTokenSecret:{}", accessTokenSecret);

            authenticationKeys = new AuthenticationKeys(consumerKey, consumerSecret, accessToken, accessTokenSecret);
            logger.info("All properties loaded from file: {}", authFileName);
        } catch (IOException e) {
            logger.error("error occurred while getting properties from file. " + authFileName, e);
            throw new PropertyNotLoadedException(authFileName);
        }
        return authenticationKeys;
    }

    private static ConfigurationBuilder buildConfigurationBuilder(AuthenticationKeys authenticationKeys) {
        return new ConfigurationBuilder()
                .setOAuthConsumerKey(authenticationKeys.getConsumerKey())
                .setOAuthConsumerSecret(authenticationKeys.getConsumerSecret())
                .setOAuthAccessToken(authenticationKeys.getAccessToken())
                .setOAuthAccessTokenSecret(authenticationKeys.getAccessTokenSecret());
    }

    private static void validateTokens(AuthenticationKeys authenticationKeys) throws TwitterException, IOException {
        if (DataUtil.isNullOrEmpty(authenticationKeys.getConsumerKey()) || DataUtil.isNullOrEmpty(authenticationKeys.getConsumerSecret())) {
            logger.error("consumerKey or consumerSecret cannot be null or empty.");
            throw new IllegalApiTokenException("consumerKey or consumerSecret cannot be null or empty.");
        } else if (DataUtil.isNullOrEmpty(authenticationKeys.getAccessToken()) || DataUtil.isNullOrEmpty(authenticationKeys.getAccessTokenSecret())) {
            getAccessTokenAndSecretFromTwitter(authenticationKeys);
        }
    }

    private static void getAccessTokenAndSecretFromTwitter(AuthenticationKeys authenticationKeys) throws TwitterException, IOException {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(authenticationKeys.getConsumerKey(), authenticationKeys.getConsumerSecret());

        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken authAccessToken = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == authAccessToken) {
            logger.info("Open the following URL and grant access to your account:");
            logger.info(requestToken.getAuthorizationURL());
            logger.info("Enter the PIN (if available) or just hit enter.[PIN]:");
            String pin;
            pin = br.readLine();
            if (pin.length() > 0) {
                authAccessToken = twitter.getOAuthAccessToken(requestToken, pin);
                authenticationKeys.setAccessToken(authAccessToken.getToken());
                authenticationKeys.setAccessTokenSecret(authAccessToken.getTokenSecret());
                logger.info("accessToken: {}", authenticationKeys.getAccessToken());
                logger.info("accessTokenSecret: {}", authenticationKeys.getAccessTokenSecret());
            } else {
                authAccessToken = twitter.getOAuthAccessToken();
            }
        }
    }
}
