package pl.edu.wszib.auth;
import org.apache.commons.codec.digest.DigestUtils;

public class Authenticator {
    private static final String seed = "!U4ZM6sxnehP*wtW";

    public static String genPasswordHash(String password) {
        return DigestUtils.sha3_256Hex(password + seed);
    }
}
