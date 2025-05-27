package model;

public abstract class AbstractUser {
    private String name;

    public AbstractUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getRole();
}
