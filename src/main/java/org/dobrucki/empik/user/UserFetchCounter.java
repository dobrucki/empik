package org.dobrucki.empik.user;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

class UserFetchCounter {

    private final EntityManagerFactory entityManagerFactory;

    UserFetchCounter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    void incrementFetchCount(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        FetchLog fetchLog = Optional.ofNullable(entityManager.find(FetchLog.class, login))
                .map(log -> {
                    log.setRequestCount(log.getRequestCount() + 1);
                    return log;
                })
                .orElseGet(() -> {
                    FetchLog log = new FetchLog();
                    log.setLogin(login);
                    log.setRequestCount(1);
                    return log;
                });

        entityManager.persist(fetchLog);
        tx.commit();
        entityManager.close();
    }

    @Entity
    @Table(name = "FETCH_LOG")
    @Data
    private static class FetchLog {
        @Id
        @Column(name = "LOGIN")
        String login;

        @Column(name = "REQUEST_COUNT")
        int requestCount;
    }
}
