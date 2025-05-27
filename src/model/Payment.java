package model;

public class Payment {
    private Reservation reservation;
    private boolean isPaid;

    public Payment(Reservation reservation) {
        this.reservation = reservation;
        this.isPaid = false; // default: belum dibayar
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
                " | Status: " + (isPaid ? "LUNAS" : "BELUM LUNAS");
    }
}