package otelyonetimi.dao;

import otelyonetimi.db.VeritabaniBaglantisi;

import java.sql.*;

public class KullaniciDAO {
    // Kullanıcı doğrulama fonksiyonu
    public static boolean kullaniciDogrula(String email, String sifre) {
        // Veritabanından kullanıcıyı almak için SQL sorgusu
        String sorgu = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            // Eğer kullanıcı bulunursa
            if (resultSet.next()) {
                String dbSifre = resultSet.getString("password");

                // Girilen şifreyi veritabanındaki şifreyle karşılaştır
                if (dbSifre.equals(sifre)) {
                    return true;  // Şifre doğruysa, giriş başarılı
                }
            }
            return false;  // Kullanıcı bulunamadı veya şifre yanlış

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kullanıcı bilgilerini almak için (email'e göre)
    public static String getKullaniciAdi(String email) {
        String sorgu = "SELECT name FROM users WHERE email = ?";
        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement statement = connection.prepareStatement(sorgu)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");  // Kullanıcı adı
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Eğer kullanıcı adı bulunamazsa
    }
}