package com.apiMashup;

import lombok.*;

@Data
public class GithubRepo {
    private String name;
    private String full_name;
    private String html_url;
    private String description;
}
