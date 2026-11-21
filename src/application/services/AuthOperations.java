package application.services;

import database.Database;
import database.user.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AuthOperations {

    private final Database database = Database.getInstance();


    public class LoginService extends Service<User> {

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

    public class LogoutService extends Service<Void> {
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