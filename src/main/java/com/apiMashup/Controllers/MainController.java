package com.apiMashup.Controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiMashup.ApiMashupException;
import com.apiMashup.GithubRepo;

@RestController
public class MainController {
    GithubController githubController;

    @RequestMapping("/githubTwitter")
    public ArrayList<GithubRepo> githubTwitter(@RequestParam(value="q", defaultValue="Reactive") String queryParm) throws Exception {
        try {
            new TwitterController();
            githubController = new GithubController("https://api.github.com/search/repositories", queryParm);
            return githubController.sendRequest();
        } catch (Exception e) {
            throw new ApiMashupException("test", e);
        }
    }
}
