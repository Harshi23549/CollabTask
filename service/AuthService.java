package service;

import dao.UserDAO;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    // LOGIN LOGIC
    public boolean login(String email, String password) {

        // Basic validation (you control flow)
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Email or Password cannot be empty");
            return false;
        }

        // Call DAO (Khushi's part)
        boolean isValid = userDAO.validateUser(email, password);

        if (isValid) {
            System.out.println("Login successful");
            return true;
        } else {
            System.out.println("Invalid credentials");
            return false;
        }
    }
}
