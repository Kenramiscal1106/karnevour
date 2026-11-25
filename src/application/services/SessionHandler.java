package application.services;

import database.user.*;

public final class SessionHandler {
    private static SessionHandler instance;
    private User currentUser;

    private SessionHandler(User user) {
        this.currentUser = user;
    }

    public static synchronized SessionHandler getInstance() {
        if (instance == null) throw new IllegalStateException("No active session exists");
        return instance;
    }

    public static synchronized SessionHandler getOrCreateInstance(User user) {
        if (instance == null) instance = new SessionHandler(user);
        if (instance.currentUser.getUid() != (user.getUid()))
            throw new IllegalStateException("Another user is logged in");
        return instance;
    }

    public static synchronized void clearSession() {
        instance = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }
}
