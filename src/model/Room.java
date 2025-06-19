package model;

public class Room {


    // Atribut
    private int id;
    private String name;
    private int capacity;
    private boolean isFree;
    private double price;

    // COnstructor
    public Room(int id, String name, int capacity, boolean isFree, double price) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.isFree = isFree;
        this.price = price;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFree(){
        return isFree;
    }

    public double getPrice(){
        return price;
    }

    // Output Room
    @Override
    public String toString() {
        return name + " (Kapasitas: " + capacity + ") - " + (isFree ? "GRATIS" : "BERBAYAR - Rp" + price);
    }
}

