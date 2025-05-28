package model;

public class Payment {
    private Reservation reservation;
    private boolean isPaid;
    private String paymentCode;

    public Payment(Reservation reservation, String paymentCode) {
        this.reservation = reservation;
        this.isPaid = false;
        this.paymentCode = paymentCode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    @Override
    public String toString() {
        return "Pembayaran untuk: " + reservation.toString() +
                " | Status: " + (isPaid ? "LUNAS ✅" : "BELUM LUNAS ❌");
    }
}