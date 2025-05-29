package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sistem Reservasi Ruangan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Masukkan nama Anda:");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(nameField);

        continueButton = new JButton("Lanjutkan");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(continueButton);

        // Tombol role, awalnya disembunyikan
        adminLoginButton = new JButton("Login sebagai Admin");
        mahasiswaLoginButton = new JButton("Login sebagai Mahasiswa");
        dosenLoginButton = new JButton("Login sebagai Dosen");

        adminLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mahasiswaLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dosenLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminLoginButton.setVisible(false);
        mahasiswaLoginButton.setVisible(false);
        dosenLoginButton.setVisible(false);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(adminLoginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(mahasiswaLoginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(dosenLoginButton);

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nama tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    enteredName = name;
                    // Sembunyikan input nama dan tombol lanjutkan
                    nameField.setVisible(false);
                    continueButton.setVisible(false);

                    // Tampilkan tombol role login
                    adminLoginButton.setVisible(true);
                    mahasiswaLoginButton.setVisible(true);
                    dosenLoginButton.setVisible(true);

                    // Update label
                    welcomeLabel.setText("Halo, " + enteredName + ". Pilih role Anda:");
                }
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractUser admin = new Admin(enteredName);
                frame.dispose();
                system.start(admin);
            }
        });

        mahasiswaLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractUser mahasiswa = new Mahasiswa(enteredName);
                frame.dispose();
                system.start(mahasiswa);
            }
        });

        dosenLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractUser dosen = new Dosen(enteredName);
                frame.dispose();
                system.start(dosen);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
