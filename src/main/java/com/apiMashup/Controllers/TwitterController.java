package com.apiMashup.Controllers;
import com.apiMashup.ApiMashupException;
import com.apiMashup.TwitterAuthConfig;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;

import java.util.List;

import javax.annotation.PostConstruct;

@Component
public class TwitterController {
    @Autowired
    TwitterAuthConfig config;

    Twitter twitter;

    @PostConstruct
    public void setupTwitterObject() {
        System.out.println(config);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(config.getConsumerKey())
                .setOAuthConsumerSecret(config.getConsumerSecret())
                .setOAuthAccessToken(config.getAccessToken())
                .setOAuthAccessTokenSecret(config.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public List<Status> queryForTweets(String searchQuery) throws Exception {
        try {
            Query query = new Query(searchQuery);
            QueryResult result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            for (Status tweet : statuses) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
            return statuses;
            //for (Status tweet : tweets) {
            //    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            //}
        } catch (Exception e) {
            throw new ApiMashupException("Error sending request to Twitter", e);
        }
    }

}
