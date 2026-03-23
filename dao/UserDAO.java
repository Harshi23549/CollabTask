package dao;

public class UserDAO {

    public boolean validateUser(String email, String password) {
        // temporary test logic
        return email.equals("test@gmail.com") && password.equals("1234");
    }
}