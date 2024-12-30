package otelyonetimi.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import otelyonetimi.dao.OdaDAO;
import otelyonetimi.model.Oda;
import java.util.List;

public class RoomManagement {
    private JFrame frame;

    public RoomManagement() {
        // Frame oluştur
        frame = new JFrame("Oda Yönetimi");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        // Oda listesi
        String[] columns = {"Oda ID", "Oda Numarası", "Yatak Tipi", "Fiyat", "Durum"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Güncelleme alanı paneli
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridBagLayout());
        actionPanel.setBorder(BorderFactory.createTitledBorder("Oda Detayları"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Alanlar
        gbc.gridx = 0;
        gbc.gridy = 0;
        actionPanel.add(new JLabel("Oda ID (Seçimden Otomatik Gelir):"), gbc);
        JTextField roomIdField = new JTextField();
        roomIdField.setEditable(false);
        gbc.gridx = 1;
        actionPanel.add(roomIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        actionPanel.add(new JLabel("Yatak Tipi:"), gbc);
        JComboBox<String> bedTypeComboBox = new JComboBox<>(new String[]{"Single", "Double"});
        gbc.gridx = 1;
        actionPanel.add(bedTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        actionPanel.add(new JLabel("Günlük Fiyat:"), gbc);
        JTextField priceField = new JTextField();
        gbc.gridx = 1;
        actionPanel.add(priceField, gbc);

        // Güncelleme butonu
        JButton updateDetailsButton = new JButton("Odayı Güncelle");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        actionPanel.add(updateDetailsButton, gbc);

        panel.add(actionPanel, BorderLayout.EAST);

        // Tablo satır seçme işlemi
        roomTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow != -1) {
                roomIdField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                bedTypeComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Detayları güncelle butonu işlemi
        updateDetailsButton.addActionListener(e -> {
            String roomId = roomIdField.getText();
            String bedType = (String) bedTypeComboBox.getSelectedItem();
            String price = priceField.getText();

            if (!roomId.isEmpty() && !bedType.isEmpty() && !price.isEmpty()) {
                try {
                    Oda oda = new Oda(
                            Integer.parseInt(roomId),
                            null, // Oda numarası değiştirilmez
                            bedType,
                            new java.math.BigDecimal(price),
                            null // Durum değiştirilmez
                    );
                    boolean updated = OdaDAO.updateRoomDetails(oda); // Yeni bir DAO fonksiyonu
                    if (updated) {
                        JOptionPane.showMessageDialog(frame, "Oda başarıyla güncellendi.");
                        refreshRoomList(tableModel); // Listeyi güncelle
                    } else {
                        JOptionPane.showMessageDialog(frame, "Oda güncellenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Geçersiz fiyat formatı.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Oda listelerini güncelleyen fonksiyon
        refreshRoomList(tableModel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Oda listesini güncelleme
    private void refreshRoomList(DefaultTableModel tableModel) {
        List<Oda> roomList = OdaDAO.odalarGetir();
        tableModel.setRowCount(0); // Mevcut tabloyu temizle

        for (Oda oda : roomList) {
            tableModel.addRow(new Object[]{
                    oda.getOdaId(),
                    oda.getOdaNumarasi(),
                    oda.getYatakTipi(),
                    oda.getFiyatPerGün(),
                    oda.getDurum() // Durum doğrudan "Boş" veya "Dolu" olarak alınır
            });
        }
    }

    public static void main(String[] args) {
        new RoomManagement();
    }
}
