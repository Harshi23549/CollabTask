package service;

import dao.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO;

    // Initialize: Sets up the authentication service by creating a UserDAO.
    public AuthService() {
        userDAO = new UserDAO();
    }

    // Authenticate: Validates user credentials so they can login.
    public boolean login(String email, String password) {

        if (email == null || email.isEmpty() || 
            password == null || password.isEmpty()) {
            return false;
        }

        return userDAO.validateUser(email, password);
    }

    // Retrieve: Fetches user details using their email.
    public User getUser(String email) {
        return userDAO.getUserByEmail(email);
    }

    // Register: Creates a new user in the system.
    public boolean register(String name, String email, String password, String role) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || 
            password == null || password.isEmpty() || role == null || role.isEmpty()) {
            return false;
        }
        User newUser = new User(name, email, password, role);
        return userDAO.addUser(newUser);
    }
}