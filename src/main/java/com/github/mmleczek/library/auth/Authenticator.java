package com.github.mmleczek.library.auth;
import com.github.mmleczek.library.model.User;
import com.github.mmleczek.library.db.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private static final String seed = "!U4ZM6sxnehP*wtW";
    public User authUser = null;
    private final UserRepo usersRepo = new UserRepo();
    private final Map<String, Integer> accessLevels = new HashMap<>();

    public Authenticator() {
        this.accessLevels.put("admin", 999);
        this.accessLevels.put("user", 0);
    }

    public boolean authenticate(String name, String password) {
        User dbUser = usersRepo.getUser(name);
        if (dbUser != null &&
                dbUser.checkPassword(Authenticator.genPasswordHash(password))) {
            authUser = dbUser;
            return true;
        } else {
            return false;
        }
    }

    public int getAccessLevelForGroup(String group) {
        return this.accessLevels.get(group);
    }

    public static String genPasswordHash(String password) {
        return DigestUtils.sha3_256Hex(password + seed);
    }
}
