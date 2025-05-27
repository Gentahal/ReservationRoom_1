package model;

public class Dosen extends AbstractUser {
    public Dosen(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "dosen";
    }
}

