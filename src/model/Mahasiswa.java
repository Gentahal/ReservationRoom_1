package model;

public class Mahasiswa extends AbstractUser {
    public Mahasiswa(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "mahasiswa";
    }
}
