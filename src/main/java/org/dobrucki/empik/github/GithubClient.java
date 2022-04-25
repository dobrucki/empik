package org.dobrucki.empik.github;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GithubClient {

    private static final String URI = "/users/{login}";

    private final RestTemplate restTemplate;

    GithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<GithubUser> fetchUser(String login) {
        Map<String, String> params = Map.of("login", login);
        ResponseEntity<User> response = restTemplate.getForEntity(URI, User.class, params);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional
                    .ofNullable(response.getBody())
                    .map(user -> new GithubUser(
                            user.getId(),
                            user.getLogin(),
                            user.getName(),
                            user.getType(),
                            user.getAvatarUrl(),
                            user.getCreatedAt(),
                            user.getFollowers(),
                            user.getPublicRepos()
                    ));
        }

        return Optional.empty();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class User {
        @JsonProperty("id") String id;
        @JsonProperty("login") String login;
        @JsonProperty("name") String name;
        @JsonProperty("type") String type;
        @JsonProperty("avatar_url") String avatarUrl;
        @JsonProperty("created_at") String createdAt;
        @JsonProperty("followers") int followers;
        @JsonProperty("public_repos") int publicRepos;
    }
}
