package otelyonetimi.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import otelyonetimi.dao.CustomerDAO;  // Müşteri veritabanı işlemleri için
import otelyonetimi.model.Customer;  // Müşteri modeli

public class CustomersInHotel {
    private JFrame frame;

    public CustomersInHotel() {
        frame = new JFrame("Otelde Kalan Müşteriler");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Müşteri listesi için tablo
        String[] columns = {"Müşteri ID", "Ad", "Telefon", "Email", "Cinsiyet", "Oda Numarası", "Giriş Tarihi"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Verileri doldur
        populateTable(tableModel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);  // Ortala
        frame.setVisible(true);
    }

    // Veritabanından müşteri bilgilerini getirip tabloya doldur
    private void populateTable(DefaultTableModel tableModel) {
        // CustomerDAO'dan verileri çek
        List<Customer> customers = CustomerDAO.getCustomersInHotel();

        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getGender(),
                customer.getRoomNumber(),
                customer.getCheckInDate()
            });
        }
    }

    public static void main(String[] args) {
        new CustomersInHotel();
    }
}
