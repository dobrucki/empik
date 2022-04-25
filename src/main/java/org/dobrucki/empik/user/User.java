package org.dobrucki.empik.user;

import lombok.Value;

@Value
class User {
    String id;
    String login;
    String name;
    String type;
    String avatarUrl;
    String createdAt;
    String calculations;
}
