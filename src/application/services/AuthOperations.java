package application.services;

import database.Database;
import database.user.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AuthOperations {

    private Database database = new Database();

    public Service<Void> loginService = new Service<>() {
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty password = new SimpleStringProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    User currentUser = database.loadUserFromDatabase(name.get(), password.get());

                    if(currentUser == null) throw new Exception("Invalid username or credentials.");

                    SessionHandler.getOrCreateInstance(currentUser);
                    throw null;
                }
            };
        }
    };

    public Service<Void> logoutService = new Service<>() {
        private SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
        private SimpleStringProperty operation = new SimpleStringProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    database.saveUsersToDatabase(user.get());
                    SessionHandler.clearSession();
                    return null;
                }
            };
        }
    };
}
