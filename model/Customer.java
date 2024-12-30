package otelyonetimi.model;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String gender;  // Cinsiyet bilgisi
    private int roomNumber; // Oda numarası
    private String checkInDate; // Giriş tarihi

    // Constructor
    public Customer(int id, String name, String phone, String email, String gender, int roomNumber, String checkInDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
    }

    // Getter metodları
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}
