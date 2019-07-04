package com.apiMashup;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ProjectSummary {
    private GithubRepo repo;
    private ArrayList<TweetData> tweets;
}
