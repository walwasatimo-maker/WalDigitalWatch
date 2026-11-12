package digitalwatch4;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DigitalWatch4 extends JFrame {

    private final JLabel timeLabel;
    private final JLabel dateLabel;
    private final JButton settingsButton;

    private boolean use24Hour = false;
    private Color timeColor = new Color(0, 205, 255);
    private Color dateColor = new Color(60, 255, 90);
    private Color backgroundColor = Color.BLACK;

    private final DateTimeFormatter format12 = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private final DateTimeFormatter format24 = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy");

    public DigitalWatch4() {
        setTitle("JAVA DIGITAL WATCH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(540, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Consolas", Font.BOLD, 55));
        timeLabel.setForeground(timeColor);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dateLabel = new JLabel("", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Consolas", Font.BOLD, 19));
        dateLabel.setForeground(dateColor);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(settingsButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(timeLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(dateLabel);

        add(panel);
        settingsButton.addActionListener(e -> openSettings(panel));
        updateClock();

        new Timer(1000, e -> updateClock()).start();
    }

    private void updateClock() {
        LocalDateTime now = LocalDateTime.now();
        timeLabel.setText(use24Hour ? now.format(format24) : now.format(format12));
        dateLabel.setText(now.format(dateFormat));
    }

    private void openSettings(JPanel mainPanel) {
        JDialog dialog = new JDialog(this, "Clock Settings", true);
        dialog.setSize(380, 330);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel settingsPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] colors = {"Cyan", "White", "Yellow", "Red", "Green"};
        String[] backgrounds = {"Black", "Dark Gray", "White", "Blue"};

        JComboBox<String> formatBox = new JComboBox<>(new String[]{"12-hour (AM/PM)", "24-hour"});
        formatBox.setSelectedIndex(use24Hour ? 1 : 0);

        JComboBox<String> timeColorBox = new JComboBox<>(colors);
        timeColorBox.setSelectedItem(getColorName(timeColor));

        JComboBox<String> dateColorBox = new JComboBox<>(colors);
        dateColorBox.setSelectedItem(getColorName(dateColor));

        JComboBox<String> backgroundBox = new JComboBox<>(backgrounds);
        backgroundBox.setSelectedItem(getBackgroundName(backgroundColor));

        settingsPanel.add(new JLabel("Time Format:"));
        settingsPanel.add(formatBox);
        settingsPanel.add(new JLabel("Time Color:"));
        settingsPanel.add(timeColorBox);
        settingsPanel.add(new JLabel("Date Color:"));
        settingsPanel.add(dateColorBox);
        settingsPanel.add(new JLabel("Background:"));
        settingsPanel.add(backgroundBox);

        JButton applyButton = new JButton("Apply Settings");
        JButton resetButton = new JButton("Reset Defaults");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyButton);
        buttonPanel.add(resetButton);

        applyButton.addActionListener(e -> {
            use24Hour = formatBox.getSelectedIndex() == 1;
            timeColor = getColor((String) timeColorBox.getSelectedItem());
            dateColor = getColor((String) dateColorBox.getSelectedItem());
            backgroundColor = getBackgroundColor((String) backgroundBox.getSelectedItem());
            timeLabel.setForeground(timeColor);
            dateLabel.setForeground(dateColor);
            mainPanel.setBackground(backgroundColor);
            updateClock();
            dialog.dispose();
        });

        resetButton.addActionListener(e -> {
            use24Hour = false;
            timeColor = new Color(0, 205, 255);
            dateColor = new Color(60, 255, 90);
            backgroundColor = Color.BLACK;
            timeLabel.setForeground(timeColor);
            dateLabel.setForeground(dateColor);
            mainPanel.setBackground(backgroundColor);
            updateClock();
            dialog.dispose();
        });

        dialog.setLayout(new BorderLayout());
        dialog.add(settingsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private Color getColor(String name) {
        return switch (name) {
            case "White" -> Color.WHITE;
            case "Yellow" -> Color.YELLOW;
            case "Red" -> Color.RED;
            case "Green" -> Color.GREEN;
            default -> new Color(0, 205, 255);
        };
    }

    private String getColorName(Color color) {
        if (color.equals(Color.WHITE)) return "White";
        if (color.equals(Color.YELLOW)) return "Yellow";
        if (color.equals(Color.RED)) return "Red";
        if (color.equals(Color.GREEN)) return "Green";
        return "Cyan";
    }

    private Color getBackgroundColor(String name) {
        return switch (name) {
            case "Dark Gray" -> Color.DARK_GRAY;
            case "White" -> Color.WHITE;
            case "Blue" -> Color.BLUE;
            default -> Color.BLACK;
        };
    }

    private String getBackgroundName(Color color) {
        if (color.equals(Color.DARK_GRAY)) return "Dark Gray";
        if (color.equals(Color.WHITE)) return "White";
        if (color.equals(Color.BLUE)) return "Blue";
        return "Black";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DigitalWatch4().setVisible(true));
    }
}
