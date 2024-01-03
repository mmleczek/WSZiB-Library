package pl.edu.wszib.auth;
import pl.edu.wszib.model.User;
import pl.edu.wszib.db.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;

public class Authenticator {
    private static final String seed = "!U4ZM6sxnehP*wtW";
    public static User authUser = null;
    private final UserRepo usersRepo = new UserRepo();

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

    public static String genPasswordHash(String password) {
        return DigestUtils.sha3_256Hex(password + seed);
    }
}
