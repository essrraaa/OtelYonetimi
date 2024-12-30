package otelyonetimi.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Calendar;
import otelyonetimi.dao.CustomerDAO;
import otelyonetimi.dao.OdaDAO;
import otelyonetimi.model.Oda;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class CheckIn {

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value instanceof java.util.Calendar) {
                return dateFormatter.format(((java.util.Calendar) value).getTime());
            } else if (value instanceof java.util.Date) {
                return dateFormatter.format(value);
            }
            return "";
        }
    }

    private JFrame frame;

    public CheckIn() {
        frame = new JFrame("Müşteri Girişi Yap");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Erkek", "Kadın"});

        panel.add(createField("İsim:", nameField));
        panel.add(createField("Telefon:", phoneField));
        panel.add(createField("Email:", emailField));
        panel.add(createField("Cinsiyet:", genderComboBox));

        // Tarih seçici ekleniyor
        JDatePickerImpl datePicker = createDatePickerField("Giriş Tarihi:");
        panel.add(createField("Giriş Tarihi:", datePicker));

        // Oda seçimi
        JComboBox<Oda> roomComboBox = new JComboBox<>();
        populateRoomList(roomComboBox);
        panel.add(createField("Oda Seç:", roomComboBox));

        JTextField bedTypeField = new JTextField();
        bedTypeField.setEditable(false);
        panel.add(createField("Yatak Tipi:", bedTypeField));

        JTextField priceField = new JTextField();
        priceField.setEditable(false);
        panel.add(createField("Günlük Fiyat:", priceField));

        // Oda seçiminde detayları doldur
        roomComboBox.addActionListener(e -> {
            Oda selectedRoom = (Oda) roomComboBox.getSelectedItem();
            if (selectedRoom != null) {
                bedTypeField.setText(selectedRoom.getYatakTipi());
                priceField.setText(String.valueOf(selectedRoom.getFiyatPerGün()));
            }
        });

        JButton checkInButton = new JButton("Giriş Yap");
        checkInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkInButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            Oda selectedRoom = (Oda) roomComboBox.getSelectedItem();
            java.util.Date checkInDate = (java.util.Date) datePicker.getModel().getValue();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || selectedRoom == null || checkInDate == null) {
                JOptionPane.showMessageDialog(frame, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tarih kontrolü: Giriş tarihi bugünden önce olamaz
            Date today = removeTime(new Date());
            Date checkInDateWithoutTime = removeTime(checkInDate);

            if (checkInDateWithoutTime.before(today)) {
                JOptionPane.showMessageDialog(frame, "Giriş tarihi bugünden önceki bir tarih olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double pricePerNight = selectedRoom.getFiyatPerGün().doubleValue();
                boolean success = CustomerDAO.addCustomer(name, phone, email, gender, selectedRoom.getOdaId(), checkInDate, pricePerNight);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Müşteri başarıyla kaydedildi.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Müşteri kaydedilirken hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(checkInButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateRoomList(JComboBox<Oda> roomComboBox) {
        List<Oda> rooms = OdaDAO.odalarGetir();
        for (Oda room : rooms) {
            if ("Boş".equals(room.getDurum())) {
                roomComboBox.addItem(room);
            }
        }
    }

    private JPanel createField(String labelText, JComponent field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);
        fieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return fieldPanel;
    }

    private JDatePickerImpl createDatePickerField(String labelText) {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Bugün");
        properties.put("text.month", "Ay");
        properties.put("text.year", "Yıl");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static void main(String[] args) {
        new CheckIn();
    }
}
