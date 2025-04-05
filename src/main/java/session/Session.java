package session;

import javax.crypto.SecretKey;

public class Session {
    private SecretKey masterKey;

    // Private constructor prevents instantiation from other classes
    private Session() {}

    //the singleton instance
    private static class SessionHolder {
        private static final Session INSTANCE = new Session();
    }

    // access point to get the singleton instance
    public static Session getInstance() {
        return SessionHolder.INSTANCE;
    }

    public SecretKey getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(SecretKey masterKey) {
        this.masterKey = masterKey;
    }

    // clear the session data
    public void clear() {
        masterKey = null;
    }
}
