package com.apiMashup.Controllers;

import java.util.ArrayList;

import com.apiMashup.ProjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiMashup.ApiMashupException;
import com.apiMashup.GithubRepo;

@RestController
public class MainController {
    GithubController githubController;
    @Autowired
    TwitterController twitterController;

    @RequestMapping("/githubTwitter")
    public ArrayList<ProjectSummary> githubTwitterReq(@RequestParam(value="q", defaultValue="Reactive") String queryParm) throws Exception {
        ArrayList<GithubRepo> repos;
        ArrayList<ProjectSummary> summaries = new ArrayList<>();
        try {
            githubController = new GithubController("https://api.github.com/search/repositories", queryParm);
            repos = githubController.sendRequest();
            for (GithubRepo repo : repos) {
                summaries.add(new ProjectSummary(repo, twitterController.queryForTweets(repo.getName())));
            }
            return summaries;
        } catch (Exception e) {
            throw new ApiMashupException("Failure to send request", e);
        }
    }
}
