package otelyonetimi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {

    private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";  // JDBC URL
    private static final String KULLANICI_ADI = "root";  // Veritabanı kullanıcı adı
    private static final String SIFRE = "200578es";  // Veritabanı şifresi

    static {
        try {
            // MySQL JDBC sürücüsünü yükle
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC sürücüsü bulunamadı.");
        }
    }

    // Veritabanına bağlantı sağla
    public static Connection baglantiGetir() throws SQLException {
        try {
            // Bağlantıyı oluştur ve döndür
            return DriverManager.getConnection(URL, KULLANICI_ADI, SIFRE);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Veritabanına bağlanırken hata oluştu: " + e.getMessage());
        }
    }
}