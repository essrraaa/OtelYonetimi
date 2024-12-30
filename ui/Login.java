package otelyonetimi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import otelyonetimi.dao.KullaniciDAO;

public class Login {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        frame = new JFrame("Otel Yönetim Sistemi - Giriş");
        frame.setLayout(new GridLayout(3, 2, 10, 10));
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // E-posta ve şifre alanları
        JLabel emailLabel = new JLabel("E-posta:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordField = new JPasswordField();

        // Giriş yap butonu
        loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Kullanıcı doğrulama işlemi yapılacak
                if (validateUser(email, password)) {
                    // Başarılı giriş, ana ekrana geçiş
                    String kullaniciAdi = KullaniciDAO.getKullaniciAdi(email);  // Kullanıcı adını SQL'den al
                    JOptionPane.showMessageDialog(frame, "Giriş Başarılı! Hoş geldiniz, " + kullaniciAdi);
                    new MainDashboard();  // Ana dashboard penceresini aç
                    frame.dispose();  // Giriş ekranını kapat
                } else {
                    JOptionPane.showMessageDialog(frame, "Geçersiz e-posta veya şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ekrana bileşenleri ekle
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel());  // Boş bir label, düzeni düzgün tutmak için
        frame.add(loginButton);

        frame.setLocationRelativeTo(null);  // Ekranı ortala
        frame.setVisible(true);
    }

    // Kullanıcı doğrulama fonksiyonu (veritabanı ile)
    private boolean validateUser(String email, String password) {
        return KullaniciDAO.kullaniciDogrula(email, password);  // Veritabanı doğrulaması
    }

    public static void main(String[] args) {
        new Login();
    }
}