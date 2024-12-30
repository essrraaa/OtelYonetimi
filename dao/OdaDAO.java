package otelyonetimi.dao;

import otelyonetimi.db.VeritabaniBaglantisi;
import otelyonetimi.model.Oda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdaDAO {

    // Odaları listeleme (durum dönüşümü ile birlikte)
    public static List<Oda> odalarGetir() {
        List<Oda> odalar = new ArrayList<>();
        String sorgu = "SELECT * FROM rooms";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sorgu)) {

            while (resultSet.next()) {
                String durum = resultSet.getString("status");
                // Durumu Türkçeye çevir
                if ("Available".equalsIgnoreCase(durum)) {
                    durum = "Boş";
                } else if ("Booked".equalsIgnoreCase(durum)) {
                    durum = "Dolu";
                }

                Oda oda = new Oda(
                        resultSet.getInt("room_id"),
                        resultSet.getString("room_number"),
                        resultSet.getString("bed_type"),
                        resultSet.getBigDecimal("price_per_night"),
                        durum  // Durum bilgisini ekliyoruz
                );
                odalar.add(oda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return odalar;
    }

    // Oda bilgilerini ID'ye göre almak
    public static Oda odaGetir(int odaId) {
        String sorgu = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setInt(1, odaId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String durum = resultSet.getString("status");
                // Durumu Türkçeye çevir
                if ("Available".equalsIgnoreCase(durum)) {
                    durum = "Boş";
                } else if ("Booked".equalsIgnoreCase(durum)) {
                    durum = "Dolu";
                }

                return new Oda(
                        resultSet.getInt("room_id"),
                        resultSet.getString("room_number"),
                        resultSet.getString("bed_type"),
                        resultSet.getBigDecimal("price_per_night"),
                        durum
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Oda bulunamazsa null döner
    }

    // Oda eklemek
    public static boolean odaEkle(Oda oda) {
        String sorgu = "INSERT INTO rooms (room_number, bed_type, price_per_night, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setString(1, oda.getOdaNumarasi());
            statement.setString(2, oda.getYatakTipi());
            statement.setBigDecimal(3, oda.getFiyatPerGün());
            statement.setString(4, "Available");  // Status: Available olarak varsayalım

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Oda eklenemezse false döner
    }
    public static boolean updateRoomDetails(Oda oda) {
        String sorgu = "UPDATE rooms SET bed_type = ?, price_per_night = ? WHERE room_id = ?";
        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setString(1, oda.getYatakTipi());
            statement.setBigDecimal(2, oda.getFiyatPerGün());
            statement.setInt(3, oda.getOdaId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Oda durumunu güncellemek (Örneğin, boş/dolu)
    public static boolean odaDurumGuncelle(int odaId, String durum) {
        String sorgu = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setString(1, durum);
            statement.setInt(2, odaId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Durum güncellenemezse false döner
    }

    // Oda silme işlemi
    public static boolean odaSil(int odaId) {
        String sorgu = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setInt(1, odaId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Oda silinemezse false döner
    }
}
