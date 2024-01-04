package com.github.mmleczek.library.db;
import com.github.mmleczek.library.auth.Authenticator;
import com.github.mmleczek.library.model.User;

import java.util.HashMap;
import java.util.Map;
public class UserRepo {
    private final Map<String, User> users = new HashMap<>();

    public UserRepo() {
        this.users.put("admin", new User("admin", Authenticator.genPasswordHash("admin123"), "admin"));
        this.users.put("user", new User("user", Authenticator.genPasswordHash("user"), "user"));
        this.users.put("user1", new User("user1", Authenticator.genPasswordHash("user1"), "user"));
        this.users.put("user2", new User("user2", Authenticator.genPasswordHash("user2"), "user"));
        this.users.put("user3", new User("user3", Authenticator.genPasswordHash("user3"), "user"));
    }

    public User getUser(String name) {
        return this.users.get(name);
    }
}
