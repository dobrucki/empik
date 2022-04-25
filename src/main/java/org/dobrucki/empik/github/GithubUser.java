package org.dobrucki.empik.github;

import lombok.Value;

@Value
public class GithubUser {
    String id;
    String login;
    String name;
    String type;
    String avatarUrl;
    String createdAt;
    int followers;
    int publicRepos;
}
