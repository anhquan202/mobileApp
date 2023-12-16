package huce.nhom15.mobileapp.view.ModelRespone;

<<<<<<< HEAD
public class LoginRespone {

    public LoginRespone(String error, String message) {
        this.error = error;
        this.message = message;
=======
import huce.nhom15.mobileapp.model.Customer;

public class LoginRespone {
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
>>>>>>> 372c82f (Update project)
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

<<<<<<< HEAD


    String error;
    String message;
    String user;
    public void setUser(String user){
        this.user = user;
    }
    public String getUser() {
        return user;

    }
=======
    public LoginRespone(Customer customer, String error, String message) {
        this.customer = customer;
        this.error = error;
        this.message = message;
    }

    Customer customer;
    String error, message;

>>>>>>> 372c82f (Update project)
}
