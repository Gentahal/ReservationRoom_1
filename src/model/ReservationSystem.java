package model;

import java.util.*;

public class ReservationSystem {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start(AbstractUser user) {
        while (true) {
            System.out.println("\n--- Menu " + user.getRole().toUpperCase() + " ---");
            if (user.getRole().equals("admin")) {
                System.out.println("1. Tambah Ruangan");
                System.out.println("2. Lihat Ruangan");
                System.out.println("3. Hapus Ruangan");
                System.out.println("4. Lihat Reservasi");
                System.out.println("5. Logout");
                System.out.println();
                System.out.println("Masukkan Pilihan anda > ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> tambahRuangan();
                    case 2 -> showRooms();
                    case 3 -> hapusRuangan();
                    case 4 -> showAllReservations();
                    case 5 -> {
                        System.out.println("Logout...");
                        return; // keluar dari menu user & kembali ke login
                    }
                    default -> System.out.println("Pilihan salah");
                }

            } else {
                System.out.println("1. Lihat Ruangan");
                System.out.println("2. Lihat Ruangan Yang Tersedia");
                System.out.println("3. Pesan Ruangan");
                System.out.println("4. Lihat Reservasi Saya");
                System.out.println("5. Bayar Reservasi");
                System.out.println("6 Logout");
                System.out.println();
                System.out.println("Masukkan Pilihan anda > ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showRooms();
                    case 2 -> showRoomAvailability();
                    case 3 -> makeReservation(user);
                    case 4 -> showUserReservations(user);
                    case 5 -> processPayment(user);
                    case 6 -> {
                        System.out.println("Logout...");
                        return;
                    }
                    default -> System.out.println("Pilihan salah");
                }
            }
        }
    }

    private void tambahRuangan() {
        System.out.print("Masukkan ID Ruangan: ");
        int id = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nama ruangan: ");
        String name = scanner.nextLine();
        System.out.print("Kapasitas: ");
        int cap = scanner.nextInt();
        scanner.nextLine();
        rooms.add(new Room(id,name, cap));
        System.out.println("Berhasil menambahkan ruangan.");
    }

    private void hapusRuangan() {
        showRooms();
        System.out.print("Pilih nomor ruangan yang ingin dihapus: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= rooms.size()) {
            rooms.remove(index - 1);
            System.out.println("Ruangan berhasil dihapus.");
        } else {
            System.out.println("Indeks tidak valid.");
        }
    }

    private void showRooms() {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan.");
            return;
        }
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i));
        }
    }

    private void showAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println("- " + r);
        }
    }

    public void makeReservation(AbstractUser user) {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan tersedia.");
            return;
        }

        showRooms();
        System.out.print("Pilih nomor ruangan: ");
        int roomIndex = scanner.nextInt();
        scanner.nextLine();

        if (roomIndex < 1 || roomIndex > rooms.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Room selectedRoom = rooms.get(roomIndex - 1);

        System.out.print("Masukkan tanggal (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        System.out.print("Jam mulai (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Jam selesai (HH:mm): ");
        String endTime = scanner.nextLine();

        for (Reservation r : reservations) {
            if (r.getRoom().getId() == selectedRoom.getId() && r.getDate().equals(date)) {
                if (isOverlap(r.getStartTime(), r.getEndTime(), startTime, endTime)) {
                    System.out.println("❌ Gagal! Ruangan sudah dipesan pada jam tersebut.");
                    return;
                }
            }
        }

        Reservation newReservation = new Reservation(user, selectedRoom, date, startTime, endTime);
        reservations.add(newReservation);
        System.out.println("✅ Data ruangan telah masuk , STATUS PEMBAYARAN : " + newReservation.getStatus());
    }

    private boolean isOverlap(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }

    private void showUserReservations(AbstractUser user) {
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getUser().getName().equalsIgnoreCase(user.getName())) {
                System.out.println("- " + r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Belum ada reservasi.");
        }
    }

    public void showRoomAvailability() {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan yang tersedia.");
            return;
        }

        System.out.println("\n📋 Daftar Ketersediaan Ruangan:");
        for (Room room : rooms) {
            System.out.println("🔹 Ruangan: " + room.getName() + " (Kapasitas: " + room.getCapacity() + ")");
            boolean hasReservation = false;

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().getId() == room.getId()) {
                    hasReservation = true;
                    System.out.println("   ❌ Telah Dipesan:");
                    System.out.println("      📅 " + reservation.getDate() + " ⏰ " + reservation.getStartTime() + " - " + reservation.getEndTime());
                    System.out.println("      👤 Oleh: " + reservation.getUser().getName() + " (" + reservation.getUser().getRole() + ")");
                }
            }

            if (!hasReservation) {
                System.out.println("   ✅ Tersedia Sepenuhnya");
            }
        }
    }

    private void processPayment(AbstractUser user) {
        List<Reservation> userUnpaid = new ArrayList<>();

        for (Reservation r : reservations) {
            boolean isPaid = false;
            for (Payment p : payments) {
                if (p.getReservation() == r && p.isPaid()) {
                    isPaid = true;
                    break;
                }
            }

            if (r.getUser().getName().equalsIgnoreCase(user.getName()) && !isPaid) {
                userUnpaid.add(r);
            }
        }

        if (userUnpaid.isEmpty()) {
            System.out.println("✅ Tidak ada reservasi yang perlu dibayar.");
            return;
        }

        System.out.println("\n📋 Reservasi Belum Dibayar:");
        for (int i = 0; i < userUnpaid.size(); i++) {
            System.out.println((i + 1) + ". " + userUnpaid.get(i));
        }

        System.out.print("Pilih reservasi yang ingin dibayar: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        if (pilihan < 1 || pilihan > userUnpaid.size()) {
            System.out.println("❌ Pilihan tidak valid.");
            return;
        }

        Reservation selected = userUnpaid.get(pilihan - 1);

        System.out.print("Masukkan metode pembayaran (GOPAY, DANA, CASH): ");
        String metode = scanner.nextLine().toUpperCase();

        // Generate kode pembayaran random misal: 8 digit alfanumerik
        String kodeBayar = generatePaymentCode();

        System.out.println("Kode pembayaran Anda: " + kodeBayar);

        int kapasitas = selected.getRoom().getCapacity();
        double harga;
        if (kapasitas > 50) {
            harga = 350000;
        } else if (kapasitas > 30) {
            harga = 200000;
        } else if (kapasitas > 10) {
            harga = 100000;
        } else {
            harga = 50000;
        }

        System.out.println("Total yang harus dibayar: Rp " + harga);

        System.out.print("Masukkan jumlah pembayaran: Rp ");
        double jumlahBayar = 0;

        try {
            jumlahBayar = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Pembayaran dibatalkan.");
            return;
        }

        if (jumlahBayar < harga) {
            System.out.println("❌ Pembayaran gagal, uang yang dibayarkan kurang.");
            return;
        }

        double kembalian = jumlahBayar - harga;

        Payment pembayaranBaru = new Payment(selected);
        payments.add(pembayaranBaru);

        selected.setStatus("LUNAS");

        System.out.println("✅ Pembayaran berhasil dengan metode " + metode + "!");
        if (kembalian > 0) {
            System.out.println("Kembalian Anda: Rp " + kembalian);
        }
    }

    private String generatePaymentCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
