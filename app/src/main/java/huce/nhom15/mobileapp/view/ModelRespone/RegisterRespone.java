package huce.nhom15.mobileapp.view.ModelRespone;

public class RegisterRespone {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    String error;
}
