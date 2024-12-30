package otelyonetimi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard {
    private JFrame frame;

    public MainDashboard() {
        frame = new JFrame("Otel Yönetim Sistemi");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        // "Odalari Yönet" butonu
        JButton manageRoomsButton = new JButton("Odalari Yönet");
        manageRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RoomManagement();  // Oda yönetim ekranını aç
            }
        });

        // "Müşteri Girişi Yap" butonu
        JButton checkInButton = new JButton("Müşteri Girişi Yap");
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckIn();  // Müşteri giriş ekranını aç
            }
        });

        // "Otelde Kalan Müşteriler" butonu
        JButton customersInHotelButton = new JButton("Otelde Kalan Müşteriler");
        customersInHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomersInHotel();  // Otelde kalan müşteriler ekranını aç
            }
        });

        // "Müşteri Çıkışı Yap" butonu
        JButton checkOutButton = new JButton("Müşteri Çıkışı Yap");
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckOut();  // Müşteri çıkış ekranını aç
            }
        });

        // "Müşteri Fatura Göster" butonu
        JButton showInvoiceButton = new JButton("Müşteri Fatura Göster");
        showInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InvoiceDisplay();  // Fatura Göster ekranını aç
            }
        });

        // "Çıkış Yap" butonu
        JButton logoutButton = new JButton("Çıkış Yap");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Ana dashboard'ı kapat
                new Login();  // Giriş ekranını tekrar aç
            }
        });

        // Paneli oluştur ve bileşenleri ekle
        panel.add(manageRoomsButton);
        panel.add(checkInButton);
        panel.add(customersInHotelButton); // Yeni butonu "Müşteri Girişi Yap" ve "Müşteri Çıkışı Yap" arasına ekliyoruz
        panel.add(checkOutButton);
        panel.add(showInvoiceButton);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);  // Ekranı ortala
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}
