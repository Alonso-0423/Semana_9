import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Actividad_9 {

    // Regular expression for validating the password
    private static final String PASSWORD_PATTERN =
            "^(?=(?:.*[A-Z]){2})(?=(?:.*[a-z]){3})(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    // Method to validate a password
    public static boolean validatePassword(String password) {
        return pattern.matcher(password).matches();
    }

    public static void main(String[] args) {
        // List of passwords to validate
        List<String> passwords = List.of(
                "Password123!",  // Valid
                "passw@1",       // Invalid (too short)
                "P@ssword",      // Invalid (missing 2 uppercase letters)
                "Password",      // Invalid (missing special character and digit)
                "Pass12345!",    // Valid
                "AAaa11!!"       // Valid
        );

        // Executor service to manage threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Validate each password in a separate thread and print result
        for (String password : passwords) {
            executorService.submit(() -> {
                boolean isValid = validatePassword(password);
                synchronized (System.out) {
                    System.out.printf("Password: %s | Valid: %s\n", password, isValid);
                }
            });
        }

        // Shutdown the executor service
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Wait for all tasks to complete
        }
    }
}
