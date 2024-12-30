package otelyonetimi.model;

import java.math.BigDecimal;

public class Oda {
    private int odaId;
    private String odaNumarasi;
    private String yatakTipi;
    private BigDecimal fiyatPerGün;
    private String durum;  // Yeni durum alanı eklendi

    public Oda(int odaId, String odaNumarasi, String yatakTipi, BigDecimal fiyatPerGün, String durum) {
        this.odaId = odaId;
        this.odaNumarasi = odaNumarasi;
        this.yatakTipi = yatakTipi;
        this.fiyatPerGün = fiyatPerGün;
        this.durum = durum;
    }

    // Getter ve Setter metodları
    public int getOdaId() {
        return odaId;
    }

    public void setOdaId(int odaId) {
        this.odaId = odaId;
    }
    @Override
    public String toString() {
        return odaNumarasi;
    }

    public String getOdaNumarasi() {
        return odaNumarasi;
    }

    public void setOdaNumarasi(String odaNumarasi) {
        this.odaNumarasi = odaNumarasi;
    }

    public String getYatakTipi() {
        return yatakTipi;
    }

    public void setYatakTipi(String yatakTipi) {
        this.yatakTipi = yatakTipi;
    }

    public BigDecimal getFiyatPerGün() {
        return fiyatPerGün;
    }

    public void setFiyatPerGün(BigDecimal fiyatPerGün) {
        this.fiyatPerGün = fiyatPerGün;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
