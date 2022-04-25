package org.dobrucki.empik.user;

import javax.persistence.EntityManagerFactory;

import org.dobrucki.empik.github.GithubClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserBeanContext {

    @Bean UserFetchCounter userFetchCounter(EntityManagerFactory entityManagerFactory) {
        return new UserFetchCounter(entityManagerFactory);
    }

    @Bean UserService userService(UserFetchCounter userFetchCounter, GithubClient githubClient) {
        return new UserService(userFetchCounter, githubClient);
    }
}
