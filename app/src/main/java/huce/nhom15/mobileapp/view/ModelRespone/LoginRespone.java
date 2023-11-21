package huce.nhom15.mobileapp.view.ModelRespone;

import huce.nhom15.mobileapp.model.Customer;

public class LoginRespone {
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public LoginRespone(Customer customer, String error, String message) {
        this.customer = customer;
        this.error = error;
        this.message = message;
    }

    Customer customer;
    String error, message;

}
