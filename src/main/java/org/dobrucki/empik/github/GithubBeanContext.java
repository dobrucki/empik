package org.dobrucki.empik.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class GithubBeanContext {

    @Value("${github.api.uri}") private String githubApiUri;

    @Bean RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        rest.setUriTemplateHandler(new DefaultUriBuilderFactory(githubApiUri));
        return rest;
    }

    @Bean GithubClient githubClient(RestTemplate restTemplate) {
        return new GithubClient(restTemplate);
    }
}
