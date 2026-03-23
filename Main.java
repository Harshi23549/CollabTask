import service.AuthService;

public class Main {
    public static void main(String[] args) {

        AuthService auth = new AuthService();

        boolean result = auth.login("test@gmail.com", "1234");

        if (result) {
            System.out.println("Login Success");
        } else {
            System.out.println("Login Failed");
        }
    }
}