import ui.LoginUI;
import utils.DBConnection;

public class Main {
    // Start: Launches the application by showing the login screen.
    public static void main(String[] args) {
        DBConnection.initialize();
        new LoginUI().showLogin();
    }
}