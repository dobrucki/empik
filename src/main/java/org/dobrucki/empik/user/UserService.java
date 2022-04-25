package org.dobrucki.empik.user;

import java.util.Optional;

import org.dobrucki.empik.github.GithubClient;
import org.dobrucki.empik.github.GithubUser;

class UserService {

    private final UserFetchCounter userFetchCounter;
    private final GithubClient githubClient;

    UserService(UserFetchCounter userFetchCounter, GithubClient githubClient) {
        this.userFetchCounter = userFetchCounter;
        this.githubClient = githubClient;
    }

    Optional<User> fetch(String login) {
        userFetchCounter.incrementFetchCount(login);
        return githubClient.fetchUser(login).map(UserService::toUser);
    }

    private static User toUser(GithubUser githubUser) {
        return new User(
                githubUser.getId(),
                githubUser.getLogin(),
                githubUser.getName(),
                githubUser.getType(),
                githubUser.getAvatarUrl(),
                githubUser.getCreatedAt(),
                makeCalculations(githubUser)
        );
    }

    private static String makeCalculations(GithubUser githubUser) {
        if (githubUser.getFollowers() == 0) {
            return null;
        }
        return String.valueOf(
                6 / githubUser.getFollowers() * (2 + githubUser.getPublicRepos())
        );
    }
}
