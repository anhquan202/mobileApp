package huce.nhom15.mobileapp.view.ModelRespone;

public class SetNewPassRespone {
    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public  String error;
    public void setMessage(String message) {
        this.message = message;
    }

    String message;
}
