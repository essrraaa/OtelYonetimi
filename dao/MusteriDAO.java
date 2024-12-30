import otelyonetimi.db.VeritabaniBaglantisi;

import java.sql.*;

public class MusteriDAO {

    // Müşteri ekleme işlemi (check-in)
    public static boolean musteriEkle(String ad, String telefon, String email, String cinsiyet, String girisTarihi, int odaId, double fiyatPerGün) {
        String sql = "INSERT INTO customers (name, phone, email, gender, check_in_date, room_id, price_per_night) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection baglanti = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement ps = baglanti.prepareStatement(sql)) {

            ps.setString(1, ad);
            ps.setString(2, telefon);
            ps.setString(3, email);
            ps.setString(4, cinsiyet);
            ps.setString(5, girisTarihi);
            ps.setInt(6, odaId);
            ps.setDouble(7, fiyatPerGün);

            int satirSayisi = ps.executeUpdate();
            return satirSayisi > 0; // Başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Çıkış işlemi (check-out)
    public static boolean musteriCikisYap(int musteriId, String cikisTarihi) {
        String sql = "UPDATE customers SET check_out_date = ? WHERE customer_id = ?";

        try (Connection baglanti = VeritabaniBaglantisi.baglantiGetir();
             PreparedStatement ps = baglanti.prepareStatement(sql)) {

            ps.setString(1, cikisTarihi);
            ps.setInt(2, musteriId);

            int satirSayisi = ps.executeUpdate();
            return satirSayisi > 0; // Başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}