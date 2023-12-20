package huce.nhom15.mobileapp.view.ModelRespone;

import huce.nhom15.mobileapp.model.Customer;

public class LoginRespone {

    public LoginRespone(String error, String message) {
        this.error = error;
        this.message = message;
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



    String error;
    String message;
//    String user;
//    public void setUser(String user){
//        this.user = user;
//    }
//    public String getUser() {
//        return user;
//
//    }

    Customer user;

    public Customer getCustomer() {
        return user;
    }

    public void setCustomer(Customer user) {
        this.user =user;
    }
}
