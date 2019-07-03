package com.apiMashup.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiMashup.ApiMashupException;
import com.apiMashup.GithubRepo;
import twitter4j.Status;

@RestController
public class MainController {
    GithubController githubController;
    @Autowired
    TwitterController twitterController;

    @RequestMapping("/githubTwitter")
    public ArrayList<GithubRepo> githubTwitter(@RequestParam(value="q", defaultValue="Reactive") String queryParm) throws Exception {
        try {
            twitterController.queryForTweets("ReactiveCocoa");
            githubController = new GithubController("https://api.github.com/search/repositories", queryParm);

            return githubController.sendRequest();
        } catch (Exception e) {
            throw new ApiMashupException("Error on MainController", e);
        }
    }
}
