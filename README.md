# 🏨 Otel Yönetim Sistemi (Hotel Management System)

Java ile geliştirilmiş, otel operasyonlarını dijitalleştiren ve otel personelinin günlük görevlerini kolaylaştıran kapsamlı bir masaüstü uygulamasıdır. Nesne Yönelimli Programlama (OOP) prensipleri ve Clean Code (Temiz Kod) standartları gözetilerek tasarlanmıştır.

## 🚀 Proje Genel Bakış ve Özellikler

Bu sistem, kullanıcı dostu bir masaüstü arayüzü üzerinden bir otelin tüm temel iş akışlarını yönetir:

* **🛡️ Kullanıcı Girişi:** Personel için yetkilendirilmiş erişim kontrolü sağlayan güvenli bir giriş paneli (`Login.java`).
* **🛌 Oda Yönetimi:** Odaların türlerini, durumlarını (dolu, boş, kirli vb.) ve özelliklerini tanımlama, güncelleme ve takip etme (`RoomManagement.java`, `OdaDAO.java`).
* **Müşteri Yönetimi:**
    * **✅ Check-In:** Yeni müşteri kaydı ve oda atama (`CheckIn.java`, `MusteriDAO.java`).
    * **🛑 Check-Out:** Müşteri çıkış işlemlerini ve faturalandırmayı yönetme (`CheckOut.java`, `InvoiceDAO.java`).
    * **📋 Müşteri Listesi:** Halen otelde konaklayan müşterileri görüntüleme (`CustomersInHotel.java`).
* **📊 Fatura ve Raporlama:** Çıkış yapan müşteriler için otomatik fatura oluşturma ve geçmiş faturaları görüntüleme (`Invoice.java`, `InvoicedDisplay.java`).
* **Veritabanı Entegrasyonu:** Tüm verileri güvenli ve performanslı bir şekilde saklayan veritabanı bağlantı katmanı (`VeritabaniBaglantisi.java`) ve DAO (Data Access Object) deseni.

## 🛠️ Teknik Detaylar ve Kullanılan Teknolojiler

Proje, yazılım dünyasındaki kabul görmüş pratikleri temel alan sağlam bir teknik altyapıya sahiptir:

* **Dil:** Java SE
* **Arayüz (UI):** Java Swing/AWT
* **Mimarisi:** Nesne Yönelimli Tasarım (OOP)
* **Tasarım Desenleri:** DAO (Data Access Object), Model-View-Controller (MVC) benzeri katmanlı yapı.
* **Veritabanı Erişimi:** JDBC (Java Database Connectivity).
* **Veritabanı:** MySQL

## 📋 Bağımlılıklar (Dependencies)

* **JDatePicker (1.3.4):** Tarih seçim işlemleri için harici bir kütüphane (`jdatepicker-1.3.4.jar`) kullanılmıştır.
* **MySQL Connector/J:** Java uygulamasını MySQL veritabanına bağlamak için JDBC sürücüsü.

## 💻 Proje Klasör Yapısı (Brief Structure)

Projenin organize yapısı, sürdürülebilirliği artırmak amacıyla şu şekildedir:

* `.idea`: IntelliJ IDEA proje dosyaları.
* `dao`: Veri Erişimi Katmanı (CustomerDAO, OdaDAO vb.). Veritabanı işlemlerini yönetir.
* `db`: Veritabanı bağlantı ayarları (`VeritabaniBaglantisi.java`).
* `model`: Veri nesneleri (Oda, Musteri, Invoice, Kullanici). Veri yapısını temsil eder.
* `ui`: Kullanıcı Arayüzü Katmanı (Masaüstü panelleri ve pencereleri).
* `OtelUygulamasi.java`: Uygulamanın ana giriş noktası.

## 🚀 Kurulum ve Çalıştırma Talimatları

Projeyi kendi bilgisayarınızda çalıştırmak için:

1.  **Ön Koşullar:** Java JDK ve MySQL bilgisayarınızda kurulu olmalıdır.
2.  **Projeyi İndirme:** Projeyi GitHub'dan indirin veya klonlayın.
3.  **Veritabanı Kurulumu:**
    * MySQL üzerinde yeni bir veritabanı oluşturun.
    * Gerekli tabloları içeren SQL dosyasını (varsa) veritabanınıza import edin.
    * `db/VeritabaniBaglantisi.java` dosyasındaki veritabanı URL, kullanıcı adı ve şifre bilgilerini kendi yerel MySQL ayarlarınıza göre güncelleyin.
4.  **IDE'de Açma:** IntelliJ IDEA gibi bir Java IDE'sinde projeyi açın.
5.  **Bağımlılıklar:** `jdatepicker-1.3.4.jar` ve MySQL Connector JDBC sürücüsünü projenizin kütüphanelerine (libraries) eklediğinizden emin olun.
6.  **Çalıştırma:** `OtelUygulamasi.java` dosyasını run edin.

---
*Geliştirici: Esra Kalay*
