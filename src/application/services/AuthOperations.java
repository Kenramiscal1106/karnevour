package application.services;

import database.Database;
import database.user.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;

public class AuthOperations {

    private final Database database = Database.getInstance();

    public void login(String name, String password, Consumer<User> onSuccess, Consumer<String> onFail){
        var service = new LoginService(name, password);
        service.setOnSucceeded(e -> onSuccess.accept(service.getValue()));
        service.setOnFailed(e -> onFail.accept(service.getException().getMessage()));
        service.start();
    }

    public void logout(Runnable onDone){
        var service = new LogoutService(SessionHandler.getInstance().getCurrentUser());
        service.setOnSucceeded(e -> onDone.run());
        service.start();
    }

    private class LoginService extends Service<User> {

        private final String name;
        private final String password;

        public LoginService(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        protected Task<User> createTask() {
            return new Task<>() {
                @Override
                protected User call() throws Exception {
                    User user = database.loadUserFromDatabase(name, password);
                    if (user == null) throw new Exception("Invalid username or password.");
                    SessionHandler.getOrCreateInstance(user);
                    return user;
                }
            };
        }
    }

    private class LogoutService extends Service<Void> {
        private final User user;

        public LogoutService(User user) {
            this.user = user;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    database.saveUsersToDatabase(user);
                    SessionHandler.clearSession();
                    return null;
                }
            };
        }
    }
}