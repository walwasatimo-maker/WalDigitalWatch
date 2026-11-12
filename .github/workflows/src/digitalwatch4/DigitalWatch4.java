package digitalwatch4;

// IMPORT PACKAGES
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DigitalWatch4 extends JFrame {

    // VARIABLES / FIELDS
private final JLabel timeLabel;
private final JLabel dateLabel;
private final JButton settingsButton;

    // Default settings
private boolean use24Hour = false;
private Color timeColor = new Color(0, 205, 255);
private Color dateColor = new Color(60, 255, 90);
private Color backgroundColor = Color.BLACK;

    // TIME FORMATS
private final DateTimeFormatter format12 =
DateTimeFormatter.ofPattern("hh:mm:ss a");

    private final DateTimeFormatter format24 =
DateTimeFormatter.ofPattern("HH:mm:ss");

    private final DateTimeFormatter dateFormat =
DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy");

    // CONSTRUCTOR
public DigitalWatch4() {

        // WINDOW SETTINGS
setTitle("JAVA DIGITAL WATCH");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setSize(540, 300);
setLocationRelativeTo(null);
setResizable(false);

        // MAIN PANEL
JPanel panel = new JPanel();
panel.setBackground(backgroundColor);
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBorder(
BorderFactory.createEmptyBorder(20, 20, 20, 20)
);

        // SETTINGS BUTTON
settingsButton = new JButton("Settings");
settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TIME LABEL
timeLabel = new JLabel("", SwingConstants.CENTER);
timeLabel.setFont(new Font("Consolas", Font.BOLD, 55));
timeLabel.setForeground(timeColor);
timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // DATE LABEL
dateLabel = new JLabel("", SwingConstants.CENTER);
dateLabel.setFont(new Font("Consolas", Font.BOLD, 19));
dateLabel.setForeground(dateColor);
dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ADD COMPONENTS TO PANEL
panel.add(settingsButton);
panel.add(Box.createVerticalStrut(20));
panel.add(timeLabel);
panel.add(Box.createVerticalStrut(15));
panel.add(dateLabel);

        // ADD PANEL TO WINDOW
add(panel);

        // MAKE SETTINGS BUTTON FUNCTIONAL
settingsButton.addActionListener(e -> openSettings(panel));

        // DISPLAY INITIAL TIME
updateClock();

        // UPDATE CLOCK EVERY SECOND
Timer timer = new Timer(1000, e -> updateClock());
timer.start();
}

    // METHOD TO UPDATE TIME AND DATE
private void updateClock() {

        LocalDateTime now = LocalDateTime.now();

        if (use24Hour) {
timeLabel.setText(now.format(format24));
} else {
timeLabel.setText(now.format(format12));
}

        dateLabel.setText(now.format(dateFormat));
}

    // METHOD TO OPEN SETTINGS WINDOW
private void openSettings(JPanel mainPanel) {

        // CREATE SETTINGS DIALOG
JDialog dialog = new JDialog(this, "Clock Settings", true);
dialog.setSize(380, 330);
dialog.setLocationRelativeTo(this);
dialog.setResizable(false);

        JPanel settingsPanel = new JPanel();
settingsPanel.setLayout(new GridLayout(0, 2, 10, 15));
settingsPanel.setBorder(
BorderFactory.createEmptyBorder(20, 20, 20, 20)
);

        // TIME FORMAT OPTIONS
JLabel formatLabel = new JLabel("Time Format:");

        String[] formats = {"12-hour (AM/PM)", "24-hour"};
JComboBox<String> formatBox = new JComboBox<>(formats);

        formatBox.setSelectedIndex(use24Hour ? 1 : 0);

        // TIME COLOR OPTIONS
JLabel timeColorLabel = new JLabel("Time Color:");

        String[] colors = {"Cyan", "White", "Yellow", "Red", "Green"};
JComboBox<String> timeColorBox = new JComboBox<>(colors);

        timeColorBox.setSelectedItem(getColorName(timeColor));

        // DATE COLOR OPTIONS
JLabel dateColorLabel = new JLabel("Date Color:");

        JComboBox<String> dateColorBox = new JComboBox<>(colors);
dateColorBox.setSelectedItem(getColorName(dateColor));

        // BACKGROUND COLOR OPTIONS
JLabel backgroundLabel = new JLabel("Background:");

        String[] backgrounds = {"Black", "Dark Gray", "White", "Blue"};
JComboBox<String> backgroundBox =
new JComboBox<>(backgrounds);

        backgroundBox.setSelectedItem(
getBackgroundName(backgroundColor)
);

        // ADD SETTINGS COMPONENTS
settingsPanel.add(formatLabel);
settingsPanel.add(formatBox);

        settingsPanel.add(timeColorLabel);
settingsPanel.add(timeColorBox);

        settingsPanel.add(dateColorLabel);
settingsPanel.add(dateColorBox);

        settingsPanel.add(backgroundLabel);
settingsPanel.add(backgroundBox);

        // APPLY BUTTON
JButton applyButton = new JButton("Apply Settings");

        // RESET BUTTON
JButton resetButton = new JButton("Reset Defaults");

        JPanel buttonPanel = new JPanel();
buttonPanel.add(applyButton);
buttonPanel.add(resetButton);

        // APPLY SELECTED SETTINGS
applyButton.addActionListener(e -> {

            use24Hour = formatBox.getSelectedIndex() == 1;

            timeColor = getColor(
(String) timeColorBox.getSelectedItem()
);

            dateColor = getColor(
(String) dateColorBox.getSelectedItem()
);

            backgroundColor = getBackgroundColor(
(String) backgroundBox.getSelectedItem()
);

            // UPDATE THE MAIN WINDOW
timeLabel.setForeground(timeColor);
dateLabel.setForeground(dateColor);
mainPanel.setBackground(backgroundColor);

            updateClock();

            // CLOSE SETTINGS WINDOW
dialog.dispose();
});

        // RESTORE DEFAULT SETTINGS
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

        // ARRANGE SETTINGS WINDOW
dialog.setLayout(new BorderLayout());
dialog.add(settingsPanel, BorderLayout.CENTER);
dialog.add(buttonPanel, BorderLayout.SOUTH);

        // DISPLAY SETTINGS
dialog.setVisible(true);
}

    // METHOD TO CONVERT COLOR NAME INTO A COLOR OBJECT
private Color getColor(String name) {

        switch (name) {
case "White":
return Color.WHITE;
case "Yellow":
return Color.YELLOW;
case "Red":
return Color.RED;
case "Green":
return Color.GREEN;
default:
return new Color(0, 205, 255);
}
}

    // METHOD TO GET THE CURRENT TIME COLOR NAME
private String getColorName(Color color) {

        if (color.equals(Color.WHITE)) return "White";
if (color.equals(Color.YELLOW)) return "Yellow";
if (color.equals(Color.RED)) return "Red";
if (color.equals(Color.GREEN)) return "Green";

        return "Cyan";
}

    // METHOD TO CONVERT BACKGROUND NAME INTO A COLOR OBJECT
private Color getBackgroundColor(String name) {

        switch (name) {
case "Dark Gray":
return Color.DARK_GRAY;
case "White":
return Color.WHITE;
case "Blue":
return Color.BLUE;
default:
return Color.BLACK;
}
}

    // METHOD TO GET THE CURRENT BACKGROUND NAME
private String getBackgroundName(Color color) {

        if (color.equals(Color.DARK_GRAY)) return "Dark Gray";
if (color.equals(Color.WHITE)) return "White";
if (color.equals(Color.BLUE)) return "Blue";

        return "Black";
}

    // MAIN METHOD
public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DigitalWatch4 watch = new DigitalWatch4();
watch.setVisible(true);

        });
}
}
