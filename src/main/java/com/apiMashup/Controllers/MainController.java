package com.apiMashup.Controllers;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiMashup.ApiMashupException;

@RestController
public class MainController {
    private final AtomicLong counter = new AtomicLong();
    GithubController githubController;

    @RequestMapping("/githubTwitter")
    public ArrayList<JSONObject> githubTwitter(@RequestParam(value="queryParm", defaultValue="Reactive") String queryParm) throws Exception {
        try {
            githubController = new GithubController("https://api.github.com/search/repositories", queryParm);
            return githubController.sendRequest();
        } catch (Exception e) {
            throw new ApiMashupException("test", e);
        }
        // return new Greeting(counter.incrementAndGet(), queryParm);
    }
}
