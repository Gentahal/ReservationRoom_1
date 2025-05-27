package model;

public class Admin extends AbstractUser {

    public Admin(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "admin";
    }
}
