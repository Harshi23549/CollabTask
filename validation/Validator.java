package validation;

import java.time.LocalDate;

public class Validator {

    // ─── Email Validation ─────────────────────────────────────────────────────

    /**
     * Checks if the given email has a valid format.
     * e.g. user@example.com
     */
    public static boolean isValidEmail(String email) {
        if (isEmptyOrNull(email)) return false;

        // Simple regex: must have text @ text . text
        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    // ─── Empty / Null Checks ──────────────────────────────────────────────────

    /**
     * Returns true if the string is null or blank.
     */
    public static boolean isEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates that a field is not empty.
     * Returns an error message if invalid, or null if valid.
     */
    public static String validateRequiredField(String fieldName, String value) {
        if (isEmptyOrNull(value)) {
            return fieldName + " cannot be empty.";
        }
        return null; // means no error
    }

    // ─── Password Validation ──────────────────────────────────────────────────

    /**
     * Password must be at least 6 characters long.
     */
    public static boolean isValidPassword(String password) {
        if (isEmptyOrNull(password)) return false;
        return password.length() >= 6;
    }

    // ─── Date / Deadline Validation ───────────────────────────────────────────

    /**
     * Deadline must be today or in the future.
     */
    public static boolean isValidDeadline(LocalDate deadline) {
        if (deadline == null) return false;
        return !deadline.isBefore(LocalDate.now());
    }

    // ─── User Registration Validation ─────────────────────────────────────────

    /**
     * Validates all fields needed to register a new user.
     * Returns an error message if something is wrong, or null if all is fine.
     */
    public static String validateUserRegistration(String name, String email,
                                                   String password, String role) {
        // Check name
        String nameError = validateRequiredField("Name", name);
        if (nameError != null) return nameError;

        // Check email format
        if (!isValidEmail(email)) {
            return "Invalid email format. Example: user@example.com";
        }

        // Check password length
        if (!isValidPassword(password)) {
            return "Password must be at least 6 characters long.";
        }

        // Check role
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("member")) {
            return "Role must be either 'admin' or 'member'.";
        }

        return null; // All valid
    }

    // ─── Task Validation ──────────────────────────────────────────────────────

    /**
     * Validates fields needed to create a task.
     * Returns an error message if something is wrong, or null if all is fine.
     */
    public static String validateTask(String title, String description, LocalDate deadline) {
        String titleError = validateRequiredField("Task Title", title);
        if (titleError != null) return titleError;

        String descError = validateRequiredField("Task Description", description);
        if (descError != null) return descError;

        if (!isValidDeadline(deadline)) {
            return "Deadline must be today or a future date.";
        }

        return null; // All valid
    }
}
