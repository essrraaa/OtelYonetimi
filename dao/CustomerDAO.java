package otelyonetimi.dao;

import otelyonetimi.db.VeritabaniBaglantisi;
import otelyonetimi.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CustomerDAO {

    // Otelde kalan müşterileri getir
    // Otelde kalan müşterileri getir
    public static List<Customer> getCustomersInHotel() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT c.customer_id, c.name, c.phone, c.email, c.gender, r.room_number, c.check_in_date " +
                "FROM customers c " +
                "INNER JOIN rooms r ON c.room_id = r.room_id " +
                "WHERE r.status = 'Booked' AND c.check_out_date IS NULL"; // Sadece çıkış yapmamış müşteriler

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("customer_id"),                // Müşteri ID
                        resultSet.getString("name"),                   // Ad
                        resultSet.getString("phone"),                  // Telefon
                        resultSet.getString("email"),                  // Email
                        resultSet.getString("gender"),                 // Cinsiyet
                        resultSet.getInt("room_number"),               // Oda Numarası
                        resultSet.getDate("check_in_date").toString()  // Giriş Tarihi
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


    // Yeni bir müşteri ekle ve oda durumunu güncelle
    public static boolean addCustomer(String name, String phone, String email, String gender, int roomId, Date checkInDate, double pricePerNight) {
        String insertCustomerQuery = "INSERT INTO customers (name, phone, email, gender, room_id, check_in_date, price_per_night) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateRoomQuery = "UPDATE rooms SET status = 'Booked' WHERE room_id = ?";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir()) {
            connection.setAutoCommit(false); // Transaction başlat

            try (PreparedStatement customerStatement = connection.prepareStatement(insertCustomerQuery);
                 PreparedStatement roomStatement = connection.prepareStatement(updateRoomQuery)) {

                // Müşteri bilgilerini ekle
                customerStatement.setString(1, name);
                customerStatement.setString(2, phone);
                customerStatement.setString(3, email);
                customerStatement.setString(4, gender);
                customerStatement.setInt(5, roomId);
                customerStatement.setDate(6, new java.sql.Date(checkInDate.getTime()));
                customerStatement.setDouble(7, pricePerNight); // price_per_night alanını ekliyoruz
                customerStatement.executeUpdate();

                // Oda durumunu güncelle
                roomStatement.setInt(1, roomId);
                roomStatement.executeUpdate();

                connection.commit(); // İşlemi tamamla
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Hata durumunda işlemi geri al
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // İşlem başarısız oldu
    }

    // Çıkış işlemini yap ve faturayı kaydet
    public static boolean checkOutCustomer(int customerId, Date checkOutDate, double totalAmount) {
        String updateCustomerQuery = "UPDATE customers SET check_out_date = ? WHERE customer_id = ?";
        String insertCheckoutQuery = "INSERT INTO checkouts (customer_id, check_out_date, total_amount) VALUES (?, ?, ?)";
        String updateRoomQuery = "UPDATE rooms SET status = 'Available' WHERE room_id = (SELECT room_id FROM customers WHERE customer_id = ?)";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir()) {
            connection.setAutoCommit(false); // Transaction başlat

            try (PreparedStatement customerStatement = connection.prepareStatement(updateCustomerQuery);
                 PreparedStatement checkoutStatement = connection.prepareStatement(insertCheckoutQuery);
                 PreparedStatement roomStatement = connection.prepareStatement(updateRoomQuery)) {

                // Müşteri tablosunu güncelle
                customerStatement.setDate(1, new java.sql.Date(checkOutDate.getTime())); // Çıkış tarihi
                customerStatement.setInt(2, customerId);
                customerStatement.executeUpdate();

                // Checkouts tablosuna kayıt ekle
                checkoutStatement.setInt(1, customerId);
                checkoutStatement.setDate(2, new java.sql.Date(checkOutDate.getTime()));
                checkoutStatement.setDouble(3, totalAmount);
                checkoutStatement.executeUpdate();

                // Oda durumunu güncelle
                roomStatement.setInt(1, customerId);
                roomStatement.executeUpdate();

                connection.commit(); // İşlemi tamamla
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Hata durumunda işlemi geri al
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // İşlem başarısız oldu
    }
}
