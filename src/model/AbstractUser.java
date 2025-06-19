package model;

    // Class yang berfungsi sebagai parent class dan akan digunakan untuk input role dari user
public abstract class AbstractUser {

    // Atribut
    private String name;

    // Constructor
    public AbstractUser(String name) {
        this.name = name;
    }

    // Functiom
    public String getName() {
        return name;
    }

    // Abstract Function
    public abstract String getRole();
}