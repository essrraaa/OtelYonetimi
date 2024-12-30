package otelyonetimi.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import otelyonetimi.dao.CustomerDAO;
import otelyonetimi.model.Customer;

public class CheckOut {
    private JFrame frame;

    public CheckOut() {
        frame = new JFrame("Müşteri Çıkışı Yap");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Müşteri Seçimi
        panel.add(new JLabel("Müşteri Seç:"));
        JComboBox<Customer> customerComboBox = new JComboBox<>();
        populateCustomerList(customerComboBox);
        panel.add(customerComboBox);

        // Çıkış Tarihi
        panel.add(new JLabel("Çıkış Tarihi:"));
        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Bugün");
        properties.put("text.month", "Ay");
        properties.put("text.year", "Yıl");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        panel.add(datePicker);

        // Çıkış Yap Butonu
        JButton checkOutButton = new JButton("Çıkış Yap");
        panel.add(checkOutButton);

        checkOutButton.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
            if (selectedCustomer == null) {
                JOptionPane.showMessageDialog(frame, "Lütfen bir müşteri seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date selectedDate = null;

            if (datePicker.getModel().getValue() instanceof Calendar) {
                selectedDate = ((Calendar) datePicker.getModel().getValue()).getTime();
            } else if (datePicker.getModel().getValue() instanceof Date) {
                selectedDate = (Date) datePicker.getModel().getValue();
            }

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(frame, "Lütfen bir çıkış tarihi seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Date checkInDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(selectedCustomer.getCheckInDate());
                if (selectedDate.before(checkInDate)) {
                    JOptionPane.showMessageDialog(frame, "Çıkış tarihi giriş tarihinden önce olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Tarih kontrolü sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalAmount = calculateTotalAmount(selectedCustomer.getId(), selectedDate);

            boolean success = CustomerDAO.checkOutCustomer(selectedCustomer.getId(), selectedDate, totalAmount);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Müşteri çıkışı başarıyla yapıldı.");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Çıkış işlemi başarısız oldu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateCustomerList(JComboBox<Customer> customerComboBox) {
        List<Customer> customers = CustomerDAO.getCustomersInHotel();
        customerComboBox.removeAllItems();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }

    private double calculateTotalAmount(int customerId, Date checkOutDate) {
        String query = "SELECT r.price_per_night, c.check_in_date " +
                "FROM customers c " +
                "INNER JOIN rooms r ON c.room_id = r.room_id " +
                "WHERE c.customer_id = ?";

        try (Connection connection = otelyonetimi.db.VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double pricePerNight = resultSet.getDouble("price_per_night");
                    Date checkInDate = resultSet.getDate("check_in_date");

                    long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
                    long diffInDays = (diffInMillies / (1000 * 60 * 60 * 24)) + 1;

                    return pricePerNight * diffInDays;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Hata durumunda 0 döndür
    }

    public static void main(String[] args) {
        new CheckOut();
    }
}
