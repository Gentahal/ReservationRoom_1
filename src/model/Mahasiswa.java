package model;

public class Mahasiswa extends AbstractUser {

    // Constructor
    public Mahasiswa(String name) {
        super(name);
    }

    // Berfungsi untuk mengembalikan role dan status nya
    @Override
    public String getRole() {
        return "mahasiswa";
    }
}
