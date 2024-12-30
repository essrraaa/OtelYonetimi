package otelyonetimi.model;

import java.math.BigDecimal;

public class Invoice {
    private int customerId;
    private String customerName;
    private int roomNumber;
    private BigDecimal totalAmount;
    private String checkInDate;
    private String checkOutDate;

    public Invoice(int customerId, String customerName, int roomNumber, BigDecimal totalAmount, String checkInDate, String checkOutDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.totalAmount = totalAmount;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }
}
