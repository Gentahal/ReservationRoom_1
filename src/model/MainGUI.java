package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainGUI extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JTextField nameField;
    private JButton continueButton;
    private JButton adminLoginButton;
    private JButton mahasiswaLoginButton;
    private JButton dosenLoginButton;
    private ReservationSystem system;
    private String enteredName;

    public MainGUI() {
        system = new ReservationSystem();
        initializeLoginScreen();
    }

    private void initializeLoginScreen() {
        frame = new JFrame("Sistem Reservasi Ruangan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(240, 245, 249));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 245, 249));
        JLabel titleLabel = new JLabel("SISTEM RESERVASI RUANGAN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 82, 155));
        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 245, 249));

        JLabel welcomeLabel = new JLabel("Masukkan nama Anda:");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        contentPanel.add(welcomeLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(300, 35));
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameField);

        continueButton = new JButton("Lanjutkan");
        styleButton(continueButton);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(continueButton);

        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
        rolePanel.setBackground(new Color(240, 245, 249));
        rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminLoginButton = new JButton("Login sebagai Admin");
        mahasiswaLoginButton = new JButton("Login sebagai Mahasiswa");
        dosenLoginButton = new JButton("Login sebagai Dosen");

        styleButton(adminLoginButton);
        styleButton(mahasiswaLoginButton);
        styleButton(dosenLoginButton);

        adminLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mahasiswaLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dosenLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminLoginButton.setVisible(false);
        mahasiswaLoginButton.setVisible(false);
        dosenLoginButton.setVisible(false);

        rolePanel.add(adminLoginButton);
        rolePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rolePanel.add(mahasiswaLoginButton);
        rolePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rolePanel.add(dosenLoginButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(rolePanel);

        panel.add(contentPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 245, 249));
        JLabel footerLabel = new JLabel("Kelompok MSU CS");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(Color.BLACK);
        footerPanel.add(footerLabel);
        panel.add(footerPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        continueButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nama tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                enteredName = name;
                nameField.setVisible(false);
                continueButton.setVisible(false);
                welcomeLabel.setText("Halo, " + enteredName + ". Pilih role Anda:");
                adminLoginButton.setVisible(true);
                mahasiswaLoginButton.setVisible(true);
                dosenLoginButton.setVisible(true);
            }
        });

        adminLoginButton.addActionListener(e -> {
            AbstractUser admin = new Admin(enteredName);
            frame.dispose();
            showAdminDashboard(admin);
        });

        mahasiswaLoginButton.addActionListener(e -> {
            AbstractUser mahasiswa = new Mahasiswa(enteredName);
            frame.dispose();
            showUserDashboard(mahasiswa);
        });

        dosenLoginButton.addActionListener(e -> {
            AbstractUser dosen = new Dosen(enteredName);
            frame.dispose();
            showUserDashboard(dosen);
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(0, 121, 211));
        button.setForeground(Color.BLUE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setMaximumSize(new Dimension(300, 40));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 82, 155));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 121, 211));
            }
        });
    }

    private void showAdminDashboard(AbstractUser user) {
        JFrame adminFrame = new JFrame("Admin Dashboard - " + user.getName());
        adminFrame.setSize(800, 600);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel roomPanel = new JPanel(new BorderLayout());
        JPanel roomButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addRoomButton = new JButton("Tambah Ruangan");
        JButton deleteRoomButton = new JButton("Hapus Ruangan");
        JButton refreshRoomsButton = new JButton("Refresh");

        // Tambahkan panel untuk logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutPanel.add(logoutButton);

        // Tambahkan logout panel ke frame
        adminFrame.add(logoutPanel, BorderLayout.SOUTH);

        // Action listener untuk logout
        logoutButton.addActionListener(e -> {
            adminFrame.dispose();
            initializeLoginScreen(); // Kembali ke login screen
        });

        styleButton(addRoomButton);
        styleButton(deleteRoomButton);
        styleButton(refreshRoomsButton);

        roomButtonPanel.add(addRoomButton);
        roomButtonPanel.add(deleteRoomButton);
        roomButtonPanel.add(refreshRoomsButton);

        DefaultListModel<String> roomListModel = new DefaultListModel<>();
        JList<String> roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane roomScrollPane = new JScrollPane(roomList);

        roomPanel.add(roomButtonPanel, BorderLayout.NORTH);
        roomPanel.add(roomScrollPane, BorderLayout.CENTER);

        JPanel reservationPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> reservationListModel = new DefaultListModel<>();
        JList<String> reservationList = new JList<>(reservationListModel);
        JScrollPane reservationScrollPane = new JScrollPane(reservationList);
        reservationPanel.add(reservationScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Manajemen Ruangan", roomPanel);
        tabbedPane.addTab("Manajemen Reservasi", reservationPanel);

        addRoomButton.addActionListener(e -> showAddRoomDialog(roomListModel));
        deleteRoomButton.addActionListener(e -> {
            int selectedIndex = roomList.getSelectedIndex();
            if (selectedIndex != -1) {
                system.getRooms().remove(selectedIndex);
                roomListModel.remove(selectedIndex);
                JOptionPane.showMessageDialog(adminFrame, "Ruangan berhasil dihapus.");
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Pilih ruangan yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshRoomsButton.addActionListener(e -> refreshRoomList(roomListModel));

        refreshRoomList(roomListModel);
        refreshReservationList(reservationListModel);

        adminFrame.add(tabbedPane);
        adminFrame.setVisible(true);
    }

    private void showUserDashboard(AbstractUser user) {
        JFrame userFrame = new JFrame(user.getRole() + " Dashboard - " + user.getName());
        userFrame.setSize(800, 600);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutPanel.add(logoutButton);

        // Tambahkan logout panel ke frame
        userFrame.add(logoutPanel, BorderLayout.SOUTH);

        // Action listener untuk logout
        logoutButton.addActionListener(e -> {
            userFrame.dispose();
            initializeLoginScreen(); // Kembali ke login screen
        });

        JPanel roomPanel = new JPanel(new BorderLayout());
        JPanel roomButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton reserveButton = new JButton("Pesan Ruangan");
        JButton checkAvailabilityButton = new JButton("Cek Ketersediaan");
        JButton refreshRoomsButton = new JButton("Refresh");

        styleButton(reserveButton);
        styleButton(checkAvailabilityButton);
        styleButton(refreshRoomsButton);

        roomButtonPanel.add(reserveButton);
        roomButtonPanel.add(checkAvailabilityButton);
        roomButtonPanel.add(refreshRoomsButton);

        DefaultListModel<String> roomListModel = new DefaultListModel<>();
        JList<String> roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane roomScrollPane = new JScrollPane(roomList);

        roomPanel.add(roomButtonPanel, BorderLayout.NORTH);
        roomPanel.add(roomScrollPane, BorderLayout.CENTER);

        JPanel reservationPanel = new JPanel(new BorderLayout());
        JPanel resButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton payButton = new JButton("Bayar Reservasi");
        JButton refreshResButton = new JButton("Refresh");

        styleButton(payButton);
        styleButton(refreshResButton);

        resButtonPanel.add(payButton);
        resButtonPanel.add(refreshResButton);

        DefaultListModel<String> reservationListModel = new DefaultListModel<>();
        JList<String> reservationList = new JList<>(reservationListModel);
        JScrollPane reservationScrollPane = new JScrollPane(reservationList);

        reservationPanel.add(resButtonPanel, BorderLayout.NORTH);
        reservationPanel.add(reservationScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Daftar Ruangan", roomPanel);
        tabbedPane.addTab("Reservasi Saya", reservationPanel);

        reserveButton.addActionListener(e -> showReservationDialog(user, roomList, roomListModel, reservationListModel));
        checkAvailabilityButton.addActionListener(e -> showAvailabilityDialog());
        refreshRoomsButton.addActionListener(e -> refreshRoomList(roomListModel));
        payButton.addActionListener(e -> showPaymentDialog(user, reservationList, reservationListModel));
        refreshResButton.addActionListener(e -> refreshUserReservationList(user, reservationListModel));

        refreshRoomList(roomListModel);
        refreshUserReservationList(user, reservationListModel);

        userFrame.add(tabbedPane);
        userFrame.setVisible(true);
    }

    private void showAddRoomDialog(DefaultListModel<String> roomListModel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Tambah Ruangan Baru");
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));
        dialog.setLocationRelativeTo(null);

        JLabel idLabel = new JLabel("ID Ruangan:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Nama Ruangan:");
        JTextField nameField = new JTextField();

        JLabel capacityLabel = new JLabel("Kapasitas:");
        JTextField capacityField = new JTextField();

        JLabel freeLabel = new JLabel("Gratis?");
        JCheckBox freeCheckBox = new JCheckBox();

        JLabel priceLabel = new JLabel("Harga:");
        JTextField priceField = new JTextField();
        priceField.setEnabled(false);

        freeCheckBox.addActionListener(e -> {
            priceField.setEnabled(!freeCheckBox.isSelected());
            if (freeCheckBox.isSelected()) {
                priceField.setText("0");
            } else {
                priceField.setText("");
            }
        });

        JButton submitButton = new JButton("Simpan");
        styleButton(submitButton);

        dialog.add(idLabel);
        dialog.add(idField);
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(capacityLabel);
        dialog.add(capacityField);
        dialog.add(freeLabel);
        dialog.add(freeCheckBox);
        dialog.add(priceLabel);
        dialog.add(priceField);
        dialog.add(new JLabel());
        dialog.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int capacity = Integer.parseInt(capacityField.getText());
                boolean isFree = freeCheckBox.isSelected();
                double price = isFree ? 0 : Double.parseDouble(priceField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Nama ruangan tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (capacity <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Kapasitas harus lebih dari 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isFree && price <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Harga harus lebih dari 0 untuk ruangan berbayar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room newRoom = new Room(id, name, capacity, isFree, price);
                system.getRooms().add(newRoom);
                roomListModel.addElement(newRoom.toString());
                dialog.dispose();
                JOptionPane.showMessageDialog(dialog, "Ruangan berhasil ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Input tidak valid. Harap masukkan angka yang benar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void showReservationDialog(AbstractUser user, JList<String> roomList, DefaultListModel<String> roomListModel,
                                       DefaultListModel<String> reservationListModel) {
        int selectedIndex = roomList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih ruangan terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Room selectedRoom = system.getRooms().get(selectedIndex);

        JDialog dialog = new JDialog();
        dialog.setTitle("Pesan Ruangan: " + selectedRoom.getName());
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));
        dialog.setLocationRelativeTo(null);

        JLabel dateLabel = new JLabel("Tanggal (yyyy-mm-dd):");
        JTextField dateField = new JTextField();

        JLabel startLabel = new JLabel("Jam Mulai (HH:mm):");
        JTextField startField = new JTextField();

        JLabel endLabel = new JLabel("Jam Selesai (HH:mm):");
        JTextField endField = new JTextField();

        JButton submitButton = new JButton("Pesan");
        styleButton(submitButton);

        dialog.add(dateLabel);
        dialog.add(dateField);
        dialog.add(startLabel);
        dialog.add(startField);
        dialog.add(endLabel);
        dialog.add(endField);
        dialog.add(new JLabel());
        dialog.add(submitButton);

        submitButton.addActionListener(e -> {
            String date = dateField.getText();
            String startTime = startField.getText();
            String endTime = endField.getText();

            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(dialog, "Format tanggal tidak valid. Gunakan yyyy-mm-dd", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!startTime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !endTime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                JOptionPane.showMessageDialog(dialog, "Format waktu tidak valid. Gunakan HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startTime.compareTo(endTime) >= 0) {
                JOptionPane.showMessageDialog(dialog, "Jam selesai harus setelah jam mulai", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Reservation r : system.getReservations()) {
                if (r.getRoom().getId() == selectedRoom.getId() && r.getDate().equals(date)) {
                    if (isOverlap(r.getStartTime(), r.getEndTime(), startTime, endTime)) {
                        JOptionPane.showMessageDialog(dialog,
                                "Ruangan sudah dipesan pada:\nTanggal: " + date +
                                        "\nJam: " + r.getStartTime() + " - " + r.getEndTime(),
                                "Konflik Reservasi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            Reservation newReservation = new Reservation(user, selectedRoom, date, startTime, endTime);

            if (selectedRoom.isFree()) {
                newReservation.setStatus("BERHASIL");
                system.getReservations().add(newReservation);
                JOptionPane.showMessageDialog(dialog, "Reservasi berhasil tanpa pembayaran karena ruangan gratis.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                newReservation.setStatus("PENDING");
                system.getReservations().add(newReservation);
                String paymentCode = generatePaymentCode();
                Payment newPayment = new Payment(newReservation, paymentCode);
                system.getPayments().add(newPayment);

                JOptionPane.showMessageDialog(dialog,
                        "<html>Reservasi berhasil, silakan lanjutkan ke pembayaran.<br>" +
                                "Kode Pembayaran: <b>" + paymentCode + "</b><br>" +
                                "Batas waktu: 5 menit dari sekarang</html>",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            refreshUserReservationList(user, reservationListModel);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private void showPaymentDialog(AbstractUser user, JList<String> reservationList, DefaultListModel<String> reservationListModel) {
        int selectedIndex = reservationList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih reservasi terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reservation selectedReservation = system.getReservations().get(selectedIndex);

        if (selectedReservation.getRoom().isFree()) {
            JOptionPane.showMessageDialog(null, "Reservasi ini gratis, tidak perlu pembayaran.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (selectedReservation.isPaymentExpired()) {
            JOptionPane.showMessageDialog(null,
                    "Pembayaran gagal! Waktu pembayaran telah habis (5 menit setelah reservasi).\nSilakan buat reservasi baru.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog();
        dialog.setTitle("Pembayaran Reservasi");
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        double price = selectedReservation.getRoom().getPrice();
        LocalDateTime deadline = selectedReservation.getReservationTime().plusMinutes(5);
        long remainingMinutes = java.time.Duration.between(LocalDateTime.now(), deadline).toMinutes();

        infoPanel.add(new JLabel("Detail Reservasi:"));
        infoPanel.add(new JLabel("Ruangan: " + selectedReservation.getRoom().getName()));
        infoPanel.add(new JLabel("Tanggal: " + selectedReservation.getDate()));
        infoPanel.add(new JLabel("Waktu: " + selectedReservation.getStartTime() + " - " + selectedReservation.getEndTime()));
        infoPanel.add(new JLabel("Total Pembayaran: Rp " + String.format("%,.0f", price)));
        infoPanel.add(new JLabel("Batas Waktu: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        infoPanel.add(new JLabel("Sisa Waktu: " + remainingMinutes + " menit"));

        JPanel inputPanel = new JPanel();

        JLabel amountLabel = new JLabel("Jumlah Pembayaran:");
        JTextField amountField = new JTextField();

        amountField.setPreferredSize(new Dimension(200, 40)); // Lebar 200, tinggi 40

        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        JButton cancelButton = new JButton("Batal");
        JButton submitButton = new JButton("Bayar");
        styleButton(submitButton);
        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());

                if (amount < price) {
                    JOptionPane.showMessageDialog(dialog, "Pembayaran gagal, uang yang dibayarkan kurang.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (Payment p : system.getPayments()) {
                    if (p.getReservation() == selectedReservation) {
                        p.setPaid(true);
                        break;
                    }
                }

                selectedReservation.setStatus("LUNAS");
                double change = amount - price;

                String message = "Pembayaran berhasil!";
                if (change > 0) {
                    message += "\nKembalian: Rp " + String.format("%,.0f", change);
                }

                JOptionPane.showMessageDialog(dialog, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshUserReservationList(user, reservationListModel);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Input tidak valid. Harap masukkan angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void showAvailabilityDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Ketersediaan Ruangan");
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);

        DefaultListModel<String> availabilityModel = new DefaultListModel<>();
        JList<String> availabilityList = new JList<>(availabilityModel);
        JScrollPane scrollPane = new JScrollPane(availabilityList);

        dialog.add(scrollPane);

        for (Room room : system.getRooms()) {
            availabilityModel.addElement("🔹 Ruangan: " + room.getName() +
                    " (Kapasitas: " + room.getCapacity() + ") - " +
                    (room.isFree() ? "Gratis" : "Berbayar: Rp" + String.format("%,.0f", room.getPrice())));

            boolean hasReservation = false;

            for (Reservation reservation : system.getReservations()) {
                if (reservation.getRoom().getId() == room.getId()) {
                    hasReservation = true;
                    availabilityModel.addElement("   ❌ Telah Dipesan:");
                    availabilityModel.addElement("      📅 " + reservation.getDate() +
                            " ⏰ " + reservation.getStartTime() + " - " + reservation.getEndTime());
                    availabilityModel.addElement("      👤 Oleh: " +
                            reservation.getUser().getName() + " (" + reservation.getUser().getRole() + ")");
                }
            }

            if (!hasReservation) {
                availabilityModel.addElement("   ✅ Tersedia Sepenuhnya");
            }

            availabilityModel.addElement("");
        }

        dialog.setVisible(true);
    }

    private void refreshRoomList(DefaultListModel<String> model) {
        model.clear();
        for (Room room : system.getRooms()) {
            model.addElement(room.toString());
        }
    }

    private void refreshReservationList(DefaultListModel<String> model) {
        model.clear();
        for (Reservation reservation : system.getReservations()) {
            model.addElement(reservation.toString());
        }
    }

    private void refreshUserReservationList(AbstractUser user, DefaultListModel<String> model) {
        model.clear();
        for (Reservation reservation : system.getReservations()) {
            if (reservation.getUser().getName().equalsIgnoreCase(user.getName())) {
                model.addElement(reservation.toString());
            }
        }
    }

    private boolean isOverlap(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }

    private String generatePaymentCode() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}