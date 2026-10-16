package main.java.solution;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PuzzleSolverInterface {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Puzzle Solver");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 250);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Оберіть файл:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton uploadButton = new JButton("Вибрати файл (.txt)");
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel();
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton copyButton = new JButton("Скопіювати");
        copyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyButton.setEnabled(false);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстові файли (*.txt)", "txt");
                fileChooser.setFileFilter(filter);

                statusLabel.setText("Це займе декілька секунд...");

                int response = fileChooser.showOpenDialog(frame);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    statusLabel.setVisible(false);
                    statusLabel.setText(PuzzleSolver.solvePuzzles(selectedFile.getAbsolutePath()));
                    statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    statusLabel.setVisible(true);

                    copyButton.setEnabled(true);
                } else {
                    statusLabel.setText("Вибір скасовано");
                    statusLabel.setForeground(Color.RED);
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!statusLabel.getText().isEmpty()) {
                    StringSelection stringSelection = new StringSelection(statusLabel.getText());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                }
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(uploadButton);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(copyButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}