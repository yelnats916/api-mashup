package com.apiMashup.Controllers;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterController {
    @Value("${oauth.consumerKey}")
    String consumerKey;
    @Value("${oauth.consumerSecret}")
    String consumerSecret;
    @Value("${oauth.accessToken}")
    String accessToken;
    @Value("${oauth.accessTokenSecret}")
    String accessTokenSecret;

    Twitter twitter;

    public TwitterController() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
    }

    public void sendRequest() {

    }
}
