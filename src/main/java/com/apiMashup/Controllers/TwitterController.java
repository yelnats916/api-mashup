package com.apiMashup.Controllers;
import com.apiMashup.ApiMashupException;
import com.apiMashup.TweetData;
import com.apiMashup.TwitterAuthConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.PostConstruct;

@Component
public class TwitterController {
    @Autowired
    TwitterAuthConfig config;

    org.slf4j.Logger logger = LoggerFactory.getLogger(TwitterController.class);

    Twitter twitter;

    @PostConstruct
    public void setupTwitterObject() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(config.getConsumerKey())
                .setOAuthConsumerSecret(config.getConsumerSecret())
                .setOAuthAccessToken(config.getAccessToken())
                .setOAuthAccessTokenSecret(config.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public ArrayList<TweetData> queryForTweets(String searchQuery) throws Exception {
        int count = 0;
        Status tweet;
        try {
            Query query = new Query(searchQuery);
            QueryResult result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            Iterator<Status> statusIterator = statuses.iterator();
            ArrayList<TweetData> tweetDataList = new ArrayList<>();
            // limits to 5 tweets per GithubRepo
            while (statusIterator.hasNext() && count < 5) {
                tweet = statusIterator.next();
                tweetDataList.add(new TweetData(tweet.getUser().getScreenName(), tweet.getText()));
                count++;
            }
            return tweetDataList;
        } catch (Exception e) {
            String errStr = "Error sending request to Twitter";
            logger.error(errStr);
            throw new ApiMashupException(errStr, e);
        }
    }

}
