package model;

public class Dosen extends AbstractUser {

    // Constructor
    public Dosen(String name) {
        super(name);
    }

    // Berfungsi untuk mengembalikan role dan status nya
    @Override
    public String getRole() {
        return "dosen";
    }
}

