package otelyonetimi.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import otelyonetimi.dao.InvoiceDAO;
import otelyonetimi.model.Invoice;

public class InvoiceDisplay {
    private JFrame frame;

    public InvoiceDisplay() {
        frame = new JFrame("Fatura Göster");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columns = {"Müşteri ID", "Ad", "Oda Numarası", "Toplam Ücret", "Giriş Tarihi", "Çıkış Tarihi"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable invoiceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(invoiceTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        populateTable(tableModel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateTable(DefaultTableModel tableModel) {
        List<Invoice> invoices = InvoiceDAO.getInvoices();

        if (invoices.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fatura bulunamadı. Çıkış yapmış müşteri yok.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Invoice invoice : invoices) {
            tableModel.addRow(new Object[]{
                    invoice.getCustomerId(),
                    invoice.getCustomerName(),
                    invoice.getRoomNumber(),
                    invoice.getTotalAmount(),
                    invoice.getCheckInDate(),
                    invoice.getCheckOutDate()
            });
        }
    }

    public static void main(String[] args) {
        new InvoiceDisplay();
    }
}
