package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ReservationSystem {
    // Atribut serta collection ArrayList
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // MENU ADMIN
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

                // Pembuatan switch case untuk menu admin
                switch (choice) {
                    case 1 -> tambahRuangan();
                    case 2 -> showRooms();
                    case 3 -> hapusRuangan();
                    case 4 -> showAllReservations();
                    case 5 -> {
                        System.out.println("Logout...");
                        return;
                    }
                    default -> System.out.println("Pilihan salah");
                }

            // Menu dosen dan mahasiswa
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

                // Switch case untuk menu mahasiswa dan dosen
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

    //  Function menambah ruangan
    void tambahRuangan() {

        // INPUT ID
        try {
            int id;
            while (true) {
                System.out.print("Masukkan ID Kelas: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt(); // Mengambil input dan dimasukkan didalam variable int
                    scanner.nextLine(); // inputan
                    if (isRoomIdUnique(id)) { // Memanggil func atau method is room unique id
                        break;
                    } else {
                        System.out.println("❌ ID sudah digunakan. Silakan masukkan ID lain.");
                    }
                } else {
                    System.out.println("❌ Harap masukkan angka untuk ID.");
                    scanner.nextLine();
                }
            }

            // INPUT NAMA
            String name;
            while (true) {
                System.out.print("Masukkan Nama Kelas: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.out.println("❌ Nama kelas tidak boleh kosong.");
            }

            // INPUT CAPACITY
            int capacity;
            while (true) {
                System.out.print("Masukkan Kapasitas Kelas: ");
                if (scanner.hasNextInt()) {
                    capacity = scanner.nextInt();
                    scanner.nextLine();
                    if (capacity > 0) {
                        break;
                    } else {
                        System.out.println("❌ Kapasitas harus lebih dari 0.");
                    }
                } else {
                    System.out.println("❌ Harap masukkan angka untuk kapasitas.");
                    scanner.nextLine();
                }
            }
            // INPUT KELAS GRATIS / TIDAK GRATIS
            boolean isFree;
            while (true) {
                System.out.print("Apakah kelas ini gratis? (ya/tidak): ");
                String isFreeInput = scanner.nextLine().trim().toLowerCase();
                if (isFreeInput.equals("ya") || isFreeInput.equals("tidak")) {
                    isFree = isFreeInput.equals("ya");
                    break;
                }
                System.out.println("❌ Harap masukkan 'ya' atau 'tidak'.");
            }

            // INPUT HARGA
            double price = 0;
            if (!isFree) {
                while (true) {
                    System.out.print("Masukkan harga kelas: Rp ");
                    if (scanner.hasNextDouble()) {
                        price = scanner.nextDouble();
                        scanner.nextLine();
                        if (price > 0) {
                            break;
                        } else {
                            System.out.println("❌ Harga harus lebih dari 0.");
                        }
                    } else {
                        System.out.println("❌ Harap masukkan angka untuk harga.");
                        scanner.nextLine();
                    }
                }
            }

            Room newRoom = new Room(id, name, capacity, isFree, price);
            rooms.add(newRoom);
            System.out.println("✅ Kelas berhasil ditambahkan.");

        } catch (Exception e) {
            System.out.println("❌ Terjadi kesalahan: " + e.getMessage());
            scanner.nextLine();
        }
    }

    // FUNCTION check id ruangan apakah ada id ruangan yang tabrakan atau belum digunakan?
    private boolean isRoomIdUnique(int id) {
        return rooms.stream().noneMatch(room -> room.getId() == id);
    } // akan return false jika sudah ada id ruangan yang menggunakan itu , dan true jika tidak ada yang menggunakannnay


    // function hapus ruangan
    void hapusRuangan() {
        showRooms(); // panggil function show room
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

    // Function memperlihatkan list semua ruangan
    void showRooms() {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan.");
            return;
        }
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i));
        }
    }

    // Function memperlihatkan semua reservasi
    void showAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println("- " + r);
        }
    }

    // Function buat reservasi
    public void makeReservation(AbstractUser user) {
        try {
            if (rooms.isEmpty()) { // Jika ruangan kosong
                System.out.println("❌ Belum ada ruangan tersedia.");
                return;
            }

            showRooms();

            int roomIndex; // Variable menyimpan ruangan
            while (true) {
                System.out.print("Pilih nomor ruangan (1-" + rooms.size() + "): ");
                if (scanner.hasNextInt()) {
                    roomIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (roomIndex >= 1 && roomIndex <= rooms.size()) {
                        break;
                    }
                    System.out.println("❌ Nomor ruangan harus antara 1 dan " + rooms.size());
                } else {
                    System.out.println("❌ Harap masukkan angka.");
                    scanner.nextLine();
                }
            }

            Room selectedRoom = rooms.get(roomIndex - 1);

            String date;
            while (true) {
                System.out.print("Masukkan tanggal reservasi (yyyy-mm-dd): ");
                date = scanner.nextLine().trim();
                if (isValidDate(date)) {
                    break;
                }
                System.out.println("❌ Format tanggal tidak valid. Gunakan format yyyy-mm-dd");
            }

            // Variable String menyimpan jam masuk dan jam mulai
            String startTime, endTime;
            while (true) {
                System.out.print("Jam mulai (HH:mm): ");
                startTime = scanner.nextLine().trim();
                System.out.print("Jam selesai (HH:mm): ");
                endTime = scanner.nextLine().trim();

                if (!isValidTime(startTime) || !isValidTime(endTime)) {
                    System.out.println("❌ Format waktu tidak valid. Gunakan format HH:mm");
                    continue;
                }

                if (startTime.compareTo(endTime) >= 0) {
                    System.out.println("❌ Jam selesai harus setelah jam mulai");
                    continue;
                }

                break;
            }

            for (Reservation r : reservations) {
                if (r.getRoom().getId() == selectedRoom.getId() && r.getDate().equals(date)) {
                    if (isOverlap(r.getStartTime(), r.getEndTime(), startTime, endTime)) {
                        System.out.println("❌ Gagal! Ruangan sudah dipesan pada:");
                        System.out.println("   Tanggal: " + date);
                        System.out.println("   Jam: " + r.getStartTime() + " - " + r.getEndTime());
                        return;
                    }
                }
            }

            // Object
            Reservation newReservation = new Reservation(user, selectedRoom, date, startTime, endTime);

            if (selectedRoom.isFree()) {
                newReservation.setStatus("BERHASIL");
                reservations.add(newReservation);
                System.out.println("✅ Reservasi berhasil tanpa pembayaran karena ruangan gratis.");
            } else {
                newReservation.setStatus("PENDING");
                reservations.add(newReservation);
                String paymentCode = generatePaymentCode();
                Payment newPayment = new Payment(newReservation, paymentCode);
                payments.add(newPayment);
                System.out.println("✅ Reservasi berhasil, silakan lanjutkan ke pembayaran.");
                System.out.println("Kode Pembayaran: " + paymentCode);
            }

        } catch (Exception e) {
            System.out.println("❌ Terjadi kesalahan saat melakukan reservasi: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private boolean isOverlap(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date); // Uses ISO format (yyyy-MM-dd)
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        return time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    void showUserReservations(AbstractUser user) {
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
            System.out.println("🔹 Ruangan: " + room.getName() +
                    " (Kapasitas: " + room.getCapacity() + ") - " +
                    (room.isFree() ? "Gratis" : "Berbayar: Rp" + String.format("%,.0f", room.getPrice())));

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

    // FUNCTION Proses pembayaran
    void processPayment(AbstractUser user) {
        List<Reservation> userUnpaid = new ArrayList<>(); // ArrayList user belum bayar


        for (Reservation r : reservations) {
            boolean isPaid = false;
            for (Payment p : payments) {
                if (p.getReservation() == r && p.isPaid()) {
                    isPaid = true;
                    break;
                }
            }

            if (r.getUser().getName().equalsIgnoreCase(user.getName()) && !isPaid && !r.getRoom().isFree()) {
                userUnpaid.add(r);
            }
        }

        if (userUnpaid.isEmpty()) {
            System.out.println("✅ Tidak ada reservasi yang perlu dibayar.");
            return;
        }

        System.out.println("\n📋 Reservasi Belum Dibayar:");
        for (int i = 0; i < userUnpaid.size(); i++) {
            Reservation r = userUnpaid.get(i);
            String timeLeft = "";
            if (!r.isPaymentExpired()) {
                Duration duration = Duration.between(LocalDateTime.now(), r.getReservationTime().plusMinutes(5));
                long minutes = duration.toMinutes();
                long seconds = duration.minusMinutes(minutes).getSeconds();
                timeLeft = String.format(" (Sisa waktu: %d menit %d detik)", minutes, seconds);
            }

            System.out.println((i + 1) + ". " + r);
            System.out.println("   Harga: Rp " + (r.getRoom().isFree() ? "Gratis" : String.format("%,.0f", r.getRoom().getPrice())));
            System.out.println("   Waktu Reservasi: " + r.getReservationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("   Batas Pembayaran: " + r.getReservationTime().plusMinutes(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("   Status: " + (r.isPaymentExpired() ? "❌ KADALUARSA" : "✅ BELUM KADALUARSA" + timeLeft));
        }

        System.out.print("Pilih reservasi yang ingin dibayar: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        if (pilihan < 1 || pilihan > userUnpaid.size()) {
            System.out.println("❌ Pilihan tidak valid.");
            return;
        }

        Reservation selected = userUnpaid.get(pilihan - 1);

        if (selected.getRoom().isFree()) {
            System.out.println("✅ Reservasi ini gratis, tidak perlu pembayaran.");
            return;
        }

        if (selected.isPaymentExpired()) {
            System.out.println("❌ Pembayaran gagal! Waktu pembayaran telah habis (5 menit setelah reservasi).");
            System.out.println("    Silakan buat reservasi baru.");
            return;
        }

        double harga = selected.getRoom().getPrice();
        System.out.println("Total yang harus dibayar: Rp " + String.format("%,.0f", harga));

        // Show remaining time
        Duration remaining = Duration.between(LocalDateTime.now(), selected.getReservationTime().plusMinutes(5));
        System.out.println("Sisa waktu pembayaran: " + remaining.toMinutes() + " menit " +
                (remaining.getSeconds() % 60) + " detik");

        String kodeBayar = generatePaymentCode();
        System.out.println("Kode pembayaran Anda: " + kodeBayar);

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

        Payment pembayaranBaru = new Payment(selected, kodeBayar);
        payments.add(pembayaranBaru);

        selected.setStatus("LUNAS");

        System.out.println("✅ Pembayaran berhasil ");
        if (kembalian > 0) {
            System.out.println("Kembalian Anda: Rp " + String.format("%,.0f", kembalian));
        }
    }

    // Universally unique identifier
    private String generatePaymentCode() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Collection dengan java array list
    public List<Room> getRooms() {
        return this.rooms;
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }

    public List<Payment> getPayments() {
        return this.payments;
    }
}
