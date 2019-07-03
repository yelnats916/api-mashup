package com.apiMashup;
import lombok.Data;
import org.springframework.stereotype.*;
import org.springframework.boot.context.properties.*;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="oauth")
@Data
public class TwitterAuthConfig {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
}
