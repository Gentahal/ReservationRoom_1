package model;

public class Admin extends AbstractUser {

    // Constructor
    public Admin(String name) {
        super(name);
    }

    // Get role , berfungsi mengembalikan status role
    @Override
    public String getRole() {
        return "admin";
    }
}
