import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SwingTipCalculator {

    public static void main(String[] args) {

        // Create the main window frame
        JFrame frame = new JFrame("Restaurant Tip Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null); // center the window

        // Header label setup
        JLabel header = new JLabel("Restaurant Tip Calculator", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Input fields
        JTextField billAmountField = new JTextField(); // where user types bill
        JTextField customTipField = new JTextField();  // user types custom tip if selected
        customTipField.setEnabled(false); // not needed unless "Custom" is picked

        // Tip percentage options (hardcoded for now)
        JComboBox<String> tipDropdown = new JComboBox<>(new String[]{"10%", "15%", "20%", "Custom"});

        // Spinner for people count (from 1 to 20 people)
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
        JSpinner peopleSpinner = new JSpinner(spinnerModel);

        // Text area for showing results
        JTextArea resultTextArea = new JTextArea(4, 20);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultTextArea.setLineWrap(true); // just in case output is long

        // Buttons
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");

        // Panel to hold form elements
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        inputPanel.add(new JLabel("Bill Amount (₹):"));
        inputPanel.add(billAmountField);
        
        inputPanel.add(new JLabel("Tip Percentage:"));
        inputPanel.add(tipDropdown);
        
        inputPanel.add(new JLabel("Custom Tip (%):"));
        inputPanel.add(customTipField);
        
        inputPanel.add(new JLabel("No. of People:"));
        inputPanel.add(peopleSpinner);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(calculateButton);
        buttonsPanel.add(clearButton);

        // Putting everything together
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(resultTextArea, BorderLayout.CENTER);

        frame.setVisible(true); // show the window

        // Tip dropdown logic
        tipDropdown.addActionListener(e -> {
            String selected = (String) tipDropdown.getSelectedItem();
            if (selected.equals("Custom")) {
                customTipField.setEnabled(true);
            } else {
                customTipField.setText(""); // just clear it in case user switched
                customTipField.setEnabled(false);
            }
        });

        // Calculate button logic
        calculateButton.addActionListener((ActionEvent e) -> {
            try {
                String billText = billAmountField.getText().trim();
                if (billText.equals("")) {
                    resultTextArea.setText("Please enter the bill amount.");
                    return;
                }

                double bill = Double.parseDouble(billText);

                int numPeople = (int) peopleSpinner.getValue(); // how many people splitting

                String selectedTipOption = (String) tipDropdown.getSelectedItem();
                double tipPercentage = 0;

                // Determine tip value
                if (selectedTipOption.equals("Custom")) {
                    String customTipText = customTipField.getText().trim();
                    if (customTipText.equals("")) {
                        resultTextArea.setText("Enter a custom tip percentage.");
                        return;
                    }
                    tipPercentage = Double.parseDouble(customTipText);
                } else {
                    // Remove % sign before converting to number
                    tipPercentage = Double.parseDouble(selectedTipOption.replace("%", ""));
                }

                // Now calculate everything
                double tipValue = bill * (tipPercentage / 100);
                double total = bill + tipValue;
                double perPersonCost = total / numPeople;

                // Show result
                resultTextArea.setText(
                        "Tip: ₹" + String.format("%.2f", tipValue) +
                        "\nTotal Amount: ₹" + String.format("%.2f", total) +
                        "\nPer Person: ₹" + String.format("%.2f", perPersonCost)
                );

            } catch (NumberFormatException nfe) {
                // Handles bad number input
                resultTextArea.setText("⚠️ Please check if the numbers are valid.");
            } catch (Exception ex) {
                // catch-all for weird cases (probably shouldn't be here, but feels safer)
                resultTextArea.setText("Something went wrong. Try again?");
            }
        });

        // Clear button resets everything
        clearButton.addActionListener(e -> {
            billAmountField.setText("");
            tipDropdown.setSelectedIndex(1); // reset to 15%
            customTipField.setText("");
            customTipField.setEnabled(false);
            peopleSpinner.setValue(1);
            resultTextArea.setText("");
        });
    }
}
