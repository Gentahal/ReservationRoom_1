package model;

public class Payment {
    // Atribut
    private Reservation reservation;
    private boolean isPaid;
    private String paymentCode;

    // Constructor
    public Payment(Reservation reservation, String paymentCode) {
        this.reservation = reservation;
        this.isPaid = false;
        this.paymentCode = paymentCode;
    }

    // function getter
    public Reservation getReservation() {
        return reservation;
    }

    // function status pembayaran
    public boolean isPaid() {
        return isPaid;
    }

    // function untuk menginput pembayaran , apakah pembayaran berhasil atau tidak
    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    // Menunjukkan status pembayaran
    @Override
    public String toString() {
        return "Pembayaran untuk: " + reservation.toString() +
                " | Status: " + (isPaid ? "LUNAS ✅" : "BELUM LUNAS ❌");
    }
}